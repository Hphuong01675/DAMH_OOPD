package ute.fit.service;

import ute.fit.entity.CustomerEntity;

public interface ICustomerService {
    // Tìm khách hàng theo SĐT, nếu không có và có tên thì tạo mới
    CustomerEntity getOrCreateCustomer(String phone, String name);
}