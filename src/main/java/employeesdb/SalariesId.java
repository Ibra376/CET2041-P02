package employeesdb;

import java.io.Serializable;
import java.time.LocalDate;

public class SalariesId implements Serializable {

    private int empNo;
    private LocalDate fromDate;

    public SalariesId(int empNo, LocalDate fromDate) {
        this.empNo = empNo;
        this.fromDate = fromDate;
    }
}
