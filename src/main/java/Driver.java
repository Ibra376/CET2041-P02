import daos.DeptDAO;
import daos.EmployeeDAO;
import employeesdb.Departments;
import employeesdb.Employees;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.core.Response;

public class Driver {

    public static void main(String[] args) {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                "EmployeeService")){
            EntityManager em = emf.createEntityManager();
            EmployeeDAO employeeDAO = new EmployeeDAO(em);
            Employees employees = employeeDAO.findEmployee(10001);
            System.out.println(employees);

            em.close();
        }

    }
}
