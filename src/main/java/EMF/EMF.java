package EMF;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class for managing the application's {@link EntityManagerFactory} and providing
 * {@link EntityManager} instances.
 */
public class EMF {

    /**
     * Singleton instance of the {@link EntityManagerFactory}.
     * Created once at application startup from the persistence unit "EmployeeService".
     */
    private static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("EmployeeService");

    /** Private constructor to prevent instantiation. */
    private EMF() {}

    /**
     * Returns a new {@link EntityManager} instance from the global {@link EntityManagerFactory}.
     *
     * @return a fresh {@link EntityManager} for database interaction
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Closes the {@link EntityManagerFactory} if it is open.
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
