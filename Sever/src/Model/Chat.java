package Model;

import Mediator.ServerMaster;

import java.time.LocalDate;
import java.util.ArrayList;

public class Chat {
  private ArrayList<User> participatingUsers;
  private String chatName;
  private String log;
  private ServerMaster serverMaster;
  public Chat(String chatName, ServerMaster serverMaster){
    log = LocalDate.now()+"\n";
    this.chatName = chatName;
    this.serverMaster = serverMaster;
    participatingUsers = new ArrayList<>();
  }
  public boolean nameEquals(String name){
    return chatName.equals(name);
  }
  public void addParticipant(User user){
    participatingUsers.add(user);
  }
  public void removeParticipant(User user){
    participatingUsers.remove(user);
  }
  public void writeToChat(String message, User user){
    log += user.getDisplayName()+":¶"+message + "\n";
    serverMaster.useredBroadcast(participatingUsers, user.getDisplayName()+":¶"+message, "Chat_"+chatName);
  }
  public boolean containsUser(User user){
    for (User u:participatingUsers){
      if (u.equals(user)){
        return true;
      }
    }
    return false;
  }
  public String getLog(){
    return log;
  }

  @Override public String toString()
  {
    return "\nChat{" + "participatingUsers='" + participatingUsers +
        '\'' + ", chatName='" + chatName + '\''
        + ", log=" + log + ", serverMaster=" + serverMaster + '}';
  }
}
