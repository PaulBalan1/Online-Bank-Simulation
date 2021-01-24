package view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import viewModel.RegisterViewModel;
import javafx.scene.layout.Region;

public class RegisterController
{
    private Region root;
    private RegisterViewModel model;
    ViewHandler viewHandler;
    @FXML private JFXTextField nameField;
    @FXML private JFXTextField cprField;
    @FXML private JFXTextField adressField;
    @FXML private JFXTextField phoneField;
    @FXML private JFXTextField emailField;
    @FXML private JFXPasswordField passwordField;
    @FXML private JFXPasswordField confirmPasswordField;
    @FXML private Label errorLabel;



    public void init(ViewHandler viewHandler, RegisterViewModel model, Region root)
    {
        this.root = root;
        this.model = model;
        this.viewHandler = viewHandler;

        nameField.textProperty().bindBidirectional(model.getFullNameProperty());
        cprField.textProperty().bindBidirectional(model.getCPRProperty());
        adressField.textProperty().bindBidirectional(model.getAdressProperty());
        phoneField.textProperty().bindBidirectional(model.getPhoneNumberProperty());
        emailField.textProperty().bindBidirectional(model.getEmailProperty());
        passwordField.textProperty().bindBidirectional(model.getPasswordProperty());
        confirmPasswordField.textProperty().bindBidirectional(model.getConfirmPasswordProperty());
        errorLabel.textProperty().bindBidirectional(model.getErrorProperty());

        model.getGoBackProperty().addListener((observable, oldValue, newValue) ->
                {
                    if(newValue)
                    {
                        model.setGoBack(false);
                        viewHandler.openView("logIn");
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

    public void goBack()
    {
        model.goBackButton();
    }

    public void signUpButtonPressed()
    {
        model.createNewUser();
    }
}
