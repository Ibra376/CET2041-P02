package employeesdb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents the {@code titles} table in the Employees database.
 */
@Entity
@Table(name = "titles")
@NamedQueries({
        @NamedQuery(
                name = "titles.findCurrent",
                query = "SELECT t FROM Titles t WHERE t.titlesId.empNo = :empNo AND t.toDate = :maxDate"
        ),
        @NamedQuery(
                name ="titles.updatedToday",
                query="SELECT COUNT(t) FROM Titles t WHERE t.titlesId.empNo =:empNo " +
                        "AND t.titlesId.title = :title AND t.titlesId.fromDate = :fromDate"
        )
})
public class Titles {

    /**
     * Composite primary key consisting of employee number, title, and start date.
     */
    @EmbeddedId
    private TitlesId titlesId;

    /**
     * End date of the title record.
     */
    @Column(name = "to_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    /**
     * Reference to the employee associated with this title record.
     */
    @ManyToOne
    @JoinColumn(name="emp_no", insertable=false, updatable=false )
    @JsonIgnore
    private Employees employee;

    /**
     * Default constructor.
     */
    public Titles() {}

    /**
     * Constructs a new title record.
     *
     * @param titlesId composite ID containing employee number, title, and start date
     * @param toDate   end date of the title record
     * @param employee associated employee
     */
    public Titles(TitlesId titlesId, LocalDate toDate, Employees employee) {
        this.titlesId = titlesId;
        this.toDate = toDate;
        this.employee = employee;
    }

    /**
     * Returns the end date of the title record.
     *
     * @return end date
     */
    public LocalDate getToDate() { return toDate; }

    /**
     * Sets the end date of the title record.
     *
     * @param toDate end date
     */
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    /**
     * Returns the employee associated with this title record.
     *
     * @return {@link Employees} entity
     */
    public Employees getEmployee() { return employee; }

    /**
     * Sets the employee associated with this title record.
     *
     * @param employee {@link Employees} entity
     */
    public void setEmployee(Employees employee) { this.employee = employee; }

    /**
     * Returns the start date of the title record.
     *
     * @return start date
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getFromDate() {
        return titlesId.getFromDate();
    }

    /**
     * Returns the job title.
     *
     * @return job title
     */
    public String getTitle() {
        return titlesId.getTitle();
    }

    /**
     * Composite primary key class for {@link Titles}.
     * <p>
     * Consists of employee number, title, and start date.
     * </p>
     */
    @Embeddable
    public static class TitlesId implements Serializable {

        /**
         * Employee number.
         */
        @Column(name = "emp_no")
        private int empNo;

        /**
         * Job title.
         */
        @Column(name = "title")
        private String title;

        /**
         * Start date of the title record.
         */
        @Column(name = "from_date")
        private LocalDate fromDate;

        /**
         * Default constructor.
         */
        public TitlesId() {}

        /**
         * Constructs a new composite ID.
         *
         * @param empNo    employee number
         * @param title    job title
         * @param fromDate start date
         */
        public TitlesId(int empNo, String title, LocalDate fromDate) {
            this.empNo = empNo;
            this.title = title;
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
         * Returns the job title.
         *
         * @return job title
         */
        public String getTitle() { return title; }

        /**
         * Sets the job title.
         *
         * @param title job title
         */
        public void setTitle(String title) { this.title = title; }

        /**
         * Returns the start date of the title record.
         *
         * @return start date
         */
        public LocalDate getFromDate() { return fromDate; }

        /**
         * Sets the start date of the title record.
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
            if (!(o instanceof TitlesId)) return false;
            TitlesId that = (TitlesId) o;
            return empNo == that.empNo &&
                    Objects.equals(fromDate, that.fromDate) &&
                    Objects.equals(title, that.title);
        }

        /**
         * Returns the hash code for this ID.
         *
         * @return hash code based on empNo, fromDate, and title
         */
        @Override
        public int hashCode() {
            return Objects.hash(getEmpNo(), getFromDate(), getTitle());
        }
    }
}