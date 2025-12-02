package DTO;

public class PromotionDTO {
    private int empNo;
    private String deptNo;
    private String newTitle;
    private int newSalary;
    private boolean isManager;

    public PromotionDTO(){}

    public int getEmpNo() {
        return empNo;
    }
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }
    public String getDeptNo() {
        return deptNo;
    }
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }
    public String getNewTitle() {
        return newTitle;
    }
    public void setNewTitle(String newTitle) {
            this.newTitle = newTitle;
    }
    public int getNewSalary() {
        return newSalary;
    }
    public void setNewSalary(int newSalary) {
        this.newSalary = newSalary;
    }
    public boolean isManager() {
        return isManager;
    }
    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }
}
