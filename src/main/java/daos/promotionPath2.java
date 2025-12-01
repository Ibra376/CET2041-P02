package daos;

public enum promotionPath2{
    STAFF("Staff"), SEN_STAFF("Senior Staff"), MANAGER("Manager");

    private final String dbString;

    promotionPath2(String dbString) { this.dbString = dbString;}
    public String getDbString() {
        return dbString;
    }
}