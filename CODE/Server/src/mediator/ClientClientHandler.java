package mediator;
import com.google.gson.Gson;
import model.ClientModel;
import networkPackages.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientClientHandler implements Runnable, PropertyChangeListener
{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private ClientModel model;
    private boolean stopCondition;
    private Gson gson;
    private String cpr;

    public ClientClientHandler(Socket socket, ClientModel model, BufferedReader reader, PrintWriter writer, String cpr)
    {
        this.model = model;
        this.socket = socket;
        stopCondition = false;
        this.reader = reader;
        this.writer = writer;
        this.cpr = cpr;
        gson = new Gson();
        model.addListener("ClientDisconnected", this);
        model.addListener("CLOSE_SERVER", this);
    }


    @Override
    public void run()
    {
        String request = null;
        NetworkPackage dataPackage;
        while(!stopCondition)
        {
            try {
                request = reader.readLine();
            } catch (IOException e)
            {
                System.out.println(socket.getRemoteSocketAddress().toString() + " - disconnected");
                model.closeByIP(socket.getRemoteSocketAddress().toString());
                stopCondition = true;
                break;
                //e.printStackTrace();
            }
            dataPackage = gson.fromJson(request, NetworkPackage.class);

            switch(dataPackage.getType())
            {
                case ClientOpenLogInRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientOpenLogInRequest");
                    this.openLogInWindow();
                    stopCondition = true;
                    break;

                case NewClientDataRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - NewClientDataRequest");
                    this.sendNewCLientData();
                    break;

                case ClientRequestLoanRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientRequestLoanRequest");
                    OneFieldPackage registerDataPackage = gson.fromJson(request, OneFieldPackage.class);
                    String aux = model.requestLoan(registerDataPackage.getValue(), this.cpr);
                    if(aux.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientHomeSuccess, aux)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientHomeError, aux)));
                    break;

                case ClientRequestTransferRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientRequestTransferRequest");
                    ThreeFieldPackage registerDataPackage2 = gson.fromJson(request, ThreeFieldPackage.class);
                    String aux2 = model.transferFunds(registerDataPackage2.getFirstField(), registerDataPackage2.getSecondField(), registerDataPackage2.getThirdField(), this.cpr);
                    if(aux2.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientHomeSuccess, aux2)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientHomeError, aux2)));
                    break;

                case ClientCloseAccountRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientCloseAccountRequest");
                    OneFieldPackage registerDataPackage3 = gson.fromJson(request, OneFieldPackage.class);
                    String aux3 = model.closeAccount(registerDataPackage3.getValue(), this.cpr);
                    if(aux3.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientAccountsSuccess, aux3)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientAccountsError, aux3)));
                    break;

                case ClientOpenAccountRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientOpenAccountRequest");
                    TwoFieldPackage registerDataPackage4 = gson.fromJson(request, TwoFieldPackage.class);
                    String aux4 = model.openAccount(registerDataPackage4.getFirstField(), registerDataPackage4.getSecondField(), this.cpr);
                    if(aux4.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientAccountsSuccess, aux4)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientAccountsError, aux4)));
                    break;

                case ClientWithdrawFundsRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientWithdrawFundsRequest");
                    TwoFieldPackage registerDataPackage6 = gson.fromJson(request, TwoFieldPackage.class);
                    String aux6 = model.withdrawFunds(registerDataPackage6.getFirstField(), registerDataPackage6.getSecondField(), this.cpr);
                    if(aux6.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientAccountsSuccess, aux6)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientAccountsError, aux6)));
                    break;

                case ClientAddFundsRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientAddFundsRequest");
                    TwoFieldPackage registerDataPackage5 = gson.fromJson(request, TwoFieldPackage.class);
                    String aux5 = model.addFunds(registerDataPackage5.getFirstField(), registerDataPackage5.getSecondField(), this.cpr);
                    if(aux5.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientAccountsSuccess, aux5)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientAccountsError, aux5)));
                    break;

                case ClientNewPasswordRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientNewPasswordRequest");
                    ThreeFieldPackage registerDataPackage8 = gson.fromJson(request, ThreeFieldPackage.class);
                    String aux8 = model.changePasswordPressed(registerDataPackage8.getFirstField(), registerDataPackage8.getSecondField(), registerDataPackage8.getThirdField(), this.cpr);
                    if(aux8.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientSettingsSuccess, aux8)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientSettingsError, aux8)));
                    break;

                case ClientNewNumberRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientNewNumberRequest");
                    OneFieldPackage registerDataPackage9 = gson.fromJson(request, OneFieldPackage.class);
                    String aux9 = model.changeNumberPressed(registerDataPackage9.getValue(), this.cpr);
                    if(aux9.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientSettingsSuccess, aux9)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientSettingsError, aux9)));
                    break;

                case ClientNewAdressRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientNewAdressRequest");
                    OneFieldPackage registerDataPackage91 = gson.fromJson(request, OneFieldPackage.class);
                    String aux91 = model.changeAdressPressed(registerDataPackage91.getValue(), this.cpr);
                    if(aux91.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientSettingsSuccess, aux91)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientSettingsError, aux91)));
                    break;

                case ClientDeleteAccountRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ClientDeleteAccountRequest");
                    OneFieldPackage registerDataPackage911 = gson.fromJson(request, OneFieldPackage.class);
                    String aux911 = model.deleteAccountPressed(this.cpr);
                    if(aux911.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientDeleteSuccess, aux911)));
                    break;

                default:
                    System.out.println("WRONG REQUEST FROM " + socket.getRemoteSocketAddress().toString());
                    System.out.println("- " + dataPackage.getType().toString());
                    break;
            }   //SWITCH END

        }
    }
    //return "Operation successful";


    public void openLogInWindow()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.ClientOpenLogInWindow, "")));
    }

    public void sendNewCLientData()
    {
        ClientDataPackage bah = new ClientDataPackage(NetworkType.NewClientData, model.getClientData(this.cpr));
        writer.println(gson.toJson(bah));
    }



    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        if(event.getPropertyName().equals("ClientDisconnected") && event.getNewValue().equals(socket.getRemoteSocketAddress().toString()))
            stopCondition = true;
        else if(event.getPropertyName().equals("CLOSE_SERVER"))
            stopCondition = true;
    }
}












