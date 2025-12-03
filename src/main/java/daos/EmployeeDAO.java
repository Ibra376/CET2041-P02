package daos;

import employeesdb.Employees;
import jakarta.persistence.EntityManager;

/**
 * Data Access Object for handling GET request related to the Employee Entity.
 * This DAO provides the method to retrieve the employee data from database
 *
 * All database operations use the provided Entity Manager
 */
public class EmployeeDAO {
    /**
     * Entity Manager used to perform databse operations
     */
    protected EntityManager em;

    /**
     * Constructor for EmployeeDAO and takes EntityManager as an input
     * @param em the Entity Manager instance for database access
     */
    public EmployeeDAO(EntityManager em) { this.em = em; }

    /**
     * Retrieves the employee from database given the empNo.
     * @param id empNo
     * @return the employee entity
     */
    public Employees findEmployee(int id) {

        Employees emp =  em.find(Employees.class, id);
        if (emp != null) {
            if (emp.getSalary() != null) {
                emp.getSalary().size();
            }
            if (emp.getTitles() != null) {
                emp.getTitles().size();
            }
            if (emp.getDept_emp() != null) {
                emp.getDept_emp().size();
            }
            if (emp.getDept_manager() != null) {
                emp.getDept_manager().size();
            }
        }
        return emp;
    }
}
