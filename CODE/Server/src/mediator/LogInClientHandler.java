package mediator;
import com.google.gson.Gson;
import model.ClientModel;
import model.LoginModel;

import model.RegisterModel;
import model.StaffModel;
import networkPackages.NetworkPackage;
import networkPackages.NetworkType;
import networkPackages.OneFieldPackage;
import networkPackages.TwoFieldPackage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LogInClientHandler implements Runnable, PropertyChangeListener
{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private LoginModel model;
    private boolean stopCondition;
    private Gson gson;

    public LogInClientHandler(Socket socket, LoginModel model)
    {
        this.model = model;
        this.socket = socket;
        stopCondition = false;
        this.openIO();
        gson = new Gson();
        model.addListener("ClientDisconnected", this);
        model.addListener("CLOSE_SERVER", this);
    }

    public void openIO()
    {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeIO()
    {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket()
    {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            switch(dataPackage.getType()) {
                case OpenRegisterRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - OpenRegisterRequest");
                    this.openRegisterWindow();
                    RegisterClientHandler c = null;
                    c = new RegisterClientHandler(socket, (RegisterModel) model, reader, writer);
                    Thread t = new Thread(c);
                    t.setDaemon(true);
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case OpenClientWindowRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - OpenClientWindowRequest");
                    TwoFieldPackage logInPackage = gson.fromJson(request, TwoFieldPackage.class);
                    String aux = model.checkLogInData(logInPackage.getFirstField(), logInPackage.getSecondField(),
                            socket.getRemoteSocketAddress().toString());
                    if(aux.equals("NOT FOUND"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.LogInError, "WRONG EMAIL/PASSWORD")));
                    else if(aux.equals("USER NOT APPROVED"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.LogInError, "User not accepted yet")));
                    else if(aux.substring(10).equals("client"))
                    {
                        this.openClientWindow();
                        ClientClientHandler c2 = null;
                        c2 = new ClientClientHandler(socket, (ClientModel) model, reader, writer, aux.substring(0, 10));
                        Thread t2 = new Thread(c2);
                        t2.setDaemon(true);
                        t2.start();
                        try {
                            t2.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                     }
                    else if(aux.substring(10).equals("staff") || aux.substring(10).equals("manager"))
                    {
                        this.openStaffWindow();
                        System.out.println("-OPENED STAFF");
                        StaffClientHandler c3 = null;
                        c3 = new StaffClientHandler(socket, (StaffModel) model, reader, writer, aux.substring(0, 10), aux.substring(10));
                        Thread t3 = new Thread(c3);
                        t3.setDaemon(true);
                        t3.start();
                        try {
                            t3.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.LogInError, "Wrong email/password")));
                    break;

                case CloseSocketRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - CloseSocketRequest");
                    this.closeAppWindow();
                    this.closeIO();
                    this.closeSocket();
                    stopCondition = true;
                    break;

                default:
                    System.out.println("WRONG REQUEST FROM " + socket.getRemoteSocketAddress().toString());
                    System.out.println(dataPackage.getType().toString());
                    break;
            }   //SWITCH END

        }
    }

    public void openRegisterWindow()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.OpenRegisterWindow, "")));
    }


    public void openClientWindow()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.OpenClientWindow, "")));
    }

    public void openStaffWindow()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.OpenStaffWindow, "")));
    }


    public void closeAppWindow()
    {
        System.out.println(socket.getRemoteSocketAddress().toString() + "Stopped connection");
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.CloseApp, "")));
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












