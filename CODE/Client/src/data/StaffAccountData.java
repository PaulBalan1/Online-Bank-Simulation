package data;

public class StaffAccountData
{
    private String name;
    private String cpr;
    private String position;

    public StaffAccountData(String name, String cpr, String position)
    {
        this.name = name;
        this.cpr = cpr;
        this.position = position;
    }

    public String getName()
    {
        return name;
    }

    public String getCpr()
    {
        return cpr;
    }

    public String getPosition()
    {
        return position;
    }
}
