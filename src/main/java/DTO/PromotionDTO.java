package DTO;

/**
 * Data Transfer Object (DTO) representing promotion details for an employee.
 */
public class PromotionDTO {

    /**
     * Unique identifier for the employee being promoted.
     */
    private int empNo;

    /**
     * Department number where the promotion is applied.
     */
    private String deptNo;

    /**
     * New job title assigned to the employee.
     */
    private String newTitle;

    /**
     * New salary assigned to the employee.
     */
    private int newSalary;

    /**
     * Default constructor.
     */
    public PromotionDTO(){}

    /**
     * Returns the employee number.
     *
     * @return employee number
     */
    public int getEmpNo() {
        return empNo;
    }

    /**
     * Sets the employee number.
     *
     * @param empNo employee number
     */
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    /**
     * Returns the department number.
     *
     * @return department number
     */
    public String getDeptNo() {
        return deptNo;
    }

    /**
     * Sets the department number.
     *
     * @param deptNo department number
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    /**
     * Returns the new job title.
     *
     * @return new job title
     */
    public String getNewTitle() {
        return newTitle;
    }

    /**
     * Sets the new job title.
     *
     * @param newTitle new job title
     */
    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    /**
     * Returns the new salary.
     *
     * @return new salary
     */
    public int getNewSalary() {
        return newSalary;
    }

    /**
     * Sets the new salary.
     *
     * @param newSalary new salary
     */
    public void setNewSalary(int newSalary) {
        this.newSalary = newSalary;
    }
}