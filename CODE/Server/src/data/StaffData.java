package data;

import java.util.ArrayList;

public class StaffData
{
    private ArrayList<RegisterData> usersToBeAccepted;
    private ArrayList<String> exchangeCurrencies;
    private ArrayList<LoanData> loans;
    private ArrayList<StaffAccountData> staffAccountData;

    public StaffData()
    {
        this.exchangeCurrencies = null;
        this.usersToBeAccepted = null;
        this.staffAccountData = null;
        this.loans = null;
    }

    public void addExchangeCurrency(String currency)
    {
        if(exchangeCurrencies == null)
            exchangeCurrencies = new ArrayList<String>();
        exchangeCurrencies.add(currency);
    }

    public void addUsersToBeAccepted(RegisterData registerData)
    {
        if(usersToBeAccepted == null)
            usersToBeAccepted = new ArrayList<RegisterData>();
        usersToBeAccepted.add(registerData);
    }

    public void addStaffAccountData(StaffAccountData data)
    {
        if(staffAccountData == null)
            staffAccountData = new ArrayList<StaffAccountData>();
        staffAccountData.add(data);
    }

    public ArrayList<RegisterData> getUsersToBeAccepted()
    {
        return usersToBeAccepted;
    }

    public void setUsersToBeAccepted(ArrayList<RegisterData> usersToBeAccepted)
    {
        this.usersToBeAccepted = usersToBeAccepted;
    }

    public ArrayList<String> getExchangeCurrencies()
    {
        return exchangeCurrencies;
    }

    public void setExchangeCurrencies(ArrayList<String> exchangeCurrencies)
    {
        this.exchangeCurrencies = exchangeCurrencies;
    }

    public ArrayList<StaffAccountData> getStaffAccountData()
    {
        return staffAccountData;
    }

    public void setStaffAccountData(ArrayList<StaffAccountData> staffAccountData)
    {
        this.staffAccountData = staffAccountData;
    }

    public ArrayList<LoanData> getLoans()
    {
        return loans;
    }

    public void addLoan(LoanData loanData)
    {
        if(loans == null)
            loans = new ArrayList<LoanData>();
        this.loans.add(loanData);
    }

    public void setLoans(ArrayList<LoanData> loans)
    {
        this.loans = loans;
    }
}
