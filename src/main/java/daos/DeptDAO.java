package daos;

import employeesdb.Departments;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for handling GET request related to the Departments Entity.
 * This DAO provides methods to retrieve department data from database
 *
 * All database operations use the provided EntityManager.
 */
public class DeptDAO {
    /**
     * EntityManager used to perform database operations.
     */
    protected EntityManager em;

    /**
     * Constructor for DeptDAO and takes EntityManager em as an input.
     * @param em the Entity Manager instance for database access.
     */
    public DeptDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Retrieves all departments from the database.
     * @return List of all departments in the database; returns an empty list if a persistence error occurs.
     **/
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