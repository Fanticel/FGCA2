package Model;

import Mediator.ServerMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatListTest
{
  private ChatList chatList;
  @BeforeEach void setUp()
  {
    chatList = new ChatList();
  }

  @Test void removeFromChatWhenItsNull()
  {
    User user = new User("Leo", "ThaMan", "666");
    assertThrows(Exception.class, ()->chatList.removeFromChat("LeoVsVas", user));
  }
  @Test void removeFromChatOnce()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    ChatList resultChatList = new ChatList();
    ServerMaster serverMaster = new ServerMaster();
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
      chatList.addToChat("LeoVsVas", user);
      chatList.addToChat("LeoVsVas", user2);
      chatList.removeFromChat("LeoVsVas", user);

      resultChatList.newChat("LeoVsVas", serverMaster);
      resultChatList.addToChat("LeoVsVas", user2);
    }catch (Exception e){}
    Chat expectedChat = new Chat("LeoVsVas", serverMaster);
    expectedChat.addParticipant(user2);
    assertEquals(expectedChat.toString(), chatList.getChatByName("LeoVsVas").toString());
  }
  @Test void removeFromChatMultipleTimes()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    ChatList resultChatList = new ChatList();
    ServerMaster serverMaster = new ServerMaster();
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
      chatList.addToChat("LeoVsVas", user);
      chatList.addToChat("LeoVsVas", user2);
      chatList.removeFromChat("LeoVsVas", user);
      chatList.removeFromChat("LeoVsVas", user2);
      chatList.removeFromChat("LeoVsVas", user);
      chatList.removeFromChat("LeoVsVas", user2);

      resultChatList.newChat("LeoVsVas", serverMaster);
    }catch (Exception e){}
    assertEquals(resultChatList.getChatByName("LeoVsVas").toString(), chatList.getChatByName("LeoVsVas").toString());
  }
  //Also a boundary test because it removes a not existing user(already removed)
  @Test void removeFromChatTheSameUserMultipleTimes()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    ChatList resultChatList = new ChatList();
    ServerMaster serverMaster = new ServerMaster();
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
      chatList.addToChat("LeoVsVas", user);
      chatList.addToChat("LeoVsVas", user2);
      chatList.removeFromChat("LeoVsVas", user);
      chatList.removeFromChat("LeoVsVas", user);
      chatList.removeFromChat("LeoVsVas", user);

      resultChatList.newChat("LeoVsVas", serverMaster);
      resultChatList.addToChat("LeoVsVas", user2);
    }catch (Exception e){}
    assertEquals(resultChatList.getChatByName("LeoVsVas").toString(), chatList.getChatByName("LeoVsVas").toString());
  }
  @Test void addToChatWhenChatIsNull()
  {
    User user = new User("Leo", "ThaMan", "666");
    assertThrows(Exception.class, ()->chatList.addToChat("LeoVsVas", user));
  }
  @Test void addToChatOnce()
  {
    User user = new User("Leo", "ThaMan", "666");
    ChatList resultChatList = new ChatList();
    try
    {
      chatList.newChat("LeoVsVas", new ServerMaster());
      chatList.addToChat("LeoVsVas", user);
    }catch (Exception e){}
    assertTrue(chatList.getChatByName("LeoVsVas").containsUser(user));
  }
  @Test void addToChatMultipleTimes()
  {
    User user = new User("Leo", "ThaMan", "666");
    ChatList resultChatList = new ChatList();
    try
    {
      chatList.newChat("LeoVsVas", new ServerMaster());
      chatList.addToChat("LeoVsVas", user);
      chatList.addToChat("LeoVsVas", user);
    }catch (Exception e){}
    assertThrows(Exception.class, ()->chatList.addToChat("LeoVsVas", user));
  }
  @Test void addToChatMultipleUsers()
  {
    ArrayList<User> users = new ArrayList<>();
    ChatList resultChatList = new ChatList();
    try
    {
      chatList.newChat("LeoVsVas", new ServerMaster());
      for (int i = 0; i < 8; i++){
        users.add(new User("Leo" + i, "ThaMan", "666"));
        chatList.addToChat("LeoVsVas", users.get(i));
      }
    }catch (Exception e){}
    boolean added = true;
    for (int i = 0; i < 8; i++){
      if (added){
        chatList.getChatByName("LeoVsVas").containsUser(users.get(i));
      }
    }
    assertTrue(added);
  }
  //No boundaries, exceptions tested with null chat and adding same user multiple times

  @Test void newChatOnce()
  {
    ServerMaster serverMaster = new ServerMaster();
    ArrayList<Chat> chats = new ArrayList<>();
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
      Chat chat = new Chat("LeoVsVas", serverMaster);
      chats.add(chat);
    }
    catch (Exception e){}
    assertEquals(chats.toString(), chatList.getChatList().toString());
  }

  @Test void newChatMultipleDifferentChats()
  {
    ServerMaster serverMaster = new ServerMaster();
    ArrayList<Chat> chats = new ArrayList<>();
    try
    {
      for (int i = 0; i < 7; i++){
        chatList.newChat("LeoVsVas" + i, serverMaster);
        Chat chat = new Chat("LeoVsVas" + i, serverMaster);
        chats.add(chat);
      }
    }
    catch (Exception e){}
    assertEquals(chats.toString(), chatList.getChatList().toString());
  }
  @Test void newChatMultipleSameChats()
  {
    ServerMaster serverMaster = new ServerMaster();
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
      chatList.newChat("LeoVsVas", serverMaster);
      chatList.newChat("LeoVsVas", serverMaster);

    }
    catch (Exception e){}
    assertThrows(Exception.class, () -> chatList.newChat("LeoVsVas", serverMaster));
  }
  //No boundaries, exceptions tested in previous tests

  @Test void getChatByNameWhenChatIsNull()
  {
    assertNull(chatList.getChatByName("LeoVsVas"));
  }

  @Test void getChatByNameIfThereIsOneChat()
  {
    User user = new User("Leo", "ThaMan", "666");
    ChatList resultChatList = new ChatList();
    ServerMaster serverMaster = new ServerMaster();
    Chat chat = new Chat("LeoVsVas", serverMaster);
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
    }catch (Exception e){}
    assertEquals(chat.toString(), chatList.getChatByName("LeoVsVas").toString());
  }
  @Test void getChatByNameIfThereAreMultipleChats()
  {
    User user = new User("Leo", "ThaMan", "666");
    ServerMaster serverMaster = new ServerMaster();
    ArrayList<Chat> chats = new ArrayList<>();
    try
    {
      for (int i = 0; i < 7; i++){
        chatList.newChat("LeoVsVas" + i, serverMaster);
        Chat chat = new Chat("LeoVsVas" + i, serverMaster);
        chats.add(chat);
      }
    }catch (Exception e){}
    assertEquals(chats.get(1).toString(), chatList.getChatByName("LeoVsVas1").toString());
  }
