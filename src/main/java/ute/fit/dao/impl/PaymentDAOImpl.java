package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IPaymentDAO;
import ute.fit.entity.PaymentEntity;

public class PaymentDAOImpl implements IPaymentDAO {

    @Override
    public void save(PaymentEntity entity) {
        // Sử dụng EntityManager từ cấu trúc JPA của dự án
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            trans.begin();
            // Sử dụng persist để lưu mới hoặc merge nếu cần cập nhật
            em.persist(entity); 
            trans.commit();
            System.out.println("PaymentDAO: Đã lưu PaymentEntity thành công qua JPA.");
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            System.err.println("Lỗi JPA khi lưu Payment: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}