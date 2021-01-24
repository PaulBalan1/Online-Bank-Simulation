package mediator;

import model.LoginModel;
import model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnector implements Runnable, PropertyChangeListener
{
    final int PORT = 9876;
    boolean running;
    ServerSocket welcomeSocket;
    Model model;

    public ClientConnector(Model model) throws IOException
    {
        this.model = model;
        running = false;
        model.addListener("CLOSE_SERVER", this);
    }

    @Override
    public void run()
    {
        running = true;
        try {
            welcomeSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(running)
        {
            Socket socket = null;
            try {
                System.out.println("----Waiting for client----");
                socket = welcomeSocket.accept();
                System.out.println("-Client connected with ip: " + socket.getRemoteSocketAddress().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            LogInClientHandler c = null;
            c = new LogInClientHandler(socket, (LoginModel) model);
            Thread t = new Thread(c);
            t.setDaemon(true);
            t.start();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        if(event.getPropertyName().equals("CLOSE_SERVER"))
            running = false;
    }
}
