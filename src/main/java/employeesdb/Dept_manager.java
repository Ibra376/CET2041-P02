package employeesdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "dept_manager")
public class Dept_manager {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "empNo", column = @Column(name = "emp_no")),
            @AttributeOverride(name = "deptNo", column = @Column(name = "dept_no", length = 4, columnDefinition = "CHAR(4)"))
    })
    private DeptManagerId id;

    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore
    private Employees employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no", insertable = false, updatable = false)
    @JsonIgnore
    private Departments department;

    public Dept_manager() {}

    public Dept_manager(DeptManagerId id, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public DeptManagerId getId() { return id; }
    public void setId(DeptManagerId id) { this.id = id; }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public Employees getEmployee() { return employee; }
    public void setEmployee(Employees employee) { this.employee = employee; }

    public Departments getDepartment() { return department; }
    public void setDepartment(Departments department) { this.department = department; }

    @Override
    public String toString() {
        return "From: " + fromDate +
                ", To: " + toDate +
                ", EmpNo: " + (id != null ? id.getEmpNo() : "null") +
                ", DeptNo: " + (id != null ? id.getDeptNo() : "null");
    }

    @Embeddable
    public static class DeptManagerId implements Serializable {

        @Column(name = "dept_no", length = 4, columnDefinition = "CHAR(4)")
        private String deptNo;

        @Column(name = "emp_no")
        private int empNo;

        public DeptManagerId() {}

        public DeptManagerId(int empNo, String deptNo) {
            this.empNo = empNo;
            this.deptNo = deptNo;
        }

        public String getDeptNo() { return deptNo; }
        public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

        public int getEmpNo() { return empNo; }
        public void setEmpNo(int empNo) { this.empNo = empNo; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DeptManagerId)) return false;
            DeptManagerId that = (DeptManagerId) o;
            return empNo == that.empNo && Objects.equals(deptNo, that.deptNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(deptNo, empNo);
        }
    }
}