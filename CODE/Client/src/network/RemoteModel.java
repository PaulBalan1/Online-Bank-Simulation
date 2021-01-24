package network;


import data.RegisterData;
import model.PropertyChangeWorker;

public interface RemoteModel extends PropertyChangeWorker
{
    public void connect();
    public void disconnect();
    public void waitFromServer();
    public boolean isConnected();


    public void logInButton(String email, String password);
    public void registerButton();
    public void registerBackButton();
    public void registerNewAccount(RegisterData registerData);
    public void exitButton();

    public void openLogIn();


    public void clientRequestLoan(String sum);
    public void clientRequestTransfer(String fromAccount, String toAccount, String sum);

    public void closeAccount(String fromAccount);
    public void openAccount(String description , String currency);
    public void withdrawFunds(String account, String amount);
    public void addFunds(String account, String amount);

    public void changePasswordPressed(String oldPassword, String newPassword, String confirmNewPassword);

    public void changeNumberPressed(String newNumber);

    public void changeAdressPressed(String newAdreess);
    public void deleteAccountPressed();


    public void clientBackButton();

    public void getNewCLientData();

    public void staffBackButton();


    void getNewStaffData();

    void staffAcceptClient(String cpr);

    void staffChangeClientPassword(String value, String value1, String value2);

    void retriveClientDataPressed(String retriveClientDataField);

    void changeStaffPassword(String value, String value1, String value2);

    void managerChangeStaffPassword(String staffCPR, String value, String value1);

    void createNewStaff(String cpr, String name, String pass, String position);

    void deleteStaff(String cpr);

    void staffAcceptLoan(String id);

    void staffCloseServer();

    void managerChangeRate(String currency, String value);
}
