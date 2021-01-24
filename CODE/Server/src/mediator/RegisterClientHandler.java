package mediator;
import com.google.gson.Gson;
import model.RegisterModel;
import networkPackages.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RegisterClientHandler implements Runnable, PropertyChangeListener
{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private RegisterModel model;
    private boolean stopCondition;
    private Gson gson;

    public RegisterClientHandler(Socket socket, RegisterModel model, BufferedReader reader, PrintWriter writer)
    {
        this.model = model;
        this.socket = socket;
        stopCondition = false;
        this.reader = reader;
        this.writer = writer;
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
                case RegisterOpenLogInRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - RegisterOpenLogInRequest");
                    this.openLogInWindow();
                    stopCondition = true;
                    break;

                case CreateAccountRequest:
                    System.out.println(socket.getRemoteSocketAddress().toString() + " - CreateAccountRequest");
                    RegisterDataPackage registerDataPackage = gson.fromJson(request, RegisterDataPackage.class);
                    String aux = model.registerClient(registerDataPackage.getValue());
                    if(aux.equals("NEW ACCOUNT CREATED"))
                        writer.println(gson.toJson(new OneFieldPackage(NetworkType.RegisterSuccess, aux)));
                    else writer.println(gson.toJson(new OneFieldPackage(NetworkType.RegisterError, aux)));
                    break;

                default:
                    System.out.println("WRONG REQUEST FROM " + socket.getRemoteSocketAddress().toString());
                    System.out.println("- " + dataPackage.getType().toString());
                    break;
            }   //SWITCH END

        }
    }

    public void openLogInWindow()
    {
        writer.println(gson.toJson(new OneFieldPackage(NetworkType.RegisterOpenLogInWindow, "")));
    }


    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        if(event.getPropertyName().equals("CLOSE_SERVER"))
            stopCondition = true;
        else if(event.getPropertyName().equals("ClientDisconnected") && event.getNewValue().equals(socket.getRemoteSocketAddress().toString()))
            stopCondition = true;
    }
}












