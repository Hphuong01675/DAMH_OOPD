package ute.fit.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ute.fit.dao.IAccountDAO;
import ute.fit.dao.INotificationDAO;
import ute.fit.dao.impl.AccountDAOImpl;
import ute.fit.dao.impl.NotificationDAOImpl;
import ute.fit.model.AccountObserver;
import ute.fit.entity.AccountEntity;
import ute.fit.entity.NotificationEntity;
import ute.fit.model.Notification;
import ute.fit.model.Roles;
import ute.fit.model.UserDTO;
import ute.fit.service.INotificationService;
import ute.fit.service.NotificationManager;

public class NotificationServiceImpl implements INotificationService {
    private final NotificationManager manager = NotificationManager.getInstance();
    private final INotificationDAO notificationDao = new NotificationDAOImpl();
    private final IAccountDAO accountDAO = new AccountDAOImpl();

    public int broadcastToRole(String content, String groupRole, UserDTO currentUser) {
        resolveAdminAccount(currentUser);

        Set<Roles> targetRoles = parseRoles(groupRole);
        if (targetRoles.isEmpty()) {
            throw new IllegalArgumentException("Nhom nhan thong bao khong hop le.");
        }

        List<AccountObserver> recipients = manager.getObserversByRoles(targetRoles);
        if (recipients.isEmpty()) {
            throw new IllegalStateException("Khong co tai khoan nao dang trong ca thuoc nhom nhan thong bao.");
        }

        Notification model = new Notification();
        model.setContent(content);
        model.makeNewNotification();

        for (AccountObserver recipient : recipients) {
            AccountEntity account = accountDAO.findActiveAccountByUsernameAndRole(
                    extractUsername(recipient), recipient.getRole());
            if (account == null) {
                continue;
            }

            NotificationEntity entity = new NotificationEntity();
            entity.setContent(model.getContent());
            entity.setSentDate(model.getSentDate());
            notificationDao.insert(entity);
        }

        manager.notifyByRoles(model, targetRoles);
        return recipients.size();
    }

    public List<NotificationEntity> getNotificationsForUser(String username) {
        return notificationDao.findByReceiver(username);
    }

    private AccountEntity resolveAdminAccount(UserDTO currentUser) {
        if (currentUser == null || currentUser.getUsername() == null || currentUser.getRole() == null) {
            throw new IllegalStateException("Khong tim thay thong tin admin trong session.");
        }

        Roles role = Roles.valueOf(currentUser.getRole());
        if (role != Roles.Admin) {
            throw new IllegalStateException("Chi admin moi duoc phep gui thong bao.");
        }

        AccountEntity adminAccount = accountDAO.findActiveAccountByUsernameAndRole(currentUser.getUsername(), role);
        if (adminAccount == null) {
            throw new IllegalStateException("Khong tim thay tai khoan admin hop le.");
        }
        return adminAccount;
    }

    private Set<Roles> parseRoles(String group) {
        Set<Roles> roles = new LinkedHashSet<>();
        if (group == null) {
            return roles;
        }
        if (group.contains("All")) {
            roles.add(Roles.Staff);
            roles.add(Roles.Barista);
            return roles;
        }
        if (group.contains("Staff")) {
            roles.add(Roles.Staff);
        }
        if (group.contains("Barista")) {
            roles.add(Roles.Barista);
        }
        return roles;
    }

    private String extractUsername(AccountObserver observer) {
        if (observer instanceof ute.fit.model.Account account) {
            return account.getUsername();
        }
        throw new IllegalStateException("Khong doc duoc username tu observer.");
    }
}
