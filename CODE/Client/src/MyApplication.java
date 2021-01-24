import model.BankModel;
import model.BankModelManager;
import viewModel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewHandler;

public class MyApplication extends Application
{
  public void start(Stage primaryStage)
  {
    setUserAgentStylesheet(STYLESHEET_MODENA);
    BankModel model = new BankModelManager("localhost", 9876);
    ViewModelFactory factory = new ViewModelFactory(model);
    ViewHandler view = new ViewHandler(factory);
      if(model.getRemoteModel().isConnected())
      {
        view.start(primaryStage);
        System.out.println("-Connection to the server successful-");
      }
      else System.out.println("-----CONNECTION TO SERVER FAILED-----");

  }
}
