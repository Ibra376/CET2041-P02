package employeesdb;

import java.io.Serializable;
import java.time.LocalDate;

public class TitlesId implements Serializable {

    private int empNo;
    private String title;
    private LocalDate fromDate;

    public TitlesId(int empNo, String title, LocalDate fromDate) {
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
    }
}
