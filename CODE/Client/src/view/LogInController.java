package view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import viewModel.LogInViewModel;


public class LogInController
{
    @FXML private JFXTextField emailField;
    @FXML private JFXPasswordField passwordField;
    @FXML private Label errorLabel;
    private Region root;
    private LogInViewModel model;
    private ViewHandler viewHandler;

    public LogInController()
    {
    }

    public void init(ViewHandler viewHandler, LogInViewModel viewModel, Region root)
    {
        this.root = root;
        this.model = viewModel;
        this.viewHandler = viewHandler;
        this.emailField.textProperty().bindBidirectional(model.getEmailProperty());
        this.passwordField.textProperty().bindBidirectional(model.getPasswordProperty());
        this.errorLabel.textProperty().bindBidirectional(model.getErrorProperty());

        viewModel.getOpenClientProperty().addListener((observable, oldValue, newValue) ->
                {
                    if(newValue)
                    {
                        viewModel.setOpenClient(false);
                        viewHandler.openView("client");
                    }
                }
        );

        viewModel.getOpenStaffProperty().addListener((observable, oldValue, newValue) ->
                {
                    if(newValue)
                    {
                        viewModel.setOpenStaff(false);
                        viewHandler.openView("staff");
                    }
                }
        );

        viewModel.getOpenRegisterProperty().addListener((observable, oldValue, newValue) ->
                {
                    if(newValue)
                    {
                        viewModel.setOpenRegister(false);
                        viewHandler.openView("register");
                    }
                }
        );

        viewModel.getExitProgramProperty().addListener((observable, oldValue, newValue) ->
                {
                    if(newValue)
                    {
                        viewHandler.closeView();
                    }
                }
        );
    }


    public void reset()
    {
        model.resetFields();
    }

    public Region getRoot()
    {
        return this.root;
    }

    public void loginButton()
    {
        model.login();
    }

    public void registerButton()
    {
        model.registerButton();
    }

    public void cancelButton()
    {
        model.exitApp();
    }

}
