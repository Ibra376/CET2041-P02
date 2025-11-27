package employeesdb;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "dept_emp")
public class Dept_emp {

    public Dept_emp() {}

    @EmbeddedId
    private DeptEmpId id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public DeptEmpId getId() { return id; }
    public void setId(DeptEmpId id) { this.id = id; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    @Embeddable
    public static class DeptEmpId implements Serializable {

        public DeptEmpId() {}

        @Column(name = "emp_no")
        private int empNo;

        @Column(name = "dept_no")
        private String deptNo;

        public int getEmpNo() { return empNo; }
        public void setEmpNo(int empNo) { this.empNo = empNo; }

        public String getDeptNo() { return deptNo; }
        public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DeptEmpId)) return false;
            DeptEmpId that = (DeptEmpId) o;
            return empNo == that.empNo && Objects.equals(deptNo, that.deptNo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(deptNo, empNo);
        }
    }
}