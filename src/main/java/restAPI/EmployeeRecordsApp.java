package restAPI;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * JAX-RS application configuration class for the Employee Records REST API.
 */
@ApplicationPath("/api")
public class EmployeeRecordsApp extends Application {

    /**
     * Registers and returns the set of resource classes for this application.
     * @return a set of resource classes exposed by this application
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(EmployeeRecords.class);
        return s;
    }
}