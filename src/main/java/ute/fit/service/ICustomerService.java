package ute.fit.service;

import java.util.Map;

import ute.fit.entity.CustomerEntity;

public interface ICustomerService {
    // Tìm khách hàng theo SĐT, nếu không có và có tên thì tạo mới
	Map<String, Object> getOrCreateCustomer(String phone, String name);
}