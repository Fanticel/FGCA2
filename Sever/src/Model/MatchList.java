package Model;

import java.util.ArrayList;

public class MatchList
{
  private ArrayList<Match> matches;

  public MatchList()
  {
    matches = new ArrayList<>();
  }

  public ArrayList<Match> getMatchesByPlayer(String userName)
  {
    ArrayList<Match> matchList = new ArrayList<>();
    for (Match match : matches)
    {
      String playerOne = match.getPlayers().get(0).getUsername();
      String playerTwo = match.getPlayers().get(1).getUsername();
      if (playerOne.equals(userName) || playerTwo.equals(userName)){
        matchList.add(match);
      }
    }
    return matchList;
  }
  public void addPlayerMatch(Match match){
    matches.add(match);
  }

  public ArrayList<Match> getMatches()
  {
    return matches;
  }
}
