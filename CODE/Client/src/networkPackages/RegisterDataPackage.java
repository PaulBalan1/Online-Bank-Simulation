package networkPackages;
import data.RegisterData;

public class RegisterDataPackage extends NetworkPackage
{
    private RegisterData value;

    public RegisterDataPackage(NetworkType type, RegisterData value)
    {
        super(type);
        this.value = value;
    }

    public RegisterData getValue()
    {
        return value;
    }

    public String toString()
    {
        return getType().toString();
    }

}