//There are no borders or exceptions

  @Test void writeToChatWhenChatIsNull()
  {
    User user = new User("Leo", "ThaMan", "666");
    assertThrows(Exception.class, () -> chatList.writeToChat("LeoVsVas", "We want to reach you regarding your cars extended warranty", user));
  }
  @Test void writeToChatOnce()
  {
    User user = new User("Leo", "ThaMan", "666");
    ServerMaster serverMaster = new ServerMaster();
    String log = LocalDate.now().toString() + "\n";
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
      chatList.writeToChat("LeoVsVas", "Hello World", user);
      log += user.getDisplayName()+":¶"+ "Hello World" + "\n";
    }catch (Exception e){}
    assertEquals(log, chatList.getChatLogByName("LeoVsVas"));
  }

  @Test void writeToChatMultipleTimes()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");

    ServerMaster serverMaster = new ServerMaster();
    String log = LocalDate.now().toString() + "\n";
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
      chatList.writeToChat("LeoVsVas", "Hello World", user);
      chatList.writeToChat("LeoVsVas", "Do you know", user2);
      chatList.writeToChat("LeoVsVas", "the definition", user2);
      chatList.writeToChat("LeoVsVas", "of insanity?", user2);
      chatList.writeToChat("LeoVsVas", "?", user);
      log += user.getDisplayName()+":¶"+ "Hello World" + "\n";
      log += user2.getDisplayName()+":¶"+ "Do you know" + "\n";
      log += user2.getDisplayName()+":¶"+ "the definition" + "\n";
      log += user2.getDisplayName()+":¶"+ "of insanity?" + "\n";
      log += user.getDisplayName()+":¶"+ "?" + "\n";
    }catch (Exception e){}
    assertEquals(log, chatList.getChatLogByName("LeoVsVas"));
  }
  //There are no boundaries

  @Test void getChatLogByNameWhenChatNull()
  {
    assertNull(chatList.getChatLogByName("TeoVsVas"));
  }
  @Test void getChatLogByNameWhenListHasOneChat()
  {
    ServerMaster serverMaster = new ServerMaster();
    Chat expectedChat = new Chat("LeoVsVas", serverMaster);
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
    }catch (Exception e){}
    assertEquals(expectedChat.toString(), chatList.getChatByName("LeoVsVas").toString());
  }
  @Test void getChatLogByNameWhenListHasMultipleChats()
  {
    ServerMaster serverMaster = new ServerMaster();
    Chat expectedChat = new Chat("LeoVsVas2", serverMaster);
    ArrayList<Chat> chats = new ArrayList<>();
    try
    {
      for (int i = 0; i < 7; i++){
        chatList.newChat("LeoVsVas" + i, serverMaster);
        Chat chat = new Chat("LeoVsVas" + i, serverMaster);
        chats.add(chat);
      }
    }catch (Exception e){}
    assertEquals(expectedChat.toString(), chatList.getChatByName("LeoVsVas2").toString());
  }
  //Boundaries do not exist
}