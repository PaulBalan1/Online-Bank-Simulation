package view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import data.LoanData;
import data.RegisterData;
import data.StaffAccountData;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import viewModel.StaffWindowViewModel;

import java.util.Optional;

public class StaffWindowController {
    @FXML    private Label watchLabel;
    @FXML  private Button acceptClientsButton;
    @FXML    private Button manageClientsButton;
    @FXML    private Button settingsButton;
    @FXML    private Button managerButton;

    @FXML    private Pane acceptClientsPane;
    @FXML    private Pane manageClientsPane;
    @FXML    private Pane settingsPane;
    @FXML    private Pane managerPane;

    @FXML    private TableView<RegisterData> acceptNewClientTable;
    @FXML    private TableColumn<RegisterData, String> newCPR;
    @FXML    private TableColumn<RegisterData, String> newName;
    @FXML    private TableColumn<RegisterData, String> newEmail;
    @FXML    private TableColumn<RegisterData, String> newNumber;
    @FXML    private TableColumn<RegisterData, String> newAdress;

    @FXML    private Label clientCPR;
    @FXML    private Label clientName;
    @FXML    private Label clientEmail;
    @FXML    private Label clientPhone;
    @FXML    private Label clientAdress;
    @FXML    private JFXTextField retriveClientDataField;
    @FXML    private JFXTextField clientPasswordCPRField;
    @FXML    private JFXPasswordField newClientPasswordField;
    @FXML    private JFXPasswordField confirmNewClientPasswordField;
    @FXML    private Label manageCLientsErrorLabel;

    @FXML    private JFXPasswordField oldPasswordField;
    @FXML    private JFXPasswordField newPasswordField;
    @FXML    private JFXPasswordField confirmNewPasswordField;
    @FXML    private Label staffSettingsErrorLabel;


    @FXML    private TableView<StaffAccountData> staffTable;
    @FXML    private TableColumn<StaffAccountData, String> staffCPR;
    @FXML    private TableColumn<StaffAccountData, String> staffName;
    @FXML    private TableColumn<StaffAccountData, String> staffPosition;

    @FXML    private ListView<String> exchangeRateList;

    @FXML    private JFXTextField nameField;
    @FXML    private JFXPasswordField passwordField;
    @FXML    private JFXTextField cprField;
    @FXML    private ComboBox<String> posittionDropDown;
    @FXML    private ComboBox<String> staffDropdown;
    @FXML    private ComboBox<String> staffDropdown1;
    @FXML    private JFXPasswordField staffNewPassword;
    @FXML    private JFXPasswordField staffConfirmNewPassword;
    @FXML    private ComboBox<String> exchangeRateDropdown;
    @FXML    private JFXTextField newExchangeRateField;
    @FXML    private Label staffErrorLabel;


    @FXML
    private TableView<LoanData> acceptLoanTable;

    @FXML
    private TableColumn<LoanData, String> loanID;

    @FXML
    private TableColumn<LoanData, String> loanCPR;

    @FXML
    private TableColumn<LoanData, String> loanAmmount;

