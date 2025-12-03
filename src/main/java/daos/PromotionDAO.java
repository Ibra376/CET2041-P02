package daos;

import employeesdb.Employees;
import employeesdb.Departments;
import employeesdb.Dept_emp;
import employeesdb.Dept_manager;
import employeesdb.Salaries;
import employeesdb.Titles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;

import java.time.LocalDate;
import java.util.Objects;

public class PromotionDAO {

    protected EntityManager em;

    private static int noUpdateCounter =0;

    public PromotionDAO(EntityManager em) {
        this.em = em;
    }

    public int promoteEmployee(
            int empNo,
            String deptNo,
            String newTitle,
            int newSalary) {

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

            Dept_emp deptEmp = em.find(Dept_emp.class, new Dept_emp.DeptEmpId(empNo, deptNo));


            LocalDate today = LocalDate.now();
            LocalDate maxDate = LocalDate.of(9999, 1, 1);

            noUpdateCounter =0;
            String previousTitle = updateTitle(empNo, emp, today, maxDate, newTitle);
            updateSalary(empNo, emp, today, maxDate, newSalary);
            String previousDept = updateDeptEmp(empNo, emp, deptNo, dept, today,maxDate);

            //if at least one table was updated, otherwise no need to run the manager check and commit
            if (noUpdateCounter <3) {
                boolean isManager = newTitle != null &&
                        newTitle.toLowerCase().contains("manager");

                boolean prevManager = previousTitle != null && previousTitle.toLowerCase().contains("manager");

                boolean deptChanged = !previousDept.equals(deptNo);
                //1. previously manager, and no longer a manager
                //2. previously manager for a department, moved to another department and is still a manager
                boolean wasManager = (prevManager && !isManager) || (prevManager && isManager && deptChanged);

                if (isManager) {
                    updateDeptManager(empNo, emp, deptNo, dept, today, maxDate);
                }

                //update the previous manager if necessary
                if (wasManager) {
                    Dept_manager previousRecord =  em.createNamedQuery("DeptManager.findRecord", Dept_manager.class)
                            .setParameter("empNo", empNo)
                            .setParameter("deptNo", previousDept)
                            .setParameter("maxDate", maxDate)
                            .getSingleResult();

                    previousRecord.setToDate(today);
                    System.out.println(previousRecord.getToDate());
                }

                em.getTransaction().commit();
            }


        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }

