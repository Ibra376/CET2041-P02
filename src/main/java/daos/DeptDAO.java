package daos;

import employeesdb.Departments;
import employeesdb.Dept_emp;
import employeesdb.Dept_manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class DeptDAO {
    protected EntityManager em;

    public DeptDAO(EntityManager em) {
        this.em = em;
    }

    public void createDept(Departments dept) {
        try {
            em.getTransaction().begin();
            em.persist(dept);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public void updateDeptName(Departments dept) {
        try {
            em.getTransaction().begin();
            em.merge(dept);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public void updateDeptID(String oldID, String newID) {
        try {
            em.getTransaction().begin();

            Departments oldDept = em.find(Departments.class, oldID);
            if (oldDept == null) {
                throw new IllegalArgumentException("Department not found: " + oldID);
            }

            Departments newDept = new Departments(newID, oldDept.getDeptName());

            List<Dept_emp> deptEmps = oldDept.getDept_emp();
            List<Dept_manager> deptManagers = oldDept.getDept_manager();

            newDept.setDept_emp(deptEmps);
            newDept.setDept_manager(deptManagers);

            em.persist(newDept);

            for (Dept_emp de : deptEmps) {
                de.setDepartment(newDept);
                em.merge(de);
            }
            for (Dept_manager dm : deptManagers) {
                dm.setDepartment(newDept);
                em.merge(dm);
            }

            em.remove(oldDept);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    public Departments findDept(String id) {
        return em.find(Departments.class, id);
    }

    public List<Departments> getAllDepartments() {
        try{
            return em.createNamedQuery("Departments.findAll", Departments.class).getResultList();
        }
        //catch any JPA related exception
        catch (PersistenceException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}