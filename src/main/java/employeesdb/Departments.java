package employeesdb;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class Departments {
    @Id
    @Column(name = "dept_no")
    private String deptNo;

    @Column(name = "dept_name")
    private String deptName;

    @OneToMany(mappedBy = "dept_no", fetch = FetchType.LAZY)
    private List<Dept_manager> dept_manager;

    @OneToMany(mappedBy = "emp_no", fetch = FetchType.LAZY)
    private List<Dept_emp>  dept_emp;

    public Departments() {}

    public Departments(String deptNo, String deptName) {
        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("\nDepartment{ id=%s, name=%s }\n", deptNo, deptName));
        for (Dept_manager dept_manager : this.dept_manager) {
            builder.append(dept_manager.toString());
            builder.append("\n");
        }
        for (Dept_emp dept_emp : this.dept_emp) {
            builder.append(dept_emp.toString());
            builder.append("\n");
        }
        return builder.toString();
    }
}