        return noUpdateCounter;
    }

    private void updateSalary(int empNo, Employees emp, LocalDate fromDate, LocalDate toDate, int newSalary){

        //salary cannot be updated more than once per day due to database constraint
        boolean salaryUpdatedToday = ( em.createNamedQuery("salaries.updatedToday", Long.class)
                .setParameter("fromDate", fromDate)
                .setParameter("empNo", empNo)
                .getSingleResult() > 0) ;


        Salaries salaries ;

        try{
            salaries = em.createNamedQuery("salaries.findCurrent", Salaries.class)
                    .setParameter("empNo", empNo)
                    .setParameter("maxDate", toDate)
                    .getSingleResult();

            if(salaries.getSalary() == newSalary){
                noUpdateCounter++;
                return; // no change needed
            }

            if(salaryUpdatedToday){
                throw new IllegalStateException("salary cannot be changed more than once on the same day.");
            }
            salaries.setToDate(fromDate);
        }
        catch(NoResultException e){
            throw new IllegalStateException("no current salary found for employee", e);
        }
        catch(NonUniqueResultException e){
            throw new IllegalStateException("more than 1 row of current salary detected", e);
        }

        Salaries.SalariesId salariesId =
                new Salaries.SalariesId(empNo, fromDate);
        Salaries newSalaryEntity =
                new Salaries(salariesId, newSalary, toDate, emp);

        em.persist(newSalaryEntity);


    }

    //returns the previous title => so can update the manager table
    private String updateTitle(int empNo, Employees emp, LocalDate fromDate, LocalDate toDate, String newTitle){

        //title cannot be inserted twice with the same (empNo, title, fromDate)
        boolean titleUpdatedToday = em.createNamedQuery("titles.updatedToday", Long.class)
                .setParameter("empNo", empNo)
                .setParameter("title", newTitle)
                .setParameter("fromDate", fromDate)
                .getSingleResult() > 0;

        if(titleUpdatedToday){
            throw new IllegalStateException("title cannot be changed more than once on the same day with the " +
                    "same (empNo, title, fromDate).");
        }

        Titles titles;
        String previousTitle;
        try{
            titles = em.createNamedQuery("titles.findCurrent",  Titles.class)
                    .setParameter("empNo", empNo)
                    .setParameter("maxDate", toDate)
                    .getSingleResult();

            previousTitle = titles.getTitle();

            if(Objects.equals(previousTitle, newTitle)){
                noUpdateCounter++;
                return ""; // no change needed
            }
            titles.setToDate(fromDate);
        }
        catch(NoResultException e){
            throw new IllegalStateException("no current title found for employee", e);
        }
        catch(NonUniqueResultException e){
            throw new IllegalStateException("more than 1 row of current title detected", e);
        }

        Titles.TitlesId titlesId =
                new Titles.TitlesId(empNo, newTitle, fromDate);
        Titles newTitleEntity =
                new Titles(titlesId, toDate, emp);
        em.persist(newTitleEntity);


        return previousTitle;
    }

    private String updateDeptEmp(int empNo, Employees emp, String deptNo, Departments dept, LocalDate fromDate,
                               LocalDate toDate){

        //employee cannot move back to a past department (database constraint)
        boolean IsPastDept = ( em.createNamedQuery("DeptEmp.findPastRecord", Long.class)
                .setParameter("empNo", empNo)
                .setParameter("deptNo", deptNo)
                .setParameter("maxDate", toDate)
                .getSingleResult() ) > 0;

        if(IsPastDept){
            throw new IllegalStateException("Employee cannot move back to a past department");
        }
        Dept_emp deptEmp;
        String previousDeptNo;
        try{

            deptEmp = em.createNamedQuery("DeptEmp.findCurrent",  Dept_emp.class)
                    .setParameter("empNo", empNo)
                    .setParameter("maxDate", toDate)
                    .getSingleResult();

            previousDeptNo = deptEmp.getId().getDeptNo();
            if(Objects.equals(previousDeptNo, deptNo)){
                noUpdateCounter++;
                return ""; // no change needed
            }
            deptEmp.setToDate(fromDate);
        }
        catch(NoResultException e){
            throw new IllegalStateException("no current DeptEmp record found for employee", e);
        }
        catch(NonUniqueResultException e){
            throw new IllegalStateException("more than 1 row of current DeptEmp records detected", e);
        }


        Dept_emp.DeptEmpId deptEmpId =
                new Dept_emp.DeptEmpId(empNo, deptNo);

        Dept_emp newDeptEmp =
                new Dept_emp(deptEmpId, fromDate, toDate);
        newDeptEmp.setEmployee(emp);
        newDeptEmp.setDepartment(dept);

        em.persist(newDeptEmp);
        return previousDeptNo;
    }

    private void updateDeptManager(int empNo,
                                   Employees emp,
                                   String deptNo,
                                   Departments dept,
                                   LocalDate fromDate,
                                   LocalDate toDate) {

        //check if the same employee had been the manager for the same department in the past (composite key
        // restriction)
        boolean isPastManager = em.createNamedQuery("DeptManager.IsPastManagerDept", Long.class)
                .setParameter("empNo", empNo)
                .setParameter("deptNo", deptNo)
                .getSingleResult() > 0;

        if(isPastManager){
            throw new IllegalStateException("Employee cannot be promoted to Manager in the same Department " +
                    "again");
        }

        // Insert new dept_manager row
        Dept_manager.DeptManagerId dmId =
                new Dept_manager.DeptManagerId(empNo, deptNo);

        Dept_manager newDeptManager =
                new Dept_manager(dmId, fromDate, toDate);
        newDeptManager.setEmployee(emp);
        newDeptManager.setDepartment(dept);

        em.persist(newDeptManager);
    }
}
