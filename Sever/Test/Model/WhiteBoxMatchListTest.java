package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WhiteBoxMatchListTest
{
  private MatchList matchList;
  @BeforeEach void setUp()
  {
    matchList = new MatchList();
  }

  @Test void getMatchesByPlayerIfNoSuchMatches()
  {
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Match> matches = new ArrayList<>();
    for (int i = 0; i < 8; i+=2){
      users.add(new User("Leo" + i, "ThaMan", "666"));
      users.add(new User("Leo" + (i + 1), "ThaMan", "666"));
      matchList.addPlayerMatch(new Match(users.get(users.size() - 2), users.get(users.size() - 1)));
    }
    assertEquals(matches, matchList.getMatchesByPlayer("Vas"));
  }

  @Test void getMatchesByPlayerIfMatchesFound()
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

  @Test void addPlayerMatch()
  {
  }
}