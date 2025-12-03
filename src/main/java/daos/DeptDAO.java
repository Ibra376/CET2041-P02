package daos;

import employeesdb.Departments;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class DeptDAO {
    protected EntityManager em;

    public DeptDAO(EntityManager em) {
        this.em = em;
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