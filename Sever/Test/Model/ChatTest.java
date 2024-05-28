package Model;

import Mediator.ServerMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest
{
  private Chat chat;
  @BeforeEach void setUp()
  {
    chat = new Chat("LeoVsVas", new ServerMaster());
  }

  //Tested under addToChat method in ChatListTest
  /*@Test void addParticipant()
  {
  }*/

  //Tested under removeFromChat method in ChatListTest
  /*@Test void removeParticipant()
  {
  }*/

  //Tested under writeToChat method in ChatListTest
  /*@Test void writeToChat()
  {
  }*/

  @Test void containsUserWhenChatIsEmpty()
  {
    User user = new User("Leo", "ThaMan", "666");
    assertFalse(chat.containsUser(user));
  }
  @Test void containsUserWhenUsersAreNull()
  {
    assertFalse(chat.containsUser(null));
  }
  @Test void containsUserWhenThereIsOne()
  {
    User user = new User("Leo", "ThaMan", "666");
    chat.addParticipant(user);
    assertTrue(chat.containsUser(user));
  }

  @Test void containsUserWhenThereIsMultiple()
  {
    ArrayList<User> users = new ArrayList<>();
    for (int i = 0; i < 8; i++)
    {
      users.add(new User("Leo" + i, "ThaMan", "666"));
      chat.addParticipant(users.get(i));
    }

    assertTrue(chat.containsUser(users.get(2)));
  }
}