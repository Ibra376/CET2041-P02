package daos;

import employeesdb.Employees;
import employeesdb.Departments;
import employeesdb.Dept_emp;
import employeesdb.Salaries;
import employeesdb.Titles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;

import java.time.LocalDate;
import java.util.Objects;

public class PromotionDAO {

    protected EntityManager em;

    public PromotionDAO(EntityManager em) {
        this.em = em;
    }

    public void promoteEmployee(
            int empNo,
            String deptNo,
            String newTitle,
            int newSalary,
            boolean isManager) {

        try {
            em.getTransaction().begin();

            Employees emp = em.find(Employees.class, empNo);
            if (emp == null) {
                throw new RuntimeException("Employee not found: " + empNo);
            }

            Departments dept = em.find(Departments.class, deptNo);
            if (dept == null) {
                throw new RuntimeException("Department not found: " + deptNo);
            }

            LocalDate today = LocalDate.now();
            LocalDate maxDate = LocalDate.of(9999, 1, 1);

            updateTitle(empNo, emp, today, maxDate, newTitle);
            updateSalary(empNo, emp, today, maxDate, newSalary);
            updateDeptEmp(empNo, emp, deptNo, dept, today,maxDate);

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }

    }

    private void updateSalary(int empNo, Employees emp, LocalDate fromDate, LocalDate toDate, int newSalary){
        Salaries salaries ;
        try{
            salaries = em.createNamedQuery("salaries.findCurrent", Salaries.class)
                    .setParameter("empNo", empNo)
                    .setParameter("maxDate", toDate)
                    .getSingleResult();

            if(salaries.getSalary() == newSalary){
                return; // no change needed
            }
            salaries.setToDate(fromDate);
        }
        catch(NoResultException e){
            throw new IllegalStateException("Error: no current salary found for employee", e);
        }
        catch(NonUniqueResultException e){
            throw new IllegalStateException("Error: more than 1 row of current salary detected", e);
        }

        Salaries.SalariesId salariesId =
                new Salaries.SalariesId(empNo, fromDate);
        Salaries newSalaryEntity =
                new Salaries(salariesId, newSalary, toDate, emp);

        em.persist(newSalaryEntity);
    }

    private void updateTitle(int empNo, Employees emp, LocalDate fromDate, LocalDate toDate, String newTitle){
        Titles titles;
        try{
            titles = em.createNamedQuery("titles.findCurrent",  Titles.class)
                    .setParameter("empNo", empNo)
                    .setParameter("maxDate", toDate)
                    .getSingleResult();

            if(Objects.equals(titles.getTitle(), newTitle)){
                return; // no change needed
            }
            titles.setToDate(fromDate);
        }
        catch(NoResultException e){
            throw new IllegalStateException("Error: no current title found for employee", e);
        }
        catch(NonUniqueResultException e){
            throw new IllegalStateException("Error: more than 1 row of current title detected", e);
        }

        Titles.TitlesId titlesId =
                new Titles.TitlesId(empNo, newTitle, fromDate);
        Titles newTitleEntity =
                new Titles(titlesId, toDate, emp);
        em.persist(newTitleEntity);
    }

    private void updateDeptEmp(int empNo, Employees emp, String deptNo, Departments dept, LocalDate fromDate,
                               LocalDate toDate){
        Dept_emp deptEmp;
        try{
            deptEmp = em.createNamedQuery("DeptEmp.findCurrent",  Dept_emp.class)
                    .setParameter("empNo", empNo)
                    .setParameter("maxDate", toDate)
                    .getSingleResult();

            if(Objects.equals(deptEmp.getId().getDeptNo(), deptNo)){
                return; // no change needed
            }
            deptEmp.setToDate(fromDate);
        }
        catch(NoResultException e){
            throw new IllegalStateException("Error: no current DeptEmp record found for employee", e);
        }
        catch(NonUniqueResultException e){
            throw new IllegalStateException("Error: more than 1 row of current DeptEmp records detected", e);
        }

        Dept_emp.DeptEmpId deptEmpId =
                new Dept_emp.DeptEmpId(empNo, deptNo);

        Dept_emp newDeptEmp =
                new Dept_emp(deptEmpId, fromDate, toDate);
        newDeptEmp.setEmployee(emp);
        newDeptEmp.setDepartment(dept);

        em.persist(newDeptEmp);
    }


}
