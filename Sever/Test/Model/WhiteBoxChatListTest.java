package Model;

import Mediator.ServerMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WhiteBoxChatListTest
{

  private ChatList chatList;
  @BeforeEach void setUp()
  {
    chatList = new ChatList();
  }

  @Test void removeFromChatIfChatDoesNotExist()
  {
    User user = new User("Leo", "ThaMan", "666");
    assertThrows(Exception.class, ()->chatList.removeFromChat("LeoVsVas", user));
  }

  @Test void removeFromChatIfChatExists()
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

  @Test void addToChatIfChatDoesNotExist()
  {
    User user = new User("Leo", "ThaMan", "666");
    assertThrows(Exception.class, ()->chatList.addToChat("LeoVsVas", user));
  }
  @Test void addToChatIfUserAlreadyInChat()
  {
    User user = new User("Leo", "ThaMan", "666");
    try
    {
      chatList.newChat("LeoVsVas", new ServerMaster());
      chatList.addToChat("LeoVsVas", user);
    }catch (Exception e){}
    assertThrows(Exception.class, ()->chatList.addToChat("LeoVsVas", user));
  }
  @Test void addToChatNormally()
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

  @Test void newChatWhenNameTaken()
  {
    ServerMaster serverMaster = new ServerMaster();
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
    }
    catch (Exception e){}
    assertThrows(Exception.class, () -> chatList.newChat("LeoVsVas", serverMaster));
  }
  @Test void newChatWhenNameUnique()
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

  @Test void getChatByNameWhenChatNotFound()
  {
    ServerMaster serverMaster = new ServerMaster();
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
    }catch (Exception e){}
    assertNull(chatList.getChatByName("BasVsVas"));
  }

  @Test void getChatByNameWhenChatFound()
  {
    ServerMaster serverMaster = new ServerMaster();
    Chat chat = new Chat("LeoVsVas", serverMaster);
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
    }catch (Exception e){}
    assertEquals(chat.toString(), chatList.getChatByName("LeoVsVas").toString());
  }

  @Test void writeToChatWhenChatNotFound()
  {
    User user = new User("Leo", "ThaMan", "666");
    assertThrows(Exception.class, () -> chatList.writeToChat("LeoVsVas", "We want to reach you regarding your cars extended warranty", user));
  }

  @Test void writeToChatWhenChatFound()
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
    assertEquals(log, chatList.getChatLogByName("LeoVsVas"));;
  }
  @Test void getChatLogByNameWhenChatNotFound()
  {
    ServerMaster serverMaster = new ServerMaster();
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
    }
    catch (Exception e){}
    assertNull(chatList.getChatByName("TeoVsVas"));
  }

  @Test void getChatLogByNameWhenChatFound()
  {
    ServerMaster serverMaster = new ServerMaster();
    Chat expectedChat = new Chat("LeoVsVas", serverMaster);
    try
    {
      chatList.newChat("LeoVsVas", serverMaster);
    }catch (Exception e){}
    assertEquals(expectedChat.toString(), chatList.getChatByName("LeoVsVas").toString());
  }
}