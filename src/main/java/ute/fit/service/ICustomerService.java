package ute.fit.service;

import ute.fit.model.CustomerSummaryDTO;

public interface ICustomerService {
    CustomerSummaryDTO getOrCreateCustomer(String phone, String name);
}
