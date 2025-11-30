package restAPI;

class EmployeeDTO {
    public int empNo;
    public String birthDate;
    public String firstName;
    public String lastName;
    public String gender;
    public String hireDate;

    public EmployeeDTO(int empNo, String birthDate, String firstName, String lastName, String gender, String hireDate) {
        this.empNo = empNo;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hireDate = hireDate;
    }
}