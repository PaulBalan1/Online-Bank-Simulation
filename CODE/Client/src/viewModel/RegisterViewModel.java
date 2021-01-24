package viewModel;

import data.RegisterData;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.BankModel;
import model.RegisterWindowModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RegisterViewModel implements PropertyChangeListener
{
    RegisterWindowModel model;
    private StringProperty fullName;
    private StringProperty CPR;
    private StringProperty adress;
    private StringProperty phoneNumber;
    private StringProperty email;
    private StringProperty password;
    private StringProperty confirmPassword;
    private StringProperty error;

    private BooleanProperty goBack;

    public RegisterViewModel(RegisterWindowModel model)
    {
        this.model = model;
        fullName = new SimpleStringProperty();
        CPR = new SimpleStringProperty();
        adress = new SimpleStringProperty();
        phoneNumber = new SimpleStringProperty();
        email = new SimpleStringProperty();
        password = new SimpleStringProperty();
        confirmPassword = new SimpleStringProperty();
        goBack = new SimpleBooleanProperty(false);
        error = new SimpleStringProperty("");

    }

    public StringProperty getFullNameProperty()
    {
        return this.fullName;
    }

    public StringProperty getCPRProperty()
    {
        return this.CPR;
    }

    public StringProperty getAdressProperty()
    {
        return this.adress;
    }

    public StringProperty getPhoneNumberProperty()
    {
        return this.phoneNumber;
    }

    public StringProperty getEmailProperty()
    {
        return this.email;
    }

    public StringProperty getPasswordProperty()
    {
        return this.password;
    }

    public StringProperty getConfirmPasswordProperty()
    {
        return this.confirmPassword;
    }

    public StringProperty getErrorProperty()
    {
        return this.error;
    }

    public void resetFields()
    {
        this.confirmPassword.setValue("");
        this.password.setValue("");
        this.email.setValue("");
        this.phoneNumber.setValue("");
        this.adress.setValue("");
        this.CPR.setValue("");
        this.fullName.setValue("");
        this.error.setValue("");
    }

    public void createNewUser()
    {
        RegisterData bai = new RegisterData(fullName.getValue(), CPR.getValue(), adress.getValue(), phoneNumber.getValue());
        bai.setRest(email.getValue(), password.getValue(), confirmPassword.getValue());
        model.registerNewAccount(bai);
    }


    public BooleanProperty getGoBackProperty()
    {
        return goBack;
    }

    public void setGoBack(boolean goBack)
    {
        this.goBack.set(goBack);
    }

    public void goBackButton()
    {
        model.registerBackButton();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        Platform.runLater(() -> {
            switch (event.getPropertyName()) {
                case "RegisterOpenLogInWindow":
                    this.setGoBack(true);
                    break;
                case "RegisterSuccess":
                    this.resetFields();
                    this.error.setValue(event.getNewValue().toString());
                    break;
                case "RegisterError":
                    this.error.setValue(event.getNewValue().toString());
                    break;
            }
        });
    }

}
