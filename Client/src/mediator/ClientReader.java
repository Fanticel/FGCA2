package mediator;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReader implements Runnable {
  private BufferedReader in;
  private boolean running;

  private Client client;

  public ClientReader(Client client, BufferedReader in) {
    this.in = in;
    this.client = client;
    running = true;
  }

  @Override public void run() {
    while (running) {
      try {
        String tmp = in.readLine();
        if (tmp.split(": ")[0].contains("AllEvents")) {
          client.receiveMessage(tmp.split(": ")[1]);
        }
        else if (tmp.split(": ")[0].contains("GetEvent")){
          client.receiveMessage(tmp.split(": ")[1]);
        }
        else if (tmp.split(": ")[0].contains("AddEvent")){
          client.receiveMessage(tmp.split(": ")[1]);
        }
        else if(tmp.split(": ")[0].contains("Login"))
        {
          client.receiveMessage(tmp.split(": ")[1]);
        }
        else if(tmp.split(": ")[0].contains("Register"))
        {
          client.receiveMessage(tmp.split(": ")[1]);
        }
        else if (tmp.split(": ")[0].contains("Notification"))
        {
          String ans = tmp.split(": ")[1];
          client.receivedNotification(ans.split("_;_")[0], ans.split("_;_")[1].equalsIgnoreCase("TRUE"));
        }
        else if (tmp.split(": ")[0].contains("Opponent"))
        {
          //to do
        }
        //        else if (tmp.split(": ")[0].equals("\t->BAdd"))
//        {
//          //receivedAdd(tmp.split(": ")[1]);
//        }
      }
      catch (IOException e) {
        System.err.println(e.getMessage());
        break;
      }
    }
  }

  public void Close() {

  }
}
