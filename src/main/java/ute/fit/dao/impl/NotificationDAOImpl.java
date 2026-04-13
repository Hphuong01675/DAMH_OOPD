package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ute.fit.config.JPAUtil;
import ute.fit.dao.INotificationDAO;
import ute.fit.entity.NotificationEntity;
import ute.fit.entity.AccountEntity;
import ute.fit.model.Roles;
import java.util.Set;
import java.util.List;
import java.time.LocalDateTime;

public class NotificationDAOImpl implements INotificationDAO {

	// Hàm này giúp Service không còn phải xử lý vòng lặp hay Entity
	public void insertForRoles(String content, Set<Roles> roles) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();

			// 1. Truy vấn tất cả Account thuộc các Role đích
			String jpql = "SELECT a FROM AccountEntity a WHERE a.role IN :roles";
			List<AccountEntity> accounts = em.createQuery(jpql, AccountEntity.class).setParameter("roles", roles)
					.getResultList();

			// 2. Lưu thông báo cho từng người trong 1 Transaction (Bulk Insert)
			LocalDateTime now = LocalDateTime.now();
			for (AccountEntity account : accounts) {
				NotificationEntity entity = new NotificationEntity();
				entity.setContent(content);
				entity.setSentDate(now);
				entity.setReceiver(account);
				em.persist(entity);
			}

			trans.commit();
		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public List<NotificationEntity> findByReceiver(String username) {
		EntityManager em = JPAUtil.getEntityManager();
		try {
			return em.createQuery(
					"SELECT n FROM NotificationEntity n WHERE n.receiver.username = :username ORDER BY n.sentDate DESC",
					NotificationEntity.class).setParameter("username", username).getResultList();
		} finally {
			em.close();
		}
	}

	public void insertForSpecificUsers(String content, List<String> usernames) {
		EntityManager em = JPAUtil.getEntityManager();
		EntityTransaction trans = em.getTransaction();
		try {
			trans.begin();
			LocalDateTime now = LocalDateTime.now();

			for (String username : usernames) {
				AccountEntity account = em.find(AccountEntity.class, username);
				if (account != null) {
					NotificationEntity entity = new NotificationEntity();
					entity.setContent(content);
					entity.setSentDate(now);
					entity.setReceiver(account);
					em.persist(entity);
				}
			}
			trans.commit();
		} catch (Exception e) {
			if (trans.isActive())
				trans.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
}