package ute.fit.service.impl;

import ute.fit.dao.ICustomerDAO;
import ute.fit.dao.impl.CustomerDAOImpl;
import ute.fit.entity.CustomerEntity;
import ute.fit.model.CustomerSummaryDTO;
import ute.fit.service.ICustomerService;

public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public CustomerSummaryDTO getOrCreateCustomer(String phone, String name) {
        CustomerEntity customer = customerDAO.findByPhone(phone);

        if (customer == null && name != null && !name.trim().isEmpty()) {
            customer = new CustomerEntity();
            customer.setName(name);
            customer.setPhoneNumber(phone);
            customer.setLoyaltyPoints(0);
            customer = customerDAO.save(customer);
        }

        if (customer == null) {
            return null;
        }

        return new CustomerSummaryDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhoneNumber(),
                customer.getLoyaltyPoints()
        );
    }
}
