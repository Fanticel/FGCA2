package Model;

import Mediator.ServerMaster;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhiteBoxChatTest
{
  private Chat chat;
  @BeforeEach void setUp()
  {
    chat = new Chat("LeoVsVas", new ServerMaster());
  }

  /*Tested in EventList
  @Test void addParticipant()
  {
  }

  @Test void removeParticipant()
  {
  }

  @Test void writeToChat()
  {
  }*/

  @Test void containsUserWhenUserFound()
  {
    User user = new User("Leo", "ThaMan", "666");
    chat.addParticipant(user);
    assertTrue(chat.containsUser(user));
  }
  @Test void containsUserWhenUserNotFound()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Barbara", "ThaMan", "666");
    chat.addParticipant(user);
    assertFalse(chat.containsUser(user2));
  }
}