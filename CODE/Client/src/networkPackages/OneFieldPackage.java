package networkPackages;

public class OneFieldPackage extends NetworkPackage
{
    private String value;

    public OneFieldPackage(NetworkType type, String value)
    {
        super(type);
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public String toString()
    {
        return getType().toString();
    }

}
