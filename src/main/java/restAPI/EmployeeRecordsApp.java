package restAPI;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * JAX-RS application configuration class for the Employee Records REST API.
 * <p>
 * This class defines the base URI path for all REST endpoints and registers
 * the resource classes that should be exposed.
 * </p>
 *
 * <p>Base path: {@code /api}</p>
 */
@ApplicationPath("/api")
public class EmployeeRecordsApp extends Application {

    /**
     * Registers and returns the set of resource classes for this application.
     * <p>
     * Currently, only {@link EmployeeRecords} is registered.
     * </p>
     *
     * @return a set of resource classes exposed by this application
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(EmployeeRecords.class);
        return s;
    }
}