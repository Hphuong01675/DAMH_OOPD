package ute.fit.config;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "LTWebPU";

    // volatile để đảm bảo visibility giữa các thread
    private static volatile EntityManagerFactory emf;

    private JPAUtil() {}

    // Double-checked locking
    private static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            synchronized (JPAUtil.class) {
                if (emf == null) {
                    Map<String, String> envConfig = new HashMap<>();
                    String dbUrl = System.getenv("DB_URL");
                    String dbUser = System.getenv("DB_USER");
                    String dbPass = System.getenv("DB_PASSWORD");

                    if (dbUrl != null) envConfig.put("jakarta.persistence.jdbc.url", dbUrl);
                    if (dbUser != null) envConfig.put("jakarta.persistence.jdbc.user", dbUser);
                    if (dbPass != null) envConfig.put("jakarta.persistence.jdbc.password", dbPass);

                    emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, envConfig);
                }
            }
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    // đóng khi shutdown app
    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
