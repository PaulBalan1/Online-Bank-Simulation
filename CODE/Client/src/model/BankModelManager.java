package model;

import data.RegisterData;
import network.AppCLient;
import network.RemoteModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BankModelManager implements BankModel, ClientWindowModel, LogInWindowModel, RegisterWindowModel, StaffWindowModel
{
    private PropertyChangeSupport support;
    private RemoteModel appCLient;

    public BankModelManager(String host, int port)
    {
        support = new PropertyChangeSupport(this);
        appCLient = new AppCLient(host, port);


        appCLient.addListener("OpenRegisterWindow", this);
        appCLient.addListener("OpenClientWindow", this);
        appCLient.addListener("OpenStaffWindow", this);
        appCLient.addListener("CloseApp", this);
        appCLient.addListener("RegisterOpenLogInWindow", this);
        appCLient.addListener("RegisterSuccess", this);
        appCLient.addListener("RegisterError", this);
        appCLient.addListener("LogInError", this);

        appCLient.addListener("ClientOpenLogInWindow", this);
        appCLient.addListener("NewClientData", this);
        appCLient.addListener("ClientHomeSuccess", this);
        appCLient.addListener("ClientHomeError", this);
        appCLient.addListener("ClientAccountsSuccess", this);
        appCLient.addListener("ClientAccountsError", this);

        appCLient.addListener("ClientDeleteSuccess", this);
        appCLient.addListener("ClientSettingsError", this);
        appCLient.addListener("ClientSettingsSuccess", this);

        appCLient.addListener("NewStaffData", this);
        appCLient.addListener("StaffManageUsersSuccess", this);
        appCLient.addListener("StaffManageUsersError", this);
        appCLient.addListener("StaffManageNewData", this);
        appCLient.addListener("StaffSettingsSuccess", this);
        appCLient.addListener("StaffSettingsError", this);
        appCLient.addListener("StaffManagerSuccess", this);
        appCLient.addListener("StaffManagerError", this);


        appCLient.connect();
        appCLient.waitFromServer();
    }

    @Override
    public RemoteModel getRemoteModel()
    {
        return this.appCLient;
    }


    public void logInButton(String email, String password)
    {
        appCLient.logInButton(email, password);
    }
    public void registerButton()
    {
        appCLient.registerButton();
    }
    public void registerBackButton()
    {
        appCLient.registerBackButton();
    }
    public void registerNewAccount(RegisterData registerData)
    {
        appCLient.registerNewAccount(registerData);
    }

    public void exitApp()
    {
        appCLient.exitButton();
    }

    public void clientBackButton()
    {
        appCLient.clientBackButton();
    }

    @Override
    public void getNewClientData()
    {
        appCLient.getNewCLientData();
    }

    @Override
    public void clientRequestLoan(String sum)
    {
        appCLient.clientRequestLoan(sum);
    }

    @Override
    public void clientRequestTransfer(String fromAccount, String toAccount, String sum)
    {
        appCLient.clientRequestTransfer(fromAccount, toAccount, sum);
    }

    @Override
    public void closeAccount(String fromAccount)
    {
        appCLient.closeAccount(fromAccount);
    }

    @Override
    public void openAccount(String description, String currency)
    {
        appCLient.openAccount(description, currency);
    }

    @Override
    public void withdrawFunds(String account, String amount)
    {
        appCLient.withdrawFunds(account, amount);
    }

    @Override
    public void addFunds(String account, String amount)
    {
        appCLient.addFunds(account, amount);
    }

    @Override
    public void changePasswordPressed(String oldPassword, String newPassword, String confirmNewPassword)
    {
        appCLient.changePasswordPressed(oldPassword, newPassword, confirmNewPassword);
    }

    @Override
    public void changeNumberPressed(String newNumber)
    {
        appCLient.changeNumberPressed(newNumber);
    }

    @Override
    public void changeAdressPressed(String newAdreess)
    {
        appCLient.changeAdressPressed(newAdreess);
    }

    @Override
    public void deleteAccountPressed()
    {
        appCLient.deleteAccountPressed();
    }


    //STAFF--------

    @Override
    public void staffBackButton()
    {
        appCLient.staffBackButton();
    }

    @Override
    public void getNewStaffData()
    {
        appCLient.getNewStaffData();
    }

    @Override
    public void staffAcceptClient(String cpr)
    {
        appCLient.staffAcceptClient(cpr);
    }

    @Override
    public void staffChangeClientPassword(String value, String value1, String value2)
    {
        appCLient.staffChangeClientPassword(value, value1, value2);
    }

    @Override
    public void retriveClientDataPressed(String retriveClientDataField)
    {
        appCLient.retriveClientDataPressed(retriveClientDataField);
    }

    @Override
    public void changeStaffPassword(String value, String value1, String value2)
    {
        appCLient.changeStaffPassword(value, value1, value2);
    }

    @Override
    public void managerChangeStaffPassword(String staffCPR, String value, String value1)
    {
        appCLient.managerChangeStaffPassword(staffCPR, value, value1);
    }

    @Override
    public void createNewStaff(String cpr, String name, String pass, String position)
    {
        appCLient.createNewStaff(cpr, name, pass, position);
    }

    @Override
    public void deleteStaff(String cpr)
    {
        appCLient.deleteStaff(cpr);
    }

    @Override
    public void staffAcceptLoan(String id)
    {
        appCLient.staffAcceptLoan(id);
    }

    @Override
    public void staffCloseServer()
    {
        appCLient.staffCloseServer();
    }

    @Override
    public void managerChangeRate(String currency, String value)
    {
        appCLient.managerChangeRate(currency, value);
    }


    @Override
    public void addListener(String propertyName, PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(propertyName, listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        switch (event.getPropertyName())
        {
            //LOG IN WINDOW-----------
            case "OpenRegisterWindow":
                support.firePropertyChange("OpenRegisterWindow", "", "1");
                break;
            case "OpenClientWindow":
                support.firePropertyChange("OpenClientWindow", "", "1");
                break;
            case "OpenStaffWindow":
                support.firePropertyChange("OpenStaffWindow", "", "1");
                break;
            case "CloseApp":
                support.firePropertyChange("CloseApp", "", "1");
                break;
            case "LogInError":
                support.firePropertyChange("LogInError", "",  event.getNewValue());
                break;


            //REGISTER WINDOW-------------
            case "RegisterOpenLogInWindow":
                support.firePropertyChange("RegisterOpenLogInWindow", "", "1");
                break;
            case "RegisterSuccess":
                support.firePropertyChange("RegisterSuccess", "", event.getNewValue());
                break;
            case "RegisterError":
                support.firePropertyChange("RegisterError", "",  event.getNewValue());
                break;


            //CLIENT WINDOW--------
            case "ClientOpenLogInWindow":
                support.firePropertyChange("ClientOpenLogInWindow", "",  event.getNewValue());
                break;
            case "NewClientData":
                support.firePropertyChange("NewClientData", "",  event.getNewValue());
                break;
            case "ClientHomeSuccess":
                support.firePropertyChange("ClientHomeSuccess", "",  event.getNewValue());
                break;
            case "ClientHomeError":
                support.firePropertyChange("ClientHomeError", "",  event.getNewValue());
                break;

            case "ClientAccountsSuccess":
                support.firePropertyChange("ClientAccountsSuccess", "",  event.getNewValue());
                break;
            case "ClientAccountsError":
                support.firePropertyChange("ClientAccountsError", "",  event.getNewValue());
                break;

            case "ClientDeleteSuccess":
                support.firePropertyChange("ClientDeleteSuccess", "",  event.getNewValue());
                break;
            case "ClientSettingsError":
                support.firePropertyChange("ClientSettingsError", "",  event.getNewValue());
                break;
            case "ClientSettingsSuccess":
                support.firePropertyChange("ClientSettingsSuccess", "",  event.getNewValue());
                break;

            //Staff window
            case "StaffOpenLogInWindow":
                support.firePropertyChange("StaffOpenLogInWindow", "",  event.getNewValue());
                break;
            case "NewStaffData":
                support.firePropertyChange("NewStaffData", "",  event.getNewValue());
                break;
            case "StaffManageUsersSuccess":
                support.firePropertyChange("StaffManageUsersSuccess", "",  event.getNewValue());
                break;
            case "StaffManageUsersError":
                support.firePropertyChange("StaffManageUsersError", "",  event.getNewValue());
                break;
            case "StaffManageNewData":
                support.firePropertyChange("StaffManageNewData", "",  event.getNewValue());
                break;
            case "StaffSettingsSuccess":
                support.firePropertyChange("StaffSettingsSuccess", "",  event.getNewValue());
                break;
            case "StaffSettingsError":
                support.firePropertyChange("StaffSettingsError", "",  event.getNewValue());
                break;
            case "StaffManagerSuccess":
                support.firePropertyChange("StaffManagerSuccess", "",  event.getNewValue());
                break;
            case "StaffManagerError":
                support.firePropertyChange("StaffManagerError", "",  event.getNewValue());
                break;


        }

    }

}
