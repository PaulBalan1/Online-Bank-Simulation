package data;

public class RegisterData
{
    private String fullName;
    private String CPR;
    private String adress;
    private String phoneNumber;
    private String email;
    private String password;
    private String confirmPassword;

    public RegisterData(String fullName, String CPR, String adress, String phoneNumber)
    {
        this.fullName = fullName;
        this.CPR = CPR;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
    }

    public void setRest(String email, String password, String confirmPassword)
    {
       this.email = email;
       this.password = password;
       this.confirmPassword = confirmPassword;
    }

    public String getFullName()
    {
        return fullName;
    }

    public String getCPR()
    {
        return CPR;
    }

    public String getAdress()
    {
        return adress;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }
}