    private Region root;
    private StaffWindowViewModel viewModel;
    ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, StaffWindowViewModel viewModel, Region root)
    {
        this.root = root;
        this.viewModel = viewModel;
        this.viewHandler = viewHandler;
        this.watchLabel.textProperty().bindBidirectional(viewModel.getWatchProperty());

        this.clientCPR.textProperty().bindBidirectional(viewModel.clientCPRProperty());
        this.clientName.textProperty().bindBidirectional(viewModel.clientNameProperty());
        this.clientEmail.textProperty().bindBidirectional(viewModel.clientEmailProperty());
        this.clientPhone.textProperty().bindBidirectional(viewModel.clientPhoneProperty());
        this.clientAdress.textProperty().bindBidirectional(viewModel.clientAdressProperty());
        this.retriveClientDataField.textProperty().bindBidirectional(viewModel.retriveClientDataFieldProperty());
        this.clientPasswordCPRField.textProperty().bindBidirectional(viewModel.clientPasswordCPRFieldProperty());
        this.newClientPasswordField.textProperty().bindBidirectional(viewModel.newClientPasswordFieldProperty());
        this.confirmNewClientPasswordField.textProperty().bindBidirectional(viewModel.confirmNewClientPasswordFieldProperty());
        this.manageCLientsErrorLabel.textProperty().bindBidirectional(viewModel.manageCLientsErrorLabelProperty());

        this.oldPasswordField.textProperty().bindBidirectional(viewModel.oldPasswordFieldProperty());
        this.newPasswordField.textProperty().bindBidirectional(viewModel.newPasswordFieldProperty());
        this.confirmNewPasswordField.textProperty().bindBidirectional(viewModel.confirmNewPasswordFieldProperty());
        this.staffSettingsErrorLabel.textProperty().bindBidirectional(viewModel.staffSettingsErrorLabelProperty());

        this.nameField.textProperty().bindBidirectional(viewModel.nameFieldProperty());
        this.passwordField.textProperty().bindBidirectional(viewModel.passwordFieldProperty());
        this.cprField.textProperty().bindBidirectional(viewModel.cprFieldProperty());
        this.staffNewPassword.textProperty().bindBidirectional(viewModel.staffNewPasswordProperty());
        this.staffConfirmNewPassword.textProperty().bindBidirectional(viewModel.staffConfirmNewPasswordProperty());
        this.newExchangeRateField.textProperty().bindBidirectional(viewModel.newExchangeRateFieldProperty());
        this.staffErrorLabel.textProperty().bindBidirectional(viewModel.staffErrorLabelProperty());

        this.newCPR.setCellValueFactory(new PropertyValueFactory<>("CPR"));
        this.newName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        this.newAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));
        this.newEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.newNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        this.loanAmmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        this.loanCPR.setCellValueFactory(new PropertyValueFactory<>("cpr"));
        this.loanID.setCellValueFactory(new PropertyValueFactory<>("id"));


        this.staffCPR.setCellValueFactory(new PropertyValueFactory<>("cpr"));
        this.staffName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.staffPosition.setCellValueFactory(new PropertyValueFactory<>("position"));

        acceptNewClientTable.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override public void handle(MouseEvent mouseEvent)
            {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                    if (mouseEvent.getClickCount() == 2)
                    {
                        if(areYouSureAlert("Are you sure you want to approve this client:", "-Client name: " + acceptNewClientTable.getSelectionModel().getSelectedItem().getFullName() + "\n-Client cpr: " + acceptNewClientTable.getSelectionModel().getSelectedItem().getCPR()))
                        {
                            viewModel.staffAcceptClient(acceptNewClientTable.getSelectionModel().getSelectedItem().getCPR());
                            System.out.println(acceptNewClientTable.getSelectionModel().getSelectedItem().getCPR());
                        }
                    }
            }
        });

        acceptLoanTable.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override public void handle(MouseEvent mouseEvent)
            {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                    if (mouseEvent.getClickCount() == 2)
                    {
                        if(areYouSureAlert("Are you sure you want to approve this loan:", "-Client cpr: " + acceptLoanTable.getSelectionModel().getSelectedItem().getCpr() + "\n-Loan amount: " + acceptLoanTable.getSelectionModel().getSelectedItem().getAmount()))
                        {
                            viewModel.staffAcceptLoan(acceptLoanTable.getSelectionModel().getSelectedItem().getId());
                        }
                    }
            }
        });


        this.acceptClientsButtonPressed();

        viewModel.getCloseWindowProperty().addListener((observable, oldValue, newValue) ->
                {
                    if(newValue)
                    {
                        viewModel.setCloseWindow(false);
                        viewHandler.openView("logIn");
                    }
                }
        );

        viewModel.getNewDataProperty().addListener((observable, oldValue, newValue) ->
                {
                    if(newValue)
                    {
                        this.loadStaffData();
                        viewModel.setNewData(false);
                    }
                }
        );

        this.reset();
    }

    public void loadStaffData()
    {
        Platform.runLater(()->
        {
            Runnable runnable = () -> {
                boolean[] bai = {true};

                while (bai[0]) {
                    if (viewModel.getStaffData() != null)
                    {
                        System.out.println("Staff window - Staff data loaded");
                        this.loadSettings();
                        this.loadAcceptClients();
                        this.loadManageClients();
                        this.loadManager();
                        bai[0] = false;
                    } else {
                        System.out.println("Staff window - Waiting for data from the server");
                    }
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
        });
    }

    public void loadAcceptClients()
    {
        Platform.runLater(()->
        {
            this.acceptNewClientTable.getItems().clear();
            for (int i = 0; i < viewModel.getStaffData().getUsersToBeAccepted().size(); i++)
                this.acceptNewClientTable.getItems().add(viewModel.getStaffData().getUsersToBeAccepted().get(i));
        });
    }

    public void loadManageClients()
    {
        Platform.runLater(()->
        {
            viewModel.resetManageClients();
            this.acceptLoanTable.getItems().clear();
            for(int i = 0; i < viewModel.getStaffData().getLoans().size(); i++)
                this.acceptLoanTable.getItems().add(viewModel.getStaffData().getLoans().get(i));
        });
    }

    public void loadSettings()
    {
        Platform.runLater(()->
        {
            viewModel.resetSettings();
        });
    }

    public void loadManager()
    {
        Platform.runLater(()->
        {
            viewModel.resetManager();
            this.posittionDropDown.getItems().clear();
            this.posittionDropDown.getItems().add("manager");
            this.posittionDropDown.getItems().add("staff");
            this.staffTable.getItems().clear();
            this.staffDropdown.getItems().clear();
            this.staffDropdown1.getItems().clear();

            if (viewModel.getStaffData().getStaffAccountData() != null)
                for (int i = 0; i < viewModel.getStaffData().getStaffAccountData().size(); i++) {
                    staffTable.getItems().add(viewModel.getStaffData().getStaffAccountData().get(i));
                    this.staffDropdown.getItems().add(viewModel.getStaffData().getStaffAccountData().get(i).getCpr());
                    this.staffDropdown1.getItems().add(viewModel.getStaffData().getStaffAccountData().get(i).getCpr());
                    managerButton.setVisible(true);
                }
            else managerButton.setVisible(false);

            this.exchangeRateList.getItems().clear();
            this.exchangeRateDropdown.getItems().clear();

            if (viewModel.getStaffData().getExchangeCurrencies() != null)
                for (int i = 0; i < viewModel.getStaffData().getExchangeCurrencies().size(); i++) {
                    exchangeRateList.getItems().add(viewModel.getStaffData().getExchangeCurrencies().get(i));
                    exchangeRateDropdown.getItems().add(viewModel.getStaffData().getExchangeCurrencies().get(i));
                }
        });
    }


    public void reset()
    {
        viewModel.resetStaffData();
        viewModel.getNewStaffData();
        viewModel.resetErrors();
    }

    private Boolean areYouSureAlert(String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Attention!");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setGraphic(new ImageView(this.getClass().getResource("../images/iconfinder_alert_298718.png").toString()));
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            return true;
        else return false;
    }


    public Region getRoot()
    {
        return this.root;
    }


    public void acceptClientsButtonPressed()
    {
        acceptClientsButton.setStyle("-fx-background-color: #5500FF;-fx-font-size: 35px;-fx-font-weight: bold;");
        settingsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        managerButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        manageClientsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 32px;-fx-font-weight: bold;");
        acceptClientsPane.setVisible(true);
        manageClientsPane.setVisible(false);
        managerPane.setVisible(false);
        settingsPane.setVisible(false);
        viewModel.resetErrors();
    }


    public void manageClientsButtonPressed()
    {
        manageClientsButton.setStyle("-fx-background-color: #5500FF;-fx-font-size: 32px;-fx-font-weight: bold;");
        acceptClientsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        settingsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        managerButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        acceptClientsPane.setVisible(false);
        manageClientsPane.setVisible(true);
        managerPane.setVisible(false);
        settingsPane.setVisible(false);
        viewModel.resetErrors();
    }

    public void managerButtonPressed()
    {
        acceptClientsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        manageClientsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 32px;-fx-font-weight: bold;");
        settingsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        managerButton.setStyle("-fx-background-color: #5500FF;-fx-font-size: 35px;-fx-font-weight: bold;");
        acceptClientsPane.setVisible(false);
        manageClientsPane.setVisible(false);
        managerPane.setVisible(true);
        settingsPane.setVisible(false);
        viewModel.resetErrors();
    }

    public void settingsButtonPressed()
    {
        acceptClientsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        manageClientsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 32px;-fx-font-weight: bold;");
        managerButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        settingsButton.setStyle("-fx-background-color: #5500FF;-fx-font-size: 35px;-fx-font-weight: bold;");
        acceptClientsPane.setVisible(false);
        manageClientsPane.setVisible(false);
        managerPane.setVisible(false);
        settingsPane.setVisible(true);
        viewModel.resetErrors();
    }

    public void logOutButtonPressed()
    {
        this.acceptClientsButtonPressed();
        viewModel.backButton();
        viewHandler.openView("logIn");
    }



    public void changeClientPasswordPressed()
    {
        if(areYouSureAlert("Are you sure you want to change this password?", ""))
            viewModel.staffChangeClientPassword();
    }

    public void retriveClientDataPressed()
    {
        viewModel.retriveClientDataPressed();
    }


    public void changePasswordPressed()
    {
        if(areYouSureAlert("Are you sure you want to change this password?", ""))
        viewModel.changePasswordPressed();
    }


    public void changeRatePressed()
    {
        if(areYouSureAlert("Are you sure you want to change this exchange rate?", "Old rate: " + this.exchangeRateDropdown.getValue() + "\nNew rate: " + this.exchangeRateDropdown.getValue().substring(0, 3) + " - " + this.newExchangeRateField.getText()))
        viewModel.managerChangeRate(this.exchangeRateDropdown.getValue().substring(0, 3));
    }


    public void changeStaffPasswordPressed()
    {
        if(areYouSureAlert("Are you sure you want to change this password?", ""))
        viewModel.changeStaffPassword(this.staffDropdown1.getValue());
    }


    public void createStaffPressed()
    {
        if(areYouSureAlert("Are you sure you want to create this new account?", "Account type: " + this.posittionDropDown.getValue()))
        viewModel.createNewStaff(this.posittionDropDown.getValue());
    }


    public void deleteStaffPressed()
    {
        if(areYouSureAlert("Are you sure you want to delete this staff?", this.staffDropdown.getValue()))
        viewModel.deleteStaff(this.staffDropdown.getValue());
    }




    public void closeServerPressed()
    {
        if(areYouSureAlert("Are you sure you want to close the server?", "This will kick all clients and staff from the server"))
        viewModel.staffCloseServer();
    }









}
