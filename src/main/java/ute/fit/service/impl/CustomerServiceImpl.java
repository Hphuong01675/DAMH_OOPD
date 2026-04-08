package ute.fit.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.fit.config.JPAUtil;
import ute.fit.dao.ICustomerDAO;
import ute.fit.dao.impl.CustomerDAOImpl;
import ute.fit.entity.CustomerEntity;
import ute.fit.service.ICustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceImpl implements ICustomerService {

	private final ICustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public Map<String, Object> getOrCreateCustomer(String phone, String name) {

        CustomerEntity customer = customerDAO.findByPhone(phone);

        if (customer == null && name != null && !name.trim().isEmpty()) {
            customer = new CustomerEntity();
            customer.setName(name);
            customer.setPhoneNumber(phone);
            customer.setLoyaltyPoints(0);

            customer = customerDAO.save(customer);
        }

        if (customer == null) return null;

        // 👉 convert sang data trả về
        Map<String, Object> result = new HashMap<>();
        result.put("id", customer.getId());
        result.put("name", customer.getName());
        result.put("phoneNumber", customer.getPhoneNumber());
        result.put("loyaltyPoints", customer.getLoyaltyPoints());

        return result;
    }
}