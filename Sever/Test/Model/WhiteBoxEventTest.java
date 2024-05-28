package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WhiteBoxEventTest
{
  private Event event;
  @BeforeEach void setUp()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
  }
//Tested in eventList part
  /*@Test void addParticipant()
  {
  }

  @Test void removeParticipant()
  {
  }

  @Test void checkIn()
  {
  }
   */

  @Test void getMatchByParticipantsWhenMatchNotFound()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    event.addMatch(user, user2);
    assertNull(event.getMatchByParticipants("Random", "Vas"));
  }

  @Test void getMatchByParticipantsWhenMatchFound()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    event.addMatch(user, user2);
    Match match = new Match(user, user2);
    assertEquals(match, event.getMatchByParticipants("Leo", "Vas"));
  }

  @Test void addMatchIfMaximumNumberReached()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    //If the event has 8 maxParticipants it can only have 7 matches (in general 2^n -1)
    for (int i = 0; i < 7; i++){
      event.addMatch(null, null);
    }
    assertThrows(IllegalStateException.class, ()-> event.addMatch(user, user2));
  }

  @Test void addMatchIfLessThanMaximumNumber()
  {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Match> matches = new ArrayList<>();
    for (int i = 0; i < 8; i+=2){
      users.add(new User("Leo" + i, "ThaMan", "666"));
      users.add(new User("Leo" + (i + 1), "ThaMan", "666"));
      event.addMatch(users.get(users.size() - 2), users.get(users.size() - 1));
      matches.add(new Match(users.get(users.size() - 2), users.get(users.size() - 1)));
    }
    assertEquals(matches, event.getMatches());
  }

  @Test void updateNextMatchWhenItIsNotLast()
  {
    ArrayList<Match> matches = new ArrayList<>();
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    event.addMatch(user, user2, "2-1");
    matches.add(new Match(user, user2));
    for (int i = 0; i < 6; i++){
      event.addMatch(null, null);
      matches.add(new Match(null, null));
    }
    Match updatedMatch = new Match(user, null);
    event.updateNextMatch(event.getMatches().get(0));
    assertEquals(updatedMatch, event.getMatches().get(event.nextMatch(8, 0)));
  }

  @Test void updateNextMatchWhenItIsNotLastAndTheSecondPlayerWins()
  {
    ArrayList<Match> matches = new ArrayList<>();
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    event.addMatch(user, user2, "1-2");
    matches.add(new Match(user, user2));
    for (int i = 0; i < 6; i++){
      event.addMatch(null, null);
      matches.add(new Match(null, null));
    }
    Match updatedMatch = new Match(user2, null);
    event.updateNextMatch(event.getMatches().get(0));
    assertEquals(updatedMatch, event.getMatches().get(event.nextMatch(8, 0)));
  }

  @Test void updateNextMatchWhenItIsLast()
  {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Match> matches = new ArrayList<>();
    for (int i = 0; i < 14; i+=2){
      users.add(new User("Leo" + i, "ThaMan", "666"));
      users.add(new User("Leo" + (i + 1), "ThaMan", "666"));
      event.addMatch(users.get(users.size() - 2), users.get(users.size() - 1));
      matches.add(new Match(users.get(users.size() - 2), users.get(users.size() - 1)));
    }
    event.updateNextMatch(event.getMatches().get(6));
    assertEquals("Finished", event.getStatus());
  }

  @Test void nextMatch()
  {
    assertEquals(4,event.nextMatch(8,0));
  }
}