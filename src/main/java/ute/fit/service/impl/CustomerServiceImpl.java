package ute.fit.service.impl;

import java.util.HashMap;
import java.util.Map;

import ute.fit.dao.ICustomerDAO;
import ute.fit.dao.impl.CustomerDAOImpl;
import ute.fit.entity.CustomerEntity;
import ute.fit.service.ICustomerService;

public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public String findCustomerJson(String phone) {
        if (phone == null || phone.trim().isEmpty()) return null;
        CustomerEntity customer = customerDAO.findByPhone(phone);
        if (customer == null) return null;
        
        return formatCustomerToJson(customer);
    }

    @Override
    public String createCustomerJson(String phone, String name) {
        if (phone == null || name == null || name.trim().isEmpty()) {
            return null;
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setName(name);
        customer.setPhoneNumber(phone);
        customer.setLoyaltyPoints(0);
        
        customer = customerDAO.save(customer);
        
        if (customer == null) return null;
        return formatCustomerToJson(customer);
    }

    // BỔ SUNG HÀM NÀY ĐỂ XỬ LÝ TÌM THEO ID
    @Override
    public String findCustomerByIdJson(Long id) {
        if (id == null) return null;
        CustomerEntity customer = customerDAO.findById(id);
        if (customer == null) return null;
        
        return formatCustomerToJson(customer);
    }

    @Override
    public boolean deductCustomerPoints(Long customerId, int pointsToDeduct) {
        if (customerId == null) return false;
        CustomerEntity customer = customerDAO.findById(customerId); 
        
        if (customer != null && customer.getLoyaltyPoints() >= pointsToDeduct) {
            customer.setLoyaltyPoints(customer.getLoyaltyPoints() - pointsToDeduct);
            customerDAO.update(customer); 
            return true;
        }
        return false;
    }

    // Hàm tiện ích format JSON nội bộ
    private String formatCustomerToJson(CustomerEntity customer) {
        return String.format(
            "{\"success\": true, \"customer\": {\"id\": %d, \"name\": \"%s\", \"phoneNumber\": \"%s\", \"loyaltyPoints\": %d}}",
            customer.getId(),
            customer.getName(),
            customer.getPhoneNumber(),
            customer.getLoyaltyPoints()
        );
    }
    @Override
    public Map<String, String> getCustomerDataMap(Long id) {
        if (id == null) return null;
        CustomerEntity customer = customerDAO.findById(id);
        if (customer == null) return null;
        
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(customer.getId()));
        map.put("name", customer.getName());
        map.put("phone", customer.getPhoneNumber());
        return map;
    }
}