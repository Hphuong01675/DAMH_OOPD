package ute.fit.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ute.fit.dao.IAccountDAO;
import ute.fit.dao.impl.AccountDAOImpl;
import ute.fit.dao.impl.NotificationDaoImpl;
import ute.fit.entity.AccountEntity;
import ute.fit.entity.NotificationEntity;
import ute.fit.model.Account;
import ute.fit.model.Notification;
import ute.fit.model.Roles;
import ute.fit.model.UserDTO;

public class NotificationService {
    private final NotificationManager manager = NotificationManager.getInstance();
    private final NotificationDaoImpl notificationDao = new NotificationDaoImpl();
    private final IAccountDAO accountDAO = new AccountDAOImpl();

    public int broadcastToRole(String content, String groupRole, UserDTO currentUser) {
        resolveAdminAccount(currentUser);

        Set<Roles> targetRoles = parseRoles(groupRole);
        if (targetRoles.isEmpty()) {
            throw new IllegalArgumentException("Nhom nhan thong bao khong hop le.");
        }

        List<AccountEntity> recipients = accountDAO.findActiveAccountsByRoles(targetRoles);
        if (recipients.isEmpty()) {
            throw new IllegalStateException("Khong tim thay tai khoan nao thuoc nhom nhan thong bao.");
        }

        Notification model = new Notification();
        model.setContent(content);
        model.makeNewNotification();

        for (AccountEntity recipient : recipients) {
            NotificationEntity entity = new NotificationEntity();
            entity.setContent(model.getContent());
            entity.setSentDate(model.getSentDate());
            entity.setReceiver(recipient);
            notificationDao.insert(entity);
        }

        manager.replaceObservers(mapToObservers(recipients));
        manager.notifyByRoles(model, targetRoles);
        return recipients.size();
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

    private List<Account> mapToObservers(List<AccountEntity> recipients) {
        return recipients.stream()
                .map(account -> new Account(
                        account.getUsername(),
                        account.getPassword(),
                        account.isState(),
                        account.getRole(),
                        null))
                .toList();
    }
}
