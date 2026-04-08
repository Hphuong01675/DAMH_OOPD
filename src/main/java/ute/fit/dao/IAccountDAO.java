package ute.fit.dao;

import ute.fit.entity.AccountEntity;
import ute.fit.model.Roles;

import java.util.List;
import java.util.Set;

public interface IAccountDAO {
    AccountEntity findActiveAccountForLogin(String identifier, Roles role);
    AccountEntity findActiveAccountByUsernameAndRole(String username, Roles role);
    List<AccountEntity> findActiveAccountsByRoles(Set<Roles> roles);
}
