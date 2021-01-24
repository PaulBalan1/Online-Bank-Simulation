import mediator.ClientConnector;
import model.Model;
import model.ModelManager;

import java.io.IOException;
import java.sql.SQLException;

public class Main
{
    public static void main(String args[]) throws IOException {
        Model model = null;
        try {
            model = new ModelManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ClientConnector connect = new ClientConnector(model);
        Thread t = new Thread(connect);
        t.start();
    }
}