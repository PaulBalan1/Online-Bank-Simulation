package viewModel;

import data.RegisterData;
import data.StaffAccountData;
import data.StaffData;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.StaffWindowModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StaffWindowViewModel implements PropertyChangeListener
{
    private StringProperty watch;
    private StaffWindowModel model;
    private BooleanProperty newData;
    private BooleanProperty closeWindow;

    private StaffData staffData;

    private StringProperty clientCPR;
    private StringProperty clientName;
    private StringProperty clientEmail;
    private StringProperty clientPhone;
    private StringProperty clientAdress;
    private StringProperty retriveClientDataField;
    private StringProperty clientPasswordCPRField;
    private StringProperty newClientPasswordField;
    private StringProperty confirmNewClientPasswordField;
    private StringProperty manageCLientsErrorLabel;

    private StringProperty oldPasswordField;
    private StringProperty newPasswordField;
    private StringProperty confirmNewPasswordField;
    private StringProperty staffSettingsErrorLabel;

    private StringProperty nameField;
    private StringProperty passwordField;
    private StringProperty cprField;
    private StringProperty staffNewPassword;
    private StringProperty staffConfirmNewPassword;
    private StringProperty newExchangeRateField;
    private StringProperty staffErrorLabel;



    public StaffWindowViewModel(StaffWindowModel model)
    {
        this.watch = new SimpleStringProperty("42:04:20");
        this.model = model;
        this.newData = new SimpleBooleanProperty(false);
        this.closeWindow = new SimpleBooleanProperty(false);
        this.staffData = null;

        this.clientCPR = new SimpleStringProperty("");
        this.clientName = new SimpleStringProperty("");
        this.clientEmail = new SimpleStringProperty("");
        this.clientPhone = new SimpleStringProperty("");
        this.clientAdress = new SimpleStringProperty("");
        this.retriveClientDataField = new SimpleStringProperty("");
        this.clientPasswordCPRField = new SimpleStringProperty("");
        this.newClientPasswordField = new SimpleStringProperty("");
        this.confirmNewClientPasswordField = new SimpleStringProperty("");
        this.manageCLientsErrorLabel = new SimpleStringProperty("ERRORS");


        this.oldPasswordField = new SimpleStringProperty("");
        this.newPasswordField = new SimpleStringProperty("");
        this.confirmNewPasswordField = new SimpleStringProperty("");
        this.staffSettingsErrorLabel = new SimpleStringProperty("--ERRORS--");

        this.nameField = new SimpleStringProperty("");
        this.passwordField = new SimpleStringProperty("");
        this.cprField = new SimpleStringProperty("");
        this.staffNewPassword = new SimpleStringProperty("");
        this.staffConfirmNewPassword = new SimpleStringProperty("");
        this.newExchangeRateField = new SimpleStringProperty("");
        this.staffErrorLabel = new SimpleStringProperty("--ERRORS--");


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

    public StringProperty getWatchProperty()
    {
        return this.watch;
    }



    public BooleanProperty getNewDataProperty()
    {
        return newData;
    }

    public void setNewData(boolean newData)
    {
        this.newData.set(newData);
    }


    public void staffAcceptClient(String cpr)
    {
        model.staffAcceptClient(cpr);
    }

    public void resetStaffData()
    {
        this.staffData = null;
    }

    public void resetErrors()
    {
        Platform.runLater(()->
        {
            this.staffSettingsErrorLabel.setValue("");
            this.manageCLientsErrorLabel.setValue("");
            this.staffErrorLabel.setValue("");
        });
    }



    public StaffData getStaffData()
    {
        return staffData;
    }


    public StringProperty clientCPRProperty()
    {
        return clientCPR;
    }

    public StringProperty clientNameProperty()
    {
        return clientName;
    }

    public StringProperty clientEmailProperty()
    {
        return clientEmail;
    }

    public StringProperty clientPhoneProperty()
    {
        return clientPhone;
    }

    public StringProperty clientAdressProperty()
    {
        return clientAdress;
    }

    public StringProperty retriveClientDataFieldProperty()
    {
        return retriveClientDataField;
    }

    public StringProperty clientPasswordCPRFieldProperty()
    {
        return clientPasswordCPRField;
    }

    public StringProperty newClientPasswordFieldProperty()
    {
        return newClientPasswordField;
    }

    public StringProperty confirmNewClientPasswordFieldProperty()
    {
        return confirmNewClientPasswordField;
    }

    public StringProperty oldPasswordFieldProperty()
    {
        return oldPasswordField;
    }

    public StringProperty newPasswordFieldProperty()
    {
        return newPasswordField;
    }

    public StringProperty confirmNewPasswordFieldProperty()
    {
        return confirmNewPasswordField;
    }

    public StringProperty staffSettingsErrorLabelProperty()
    {
        return staffSettingsErrorLabel;
    }

    public StringProperty nameFieldProperty()
    {
        return nameField;
    }


    public StringProperty passwordFieldProperty()
    {
        return passwordField;
    }

    public StringProperty cprFieldProperty()
    {
        return cprField;
    }

    public StringProperty staffNewPasswordProperty()
    {
        return staffNewPassword;
    }

    public StringProperty staffConfirmNewPasswordProperty()
    {
        return staffConfirmNewPassword;
    }

    public StringProperty newExchangeRateFieldProperty()
    {
        return newExchangeRateField;
    }

    public StringProperty staffErrorLabelProperty()
    {
        return staffErrorLabel;
    }

    public StringProperty manageCLientsErrorLabelProperty()
    {
        return manageCLientsErrorLabel;
    }


    public BooleanProperty getCloseWindowProperty()
    {
        return closeWindow;
    }

    public void setCloseWindow(boolean closeWindow)
    {
        this.closeWindow.set(closeWindow);
    }

    public void resetManageClients()
    {
        this.clientCPR.setValue("");
        this.clientAdress.setValue("");
        this.clientEmail.setValue("");
        this.clientName.setValue("");
        this.clientPasswordCPRField.setValue("");
        this.clientPhone.setValue("");
        this.confirmNewClientPasswordField.setValue("");
        this.newClientPasswordField.setValue("");
        this.retriveClientDataField.setValue("");
        this.clientCPR.setValue("");
        this.clientCPR.setValue("");
    }




    public void resetSettings()
    {
        this.oldPasswordField.setValue("");
        this.newPasswordField.setValue("");
        this.confirmNewPasswordField.setValue("");
        this.staffSettingsErrorLabel.setValue("");
    }

    public void resetManager()
    {
        this.staffNewPassword.setValue("");
        this.staffConfirmNewPassword.setValue("");
        this.nameField.setValue("");
        this.cprField.setValue("");
        this.passwordField.setValue("");
        this.newExchangeRateField.setValue("");
    }

    public void backButton()
    {
        model.staffBackButton();
        this.staffData = null;
    }

    public void getNewStaffData()
    {
        model.getNewStaffData();
    }


    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        Platform.runLater(() -> {
            switch (event.getPropertyName())
            {
                case "StaffOpenLogInWindow":
                    this.setCloseWindow(true);
                    break;
                case "NewStaffData":
                    this.setNewData(true);
                    System.out.println("GOT HERE");
                    this.staffData = (StaffData) event.getNewValue();
                    break;
                case "StaffManageUsersSuccess":
                    this.manageCLientsErrorLabel.setValue(event.getNewValue().toString());
                    this.resetManageClients();
                    break;
                case "StaffManageUsersError":
                    this.manageCLientsErrorLabel.setValue(event.getNewValue().toString());
                    break;

                case "StaffManageNewData":
                    this.manageCLientsErrorLabel.setValue("SUCCESS!");
                    this.retriveClientDataField.setValue("");
                    RegisterData aux = (RegisterData)event.getNewValue();
                    System.out.println(aux.getPhoneNumber());
                    this.clientPhone.setValue(aux.getPhoneNumber());
                    this.clientName.setValue(aux.getFullName());
                    this.clientEmail.setValue(aux.getEmail());
                    this.clientAdress.setValue(aux.getAdress());
                    this.clientCPR.setValue(aux.getCPR());
                    break;

                case "StaffSettingsSuccess":
                    this.staffSettingsErrorLabel.setValue(event.getNewValue().toString());
                    this.resetSettings();
                    break;

                case "StaffSettingsError":
                    this.staffSettingsErrorLabel.setValue(event.getNewValue().toString());
                    break;

                case "StaffManagerSuccess":
                    this.staffErrorLabel.setValue(event.getNewValue().toString());
                    this.resetManager();
                    break;

                case "StaffManagerError":
                    this.staffErrorLabel.setValue(event.getNewValue().toString());
                    break;

            }
        });
    }

    public void staffChangeClientPassword()
    {
        model.staffChangeClientPassword(this.clientPasswordCPRField.getValue(), this.newClientPasswordField.getValue(), this.confirmNewClientPasswordField.getValue());
    }

    public void retriveClientDataPressed()
    {
        model.retriveClientDataPressed(this.retriveClientDataField.getValue());
    }

    public void changePasswordPressed()
    {
        model.changeStaffPassword(this.oldPasswordField.getValue(), this.newPasswordField.getValue(), this.confirmNewPasswordField.getValue());
    }

    public void changeStaffPassword(String staffCPR)
    {
        model.managerChangeStaffPassword(staffCPR, this.staffNewPassword.getValue(), this.staffConfirmNewPassword.getValue());
    }

    public void createNewStaff(String position)
    {
        model.createNewStaff(this.cprField.getValue(), this.nameField.getValue(), this.passwordField.getValue(), position);
    }

    public void deleteStaff(String cpr)
    {
        model.deleteStaff(cpr);
    }

    public void staffAcceptLoan(String id)
    {
        model.staffAcceptLoan(id);
    }

    public void staffCloseServer()
    {
        model.staffCloseServer();
    }

    public void managerChangeRate(String currency)
    {
        model.managerChangeRate(currency, this.newExchangeRateField.getValue());
    }
}
