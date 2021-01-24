package data;

public class AccountData
{
    private String currency;
    private String accountNumber;
    private String description;
    private String balance;


    public AccountData(String currency, String accountNumber, String description, String balance)
    {
        this.balance = balance;
        this.currency = currency;
        this.description = description;
        this.accountNumber = accountNumber;
    }


    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getBalance()
    {
        return balance;
    }

    public void setBalance(String balance)
    {
        this.balance = balance;
    }
}
