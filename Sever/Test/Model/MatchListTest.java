package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatchListTest
{
  private MatchList matchList;
  @BeforeEach void setUp()
  {
    matchList = new MatchList();
  }
  @Test void getMatchesByPlayer()
  {
  }
  @Test void getMatchesByPlayerWhenThereIsNoMatch()
  {
    ArrayList<Match> matches = new ArrayList<>();
    assertEquals(matches, matchList.getMatchesByPlayer("Ivica"));
  }
 @Test void getMatchesByPlayerWhenThereIsOneMatch()
  {
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    Match match = new Match(user, user2);
    matchList.addPlayerMatch(match);
    ArrayList<Match> matches = new ArrayList<>(Arrays.asList(match));
    assertEquals(matches, matchList.getMatchesByPlayer("Leo"));
  }
  @Test void getMatchesByPlayerWhenThereAreMultipleDifferentMatches()
  {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Match> matches = new ArrayList<>();
    for (int i = 0; i < 8; i+=2){
      users.add(new User("Leo" + i, "ThaMan", "666"));
      users.add(new User("Leo" + (i + 1), "ThaMan", "666"));
      matchList.addPlayerMatch(new Match(users.get(users.size() - 2), users.get(users.size() - 1)));
    }
    matches.add(new Match(users.get(2), users.get(3)));
    assertEquals(matches, matchList.getMatchesByPlayer("Leo2"));
  }
  @Test void getMatchesByPlayerWhenThereAreMultipleSameMatches()
  {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Match> matches = new ArrayList<>();
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    Match match = new Match(user, user2);
    matches.add(match);
    matchList.addPlayerMatch(match);
    for (int i = 0; i < 8; i+=2){
      users.add(new User("Leo" + i, "ThaMan", "666"));
      users.add(new User("Leo" + (i + 1), "ThaMan", "666"));
      matchList.addPlayerMatch(new Match(users.get(users.size() - 2), users.get(users.size() - 1)));
    }
    matchList.addPlayerMatch(match);
    matchList.addPlayerMatch(match);
    matches.add(match);
    matches.add(match);
    assertEquals(matches, matchList.getMatchesByPlayer("Vas"));
  }
  //No border tests for getMatchByParticipants
  //Method returns no exceptions

  //A wrapper for arrayList add method
  @Test void addPlayerMatchWhenListIsEmpty()
  {
    ArrayList<Match> matches = new ArrayList<>();
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    Match match = new Match(user, user2);
    matches.add(match);
    matchList.addPlayerMatch(match);
    assertEquals(matches, matchList.getMatches());
  }
  @Test void addPlayerMatchWhenListIsOne()
  {
    ArrayList<Match> matches = new ArrayList<>();
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    Match match = new Match(user, user2);
    matches.add(match);
    matches.add(match);
    matchList.addPlayerMatch(match);
    matchList.addPlayerMatch(match);
    assertEquals(matches, matchList.getMatches());
  }
  @Test void addPlayerMatchWhenThereAreMultiple()
  {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Match> matches = new ArrayList<>();
    for (int i = 0; i < 8; i+=2){
      users.add(new User("Leo" + i, "ThaMan", "666"));
      users.add(new User("Leo" + (i + 1), "ThaMan", "666"));
      matchList.addPlayerMatch(new Match(users.get(users.size() - 2), users.get(users.size() - 1)));
      matches.add(new Match(users.get(users.size() - 2), users.get(users.size() - 1)));
    }
    assertEquals(matches, matchList.getMatches());
  }
  //No boundaries or exceptions exist
}