package daos;

public enum promotionPath1{
    AST_ENG("Assistant Engineer"), ENG("Engineer"), SEN_ENG("Senior Engineer"), MANAGER("Manager"), TECH_LEAD(
            "Technique Leader");

    private final String dbString;

    promotionPath1(String dbString) { this.dbString = dbString;}

    public String getDbString() {
        return dbString;
    }
}