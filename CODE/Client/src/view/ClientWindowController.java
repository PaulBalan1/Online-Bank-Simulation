package view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import data.AccountData;
import data.HistoryData;
import viewModel.ClientWindowViewModel;
import javafx.scene.layout.Region;

import java.util.Optional;


public class ClientWindowController
{
    @FXML private Button homeButton;
    @FXML private Button accountsButton;
    @FXML private Button historyButton;
    @FXML private Button settingsButton;
    @FXML private Label watchLabel;

    @FXML private Pane homePane;
    @FXML private Pane settingsPane;
    @FXML private Pane accountsPane;
    @FXML private Pane historyPane;
    @FXML private AnchorPane pagePane;


    //Home declaration
    @FXML private ComboBox<String> transferSelectFromAccount;
    @FXML private ComboBox<String> transferSelectToAccount;
    @FXML private ComboBox<String> transferFundsForeignSelect;
    @FXML private JFXTextField transferInternAmmountField;
    @FXML private ListView<String> exchangeRatesList;
    @FXML private JFXTextField transferForeignAccountField;
    @FXML private JFXTextField transferForeignAmmountField;
    @FXML private JFXTextField requestLoanAmmountField;
    @FXML private Label mainAccountSoldLabel;
    @FXML private Label homeErrorLabel;
    //Home end


    //Accounts declaration
    @FXML private TableView<AccountData> accountsTable;
    @FXML private TableColumn<AccountData, String> accountsTableCurrency;
    @FXML private TableColumn<AccountData, String> accountsTableIban;
    @FXML private TableColumn<AccountData, String> accountsTableDescription;
    @FXML private TableColumn<AccountData, String> accountsTableBalance;
    @FXML private Label accountsErrorLabel;
    @FXML private ComboBox<String> closeSelectAccount;
    @FXML private ComboBox<String> openCurrency;
    @FXML private ComboBox<String> addSelectAccount;
    @FXML private JFXTextField addAmountField;
    @FXML private JFXTextField openDescriptionField;
    //Accounts end


    //History declaration
    @FXML private TableView<HistoryData> historyTable;
    @FXML private TableColumn<HistoryData, String> historyDate;
    @FXML private TableColumn<HistoryData, String> historyTime;
    @FXML private TableColumn<HistoryData, String> historyDescription;
    //History end

    //Settings declaration
    @FXML private JFXPasswordField oldPasswordField;
    @FXML private JFXPasswordField newPasswordField;
    @FXML private JFXPasswordField confirmNewPasswordField;
    @FXML private Label settingsErrorLabel;
    @FXML private Label oldNumberLabel;
    @FXML private Label oldAdressLabel;
    @FXML private JFXTextField newNumberField;
    @FXML private JFXTextArea newAdressField;

    //Settings end



    private Boolean areYouSureAlert(String header, String content) {
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


    private Region root;
    private ClientWindowViewModel viewModel;
    ViewHandler viewHandler;

    public void init(ViewHandler viewHandler, ClientWindowViewModel viewModel, Region root)
    {
        this.root = root;
        this.viewModel = viewModel;
        this.viewHandler = viewHandler;
        this.watchLabel.textProperty().bind(viewModel.getWatchProperty());
        this.homeButtonPressed();

        this.historyDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.historyTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.historyDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        this.accountsTableBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        this.accountsTableCurrency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        this.accountsTableDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.accountsTableIban.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));

        //this.darkModePressed();

        oldPasswordField.textProperty().bindBidirectional(viewModel.getOldPasswordProperty());
        confirmNewPasswordField.textProperty().bindBidirectional(viewModel.getConfirmNewPasswordProperty());
        newPasswordField.textProperty().bindBidirectional(viewModel.getNewPasswordProperty());
        settingsErrorLabel.textProperty().bindBidirectional(viewModel.getSettingsErrorProperty());

