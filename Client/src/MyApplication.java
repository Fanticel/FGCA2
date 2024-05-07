import Model.EventListModel;
import Model.EventListModelManager;
import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewHandler;
import viewModel.ViewModelFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyApplication extends Application {
  private final int PORT = 1234;
  private final String HOST = "localhost";
  private PrintWriter out;
  private BufferedReader in;
  private Socket localSocket;
  private EventListModel model;
  @Override public void start(Stage primaryStage) throws Exception {
    localSocket = new Socket(HOST, PORT);
    out = new PrintWriter(localSocket.getOutputStream(), true);
    in = new BufferedReader(
        new InputStreamReader(localSocket.getInputStream()));
    out.println("initCall");
    System.out.println(in.readLine());
    if (in.readLine().split(":")[1].contains("initCallReply")){
      out.println("log;a;b;c");
      System.out.println("Connection successful");
      model = new EventListModelManager(localSocket);
      System.out.println("Model created");
      ViewModelFactory viewModelFactory = new ViewModelFactory(model);
      System.out.println("Factory created");
      ViewHandler viewHandler = new ViewHandler(viewModelFactory);
      System.out.println("ViewHandler created");
      viewHandler.start(primaryStage);
      System.out.println("Stage started");
      viewHandler.openPopupView("");
      viewHandler.closePopupView();
    }
    else {
      System.out.println("Something went wrong with the connection... \nClosing...");
      stop();
    }
  }

  @Override public void stop() throws Exception {
    System.out.println("stopping the application");
    out.println("^Q");
    localSocket.close();
  }
}
