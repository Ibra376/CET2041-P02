package EMF;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utility class for managing the application's {@link EntityManagerFactory} and providing
 * {@link EntityManager} instances.
 * <p>
 * This class implements a singleton-style pattern using a static factory that is created
 * once per application lifecycle based on the {@code persistence.xml} configuration.
 * </p>
 *
 * <p><b>Usage:</b></p>
 * <ul>
 *     <li>Call {@link #getEntityManager()} to obtain an {@link EntityManager}.</li>
 *     <li>Call {@link #close()} during application shutdown to release factory resources.</li>
 * </ul>
 *
 * <p><b>Persistence Unit:</b> "EmployeeService"</p>
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
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    /**
     * Closes the {@link EntityManagerFactory} if it is open.
     * <p>
     * Should be called once during application shutdown to free database and resource handles.
     * </p>
     */
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
