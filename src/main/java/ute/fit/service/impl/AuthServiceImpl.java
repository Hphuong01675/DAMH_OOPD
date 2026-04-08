package ute.fit.service.impl;

import ute.fit.dao.IAccountDAO;
import ute.fit.dao.impl.AccountDAOImpl;
import ute.fit.entity.AccountEntity;
import ute.fit.entity.BaristaEntity;
import ute.fit.entity.StaffEntity;
import ute.fit.model.Roles;
import ute.fit.model.UserDTO;
import ute.fit.service.IAuthService;

public class AuthServiceImpl implements IAuthService {
    private final IAccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public UserDTO login(String identifier, String password, String roleStr) {
        Roles role = mapRole(roleStr);
        if (role == null) return null;

        // 1. Gọi DAO lấy Account
        AccountEntity account = accountDAO.findActiveAccount(identifier, role);
        
        // 2. Kiểm tra mật khẩu
        if (account == null || !account.getPassword().equals(password)) {
            return null;
        }

        // 3. Khởi tạo DTO và điền dữ liệu qua phương thức hỗ trợ
        UserDTO dto = new UserDTO();
        dto.setRole(account.getRole().name());

        if (role == Roles.Admin) {
            dto.setFullName("System Administrator");
            dto.setId(0L);
        } else {
            fillUserData(dto, account.getUsername(), role);
        }

        return (dto.getId() != null) ? dto : null;
    }

    private void fillUserData(UserDTO dto, String username, Roles role) {
        if (role == Roles.Staff) {
            StaffEntity staff = accountDAO.findStaffByUsername(username);
            if (staff != null) {
                dto.setId(staff.getId());
                dto.setFullName(staff.getName());
                dto.setPhone(staff.getPhoneNumber());
            }
        } else if (role == Roles.Barista) {
            BaristaEntity barista = accountDAO.findBaristaByUsername(username);
            if (barista != null) {
                dto.setId(barista.getId());
                dto.setFullName(barista.getName());
                dto.setPhone(barista.getPhoneNumber());
            }
        }
    }
    
    private Roles mapRole(String roleStr) {
        if (roleStr == null || roleStr.isBlank()) {
            return null;
        }
        
        for (Roles role : Roles.values()) {
            if (role.name().equalsIgnoreCase(roleStr.trim())) {
                return role;
            }
        }
        return null; // Trả về null thay vì văng Exception để Service dễ xử lý
    }
}
