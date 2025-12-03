package employeesdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents the {@code dept_manager} table in the Employees database.
 */
@Entity
@Table(name = "dept_manager")
@NamedQueries({
        @NamedQuery(name="DeptManager.IsPastManagerDept", query = "SELECT COUNT(dm) FROM Dept_manager dm WHERE dm.id" +
                ".empNo = :empNo AND dm.id.deptNo = :deptNo"),
        @NamedQuery(name="DeptManager.findRecord", query ="SELECT dm FROM Dept_manager dm WHERE dm.id.empNo = :empNo " +
                "AND dm.id.deptNo = :deptNo AND dm.toDate > :toDate")
})
/**
 *  Represents the association between a manager and a department
 */
public class Dept_manager {
    /**
     * Composite primary key consisting of employee number and department number.
     */
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "empNo", column = @Column(name = "emp_no")),
            @AttributeOverride(name = "deptNo", column = @Column(name = "dept_no", length = 4, columnDefinition = "CHAR(4)"))
    })
    private DeptManagerId id;
    /**
     * The start date of the employee's managerial role for the department.
     */
    @Column(name = "from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;
    /**
     * The end date of the employee's managerial role for the department.
     */
    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;
    /**
     * Reference to the employee who served as manager.
     * <p>
     * Marked as {@link JsonIgnore} to prevent circular JSON serialization
     * issues with bidirectional relationships.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @JsonIgnore
    private Employees employee;
    /**
     * Reference to the department associated with this managerial record.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no", insertable = false, updatable = false)
    @JsonIgnore
    private Departments department;

    /**
     * Default constructor.
     */
    public Dept_manager() {}

    /**
     * Constructs a new Dept_manager entity.
     *
     * @param id       composite ID consisting of employee and department numbers
     * @param fromDate start date of management
     * @param toDate   end date of management
     */
    public Dept_manager(DeptManagerId id, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * Returns the department manager ID.
     * @return department manager ID
     */
    public DeptManagerId getId() { return id; }

    /**
     * Sets the composite ID.
     * @param id the new {@link DeptManagerId}
     */
    public void setId(DeptManagerId id) { this.id = id; }

    /**
     * Returns the start date of the managerial role.
     *
     * @return start date as {@link LocalDate}
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getFromDate() { return fromDate; }

    /**
     * Sets the start date of the managerial role.
     *
     * @param fromDate start date as {@link LocalDate}
     */
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    /**
     * Returns the end date of the managerial role.
     *
     * @return end date as {@link LocalDate}
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getToDate() { return toDate; }

    /**
     * Sets the end date of the managerial role.
     *
     * @param toDate end date as {@link LocalDate}
     */
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    /**
     * Returns the employee associated with this managerial record.
     *
     * @return {@link Employees} entity
     */
    public Employees getEmployee() { return employee; }

    /**
     * Sets the employee associated with this managerial record.
     *
     * @param employee {@link Employees} entity
     */
    public void setEmployee(Employees employee) { this.employee = employee; }

    /**
     * Returns the department associated with this managerial record.
     *
     * @return {@link Departments} entity
     */
    public Departments getDepartment() { return department; }

    /**
     * Sets the department associated with this managerial record.
     *
     * @param department {@link Departments} entity
     */
    public void setDepartment(Departments department) { this.department =
            department; }

    /**
     * Returns a string representation of the managerial record.
     *
     * @return formatted string containing fromDate, toDate, empNo, and deptNo
     */
    @Override
    public String toString() {
        return "From: " + fromDate +
                ", To: " + toDate +
                ", EmpNo: " + (id != null ? id.getEmpNo() : "null") +
                ", DeptNo: " + (id != null ? id.getDeptNo() : "null");
    }

    /**
     * Composite primary key class for {@link Dept_manager}.
     * <p>
     * Consists of employee number and department number.
     * </p>
     */
    @Embeddable
    public static class DeptManagerId implements Serializable {

        /**
         * Department number (4-character code).
         */
        @Column(name = "dept_no", length = 4, columnDefinition = "CHAR(4)")
        private String deptNo;

        /**
         * Employee number.
         */
        @Column(name = "emp_no")
        private int empNo;

        /**
         * Default constructor.
         */
        public DeptManagerId() {}

        /**
         * Constructs a new composite ID.
         *
         * @param empNo  employee number
         * @param deptNo department number
         */
        public DeptManagerId(int empNo, String deptNo) {
            this.empNo = empNo;
            this.deptNo = deptNo;
        }

        /**
         * Returns the department number.
         *
         * @return department number as {@link String}
         */
        public String getDeptNo() { return deptNo; }

        /**
         * Sets the department number.
         *
         * @param deptNo department number as {@link String}
         */
        public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

        /**
         * Returns the employee number.
         *
         * @return employee number as {@code int}
         */
        public int getEmpNo() { return empNo; }

        /**
         * Sets the employee number.
         *
         * @param empNo employee number as {@code int}
         */
        public void setEmpNo(int empNo) { this.empNo = empNo; }

        /**
         * Compares this ID with another for equality.
         *
         * @param o another object
         * @return true if both IDs match, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DeptManagerId)) return false;
            DeptManagerId that = (DeptManagerId) o;
            return empNo == that.empNo && Objects.equals(deptNo, that.deptNo);
        }

        /**
         * Returns the hash code for this ID.
         *
         * @return hash code based on deptNo and empNo
         */
        @Override
        public int hashCode() {
            return Objects.hash(deptNo, empNo);
        }
    }
}