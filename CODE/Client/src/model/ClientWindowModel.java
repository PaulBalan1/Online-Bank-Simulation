package model;

public interface ClientWindowModel
{
    public void clientBackButton();
    public void getNewClientData();

    //Home tab --
    public void clientRequestLoan(String sum);
    public void clientRequestTransfer(String fromAccount, String toAccount, String sum);
    //Home end---

    //Account tab --
    public void closeAccount(String fromAccount);
    public void openAccount(String description , String currency);
    public void withdrawFunds(String account, String amount);
    public void addFunds(String account, String amount);
    //Home end---

    //History tab--

    //History end--


    //Settings tab---
    public void changePasswordPressed(String oldPassword, String newPassword, String confirmNewPassword);

    public void changeNumberPressed(String newNumber);

    public void changeAdressPressed(String newAdreess);

    public void deleteAccountPressed();
    //Settings end---

}
