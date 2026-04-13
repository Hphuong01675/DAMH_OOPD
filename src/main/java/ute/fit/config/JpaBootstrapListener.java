package ute.fit.config;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class JpaBootstrapListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            em.createNativeQuery("SELECT 1").getSingleResult();
        } catch (Exception e) {
            sce.getServletContext().log("Failed to initialize JPA persistence unit LTWebPU", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.shutdown();
    }
}