        oldNumberLabel.textProperty().bindBidirectional(viewModel.getOldNumberLabelProperty());
        oldAdressLabel.textProperty().bindBidirectional(viewModel.getOldAdressLabelProperty());
        newNumberField.textProperty().bindBidirectional(viewModel.getNewNumberFieldProperty());
        newAdressField.textProperty().bindBidirectional(viewModel.getNewAdressFieldProperty());

        addAmountField.textProperty().bindBidirectional(viewModel.getAmountProperty());
        openDescriptionField.textProperty().bindBidirectional(viewModel.getAccountDescriptionProperty());
        accountsErrorLabel.textProperty().bindBidirectional(viewModel.getAccountErrorsProperty());

        transferForeignAccountField.textProperty().bindBidirectional(viewModel.getTransferForeignAccountProperty());
        transferForeignAmmountField.textProperty().bindBidirectional(viewModel.getTransferForeignAmountProperty());
        requestLoanAmmountField.textProperty().bindBidirectional(viewModel.getRequestLoanAmountProperty());
        mainAccountSoldLabel.textProperty().bindBidirectional(viewModel.getMainAccountSoldProperty());
        transferInternAmmountField.textProperty().bindBidirectional(viewModel.getTransferInternAmountProperty());
        homeErrorLabel.textProperty().bindBidirectional(viewModel.getHomeErrorProperty());


        viewModel.getNewDataProperty().addListener((observable, oldValue, newValue) ->
        {
            if(newValue)
            {
                this.mainAccountSoldLabel.setText("");
                this.loadClientWindowData();
                viewModel.setNewData(false);
            }
        }
        );

        viewModel.getCloseWindowProperty().addListener((observable, oldValue, newValue) ->
                {
                    if(newValue)
                    {
                        viewModel.setCloseWindow(false);
                        viewHandler.openView("logIn");
                    }
                }
        );

