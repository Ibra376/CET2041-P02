package daos;

import employeesdb.Employees;
import jakarta.persistence.EntityManager;

public class EmployeeDAO {
    protected EntityManager em;

    public EmployeeDAO(EntityManager em) { this.em = em; }

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
