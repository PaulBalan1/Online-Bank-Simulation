package data;

public class LoanData
{
    private String id;
    private String cpr;
    private String amount;

    public LoanData(String id, String cpr, String amount)
    {
        this.id = id;
        this.cpr = cpr;
        this.amount = amount;
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCpr()
    {
        return cpr;
    }

    public void setCpr(String cpr)
    {
        this.cpr = cpr;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }
}
