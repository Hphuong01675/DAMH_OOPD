package ute.fit.dao;

import ute.fit.entity.CustomerEntity;

public interface ICustomerDAO {
    CustomerEntity findByPhone(String phone);
    CustomerEntity save(CustomerEntity customer);
}