package ute.fit.service.impl;

import ute.fit.dao.IAccountDAO;
import ute.fit.dao.impl.AccountDAOImpl;
import ute.fit.entity.AccountEntity;
import ute.fit.model.Roles;
import ute.fit.model.UserDTO;
import ute.fit.service.IAuthService;

public class AuthServiceImpl implements IAuthService {
    private final IAccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public UserDTO login(String identifier, String password, String roleStr) {
        Roles role = Roles.valueOf(roleStr.substring(0, 1).toUpperCase() + roleStr.substring(1).toLowerCase());
        
        // Nhận Entity từ DAO
        AccountEntity account = accountDAO.findActiveAccountForLogin(identifier, role);

        // Kiểm tra tài khoản và mật khẩu
        if (account != null && account.getPassword().equals(password)) {
            // Mapping sang DTO ngay tại Service
            UserDTO dto = new UserDTO();
            dto.setId(account.getPerson() != null ? account.getPerson().getId() : null);
            dto.setUsername(account.getUsername());
            dto.setFullName(account.getPerson() != null ? account.getPerson().getName() : account.getUsername());
            dto.setEmail(null); // Bạn có thể bổ sung logic lấy email từ StaffEntity/BaristaEntity nếu cần
            dto.setRole(account.getRole().name());
            dto.setPhone(account.getPerson() != null ? account.getPerson().getPhoneNumber() : null);
            return dto;
        }
        return null;
    }

    private Roles mapRole(String role) {
        if ("admin".equalsIgnoreCase(role)) {
            return Roles.Admin;
        }
        if ("staff".equalsIgnoreCase(role)) {
            return Roles.Staff;
        }
        if ("barista".equalsIgnoreCase(role)) {
            return Roles.Barista;
        }
        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
