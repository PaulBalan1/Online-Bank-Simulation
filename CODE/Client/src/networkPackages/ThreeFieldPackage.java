package networkPackages;

public class ThreeFieldPackage extends NetworkPackage
{
    private String firstField;
    private String secondField;
    private String thirdField;

    public ThreeFieldPackage(NetworkType type, String firstField, String secondField, String thirdField)
    {
        super(type);
        this.firstField = firstField;
        this.secondField = secondField;
        this.thirdField = thirdField;
    }

    public String getFirstField()
    {
        return firstField;
    }

    public String getSecondField()
    {
        return secondField;
    }

    public String getThirdField()
    {
        return thirdField;
    }

    public String toString()
    {
        return getType().toString();
    }

}


