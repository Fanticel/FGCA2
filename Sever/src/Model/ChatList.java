package Model;

import Mediator.ServerMaster;

import java.util.ArrayList;

public class ChatList {
  private ArrayList<Chat> chatList;

  public ChatList() {
    chatList = new ArrayList<>();
  }

  public void removeFromChat(String name, User user) throws Exception {
    if (getChatByName(name) == null) {
      throw new Exception("Cannot leave a chat that doesn't exist");
    }
    getChatByName(name).removeParticipant(user);
  }

  public void addToChat(String name, User user) throws Exception {
    if (getChatByName(name) == null) {
      throw new Exception("Cannot join a chat that doesn't exist");
    }
    if (getChatByName(name).containsUser(user)) {
      throw new Exception("Cannot join a chat twice");
    }
    getChatByName(name).addParticipant(user);
  }

  public void newChat(String chatName, ServerMaster serverMaster)
      throws Exception {
    if (getChatByName(chatName) != null){
      throw new Exception("Cannot create a chat with a name that already exists");
    }
    chatList.add(new Chat(chatName, serverMaster));
  }

  public Chat getChatByName(String name) {
    for (Chat c : chatList) {
      if (c.nameEquals(name)) {
        return c;
      }
    }
    return null;
  }
  public void writeToChat(String name, String message, User user) throws Exception {
    if (getChatByName(name) == null){
      throw new Exception("Cannot write to a chat that doesn't exists");
    }
    getChatByName(name).writeToChat(message, user);
  }
  public String getChatLogByName(String name){
    if (getChatByName(name) == null){
      return null;
    }
    return getChatByName(name).getLog();
  }

  public ArrayList<Chat> getChatList()
  {
    return chatList;
  }
}
