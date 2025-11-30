package employeesdb;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
@NamedQuery(name="Departments.findAll", query ="SELECT d FROM Departments d")
public class Departments {
    @Id
    @Column(name = "dept_no", length = 4, columnDefinition = "CHAR(4)")
    private String deptNo;

    @Column(name = "dept_name")
    private String deptName;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade =
            CascadeType.ALL,  orphanRemoval = true)
    @JsonIgnore // annotation so that Jackson will ignore this field when converting to JSON
    //to fix the failed to lazily initialize error for endpoint 1
    private List<Dept_manager> dept_manager;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade =
            CascadeType.ALL,  orphanRemoval = true)
    @JsonIgnore
    private List<Dept_emp> dept_emp;

    public Departments() {}

    public Departments(String deptNo, String deptName) {
        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    public String getDeptNo() { return deptNo; }
    public void setDeptNo(String deptNo) { this.deptNo = deptNo; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public List<Dept_manager> getDept_manager() { return dept_manager; }
    public void setDept_manager(List<Dept_manager> dept_manager) { this.dept_manager = dept_manager; }

    public List<Dept_emp> getDept_emp() { return dept_emp; }
    public void setDept_emp(List<Dept_emp> dept_emp) { this.dept_emp = dept_emp; }

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