package ute.fit.dao;

import ute.fit.entity.AccountEntity;
import ute.fit.model.Roles;

public interface IAccountDAO {
    AccountEntity findActiveAccountForLogin(String identifier, Roles role);
}
