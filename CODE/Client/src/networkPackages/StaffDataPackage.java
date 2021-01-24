package networkPackages;

import data.StaffData;

public class StaffDataPackage extends NetworkPackage
{
    private StaffData value;
    public StaffDataPackage(NetworkType type, StaffData staffData)
    {
        super(type);
        this.value = staffData;
    }

    public StaffData getValue()
    {
        return value;
    }

    public String toString()
    {
        return getType().toString();
    }
}
