import Mediator.ServerMaster;
import Mediator.ThreadedServer;
import Model.EventListModelManager;
import Model.EventListModel;
import utility.AdminConsole;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Scanner;

public class ServerStart {
  public static void main(String[] args) throws IOException {
    int PORT = 1234;
    ServerSocket socket = new ServerSocket(PORT);
    ServerMaster serverMaster = new ServerMaster();
    EventListModel model = new EventListModelManager(serverMaster);
    ThreadedServer threadedServer = new ThreadedServer(socket, serverMaster, model);
    Thread s0 = new Thread(threadedServer);
    AdminConsole adminConsole = new AdminConsole(model);
    Thread a0 = new Thread(adminConsole);
    s0.start();
    safeWait(0.5);
    a0.start();
  }
  private static void safeWait(double seconds){
    try {
      Thread.sleep((long) (seconds * 1000));
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}