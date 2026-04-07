package ute.fit.service.impl;

import ute.fit.dao.IAccountDAO;
import ute.fit.dao.impl.AccountDAOImpl;
import ute.fit.entity.AccountEntity;
import ute.fit.model.Roles;
import ute.fit.service.IAuthService;

public class AuthServiceImpl implements IAuthService {
    private final IAccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public AccountEntity login(String identifier, String password, String role) {
        if (isBlank(identifier) || isBlank(password) || isBlank(role)) {
            return null;
        }

        Roles mappedRole = mapRole(role);
        if (mappedRole == null) {
            return null;
        }

        AccountEntity account = accountDAO.findActiveAccountForLogin(identifier, mappedRole);
        if (account == null) {
            return null;
        }

        return password.equals(account.getPassword()) ? account : null;
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
