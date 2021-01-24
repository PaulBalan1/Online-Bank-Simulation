package model;

import data.ClientData;

import java.beans.PropertyChangeListener;

public interface ClientModel
{
    public ClientData getClientData(String cpr);
    public String transferFunds(String from, String to, String sum, String cpr);
    public String requestLoan(String sum, String cpr);

    public String closeAccount(String fromAccount, String cpr);
    public String openAccount(String description , String currency, String cpr);
    public String withdrawFunds(String account, String amount, String cpr);
    public String addFunds(String account, String amount, String cpr);


    public String changePasswordPressed(String oldPassword, String newPassword, String confirmNewPassword, String cpr);

    public String changeNumberPressed(String newNumber, String cpr);

    public String changeAdressPressed(String newAdreess, String cpr);
    public String deleteAccountPressed(String cpr);

    public void closeByIP(String ip);

    public void addListener(String propertyName, PropertyChangeListener listener);
}
