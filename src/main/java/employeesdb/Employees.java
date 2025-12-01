package employeesdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name="employees")
public class Employees {
    @Id
    @Column(name ="emp_no")
    int empNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name ="first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name= "hire_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Salaries> salary;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Titles> title;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Dept_emp>  dept_emp;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<Dept_manager>  dept_manager;

    public Employees() {}

    public Employees(int empNo, LocalDate fromDate, Gender gender, LocalDate hireDate) {
        this.empNo = empNo;
        this.birthDate = fromDate;
        this.gender = gender;
        this.hireDate = hireDate;
    }

    public int getEmpNo() {return empNo; }
    public void setEmpNo(int empNo) {this.empNo = empNo;}

    public LocalDate getBirthDate() {return birthDate;}
    public void setBirthDate(LocalDate birthDate) {this.birthDate = birthDate;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public Gender getGender() {return gender;}
    public void setGender(Gender gender) {this.gender = gender;}

    public LocalDate getHireDate() {return hireDate;}
    public void setHireDate(LocalDate hireDate) {this.hireDate = hireDate;}

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
