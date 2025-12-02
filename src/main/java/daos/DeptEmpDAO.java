package daos;

import DTO.DeptEmpDTO;
import employeesdb.Dept_emp;
import employeesdb.Employees;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeptEmpDAO {
    protected EntityManager em;

    public DeptEmpDAO(EntityManager em) {
        this.em = em;
    }

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
