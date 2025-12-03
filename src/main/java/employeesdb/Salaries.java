package employeesdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents the {@code salaries} table in the Employees database.
 * <p>
 * This entity models salary records for employees, including salary amount,
 * start and end dates, and the relationship to the {@link Employees} entity.
 * </p>
 *
 * <p>Named queries:</p>
 * <ul>
 *   <li>{@code salaries.findCurrent} - Finds the current salary record for a given employee.</li>
 *   <li>{@code salaries.updatedToday} - Checks if a salary record was updated today for a given employee.</li>
 * </ul>
 */
@Entity
@Table(name="salaries")
@NamedQueries({
        @NamedQuery(
                name="salaries.findCurrent",
                query = "SELECT s FROM Salaries s WHERE s.salariesId.empNo = :empNo AND s.toDate = :maxDate"
        ),
        @NamedQuery(
                name="salaries.updatedToday",
                query="SELECT COUNT(s) FROM Salaries s WHERE s.salariesId.empNo = :empNo AND s.salariesId.fromDate= :fromDate"
        )
})
public class Salaries {

    /**
     * Composite primary key consisting of employee number and start date.
     */
    @EmbeddedId
    private SalariesId salariesId;

    /**
     * Salary amount for the employee.
     */
    @Column(name = "salary")
    private int salary;

    /**
     * End date of the salary record.
     */
    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    /**
     * Reference to the employee associated with this salary record.
     */
    @ManyToOne
    @JoinColumn(name="emp_no", insertable=false, updatable=false )
    @JsonIgnoreProperties("salary")
    private Employees employee;

    /**
     * Default constructor.
     */
    public Salaries() {}

    /**
     * Constructs a new salary record.
     *
     * @param salariesId composite ID containing employee number and start date
     * @param salary     salary amount
     * @param toDate     end date of the salary record
     * @param employee   associated employee
     */
    public Salaries(SalariesId salariesId, int salary, LocalDate toDate, Employees employee) {
        this.salariesId = salariesId;
        this.salary = salary;
        this.toDate = toDate;
        this.employee = employee;
    }

    /**
     * Returns the salary amount.
     *
     * @return salary amount
     */
    public int getSalary() { return salary; }

    /**
     * Sets the salary amount.
     *
     * @param salary salary amount
     */
    public void setSalary(int salary) { this.salary = salary; }

    /**
     * Returns the end date of the salary record.
     *
     * @return end date
     */
    public LocalDate getToDate() { return toDate; }

    /**
     * Sets the end date of the salary record.
     *
     * @param toDate end date
     */
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    /**
     * Returns the start date of the salary record.
     *
     * @return start date
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getFromDate() { return salariesId.getFromDate(); }

    /**
     * Sets the start date of the salary record.
     *
     * @param fromDate start date
     */
    public void setFromDate(LocalDate fromDate) { this.salariesId.setFromDate(fromDate); }

    /**
     * Returns the employee associated with this salary record.
     *
     * @return {@link Employees} entity
     */
    public Employees getEmployee() { return employee; }

    /**
     * Sets the employee associated with this salary record.
     *
     * @param employee {@link Employees} entity
     */
    public void setEmployee(Employees employee) { this.employee = employee; }

    /**
     * Returns a string representation of the salary record.
     *
     * @return formatted string with fromDate, toDate, and salary
     */
    @Override
    public String toString() {
        return "From: "+salariesId.fromDate+", To: "+toDate+", Salary: "+salary;
    }

    /**
     * Composite primary key class for {@link Salaries}.
     * <p>
     * Consists of employee number and start date.
     * </p>
     */
    @Embeddable
    public static class SalariesId implements Serializable {

        /**
         * Employee number.
         */
        @Column(name = "emp_no")
        private int empNo;

        /**
         * Start date of the salary record.
         */
        @Column(name = "from_date")
        private LocalDate fromDate;

        /**
         * Default constructor.
         */
        public SalariesId() {}

        /**
         * Constructs a new composite ID.
         *
         * @param empNo    employee number
         * @param fromDate start date
         */
        public SalariesId(int empNo, LocalDate fromDate) {
            this.empNo = empNo;
            this.fromDate = fromDate;
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
         * Returns the start date of the salary record.
         *
         * @return start date
         */
        @JsonGetter("fromDate")
        @JsonFormat(pattern = "yyyy-MM-dd")
        public LocalDate getFromDate() { return fromDate; }

        /**
         * Sets the start date of the salary record.
         *
         * @param fromDate start date
         */
        public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

        /**
         * Compares this ID with another for equality.
         *
         * @param o other object
         * @return true if both IDs match, false otherwise
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SalariesId)) return false;
            SalariesId that = (SalariesId) o;
            return empNo == that.empNo && fromDate.equals(that.fromDate);
        }

        /**
         * Returns the hash code for this ID.
         *
         * @return hash code based on empNo and fromDate
         */
        @Override
        public int hashCode() { return Objects.hash(getEmpNo(), getFromDate()); }
    }
}