package DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

/**
 * Data Transfer Object representing a simplified view of an employee within a department.
 * This DTO contains the empNo, first name, lastname, and hireDate.
 */
public class DeptEmpDTO {
    /**
     * employee number
     */
    private int empNo;
    /**
     * first name of the employee
     */
    private String firstName;
    /**
     * last name of the employee
     */
    private String lastName;
    /**
     * hire date of the employee
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    /**
     * empty constructor for DeptEmpDTO
     */
    public DeptEmpDTO() {}

    /**
     * Constructor for DeptEmpDTO which takes empNo, firstName, lastName and hireDate as inputs
     * @param empNo employee number
     * @param firstName first name of the employee
     * @param lastName last name of the employee
     * @param hireDate hire date of the employee
     */
    public DeptEmpDTO(int empNo, String firstName, String lastName, LocalDate hireDate) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
    }

    /**
     * getter method for employee Number
     * @return employee Number
     */
    public int getEmpNo() {return empNo;}

    /**
     * setter method for employee Number
     * @param empNo employee Number
     */
    public void setEmpNo(int empNo) {this.empNo = empNo;}

    /**
     * getter method for first name of the employee
     * @return first name of the employee
     */
    public String getFirstName() {return firstName;}

    /**
     * setter method for first name of the employee
     * @param firstName first name of the employee
     */
    public void setFirstName(String firstName) {this.firstName = firstName;}

    /**
     * getter method for last name of the employee
     * @return last name of the employee
     */
    public String getLastName() {return lastName;}

    /**
     * setter method for last name of the employee
     * @param lastName last name of the employee
     */
    public void setLastName(String lastName) {this.lastName = lastName;}

    /**
     * getter method for the hire date of the employee
     * @return hire date of the employee
     */
    public LocalDate getHireDate() {return hireDate;}

    /**
     * setter method for the hire date of the employee
     * @param hireDate hire date of the employee
     */
    public void setHireDate(LocalDate hireDate) {this.hireDate = hireDate;}

    /**
     * Returns the string representation of this deptEmpDTO
     * @return a formatted string containing the employee number, first name, last name, and hire date.
     */
    @Override
    public String toString() {
        return "DeptEmpDTO{empNo=" + getEmpNo() + ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' + ", hireDate=" + getHireDate() + '}';
    }
}
