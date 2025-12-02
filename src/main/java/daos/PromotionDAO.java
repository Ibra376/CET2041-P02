package daos;

import employeesdb.Employees;
import employeesdb.Departments;
import employeesdb.Dept_emp;
import employeesdb.Salaries;
import employeesdb.Titles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class PromotionDAO {

    protected EntityManager em;

    public PromotionDAO(EntityManager em) {
        this.em = em;
    }

    public void promoteEmployee(int empNo,
                                String deptNo,
                                String newTitle,
                                int newSalary,
                                LocalDate startDate) {
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

            LocalDate maxDate = LocalDate.of(9999, 1, 1);
            LocalDate newEndDate = startDate.minusDays(1);

            TypedQuery<Titles> titleQuery = em.createQuery(
                    "SELECT t FROM Titles t " +
                            "WHERE t.titlesId.empNo = :empNo " +
                            "AND t.toDate = :maxDate",
                    Titles.class
            );
            titleQuery.setParameter("empNo", empNo);
            titleQuery.setParameter("maxDate", maxDate);
            List<Titles> currentTitles = titleQuery.getResultList();

            for (Titles t : currentTitles) {
                t.setToDate(newEndDate);
            }

            TypedQuery<Salaries> salaryQuery = em.createQuery(
                    "SELECT s FROM Salaries s " +
                            "WHERE s.salariesId.empNo = :empNo " +
                            "AND s.toDate = :maxDate",
                    Salaries.class
            );
            salaryQuery.setParameter("empNo", empNo);
            salaryQuery.setParameter("maxDate", maxDate);
            List<Salaries> salaries = salaryQuery.getResultList();

            for (Salaries sal : salaries) {
                sal.setToDate(newEndDate);
            }

            TypedQuery<Dept_emp> deptEmpQuery = em.createQuery(
                    "SELECT de FROM Dept_emp de " +
                            "WHERE de.id.empNo = :empNo " +
                            "AND de.toDate = :maxDate",
                    Dept_emp.class
            );
            deptEmpQuery.setParameter("empNo", empNo);
            deptEmpQuery.setParameter("maxDate", maxDate);
            List<Dept_emp> currentDeptEmps = deptEmpQuery.getResultList();

            for (Dept_emp de : currentDeptEmps) {
                de.setToDate(newEndDate);
            }

            Titles.TitlesId titlesId =
                    new Titles.TitlesId(empNo, newTitle, startDate);
            Titles newTitleEntity =
                    new Titles(titlesId, maxDate, emp);
            em.persist(newTitleEntity);

            Salaries.SalariesId salariesId =
                    new Salaries.SalariesId(empNo, startDate);
            Salaries newSalaryEntity =
                    new Salaries(salariesId, newSalary, maxDate, emp);
            em.persist(newSalaryEntity);

            Dept_emp.DeptEmpId deptEmpId =
                    new Dept_emp.DeptEmpId(empNo, deptNo);
            Dept_emp newDeptEmp =
                    new Dept_emp(deptEmpId, startDate, maxDate);
            newDeptEmp.setEmployee(emp);
            newDeptEmp.setDepartment(dept);
            em.persist(newDeptEmp);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}