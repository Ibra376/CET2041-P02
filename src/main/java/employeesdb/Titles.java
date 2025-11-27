package employeesdb;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@IdClass(TitlesId.class)
@Table(name = "titles")
public class Titles {
    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Id
    @Column(name = "title")
    private String title;

    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}
