package employeesdb;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@IdClass(TitlesId.class)
@Table(name = "titles")
public class Titles {
    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Id
    @Column(name = "title")
    private String title;

    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @ManyToOne
    @JoinColumn(name="empNo", referencedColumnName = "emp_no")
    private Employees employee;

    public Titles() {}

    public Titles(int empNo, String title, LocalDate fromDate, LocalDate toDate, Employees employee) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.employee = employee;
    }

    public int getEmpNo() { return empNo;}
    public void setEmpNo(int empNo) { this.empNo = empNo;}

    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}

    public LocalDate getFromDate() { return fromDate;}
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate;}

    public LocalDate getToDate() { return toDate;}
    public void setToDate(LocalDate toDate) {this.toDate = toDate;}

    public Employees getEmployee() {return employee;}
    public void setEmployee(Employees employee) {this.employee = employee;}
}