        this.reset();

    }

    public void reset()
    {
        Platform.runLater(()->
        {
            this.homeButtonPressed();
            viewModel.resetAccountsFields();
            this.resetAccounts();
            viewModel.resetSettingsFields();
            viewModel.resetHomeField();
            this.resetHome();
            this.resetHistory();
            this.resetSettings();
            viewModel.getNewCLientData();
            viewModel.resetErrors();
        });
    }

    public void loadClientWindowData()
    {
        Platform.runLater(() ->
        {
            Runnable runnable = () -> {
                boolean[] stop = {true};

                while (stop[0]) {
                    if (viewModel.getMainAccountSoldProperty().getValue().equals("")) {
                        System.out.println("Client window - Waiting for data from the server");
                        loadHome();
                        loadAccounts();
                        loadHistory();
                        loadSettings();
                    } else {
                        stop[0] = false;
                        System.out.println("Client window - Client data loaded");
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


    public void homeButtonPressed()
    {
        homeButton.setStyle("-fx-background-color: #5500FF;-fx-font-size: 35px;-fx-font-weight: bold;");
        settingsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        historyButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        accountsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 32px;-fx-font-weight: bold;");
        homePane.setVisible(true);
        accountsPane.setVisible(false);
        historyPane.setVisible(false);
        settingsPane.setVisible(false);
        settingsErrorLabel.setText("");
        accountsErrorLabel.setText("");
        homeErrorLabel.setText("");
    }


    public void accountsButtonPressed()
    {
        accountsButton.setStyle("-fx-background-color: #5500FF;-fx-font-size: 32px;-fx-font-weight: bold;");
        homeButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        settingsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        historyButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        homePane.setVisible(false);
        accountsPane.setVisible(true);
        historyPane.setVisible(false);
        settingsPane.setVisible(false);
        settingsErrorLabel.setText("");
        accountsErrorLabel.setText("");
        homeErrorLabel.setText("");
    }

    public void historyButtonPressed()
    {
        homeButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        accountsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 32px;-fx-font-weight: bold;");
        settingsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        historyButton.setStyle("-fx-background-color: #5500FF;-fx-font-size: 35px;-fx-font-weight: bold;");
        homePane.setVisible(false);
        accountsPane.setVisible(false);
        historyPane.setVisible(true);
        settingsPane.setVisible(false);
        settingsErrorLabel.setText("");
        accountsErrorLabel.setText("");
        homeErrorLabel.setText("");
    }

    public void settingsButtonPressed()
    {
        homeButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        accountsButton.setStyle("-fx-background-color: transparent;-fx-font-size: 32px;-fx-font-weight: bold;");
        historyButton.setStyle("-fx-background-color: transparent;-fx-font-size: 35px;-fx-font-weight: bold;");
        settingsButton.setStyle("-fx-background-color: #5500FF;-fx-font-size: 35px;-fx-font-weight: bold;");
        homePane.setVisible(false);
        accountsPane.setVisible(false);
        historyPane.setVisible(false);
        settingsPane.setVisible(true);
        settingsErrorLabel.setText("");
        accountsErrorLabel.setText("");
        homeErrorLabel.setText("");
    }

    public void logOutButtonPressed()
    {
        settingsErrorLabel.setText("");
        accountsErrorLabel.setText("");
        homeErrorLabel.setText("");
        this.homeButtonPressed();
        viewModel.backButton();
        //viewHandler.openView("logIn");
    }

    public Region getRoot()
    {
        return this.root;
    }



    //Home tab methods -------------------

    public void transferInternButtonPressed()
    {
        if(this.areYouSureAlert("Are you sure you want to transfer money?", "From account: " +
                this.transferSelectFromAccount.getValue() + "\n to account: " + this.transferSelectToAccount.getValue()
                + " amount: " + this.transferInternAmmountField.getText()))
            viewModel.transferIntern(transferSelectFromAccount.getValue(), transferSelectToAccount.getValue());
    }

    public void transferForeignButtonPressed()
    {
        viewModel.transferForeign(transferFundsForeignSelect.getValue());
    }

    public void requestLoanButtonPressed()
    {
        if(this.areYouSureAlert("ARE YOU SURE YOU WANT TO REQUEST A LOAN", "AMOUNT: " + this.requestLoanAmmountField.getText()))
            viewModel.requestLoan();
    }

    public void loadHome()
    {
            if(viewModel.getClientData() != null)
            {
                Platform.runLater(()->
                {
                    resetHome();
                    for (int i = 0; i < viewModel.getClientData().getAccountDataList().size(); i++)
                        if (viewModel.getClientData().getAccountDataList().get(i).getDescription().equals("-Main account-----Cannot be deleted-----Do not use this name-"))
                            this.mainAccountSoldLabel.setText(viewModel.getClientData().getAccountDataList().get(i).getBalance() + " DKK");

                for (int i = 0; i < viewModel.getClientData().getExchangeCurrencies().size(); i++)
                    exchangeRatesList.getItems().add("                 " + viewModel.getClientData().getExchangeCurrencies().get(i));

                for (int i = 0; i < viewModel.getClientData().getAccountDataList().size(); i++)
                    transferFundsForeignSelect.getItems().add(viewModel.getClientData().getAccountDataList().get(i).getAccountNumber());

                for (int i = 0; i < viewModel.getClientData().getAccountDataList().size(); i++)
                    transferSelectFromAccount.getItems().add(viewModel.getClientData().getAccountDataList().get(i).getAccountNumber());

                for (int i = 0; i < viewModel.getClientData().getAccountDataList().size(); i++)
                    transferSelectToAccount.getItems().add(viewModel.getClientData().getAccountDataList().get(i).getAccountNumber());
                });
            }


    }

    public void resetHome()
    {
        viewModel.resetHomeField();
        transferFundsForeignSelect.getItems().clear();
        transferSelectToAccount.getItems().clear();
        transferSelectFromAccount.getItems().clear();
        exchangeRatesList.getItems().clear();
    }




    //Home tab end ---------------





    //Accounts tab methods -------------------
    public void deleteAccountButtonPressed()
    {
        if(this.areYouSureAlert("Are you sure you want to delete this account", "-Account nr: " + closeSelectAccount.getValue()))
            viewModel.deleteAccount(closeSelectAccount.getValue());
    }

    public void createAccountButtonPressed()
    {
        if(this.areYouSureAlert("Are you sure you want to open this account", "Currency: " + openCurrency.getValue()))
        viewModel.createAccount(openCurrency.getValue());
    }

    public void addFundsButtonPressed()
    {
        if(this.areYouSureAlert("Are you sure you want to add funds:", "Account: " + addSelectAccount.getValue() + " \n Ammount: " + addAmountField.getText()))
        viewModel.addFunds(addSelectAccount.getValue());
    }

    public void withdrawFundsButtonPressed()
    {
        if(this.areYouSureAlert("Are you sure you want to withdraw funds:", "Account: " + addSelectAccount.getValue() + " \n Ammount: " + addAmountField.getText()))
            viewModel.withdrawFunds(addSelectAccount.getValue());
    }

    public void loadAccounts()
    {
            if(viewModel.getClientData() != null) {
                Platform.runLater(()->
                {
                    resetAccounts();
                    for (int i = 0; i < viewModel.getClientData().getExchangeCurrencies().size(); i++)
                        openCurrency.getItems().add(viewModel.getClientData().getExchangeCurrencies().get(i));
                    for (int i = 0; i < viewModel.getClientData().getAccountDataList().size(); i++)
                        accountsTable.getItems().add(viewModel.getClientData().getAccountDataList().get(i));

                    for (int i = 0; i < viewModel.getClientData().getAccountDataList().size(); i++)
                        closeSelectAccount.getItems().add(viewModel.getClientData().getAccountDataList().get(i).getAccountNumber());

                    for (int i = 0; i < viewModel.getClientData().getAccountDataList().size(); i++)
                        addSelectAccount.getItems().add(viewModel.getClientData().getAccountDataList().get(i).getAccountNumber());

                });
            }
    }

    public void resetAccounts()
    {
                viewModel.resetAccountsFields();
                accountsTable.getItems().clear();
                closeSelectAccount.getItems().clear();
                addSelectAccount.getItems().clear();
                openCurrency.getItems().clear();
    }

    //Accounts end-----------------






    //History tab methods -------------------------------------

    public void loadHistory()
    {
            if(viewModel.getClientData() != null)
            {
                Platform.runLater(()->
                {
                    resetHistory();
                    for (int i = viewModel.getClientData().getHistoryDataList().size() - 1; i >= 0 ; i--)
                        historyTable.getItems().add(viewModel.getClientData().getHistoryDataList().get(i));
                });
            }
    }

    public void resetHistory()
    {

                historyTable.getItems().clear();
    }

    //History end -------------------------------------



    //Settings tab methods---------------------------------------------


    public void loadSettings()
    {
        resetSettings();
        viewModel.loadSettings();
    }

    public void resetSettings()
    {
        viewModel.resetSettingsFields();
    }

    public void changePasswordPressed()
    {
        if(this.areYouSureAlert("Are you sure you want to change the password", ""));
        viewModel.changePassword();
    }

    public void changeNumberPressed()
    {
        if(this.areYouSureAlert("Are you sure you want to change the phone nr:", "New phone nr: " + newNumberField.getText()));
            viewModel.changeNumberPressed();
    }

    public void changeAdressPressed()
    {
        if(this.areYouSureAlert("Are you sure you want to change adress:", "New address: " + newAdressField.getText()));
            viewModel.changeAdressPressed();
    }

    public void deleteAccountPressed()
    {
        if(this.areYouSureAlert("Are you sure you want to delete your account:",""));
            viewModel.deleteAccountPressed();
    }


    //Settings end ---------------------------------------------




}
