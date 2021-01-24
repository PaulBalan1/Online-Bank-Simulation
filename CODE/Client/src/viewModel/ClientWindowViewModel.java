package viewModel;
import data.ClientData;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import model.ClientWindowModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientWindowViewModel implements PropertyChangeListener
{
    private ClientWindowModel model;
    private StringProperty watch;
    private BooleanProperty newData;
    private BooleanProperty closeWindow;

    //Data declaration --------------
    private ClientData clientData;

    //Data end ----------


    //Home pane
    private StringProperty requestLoanAmount;
    private StringProperty transferInternAmount;
    private StringProperty transferForeignAccount;
    private StringProperty transferForeignAmount;
    private StringProperty mainAccountSold;
    private StringProperty homeError;

    //Home end


    //Accounts pane
    private StringProperty accountDescription;
    private StringProperty amount;
    private StringProperty accountErrors;
    //Accounds end


    //Settings pane
    private StringProperty oldPassword;
    private StringProperty newPassword;
    private StringProperty confirmNewPassword;
    private StringProperty settingsError;
    private StringProperty oldNumberLabel;
    private StringProperty oldAdressLabel;
    private StringProperty newNumberField;
    private StringProperty newAdressField;

    //Settings end


    public ClientWindowViewModel(ClientWindowModel model)
    {


        this.model = model;
        this.clientData = null;

        watch = new SimpleStringProperty("13:12:00");
        this.newData = new SimpleBooleanProperty(false);
        this.closeWindow = new SimpleBooleanProperty(false);
        this.oldPassword = new SimpleStringProperty();
        this.confirmNewPassword = new SimpleStringProperty();
        this.newPassword = new SimpleStringProperty();
        this.settingsError = new SimpleStringProperty("-------settings errors--------");
        this.accountDescription = new SimpleStringProperty();
        this.amount = new SimpleStringProperty();
        this.accountErrors = new SimpleStringProperty("---------accounts errors----------");
        this.requestLoanAmount = new SimpleStringProperty();
        this.transferInternAmount = new SimpleStringProperty();
        this.transferForeignAccount = new SimpleStringProperty();
        this.transferForeignAmount = new SimpleStringProperty();
        this.homeError = new SimpleStringProperty();
        this.mainAccountSold = new SimpleStringProperty("");
        this.oldNumberLabel = new SimpleStringProperty();
        this.oldAdressLabel = new SimpleStringProperty();
        this.newNumberField = new SimpleStringProperty();
        this.newAdressField = new SimpleStringProperty();



        Runnable runnable = () -> {
            while(true)
            {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        watch.setValue(formatter.format(date));
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        t.start();


    }

    public void resetErrors()
    {
        this.settingsError.setValue("");
        this.accountErrors.setValue("");
        this.homeError.setValue("");
    }

    public BooleanProperty getNewDataProperty()
    {
        return newData;
    }

    public void setNewData(boolean b)
    {
        this.newData.setValue(b);
    }

    public StringProperty getWatchProperty()
    {
        return this.watch;
    }


    public String getAdress()
    {
        return this.clientData.getAdress();
    }

    public String getPhoneNumber()
    {
        return this.clientData.getPhoneNumber();
    }

    public String getEmail()
    {
        return this.clientData.getEmailAdress();
    }

    public void backButton()
    {
        model.clientBackButton();
    }

    public BooleanProperty getCloseWindowProperty()
    {
        return closeWindow;
    }

    public void setCloseWindow(boolean closeWindow)
    {
        this.closeWindow.set(closeWindow);
    }

    public void getNewCLientData()
    {
        model.getNewClientData();
    }

    public ClientData getClientData()
    {
        return this.clientData;
    }






    //Home pane ------------

    public StringProperty getRequestLoanAmountProperty()
    {
        return requestLoanAmount;
    }

    public StringProperty getTransferInternAmountProperty()
    {
        return transferInternAmount;
    }


    public StringProperty getTransferForeignAccountProperty()
    {
        return transferForeignAccount;
    }


    public StringProperty getTransferForeignAmountProperty()
    {
        return transferForeignAmount;
    }


    public StringProperty getMainAccountSoldProperty()
    {
        return mainAccountSold;
    }


    public StringProperty getHomeErrorProperty()
    {
        return homeError;
    }



    public void transferIntern(String inAccount, String outAccount)
    {
        //this.transferInternAmount
        model.clientRequestTransfer(inAccount, outAccount, this.transferInternAmount.getValue());
    }

    public void transferForeign(String inAccount)
    {
        model.clientRequestTransfer( inAccount, this.transferForeignAccount.getValue(), this.transferForeignAmount.getValue());
    }

    public void requestLoan()
    {
        //this.requestLoanAmount
        model.clientRequestLoan(this.requestLoanAmount.getValue());
    }

    public void resetHomeField()
    {

            this.requestLoanAmount.setValue("");
            this.transferInternAmount.setValue("");
            this.transferForeignAccount.setValue("");
            this.transferForeignAmount.setValue("");
            this.mainAccountSold.setValue("");

    }

    //Home end-------------




    //Accounts pane -------------

    public StringProperty getAccountDescriptionProperty()
    {
        return accountDescription;
    }

    public StringProperty getAmountProperty()
    {
        return amount;
    }

    public StringProperty getAccountErrorsProperty() {
        return accountErrors;
    }


    public void resetAccountsFields()
    {
            this.accountDescription.setValue("");
            this.amount.setValue("");
    }


    public void deleteAccount(String accountNumber)
    {
        model.closeAccount(accountNumber);
    }

    public void createAccount(String currency)
    {
        model.openAccount(this.accountDescription.getValue(), currency);
    }

    public void addFunds(String accountNumber)
    {
        model.addFunds(accountNumber, this.amount.getValue());
    }

    public void withdrawFunds(String accountNumber)
    {
        model.withdrawFunds(accountNumber, this.amount.getValue());
    }


    //Accounts end-----------




    //History pane --------------------------


    //History end ----------------





    //Settings pane -----------------------------------

    public StringProperty getOldPasswordProperty()
    {
        return this.oldPassword;
    }

    public StringProperty getNewPasswordProperty()
    {
        return this.newPassword;
    }

    public StringProperty getConfirmNewPasswordProperty()
    {
        return this.confirmNewPassword;
    }

    public StringProperty getSettingsErrorProperty()
    {
        return this.settingsError;
    }

    public StringProperty getOldNumberLabelProperty()
    {
        return oldNumberLabel;
    }

    public StringProperty getOldAdressLabelProperty()
    {
        return oldAdressLabel;
    }

    public StringProperty getNewNumberFieldProperty()
    {
        return newNumberField;
    }


    public StringProperty getNewAdressFieldProperty()
    {
        return newAdressField;
    }

    public void resetSettingsFields()
    {
        Platform.runLater(()->
        {
            this.oldPassword.setValue("");
            this.newPassword.setValue("");
            this.confirmNewPassword.setValue("");
            newAdressField.setValue("");
            newNumberField.setValue("");
            oldAdressLabel.setValue("");
            oldNumberLabel.setValue("");
        });
    }

    public void loadSettings()
    {
        Platform.runLater(()->
        {
            oldNumberLabel.setValue(clientData.getPhoneNumber());
            oldAdressLabel.setValue(clientData.getAdress());
        });
    }

    public void changePassword()
    {
        model.changePasswordPressed(oldPassword.getValue(), newPassword.getValue(), confirmNewPassword.getValue());
    }

    public void changeNumberPressed()
    {
        model.changeNumberPressed(this.newNumberField.getValue());
    }

    public void changeAdressPressed()
    {
        model.changeAdressPressed(this.newAdressField.getValue());
    }

    public void deleteAccountPressed()
    {
        model.deleteAccountPressed();
    }




    //Settings end -------------------------------------



    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        Platform.runLater(() -> {
            switch (event.getPropertyName())
            {
                case "NewClientData":
                    this.setNewData(true);
                    this.clientData = (ClientData)event.getNewValue();
                    break;
                case "ClientOpenLogInWindow":
                    this.setCloseWindow(true);
                    break;
                case "ClientHomeSuccess":
                    this.getNewCLientData();
                    this.homeError.setValue(event.getNewValue().toString());
                    break;
                case "ClientHomeError":
                    this.homeError.setValue(event.getNewValue().toString());
                    break;

                case "ClientAccountsSuccess":
                    this.getNewCLientData();
                    this.accountErrors.setValue(event.getNewValue().toString());
                    break;
                case "ClientAccountsError":
                    this.accountErrors.setValue(event.getNewValue().toString());
                    break;

                case "ClientDeleteSuccess":
                    this.backButton();
                    break;
                case "ClientSettingsError":
                    this.settingsError.setValue(event.getNewValue().toString());
                    break;
                case "ClientSettingsSuccess":
                    this.getNewCLientData();
                    this.settingsError.setValue(event.getNewValue().toString());
                    break;
            }
        });
    }



}
