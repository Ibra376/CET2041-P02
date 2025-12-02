package employeesdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="salaries")
@NamedQuery(name="salaries.findCurrent", query = "SELECT s FROM Salaries s " +
        "WHERE s.salariesId.empNo = :empNo " +
        "AND s.toDate = :maxDate")
public class Salaries {
    @EmbeddedId
    private SalariesId salariesId;

    @Column(name = "salary")
    private int salary;

    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    @ManyToOne
    @JoinColumn(name="emp_no", insertable=false, updatable=false )
    @JsonIgnoreProperties("salary")
    private Employees employee;

    public Salaries() {}

    public Salaries(SalariesId salariesId, int salary, LocalDate toDate, Employees employee) {
        this.salariesId = salariesId;
        this.salary = salary;
        this.toDate = toDate;
        this.employee = employee;
    }

    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getFromDate() { return salariesId.getFromDate(); }
    public void setFromDate(LocalDate fromDate) { this.salariesId.setFromDate(fromDate);}

    public Employees getEmployee() { return employee;}
    public void setEmployee(Employees employee) { this.employee = employee;}

    @Override
    public String toString() {
        return "From: "+salariesId.fromDate+", To: "+toDate+", Salary: "+salary;
    }

    @Embeddable
    public static class SalariesId implements Serializable
    {
        @Column(name = "emp_no")
        private int empNo;

        @Column(name = "from_date")
        private LocalDate fromDate;

        public SalariesId() {}

        public SalariesId(int empNo, LocalDate fromDate) {
            this.empNo = empNo;
            this.fromDate = fromDate;
        }

        public int getEmpNo() { return empNo; }
        public void setEmpNo(int empNo) { this.empNo = empNo; }

        @JsonGetter("fromDate")
        @JsonFormat(pattern = "yyyy-MM-dd")
        public LocalDate getFromDate() { return fromDate; }
        public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SalariesId)) return false;
            SalariesId that = (SalariesId) o;
            return empNo == that.empNo && fromDate.equals(that.fromDate);
        }

        @Override
        public int hashCode() { return Objects.hash(empNo, fromDate); }


    }
}
