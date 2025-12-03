package DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class DeptEmpDTO {
    private int empNo;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    public DeptEmpDTO() {}

    public DeptEmpDTO(int empNo, String firstName, String lastName, LocalDate hireDate) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hireDate = hireDate;
    }

    public int getEmpNo() {return empNo;}
    public void setEmpNo(int empNo) {this.empNo = empNo;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public LocalDate getHireDate() {return hireDate;}
    public void setHireDate(LocalDate hireDate) {this.hireDate = hireDate;}

    @Override
    public String toString() {
        return "DeptEmpDTO{empNo=" + getEmpNo() + ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' + ", hireDate=" + getHireDate() + '}';
    }
}
