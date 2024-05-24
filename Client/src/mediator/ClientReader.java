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
        else if (tmp.split(": ")[0].contains("EventChange"))
        {
          String ans = tmp.split(": ")[1];
          client.eventChange(ans);
        }
        else if (tmp.split(": ")[0].contains("OpponentRequest"))
        {
          //to do
          String ans = tmp.split(": ")[1];
          client.opponentFound(ans.split("_;_")[1],Integer.parseInt(ans.split("_;_")[2]), ans.split("_;_")[3]);
        }
        else if (tmp.split(": ")[0].contains("OpponentRefused"))
        {
          String ans = tmp.split(": ")[1];
          client.opponentRefused();
          client.receivedNotification(ans.split("_;_")[0], ans.split("_;_")[1].equalsIgnoreCase("TRUE"));
        }
        else if (tmp.split(": ")[0].contains("OpponentFound"))
        {
          client.opponentAccepted(tmp.split(": ")[1].split("_")[0], tmp.split(": ")[1].split("_")[1]);
        }
        else if (tmp.split(": ")[0].contains("MatchSaved"))
        {
          String ans = tmp.split(": ")[1];
          client.matchSaved();
          client.receivedNotification(ans.split("_;_")[0],false);
          //property.firePropertyChange(new PropertyChangeEvent(this, "Notification", false, message));
        }
        //        else if (tmp.split(": ")[0].equals("\t->BAdd"))
//        {
//          //receivedAdd(tmp.split(": ")[1]);
//        }
        else if (tmp.split(": ")[0].split("_")[0].contains("Chat")){
          if (tmp.split(": ")[0].split("_").length > 2){
            client.receiveMessage(tmp.split(": ")[1].replaceAll("_pbs_", "\n").replaceAll("¶"," "));
          }
          else {
            client.receivedChat(tmp.split(": ")[1].replaceAll("_pbs_", "\n").replaceAll("¶"," "), tmp.split(": ")[0].split("_")[1]);
          }
        }
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
