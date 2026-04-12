package ute.fit.dao;

import ute.fit.entity.CustomerEntity;

public interface ICustomerDAO {
    CustomerEntity findByPhone(String phone);
    CustomerEntity findById(Long id);
    CustomerEntity save(CustomerEntity customer);
    void update(CustomerEntity customer);
}
