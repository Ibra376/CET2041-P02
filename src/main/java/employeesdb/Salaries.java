package employeesdb;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@IdClass(SalariesId.class)
@Table(name="salaries")
public class Salaries {
    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "salary")
    private int salary;

    @Column(name = "to_date")
    private LocalDate toDate;

    @ManyToOne
    @JoinColumn(name="empNo", referencedColumnName = "emp_no")
    private Employees employee;

    public Salaries() {}

    public Salaries(int empNo, LocalDate fromDate, int salary, Employees employee) {
        this.empNo = empNo;
        this.fromDate = fromDate;
        this.salary = salary;
        this.employee = employee;
    }

    public int getEmpNo() { return empNo; }
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public Employees getEmployee() { return employee;}
    public void setEmployee(Employees employee) { this.employee = employee;}

    @Override
    public String toString() {
        return "From: "+fromDate+", To: "+toDate+", Salary: "+salary;
    }
}
