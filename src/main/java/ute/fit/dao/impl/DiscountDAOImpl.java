package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IDiscountDAO;
import ute.fit.entity.DiscountEntity;

import java.util.List;

public class DiscountDAOImpl implements IDiscountDAO {

    @Override
    public DiscountEntity findByCode(String code) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            TypedQuery<DiscountEntity> query = em.createQuery(
                "SELECT d FROM DiscountEntity d WHERE d.code = :code", DiscountEntity.class);
            query.setParameter("code", code);
            
            List<DiscountEntity> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DiscountEntity> findAll() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createQuery("SELECT d FROM DiscountEntity d", DiscountEntity.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // Trả về list rỗng nếu có lỗi để không làm sập giao diện
        }
    }
}