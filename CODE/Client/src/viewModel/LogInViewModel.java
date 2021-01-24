package viewModel;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.LogInWindowModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LogInViewModel implements PropertyChangeListener
{
    private LogInWindowModel model;
    private StringProperty email;
    private StringProperty password;
    private StringProperty error;
    private BooleanProperty openClient;
    private BooleanProperty openRegister;
    private BooleanProperty exitProgram;
    private BooleanProperty openStaff;

    public LogInViewModel(LogInWindowModel model)
    {
        this.model = model;
        this.openClient = new SimpleBooleanProperty(false);
        this.openRegister = new SimpleBooleanProperty(false);
        this.exitProgram = new SimpleBooleanProperty(false);
        this.openStaff = new SimpleBooleanProperty(false);



        email = new SimpleStringProperty();
        password = new SimpleStringProperty();
        error = new SimpleStringProperty("");
    }

    public StringProperty getEmailProperty()
    {
        return this.email;
    }

    public StringProperty getPasswordProperty()
    {
        return this.password;
    }

    public StringProperty getErrorProperty()
    {
        return this.error;
    }

    public void resetFields()
    {
        Platform.runLater(()->
        {
            this.password.setValue("");
            this.email.setValue("");
            this.error.setValue("");
        });
    }

    public void registerButton()
    {
        model.registerButton();
    }

    public void login()
    {
        model.logInButton(email.getValue(), password.getValue());
    }

    public BooleanProperty getOpenClientProperty()
    {
        return openClient;
    }

    public void setOpenClient(boolean b)
    {
        if(b)
            this.resetFields();
        this.openClient.setValue(b);
    }

    public BooleanProperty getOpenRegisterProperty()
    {
        return openRegister;
    }

    public void setOpenRegister(boolean openRegister)
    {
        this.openRegister.set(openRegister);
    }

    public BooleanProperty getExitProgramProperty()
    {
        return exitProgram;
    }

    public void setExitProgram(boolean exitProgram)
    {
        this.exitProgram.set(exitProgram);
    }

    public void exitApp()
    {
        model.exitApp();
    }

    public BooleanProperty getOpenStaffProperty()
    {
        return openStaff;
    }

    public void setOpenStaff(boolean openStaff)
    {
        this.openStaff.set(openStaff);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        Platform.runLater(() -> {
        switch (event.getPropertyName())
        {
            case "OpenRegisterWindow":
                this.setOpenRegister(true);
                break;
            case "CloseApp":
                this.setExitProgram(true);
                break;
            case "LogInError":
                this.error.setValue(event.getNewValue().toString());
                break;
            case "OpenClientWindow":
                this.setOpenClient(true);
                break;

            case "OpenStaffWindow":
                this.setOpenStaff(true);
                break;

        }
        });
    }



}
