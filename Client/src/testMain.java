import Model.EventListModelManager;
import Model.User;

import java.io.IOException;
import java.net.Socket;

public class testMain {
  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("localhost", 1234);
    EventListModelManager model = new EventListModelManager(socket);
    System.out.println(model.getEvent("Sample event"));
  }
}
