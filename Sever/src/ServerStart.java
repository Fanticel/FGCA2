import Mediator.ServerMaster;
import Mediator.ThreadedServer;
import Model.EventListModelManager;
import Model.EventListModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class ServerStart {
  public static void main(String[] args) throws IOException, SQLException {
    int PORT = 1234;
    ServerSocket socket = new ServerSocket(PORT);
    ServerMaster serverMaster = new ServerMaster();
    EventListModel model = new EventListModelManager(serverMaster);
    ThreadedServer s0 = new ThreadedServer(socket, serverMaster, model);
    s0.run();
  }
}