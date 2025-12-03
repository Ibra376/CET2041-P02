package employeesdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents an employee assignment to a department in the Employees database.
 */
@Entity
@Table(name = "dept_emp")
@NamedQueries({
        @NamedQuery(name="DeptEmp.EmpByDept", query = "SELECT e FROM Dept_emp e WHERE e.department.deptNo = :deptNo"),
        @NamedQuery(name = "DeptEmp.findCurrent", query = "SELECT de from Dept_emp de WHERE de.id.empNo = :empNo " +
                "AND de.toDate = :maxDate"),
        @NamedQuery(name = "DeptEmp.findPastRecord", query = "SELECT COUNT(de) from Dept_emp de WHERE de.id.empNo = " +
                ":empNo AND de.id.deptNo = :deptNo AND de.toDate != :maxDate")
})

/**
 * Represents the association between an employee and a department
 */
public class Dept_emp {

    /**
     * Composite primary key containing employee number and department number.
     */
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "empNo", column = @Column(name = "emp_no")),
            @AttributeOverride(name = "deptNo", column = @Column(name = "dept_no", length = 4, columnDefinition =
                    "CHAR(4)"))
    })
    private DeptEmpId id;

    /**
     * The date the employee joined the department.
     */
    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    /**
     * The date the employee left the department.
     * A value of {@code 9999-01-01} indicates an active assignment.
     */
    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    /**
     * Reference to the {@link Employees} entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore
    private Employees employee;

    /**
     * Reference to the {@link Departments} entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no", insertable = false, updatable = false)
    @JsonIgnore
    private Departments department;

    /**
     * Default constructor.
     */
    public Dept_emp() {}

    /**
     * Constructs a department-employee assignment.
     *
     * @param id        the composite key containing employee and department numbers
     * @param fromDate  the start date of the assignment
     * @param toDate    the end date of the assignment
     */
    public Dept_emp(DeptEmpId id, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * Returns the composite ID.
     * @return the {@link DeptEmpId}
     */
    public DeptEmpId getId() { return id; }

    /**
     * Sets the composite ID.
     * @param id the new {@link DeptEmpId}
     */
    public void setId(DeptEmpId id) { this.id = id; }

    /**
     * Returns the start date of the department assignment.
     * @return start date
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getFromDate() { return fromDate; }

    /**
     * Sets the start date of the assignment.
     * @param fromDate the new start date
     */
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    /**
     * Returns the end date of the assignment.
     * A value of {@code 9999-01-01} indicates an ongoing assignment.
     *
     * @return end date
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getToDate() { return toDate; }

    /**
     * Sets the end date of the assignment.
     *
     * @param toDate the new end date
     */
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    /**
     * Returns the associated employee entity.
     *
     * @return the employee
     */
    public Employees getEmployee() { return employee; }

    /**
     * Sets the associated employee.
     *
     * @param employee the employee to assign
     */
    public void setEmployee(Employees employee) { this.employee = employee; }

    /**
     * Returns the associated department entity.
     *
     * @return the department
     */
    public Departments getDepartment() { return department; }

    /**
     * Sets the department for the employee.
     *
     * @param department the department entity
     */
    public void setDepartment(Departments department) { this.department = department; }

    /**
     * Provides a readable string representation of a department-employee assignment.
     *
     * @return formatted string of assignment details
     */
    @Override
    public String toString() {
        return "From: " + fromDate +
                ", To: " + toDate +
                ", EmpNo: " + (id != null ? id.getEmpNo() : "null") +
                ", DeptNo: " + (id != null ? id.getDeptNo() : "null");
    }

    /**
     * Composite key class for {@link Dept_emp}.
     */
    @Embeddable
    public static class DeptEmpId implements Serializable {
        /**
         * Employee number.
         */
        @Column(name = "emp_no")
        private int empNo;
        /**
         * Department number (4-character fixed string).
         */
        @Column(name = "dept_no", length = 4, columnDefinition = "CHAR(4)")
        private String deptNo;

        /**
         * Default constructor.
         */
        public DeptEmpId() {}

        /**
         * Constructs a composite ID with employee and department numbers.
         *
         * @param empNo  employee number
         * @param deptNo department number
         */
        public DeptEmpId(int empNo, String deptNo) {
            this.empNo = empNo;
            this.deptNo = deptNo;
        }

        /**
         * Get employee number.
         * @return employee number
         */
        public int getEmpNo() { return empNo; }

        /**
         * Set employee number
         * @param empNo new employee number
         */
        public void setEmpNo(int empNo) { this.empNo = empNo; }

        /**
         * Get department number.
         * @return department number
         */
        public String getDeptNo() { return deptNo; }

        /**
         * Set department number.
         * @param deptNo new department number
         */
        public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

        /**
         * Compares this composite key with another object for equality.
         * @param o the object to compare with this ID
         * @return {@code true} if both objects represent the same key, {@code false} otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DeptEmpId)) return false;
            DeptEmpId that = (DeptEmpId) o;
            return empNo == that.empNo && Objects.equals(deptNo, that.deptNo);
        }

        /**
         * Computes the hash code for this composite key.
         * @return a hash code based on both {@code empNo} and {@code deptNo}
         */
        @Override
        public int hashCode() {
            return Objects.hash(deptNo, empNo);
        }
    }
}