package daos;

import employeesdb.Dept_emp;
import jakarta.persistence.EntityManager;

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

    public List<DeptEmpDTO> getDeptEmp2(String deptNo, int pageNum) {
        //if pageNum <=0 set to default 1
        if(pageNum<1){
            pageNum=1;
        }
        //records number per page
        int recordsNum = 20;
        int firstRecord= (pageNum-1) *recordsNum;

        try{
            List<Dept_emp> dept_empList = em.createNamedQuery("DeptEmp.EmpByDept", Dept_emp.class)
                    .setParameter("deptNo",deptNo)
                    .setMaxResults(100)
                    .getResultList();

            return (getDeptEmpDTO(dept_empList));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<DeptEmpDTO> getDeptEmpDTO(List<Dept_emp> dept_empList) {
        List<DeptEmpDTO> deptEmpDTOList = new ArrayList<>();
        for(Dept_emp dept_emp : dept_empList){
            deptEmpDTOList.add(new DeptEmpDTO(dept_emp.getId().getEmpNo(), dept_emp.getEmployee().getFirstName(),
                    dept_emp.getEmployee().getLastName(), dept_emp.getEmployee().getHireDate()));
        }
        return deptEmpDTOList;
    }
}
