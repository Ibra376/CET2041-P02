package daos;

import employeesdb.Employees;
import jakarta.persistence.EntityManager;

public class EmployeeDAO {
    protected EntityManager em;

    public EmployeeDAO(EntityManager em) { this.em = em; }

    public void createEmployee(Employees emp) {
        em.getTransaction().begin();
        em.persist(emp);
        em.getTransaction().commit();
    }

    public void removeEmployee(int id) {
        Employees emp = findEmployee(id);
        if (emp != null) {
            em.getTransaction().begin();
            em.remove(emp);
            em.getTransaction().commit();
        }
    }

    public void updateEmployeeDetails(Employees emp) {
        if (emp != null) {
            em.getTransaction().begin();
            em.merge(emp);
            em.getTransaction().commit();
        }
    }

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
        return em.find(Employees.class, id);
    }
}
