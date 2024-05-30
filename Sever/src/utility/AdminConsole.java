package utility;

import Model.EventListModel;

import java.util.Scanner;

public class AdminConsole implements Runnable{
  private Scanner in;
  private boolean running;
  private EventListModel model;
  public AdminConsole(EventListModel model) {
    in = new Scanner(System.in);
    running = true;
    this.model = model;
  }

  @Override public void run() {
    System.out.println("Provide a login and password separated by a ';'");
    while (true){
      String ans = in.nextLine();
      if (ans.split(";")[0].equals("admin1") && ans.split(";")[1].equals("1234")){
        System.out.println("Login successful!");
        break;
      }
      else {
        System.err.println("Something went wrong.");
      }
    }
    System.out.println("type 'help' for help");
    while (running){
      processRequest(in.nextLine().split(";"));
    }
  }
  private void processRequest(String[] reqSplit){
    switch (reqSplit[0].toLowerCase()){
      case "help" -> {
        System.out.println(" makemoderator;{name of player} \t: changes a user into a moderator"
            + "\n makeuser;{name of moderator} \t\t: changes a moderator into a user"); // list of available commands
      }
      case "makemoderator" -> {
        if (reqSplit.length == 1){
          System.err.println("makemoderator takes one argument");
        }
        else {
          System.out.println(model.makeUserIntoModerator(reqSplit[1]));
        }
      }
      case "makeuser" -> {
        if (reqSplit.length == 1){
          System.err.println("makeuser takes one argument");
        }
        else {
          System.out.println(model.makeModeratorIntoUser(reqSplit[1]));
        }
      }
      default -> {
        System.err.println("Unknown request, type 'help' for help");
      }
    }
  }
}
