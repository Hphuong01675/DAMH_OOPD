package ute.fit.config;

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
                    emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
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
