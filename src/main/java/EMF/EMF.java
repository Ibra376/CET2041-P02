package EMF;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EMF {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeService");

    private EMF(){}

    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void close(){
        if (emf.isOpen() && emf!= null){
            emf.close();
        }
    }
}
