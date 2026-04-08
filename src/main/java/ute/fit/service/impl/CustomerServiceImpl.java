package ute.fit.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.fit.config.JPAUtil;
import ute.fit.entity.CustomerEntity;
import ute.fit.service.ICustomerService;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {

    @Override
    public CustomerEntity getOrCreateCustomer(String phone, String name) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // 1. Tìm kiếm khách hàng theo số điện thoại
            List<CustomerEntity> list = em.createQuery(
                "SELECT c FROM CustomerEntity c WHERE c.phoneNumber = :phone", CustomerEntity.class)
                .setParameter("phone", phone)
                .getResultList();

            if (!list.isEmpty()) {
                return list.get(0);
            }

            // 2. Nếu không tìm thấy và nhân viên có nhập tên -> Tạo khách mới
            if (name != null && !name.trim().isEmpty()) {
                EntityTransaction trans = em.getTransaction();
                try {
                    trans.begin();
                    CustomerEntity newCust = new CustomerEntity();
                    newCust.setName(name);
                    newCust.setPhoneNumber(phone);
                    newCust.setLoyaltyPoints(0); // Mặc định điểm khi mới tạo là 0
                    
                    em.persist(newCust);
                    trans.commit();
                    return newCust;
                } catch (Exception e) {
                    if (trans.isActive()) trans.rollback();
                    throw e;
                }
            }
            
            return null; // Trả về null nếu không tìm thấy và cũng không có tên để tạo mới
        } finally {
            em.close();
        }
    }
}