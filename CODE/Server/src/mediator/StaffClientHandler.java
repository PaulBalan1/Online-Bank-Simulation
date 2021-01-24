package mediator;

import com.google.gson.Gson;
import data.RegisterData;
import model.StaffModel;
import networkPackages.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class StaffClientHandler implements Runnable, PropertyChangeListener
{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private StaffModel model;
    private boolean stopCondition;
    private String position;
    private Gson gson;
    String cpr;

    public StaffClientHandler(Socket socket, StaffModel model, BufferedReader reader, PrintWriter writer, String cpr,  String position)
    {
        System.out.println("INITIATED STAFF");
        this.model = model;
        this.socket = socket;
        stopCondition = false;
        this.reader = reader;
        this.writer = writer;
        this.cpr = cpr;
        this.position = position;
        gson = new Gson();
        model.addListener("ClientDisconnected", this);
        model.addListener("CLOSE_SERVER", this);
    }


    @Override
    public void run()
    {
        System.out.println("STAFF RUNNING");
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
                case StaffOpenLogInRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - StaffOpenLogInRequest");
                    this.openLogInWindow();
                    stopCondition = true;
                    break;
                    
                case StaffGetNewDataRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - StaffGetNewDataRequest");
                    this.sendNewStaffData();
                    break;

                case StaffAcceptClientRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - StaffAcceptClientRequest");
                    OneFieldPackage oneFieldPackage1 = gson.fromJson(request, OneFieldPackage.class);
                    String result1 = model.staffAcceptClient(oneFieldPackage1.getValue());
                    if(result1.equals("Operation successful"))
                        this.sendNewStaffData();
                    break;

                case StaffChangeClientPasswordRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - StaffChangeClientPasswordRequest");
                    ThreeFieldPackage registerDataPackage8 = gson.fromJson(request, ThreeFieldPackage.class);
                    String aux8 = model.staffChangeClientPassword(registerDataPackage8.getFirstField(), registerDataPackage8.getSecondField(), registerDataPackage8.getThirdField(), false);
                    if(aux8.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManageUsersSuccess, aux8)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManageUsersError, aux8)));
                    break;

                case StaffRetrieveClientDataRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - StaffChangeClientPasswordRequest");
                    OneFieldPackage oneFieldPackage155 = gson.fromJson(request, OneFieldPackage.class);
                    RegisterData aux28 = model.staffGetCLientData(oneFieldPackage155.getValue());
                    if(aux28 != null)
                        writer.println(gson.toJson(new RegisterDataPackage(NetworkType.StaffManageNewData, aux28)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManageUsersError, "CHECK CPR AND TRY AGAIN")));
                    break;

                case ChangeOwnStaffPasswordRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ChangeOwnStaffPasswordRequest");
                    ThreeFieldPackage registerDataPackage28 = gson.fromJson(request, ThreeFieldPackage.class);
                    String aux282 = model.changePasswordPressed(registerDataPackage28.getFirstField(), registerDataPackage28.getSecondField(), registerDataPackage28.getThirdField(), this.cpr);
                    if(aux282.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffSettingsSuccess, aux282)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffSettingsError, aux282)));
                    break;

                case ManagerChangeStaffPasswordRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ManagerChangeStaffPasswordRequest");
                    ThreeFieldPackage registerDataPackage88 = gson.fromJson(request, ThreeFieldPackage.class);
                    String aux88 = model.staffChangeClientPassword(registerDataPackage88.getFirstField(), registerDataPackage88.getSecondField(), registerDataPackage88.getThirdField(), true);
                    if(aux88.equals("Operation successful"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManagerSuccess, aux88)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManagerError, aux88)));
                    break;

                case ManagerCreateNewStaffRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ManagerCreateNewStaffRequest");
                    FourFieldPackage registerDataPackage818 = gson.fromJson(request, FourFieldPackage.class);
                    String aux818 = model.managerCreateNewStaff(registerDataPackage818.getFirstField(), registerDataPackage818.getSecondField(), registerDataPackage818.getThirdField(), registerDataPackage818.getFourthField());
                    if(aux818.equals("Operation successful"))
                    {
                        this.sendNewStaffData();
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManagerSuccess, aux818)));
                    }
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManagerError, aux818)));
                    break;

                case DeleteStaffRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - DeleteStaffRequest");
                    OneFieldPackage registerDataPackage3 = gson.fromJson(request, OneFieldPackage.class);
                    String aux3 = model.deleteStaff(registerDataPackage3.getValue());
                    if(aux3.equals("Operation successful"))
                    {
                        this.sendNewStaffData();
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManagerSuccess, aux3)));
                    }
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManagerError, aux3)));
                    break;

                case StaffAcceptLoanRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - StaffAcceptLoanRequest");
                    OneFieldPackage registerDataPackage289 = gson.fromJson(request, OneFieldPackage.class);
                    String aux2829 = model.staffAcceptLoan(registerDataPackage289.getValue());
                    if(aux2829.equals("Operation successful"))
                    {
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManageUsersSuccess, aux2829)));
                        this.sendNewStaffData();
                    }
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManageUsersError, aux2829)));
                    break;

                case StaffCloseServerRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - StaffCloseServerRequest");
                    model.closeAll();
                    break;

                case ManagerChangeRateRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - ManagerChangeRateRequest");
                    TwoFieldPackage registerDataPackage35 = gson.fromJson(request, TwoFieldPackage.class);
                    String aux35 = model.staffChangeRate(registerDataPackage35.getFirstField(), registerDataPackage35.getSecondField());
                    if(aux35.equals("Operation successful"))
                    {
                        this.sendNewStaffData();
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManagerSuccess, aux35)));
                    }
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffManagerError, aux35)));
                    break;



                default:
                    System.out.println("WRONG REQUEST FROM " + socket.getRemoteSocketAddress().toString());
                    System.out.println("- " + dataPackage.getType().toString());
                    break;
            }   //SWITCH END

        }
    }



    private void sendNewStaffData()
    {
        Boolean bai = this.position.equals("manager");
        StaffDataPackage bah = new StaffDataPackage(NetworkType.NewStaffData, model.getStaffData(this.cpr, bai));
        writer.println(gson.toJson(bah));
    }


    public void openLogInWindow()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.StaffOpenLogInWindow, "")));
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
