package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IAccountDAO;
import ute.fit.entity.AccountEntity;
import ute.fit.model.Roles;

import java.util.List;

public class AccountDAOImpl implements IAccountDAO {

    @Override
    public AccountEntity findActiveAccountForLogin(String identifier, Roles role) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            String normalizedIdentifier = identifier == null ? "" : identifier.trim();
            String jpql = """
                    SELECT a
                    FROM AccountEntity a
                    LEFT JOIN a.person p
                    WHERE a.state = true
                      AND a.role = :role
                      AND (
                            lower(a.username) = :username
                            OR p.phoneNumber = :phoneNumber
                          )
                    """;

            List<AccountEntity> accounts = em.createQuery(jpql, AccountEntity.class)
                    .setParameter("role", role)
                    .setParameter("username", normalizedIdentifier.toLowerCase())
                    .setParameter("phoneNumber", normalizedIdentifier)
                    .setMaxResults(1)
                    .getResultList();

            return accounts.isEmpty() ? null : accounts.get(0);
        }
    }
}
