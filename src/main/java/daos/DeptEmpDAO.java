package daos;

import DTO.DeptEmpDTO;
import employeesdb.Dept_emp;
import employeesdb.Employees;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for handling GET request related to the dept_emp Entity.
 * This DAO provides methods to retrieve dept_emp assignments and convert them into DeptEmpDTOs.
 *
 * All database operations use the provided EntityManager.
 */
public class DeptEmpDAO {
    /**
     * EntityManager used to perform database operations.
     */
    protected EntityManager em;

    /**
     * Constructor for DeptEmpDAO and takes EntityManager em as an input.
     * @param em the Entity Manager instance for database access.
     */
    public DeptEmpDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Retrieves a paginated list of employees for a given department
     * Each page contains up to 20 employees.
     * @param deptNo department number
     * @param pageNum page Number (optional, default value =1)
     * @return list of DeptEmpDTO representing employees in the department
     */
    public List<DeptEmpDTO> getDeptEmp(String deptNo, int pageNum) {
        //records number per page
        int recordsNum = 20;
        int firstRecord= (pageNum-1) *recordsNum;

        try{
            List<Dept_emp> dept_empList = em.createNamedQuery("DeptEmp.EmpByDept", Dept_emp.class)
                    .setParameter("deptNo",deptNo)
                    .setFirstResult(firstRecord)
                    .setMaxResults(recordsNum)
                    .getResultList();

            return(getDeptEmpDTO(dept_empList));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts a list of dept_emp into a list of DeptEmptDTO.
     * Each DTO contains empNo, first name, last name, and hire date.
     * @param dept_empList List of Dept_emp objects
     * @return a list of deptEmpDTO objects
     */
    public List<DeptEmpDTO> getDeptEmpDTO(List<Dept_emp> dept_empList) {
        List<DeptEmpDTO> deptEmpDTOList = new ArrayList<>();

        for(Dept_emp dept_emp : dept_empList){
            Employees emp = dept_emp.getEmployee();
            LocalDate hireDate = emp.getHireDate();
            deptEmpDTOList.add(new DeptEmpDTO(dept_emp.getId().getEmpNo(),
                    emp.getFirstName(),
                    emp.getLastName(),
                    hireDate));
        }
        return deptEmpDTOList;
    }
}
