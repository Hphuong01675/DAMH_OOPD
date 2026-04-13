package ute.fit.dao;

import ute.fit.entity.AccountEntity;
import ute.fit.entity.BaristaEntity;
import ute.fit.entity.StaffEntity;
import ute.fit.model.Roles;

public interface IAccountDAO {
    // Tìm thông tin Staff theo username của Account
    StaffEntity findStaffByUsername(String username);
    
    // Tìm thông tin Barista theo username của Account
    BaristaEntity findBaristaByUsername(String username);
    
    // Tìm tài khoản đang hoạt động theo username và role
    AccountEntity findActiveAccountByUsernameAndRole(String username, Roles role);
}
