package ute.fit.dao.impl;

import jakarta.persistence.EntityManager;
import ute.fit.config.JPAUtil;
import ute.fit.dao.IAccountDAO;
import ute.fit.entity.AccountEntity;
import ute.fit.model.Roles;

import java.util.List;
import java.util.Set;

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

    @Override
    public AccountEntity findActiveAccountByUsernameAndRole(String username, Roles role) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            String normalizedUsername = username == null ? "" : username.trim().toLowerCase();
            String jpql = """
                    SELECT a
                    FROM AccountEntity a
                    WHERE a.state = true
                      AND a.role = :role
                      AND lower(a.username) = :username
                    """;

            List<AccountEntity> accounts = em.createQuery(jpql, AccountEntity.class)
                    .setParameter("role", role)
                    .setParameter("username", normalizedUsername)
                    .setMaxResults(1)
                    .getResultList();

            return accounts.isEmpty() ? null : accounts.get(0);
        }
    }

    @Override
    public List<AccountEntity> findActiveAccountsByRoles(Set<Roles> roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of();
        }

        try (EntityManager em = JPAUtil.getEntityManager()) {
            String jpql = """
                    SELECT a
                    FROM AccountEntity a
                    WHERE a.state = true
                      AND a.role IN :roles
                    ORDER BY a.username
                    """;

            return em.createQuery(jpql, AccountEntity.class)
                    .setParameter("roles", roles)
                    .getResultList();
        }
    }
}
