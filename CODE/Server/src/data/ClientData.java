package data;

import java.util.ArrayList;

public class ClientData
{
    private ArrayList<AccountData> accountDataList;
    private ArrayList<HistoryData> historyDataList;
    private ArrayList<String> exchangeCurrencies;
    private String phoneNumber;
    private String emailAdress;
    private String adress;

    public ClientData()
    {
        accountDataList = null;
        historyDataList = null;
        exchangeCurrencies = null;
        phoneNumber = null;
        emailAdress = null;
        adress = null;
    }

    public void setAccountDataList(ArrayList<AccountData> accountDataList)
    {
        this.accountDataList = accountDataList;
    }

    public void setHistoryDataList(ArrayList<HistoryData> historyDataList)
    {
        this.historyDataList = historyDataList;
    }

    public void setExchangeCurrencies(ArrayList<String> exchangeCurrencies)
    {
        this.exchangeCurrencies = exchangeCurrencies;
    }

    public void addAccountData(AccountData account)
    {
        if(accountDataList == null)
            accountDataList = new ArrayList<AccountData>();
        accountDataList.add(account);
    }

    public void addHistoryData(HistoryData historyData)
    {
        if(historyDataList == null)
            historyDataList = new ArrayList<HistoryData>();
        historyDataList.add(historyData);
    }

    public void addExchangeCurrency(String currency)
    {
        if(exchangeCurrencies == null)
            exchangeCurrencies = new ArrayList<String>();
        exchangeCurrencies.add(currency);
    }

    public void setAdress(String adress)
    {
        this.adress = adress;
    }


    public void setEmailAdress(String emailAdress)
    {
        this.emailAdress = emailAdress;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<AccountData> getAccountDataList()
    {
        return accountDataList;
    }

    public ArrayList<HistoryData> getHistoryDataList()
    {
        return historyDataList;
    }

    public ArrayList<String> getExchangeCurrencies()
    {
        return exchangeCurrencies;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmailAdress()
    {
        return emailAdress;
    }

    public String getAdress()
    {
        return adress;
    }
}
