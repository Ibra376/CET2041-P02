package employeesdb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a department within the Employees database.
 */
@Entity
@Table(name = "departments")
@NamedQuery(name="Departments.findAll", query ="SELECT d FROM Departments d")
public class Departments {
    /**
     *  The unique department number (primary key).
     */
    @Id
    @Column(name = "dept_no", length = 4, columnDefinition = "CHAR(4)")
    private String deptNo;
    /**
     * The name of the department.
     */
    @Column(name = "dept_name")
    private String deptName;
    /**
     * The list of department managers associated with this department.
     */
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade =
            CascadeType.ALL,  orphanRemoval = true)
    @JsonIgnore
    private List<Dept_manager> dept_manager;
    /**
     * The list of employees assigned to this department.
     */
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade =
            CascadeType.ALL,  orphanRemoval = true)
    @JsonIgnore
    private List<Dept_emp> dept_emp;
    /**
     * Default constructor
     */
    public Departments() {}
    /**
     * Constructs a department entity with the given department number and name.
     * @param deptNo the department number
     * @param deptName the department name
     */
    public Departments(String deptNo, String deptName) {
        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    /**
     * Returns the department number.
     * @return the department number
     */
    public String getDeptNo() { return deptNo; }
    /**
     * Sets the department number.
     * @param deptNo the new department number
     */
    public void setDeptNo(String deptNo) { this.deptNo = deptNo; }
    /**
     * Returns the department name.
     * @return the department name
     */
    public String getDeptName() { return deptName; }
    /**
     * Sets the department name.
     * @param deptName the new department name
     */
    public void setDeptName(String deptName) { this.deptName = deptName; }
    /**
     * Returns the list of department manager records associated with this department.
     * @return list of {@link Dept_manager} entities
     */
    public List<Dept_manager> getDept_manager() { return dept_manager; }
    /**
     * Sets the list of department managers.
     * @param dept_manager the new list of managers
     */
    public void setDept_manager(List<Dept_manager> dept_manager) { this.dept_manager = dept_manager; }
    /**
     * Returns the list of department-employee assignment records.
     * @return list of {@link Dept_emp} entities
     */
    public List<Dept_emp> getDept_emp() { return dept_emp; }
    /**
     * Sets the list of department-employee records.
     * @param dept_emp the new list of dept-employee assignments
     */
    public void setDept_emp(List<Dept_emp> dept_emp) { this.dept_emp = dept_emp; }

    /**
     * Returns a formatted string representation of the department,
     * including manager and employee relationship details if available.
     *
     * @return formatted department details
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("\nDepartment{ id=%s, name=%s }\n", deptNo, deptName));
        if (dept_manager != null) {
            for (Dept_manager dm : dept_manager) {
                builder.append(dm.toString()).append("\n");
            }
        }
        if (dept_emp != null) {
            for (Dept_emp de : dept_emp) {
                builder.append(de.toString()).append("\n");
            }
        }
        return builder.toString();
    }
}