package restAPI;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/api")
public class EmployeeRecordsApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(EmployeeRecords.class);
//        s.add(DepartmentRecords.class);
        return s;
    }

//    @Override
//    public Map<String, Object> getProperties() {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("jersey.config.server.provider.classnames",
//                "org.glassfish.jersey.media.multipart.MultiPartFeature");
//        return properties;
//    }
}
