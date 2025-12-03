package employeesdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents the {@code employees} table in the Employees database.
 */
@Entity
@Table(name="employees")
public class Employees {

    /**
     * Unique identifier for the employee (primary key).
     */
    @Id
    @Column(name ="emp_no")
    int empNo;

    /**
     * Employee's date of birth.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * Employee's first name.
     */
    @Column(name ="first_name")
    private String firstName;

    /**
     * Employee's last name.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Employee's gender.
     */
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * Date the employee was hired.
     */
    @Column(name= "hire_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    /**
     * List of salary records associated with the employee.
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("employee")
    private List<Salaries> salary;

    /**
     * List of title records associated with the employee.
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("employee")
    private List<Titles> title;

    /**
     * Returns the list of salary records.
     *
     * @return list of {@link Salaries}
     */
    public List<Salaries> getSalary() {
        return salary;
    }

    /**
     * Sets the list of salary records.
     *
     * @param salary list of {@link Salaries}
     */
    public void setSalary(List<Salaries> salary) {
        this.salary = salary;
    }

    /**
     * Returns the list of title records.
     *
     * @return list of {@link Titles}
     */
    public List<Titles> getTitles() {
        return title;
    }

    /**
     * Sets the list of title records.
     *
     * @param title list of {@link Titles}
     */
    public void setTitles(List<Titles> title) {
        this.title = title;
    }

    /**
     * List of department-employee associations for this employee.
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("employee")
    private List<Dept_emp> dept_emp;

    /**
     * List of department-manager associations for this employee.
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("employee")
    private List<Dept_manager> dept_manager;

    /**
     * Default constructor.
     */
    public Employees() {}

    /**
     * Constructs a new employee entity.
     *
     * @param empNo    employee number
     * @param fromDate date of birth
     * @param gender   employee gender
     * @param hireDate hire date
     */
    public Employees(int empNo, LocalDate fromDate, Gender gender, LocalDate hireDate) {
        this.empNo = empNo;
        this.birthDate = fromDate;
        this.gender = gender;
        this.hireDate = hireDate;
    }

    /**
     * Returns the employee number.
     *
     * @return employee number
     */
    public int getEmpNo() { return empNo; }

    /**
     * Sets the employee number.
     *
     * @param empNo employee number
     */
    public void setEmpNo(int empNo) { this.empNo = empNo; }

    /**
     * Returns the employee's date of birth.
     *
     * @return birthdate
     */
    public LocalDate getBirthDate() { return birthDate; }

    /**
     * Sets the employee's date of birth.
     *
     * @param birthDate birthdate
     */
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    /**
     * Returns the employee's first name.
     *
     * @return first name
     */
    public String getFirstName() { return firstName; }

    /**
     * Sets the employee's first name.
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * Returns the employee's last name.
     *
     * @return last name
     */
    public String getLastName() { return lastName; }

    /**
     * Sets the employee's last name.
     *
     * @param lastName last name
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     * Returns the employee's gender.
     *
     * @return gender
     */
    public Gender getGender() { return gender; }

    /**
     * Sets the employee's gender.
     *
     * @param gender gender
     */
    public void setGender(Gender gender) { this.gender = gender; }

    /**
     * Returns the employee's hire date.
     *
     * @return hire date
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getHireDate() { return hireDate; }

    /**
     * Sets the employee's hire date.
     *
     * @param hireDate hire date
     */
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    /**
     * Returns the list of department-employee associations.
     *
     * @return list of {@link Dept_emp}
     */
    public List<Dept_emp> getDept_emp() { return dept_emp; }

    /**
     * Sets the list of department-employee associations.
     *
     * @param dept_emp list of {@link Dept_emp}
     */
    public void setDept_emp(List<Dept_emp> dept_emp) { this.dept_emp = dept_emp; }

    /**
     * Returns the list of department-manager associations.
     *
     * @return list of {@link Dept_manager}
     */
    public List<Dept_manager> getDept_manager() { return dept_manager; }

    /**
     * Sets the list of department-manager associations.
     *
     * @param dept_manager list of {@link Dept_manager}
     */
    public void setDept_manager(List<Dept_manager> dept_manager) { this.dept_manager = dept_manager; }

    /**
     * Returns a string representation of the employee, including salary records.
     *
     * @return formatted string with employee ID and salary details
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("\nEmployee{" + "id= "+empNo + "}\n"));
        for (Salaries salary : this.salary) {
            builder.append(salary.toString());
            builder.append("\n");
        }
        return builder.toString();
    }
}