package network;

import com.google.gson.Gson;
import data.RegisterData;
import networkPackages.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AppCLient implements RemoteModel
{
    private PropertyChangeSupport support;

    private String HOST;
    private int PORT;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean stopCondition;
    Gson gson;

    public AppCLient(String host, int port)
    {
        this.HOST = host;
        this.PORT = port;
        stopCondition = false;
        support = new PropertyChangeSupport(this);
        gson = new Gson();
    }

    public AppCLient()
    {
        String HOST = "localhost";
        int PORT = 9876;
        support = new PropertyChangeSupport(this);
        stopCondition = false;
        gson = new Gson();
    }

    @Override
    public void connect()
    {
        try {
            socket = new Socket(HOST, PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isConnected()
    {
        return this.socket.isConnected();
    }


    @Override
    public void disconnect()
    {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void exitButton()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.CloseSocketRequest, "")));
    }

    @Override
    public void openLogIn()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.OpenLogInRequest, "")));
    }

    @Override
    public void clientRequestLoan(String sum)
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientRequestLoanRequest, sum)));
    }

    @Override
    public void clientRequestTransfer(String fromAccount, String toAccount, String sum)
    {
        writer.println(gson.toJson(new ThreeFieldPackage(NetworkType.ClientRequestTransferRequest, fromAccount, toAccount, sum)));
    }

    @Override
    public void closeAccount(String fromAccount)
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientCloseAccountRequest, fromAccount)));
    }

    @Override
    public void openAccount(String description, String currency)
    {
        writer.println(gson.toJson(new TwoFieldPackage(NetworkType.ClientOpenAccountRequest, description, currency)));
    }

    @Override
    public void withdrawFunds(String account, String amount)
    {
        writer.println(gson.toJson(new TwoFieldPackage(NetworkType.ClientWithdrawFundsRequest, account, amount)));
    }

    @Override
    public void addFunds(String account, String amount)
    {
        writer.println(gson.toJson(new TwoFieldPackage(NetworkType.ClientAddFundsRequest, account, amount)));
    }

    @Override
    public void changePasswordPressed(String oldPassword, String newPassword, String confirmNewPassword)
    {
        writer.println(gson.toJson(new ThreeFieldPackage(NetworkType.ClientNewPasswordRequest, oldPassword, newPassword, confirmNewPassword)));
    }

    @Override
    public void changeNumberPressed(String newNumber)
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientNewNumberRequest, newNumber)));
    }

    @Override
    public void changeAdressPressed(String newAdreess)
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientNewAdressRequest, newAdreess)));
    }

    @Override
    public void deleteAccountPressed()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientDeleteAccountRequest, "")));
    }

    @Override
    public void clientBackButton()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientOpenLogInRequest, "")));
    }

    @Override
    public void getNewCLientData()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.NewClientDataRequest, "")));
    }

    @Override
    public void staffBackButton()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffOpenLogInRequest, "")));
    }

    @Override
    public void getNewStaffData()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffGetNewDataRequest, "")));
    }

    @Override
    public void staffAcceptClient(String cpr)
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffAcceptClientRequest, cpr)));
    }

    @Override
    public void staffChangeClientPassword(String value, String value1, String value2)
    {
        writer.println(gson.toJson(new ThreeFieldPackage(NetworkType.StaffChangeClientPasswordRequest, value, value1, value2)));
    }

    @Override
    public void retriveClientDataPressed(String retriveClientDataField)
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffRetrieveClientDataRequest, retriveClientDataField)));
    }

    @Override
    public void changeStaffPassword(String value, String value1, String value2)
    {
        writer.println(gson.toJson(new ThreeFieldPackage(NetworkType.ChangeOwnStaffPasswordRequest, value, value1, value2)));
    }

    @Override
    public void managerChangeStaffPassword(String staffCPR, String value, String value1)
    {
        writer.println(gson.toJson(new ThreeFieldPackage(NetworkType.ManagerChangeStaffPasswordRequest, staffCPR, value, value1)));
    }

    @Override
    public void createNewStaff(String cpr, String name, String pass, String position)
    {
        writer.println(gson.toJson(new FourFieldPackage(NetworkType.ManagerCreateNewStaffRequest, cpr, name, pass, position)));
    }

    @Override
    public void deleteStaff(String cpr)
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.DeleteStaffRequest, cpr)));
    }

    @Override
    public void staffAcceptLoan(String id)
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffAcceptLoanRequest, id)));
    }

    @Override
    public void staffCloseServer()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffCloseServerRequest, "")));
    }

    @Override
    public void managerChangeRate(String currency, String value)
    {
        writer.println(gson.toJson(new TwoFieldPackage(NetworkType.ManagerChangeRateRequest, currency, value)));
    }


    public void waitFromServer()
    {
        Runnable runnable = () -> {
        String request = null;
        NetworkPackage dataPackage;

        while(!stopCondition)
        {
            try {
                request = reader.readLine();
            } catch (IOException e)
            {
                stopCondition = true;
                System.out.println("Connection to the server lost");
                support.firePropertyChange("CloseApp", "", "bah");
                this.disconnect();
                e.printStackTrace();
            }
            dataPackage = gson.fromJson(request, NetworkPackage.class);

            System.out.println("- " + dataPackage.getType().toString());
            switch(dataPackage.getType())
            {
                case OpenRegisterWindow:
                    System.out.println("OPEN REGISTER");
                    support.firePropertyChange("OpenRegisterWindow", "", "1");
                    break;

                case RegisterSuccess:
                    OneFieldPackage oneFieldPackage = gson.fromJson(request, OneFieldPackage.class);
                    System.out.println("RegisterSuccess");
                    support.firePropertyChange("RegisterSuccess", "", oneFieldPackage.getValue());
                    break;

                case RegisterError:
                    OneFieldPackage oneFieldPackage2 = gson.fromJson(request, OneFieldPackage.class);
                    System.out.println("RegisterError");
                    support.firePropertyChange("RegisterError", "", oneFieldPackage2.getValue());
                    break;

                case LogInError:
                    OneFieldPackage oneFieldPackage3 = gson.fromJson(request, OneFieldPackage.class);
                    System.out.println("LOG IN ERROR");
                    support.firePropertyChange("LogInError", "", oneFieldPackage3.getValue());
                    break;

                case OpenClientWindow:
                    System.out.println("OPEN CLIENT WINDOW");
                    support.firePropertyChange("OpenClientWindow", "", "1");
                    break;

                case OpenStaffWindow:
                    System.out.println("OPEN STAFF WINDOW");
                    support.firePropertyChange("OpenStaffWindow", "", "1");
                    break;

                case ClientOpenLogInWindow:
                    System.out.println("CLIENT OPEN LOGIN WINDOW");
                    support.firePropertyChange("ClientOpenLogInWindow", "", "1");
                    break;

                case RegisterOpenLogInWindow:
                    System.out.println("OPEN LOGIN WINDOW");
                    support.firePropertyChange("RegisterOpenLogInWindow", "", "1");
                    break;

                case CloseApp:
                    System.out.println("--CLOSE APP");
                    support.firePropertyChange("CloseApp", "", "bah");
                    this.disconnect();
                    stopCondition = true;
                    break;

                case NewClientData:
                    System.out.println("--NEW CLIENT DATA");
                    ClientDataPackage bai = gson.fromJson(request, ClientDataPackage.class);
                    support.firePropertyChange("NewClientData", "", bai.getValue());
                    break;

                case ClientHomeSuccess:
                    System.out.println("-- ClientHomeSuccess");
                    OneFieldPackage bai2 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("ClientHomeSuccess", "", bai2.getValue());
                    break;

                case ClientHomeError:
                    System.out.println("-- ClientHomeError");
                    OneFieldPackage bai3 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("ClientHomeError", "", bai3.getValue());
                    break;

                case ClientAccountsSuccess:
                    System.out.println("-- ClientAccountsSuccess");
                    OneFieldPackage bai4 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("ClientAccountsSuccess", "", bai4.getValue());
                    break;

                case ClientAccountsError:
                    System.out.println("-- ClientAccountsError");
                    OneFieldPackage bai5 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("ClientAccountsError", "", bai5.getValue());
                    break;

                case ClientDeleteSuccess:
                    System.out.println("-- ClientDeleteSuccess");
                    OneFieldPackage bai6 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("ClientDeleteSuccess", "", bai6.getValue());
                    break;

                case ClientSettingsError:
                    System.out.println("-- ClientSettingsError");
                    OneFieldPackage bai7 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("ClientSettingsError", "", bai7.getValue());
                    break;

                case ClientSettingsSuccess:
                    System.out.println("-- ClientSettingsSuccess");
                    OneFieldPackage bai8 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("ClientSettingsSuccess", "", bai8.getValue());
                    break;



                case StaffOpenLogInWindow:
                    System.out.println("STAFF OPEN LOGIN WINDOW");
                    support.firePropertyChange("StaffOpenLogInWindow", "", "1");
                    break;

                case NewStaffData:
                    System.out.println("STAFF NEW DATA");
                    StaffDataPackage bai22 = gson.fromJson(request, StaffDataPackage.class);
                    support.firePropertyChange("NewStaffData", "", bai22.getValue());
                    break;

                case StaffManageUsersSuccess:
                    System.out.println("-- StaffManageUsersSuccess");
                    OneFieldPackage bai73 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("StaffManageUsersSuccess", "", bai73.getValue());
                    break;

                case StaffManageUsersError:
                    System.out.println("-- StaffManageUsersError");
                    OneFieldPackage bai723 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("StaffManageUsersError", "", bai723.getValue());
                    break;

                case StaffManageNewData:
                    System.out.println("-- StaffManageNewData");
                    RegisterDataPackage bai7223 = gson.fromJson(request, RegisterDataPackage.class);
                    support.firePropertyChange("StaffManageNewData", "", bai7223.getValue());
                    break;

                case StaffSettingsSuccess:
                    System.out.println("-- StaffSettingsSuccess");
                    OneFieldPackage bai731 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("StaffSettingsSuccess", "", bai731.getValue());
                    break;

                case StaffSettingsError:
                    System.out.println("-- StaffSettingsError");
                    OneFieldPackage bai7232 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("StaffSettingsError", "", bai7232.getValue());
                    break;

                case StaffManagerSuccess:
                    System.out.println("-- StaffManagerSuccess");
                    OneFieldPackage bai7312 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("StaffManagerSuccess", "", bai7312.getValue());
                    break;

                case StaffManagerError:
                    System.out.println("-- StaffManagerError");
                    OneFieldPackage bai72322 = gson.fromJson(request, OneFieldPackage.class);
                    support.firePropertyChange("StaffManagerError", "", bai72322.getValue());
                    break;



                default:
                    System.out.println("WRONG REQUEST FROM " + socket.getRemoteSocketAddress().toString());
                    System.out.println("- " + dataPackage.getType().toString());
                    break;
            }   //SWITCH END

        }
        };

        Thread t = new Thread(runnable);
        t.setDaemon(true);
        t.start();
    }


    @Override
    public void logInButton(String email, String password)
    {
        writer.println(gson.toJson(new TwoFieldPackage(NetworkType.OpenClientWindowRequest, email, password)));
    }

    @Override
    public void registerButton()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.OpenRegisterRequest, "")));
    }

    @Override
    public void registerBackButton()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.RegisterOpenLogInRequest, "")));
    }

    @Override
    public void registerNewAccount(RegisterData registerData)
    {
        writer.println(gson.toJson(new RegisterDataPackage(NetworkType.CreateAccountRequest, registerData)));
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

}
