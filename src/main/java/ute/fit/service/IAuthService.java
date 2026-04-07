package ute.fit.service;

import ute.fit.entity.AccountEntity;

public interface IAuthService {
    AccountEntity login(String identifier, String password, String role);
}
