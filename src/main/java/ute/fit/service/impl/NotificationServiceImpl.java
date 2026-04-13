package ute.fit.service.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import ute.fit.dao.impl.NotificationDAOImpl;
import ute.fit.dao.INotificationDAO;
import ute.fit.model.*;
import ute.fit.service.INotificationService;
import ute.fit.service.NotificationManager;
import ute.fit.mapper.NotificationMapper;

public class NotificationServiceImpl implements INotificationService {
    private final NotificationManager manager = NotificationManager.getInstance();
    private final INotificationDAO notificationDao = new NotificationDAOImpl();
    
    @Override
    public void broadcastToRole(String content, String groupRole, UserDTO currentUser) {
        // 1. Check quyền Admin
        if (currentUser == null || !Roles.Admin.name().equalsIgnoreCase(currentUser.getRole())) {
            throw new IllegalStateException("Chỉ Admin mới được gửi.");
        }

        Set<Roles> targetRoles = parseRoles(groupRole);
        if (targetRoles.isEmpty()) return;

        // 2. LẤY DANH SÁCH NHỮNG NGƯỜI ĐANG TRONG CA (Đã addObserver)
        List<AccountObserver> activeObservers = manager.getObserversByRoles(targetRoles);
        if (activeObservers.isEmpty()) {
            return; // Không có ai trong ca thì không cần gửi/lưu
        }

        // 3. TRÍCH XUẤT USERNAME VÀ LƯU VÀO DB (Chỉ cho người đang trong ca)
        List<String> activeUsernames = activeObservers.stream()
                .map(this::extractUsername)
                .filter(uname -> uname != null)
                .collect(Collectors.toList());

        if (!activeUsernames.isEmpty()) {
            notificationDao.insertForSpecificUsers(content, activeUsernames);
        }

        // 4. THÔNG BÁO REAL-TIME
        Notification model = new Notification();
        model.setContent(content);
        model.makeNewNotification();
        manager.notifyByRoles(model, targetRoles);
    }

    private String extractUsername(AccountObserver observer) {
        if (observer instanceof ute.fit.model.Account account) {
            return account.getUsername();
        }
        if (observer instanceof UserDTO userDTO) {
            return userDTO.getUsername();
        }
        if (observer instanceof ute.fit.entity.AccountEntity accountEntity) {
            return accountEntity.getUsername();
        }
        return null;
    }
    
    @Override
    public List<Notification> getNotificationsForUser(String username) {
        return notificationDao.findByReceiver(username).stream()
                .map(NotificationMapper::toDTO)
                .collect(Collectors.toList());
    }

    private Set<Roles> parseRoles(String group) {
        Set<Roles> roles = new LinkedHashSet<>();
        if (group == null) return roles;
        if (group.contains("All")) {
            roles.add(Roles.Staff); roles.add(Roles.Barista);
        } else {
            if (group.contains("Staff")) roles.add(Roles.Staff);
            if (group.contains("Barista")) roles.add(Roles.Barista);
        }
        return roles;
    }
}