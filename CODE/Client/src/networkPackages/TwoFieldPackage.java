package networkPackages;

public class TwoFieldPackage extends NetworkPackage
{
    private String firstField;
    private String secondField;

    public TwoFieldPackage(NetworkType type, String firstField, String secondField)
    {
        super(type);
        this.firstField = firstField;
        this.secondField = secondField;
    }

    public String getFirstField()
    {
        return firstField;
    }

    public String getSecondField()
    {
        return secondField;
    }

    public String toString()
    {
        return getType().toString();
    }

}

