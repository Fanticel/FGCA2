package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EventTest
{
  private Event event;
  @BeforeEach void setUp()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
  }
  @Test void getMatchByParticipantsWhenThereIsNoMatch()
  {
    assertEquals(null, event.getMatchByParticipants("Ivica", "Marica"));
  }
  @Test void getMatchByParticipantsWhenThereIsOneMatch()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    event.addMatch(user, user2);
    Match match = new Match(user, user2);
    assertEquals(match, event.getMatchByParticipants("Leo", "Vas"));
  }
  @Test void getMatchByParticipantsWhenThereAreMultiple()
  {
    ArrayList<User> users = new ArrayList<>();
    for (int i = 0; i < 8; i+=2){
      users.add(new User("Leo" + i, "ThaMan", "666"));
      users.add(new User("Leo" + (i + 1), "ThaMan", "666"));
      event.addMatch(users.get(users.size() - 2), users.get(users.size() - 1));
    }
    Match match = new Match(users.get(2), users.get(3));
    assertEquals(match, event.getMatchByParticipants("Leo2", "Leo3"));
  }
  //No border tests for getMatchByParticipants
  //Method returns no exceptions

  @Test void addMatchWhenThereAreNone()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    event.addMatch(user, user2);
    Match match = new Match(user, user2);
    ArrayList<Match> matches = new ArrayList<>(Arrays.asList(match));
    assertEquals(matches, event.getMatches());
  }
  @Test void addEmptyMatch()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    event.addMatch(null, null);
    Match match = new Match(null, null);
    ArrayList<Match> matches = new ArrayList<>(Arrays.asList(match));
    assertEquals(matches, event.getMatches());
  }
  @Test void addMultipleMatches()
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
  @Test void addMatchAfterMaximumNumberOfMatches()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    //If the event has 8 maxParticipants it can only have 7 matches (in general 2^n -1)
    for (int i = 0; i < 7; i++){
      event.addMatch(null, null);
    }
    assertThrows(IllegalStateException.class, ()-> event.addMatch(user, user2));
  }
  /*Done in matchTest
  @Test void checkIn()
  {
  }*/

  /*Done in eventListTest
  @Test void addParticipant()
  {
  }*/

  /*Done in eventListTest
  @Test void removeParticipant()
  {
  }*/

  @Test void updateNextMatchWhenItIsNull()
  {
    ArrayList<Match> matches = new ArrayList<>();
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    event.addMatch(user, user2, "2-1");
    matches.add(new Match(user, user2));
    assertThrows(IndexOutOfBoundsException.class, ()-> event.updateNextMatch(event.getMatches().get(0)));
  }
  @Test void updateNextMatch()
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
  @Test void updateMultipleNextMatches()
  {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Match> matches = new ArrayList<>();
    for (int i = 0; i < 8; i+=2){
      users.add(new User("Leo" + i, "ThaMan", "666"));
      users.add(new User("Leo" + (i + 1), "ThaMan", "666"));
      event.addMatch(users.get(users.size() - 2), users.get(users.size() - 1), "2-1");
      matches.add(new Match(users.get(users.size() - 2), users.get(users.size() - 1),"2-1"));
    }
    for (int i = 0; i < 3; i++){
      event.addMatch(null, null);
      matches.add(new Match(null, null));
    }
    matches.set(4, new Match(users.get(0), users.get(2)));
    matches.set(5, new Match(null, users.get(6)));
    event.updateNextMatch(event.getMatches().get(0));
    event.updateNextMatch(event.getMatches().get(1));
    event.updateNextMatch(event.getMatches().get(3));
    assertEquals(matches, event.getMatches());
  }
  @Test void updateMatchAfterFinal()
  {
    ArrayList<User> users = new ArrayList<>();
    for (int i = 0; i < 14; i+=2){
      users.add(new User("Leo" + i, "ThaMan", "666"));
      users.add(new User("Leo" + (i + 1), "ThaMan", "666"));
      event.addMatch(users.get(users.size() - 2), users.get(users.size() - 1), "2-1");
    }
    assertThrows(IndexOutOfBoundsException.class, ()-> event.updateNextMatch(event.getMatches().get(7)));
  }
}