package employeesdb;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "titles")
public class Titles {
    @EmbeddedId
    private  TitlesId titlesId;

    @Column(name = "to_date")
    private LocalDate toDate;

    @ManyToOne
    @JoinColumn(name="emp_no",  insertable=false, updatable=false )
    private Employees employee;

    public Titles() {}

    public Titles(TitlesId titlesId, LocalDate toDate, Employees employee) {
        this.titlesId = titlesId;
        this.toDate = toDate;
        this.employee = employee;
    }

    public LocalDate getToDate() { return toDate;}
    public void setToDate(LocalDate toDate) {this.toDate = toDate;}

    public Employees getEmployee() {return employee;}
    public void setEmployee(Employees employee) {this.employee = employee;}

    @Embeddable
    public static class TitlesId implements Serializable
    {
        @Column(name = "emp_no")
        private int empNo;

        @Column(name = "title")
        private String title;

        @Column(name = "from_date")
        private LocalDate fromDate;

        public TitlesId() {}

        public TitlesId(int empNo, String title,  LocalDate fromDate) {
            this.empNo = empNo;
            this.title = title;
            this.fromDate = fromDate;
        }

        public int getEmpNo() { return empNo;}
        public void setEmpNo(int empNo) { this.empNo = empNo;}

        public String getTitle() { return title;}
        public void setTitle(String title) { this.title = title;}

        public LocalDate getFromDate() { return fromDate;}
        public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate;}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TitlesId)) return false;
            TitlesId that = (TitlesId) o;
            return empNo == that.empNo &&  fromDate.equals(that.fromDate) && title == that.title;
        }

        @Override
        public int hashCode() { return Objects.hash (empNo, fromDate, title);}
    }
}
