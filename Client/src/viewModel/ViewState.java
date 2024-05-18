package viewModel;

import Model.Match;

public class ViewState {
  private static ViewState instance;
  private String tittle;
  private Match currentMatch;
  private String[] matchPlayers;//for 1v1 matches when going from searching view to opponentFound view
  private ViewState(){
    tittle = "Sample event";
    currentMatch = null;
    matchPlayers = new String[2];
  }
  public static ViewState getInstance(){
    if (instance == null) {
      instance = new ViewState();
    }
    return instance;
  }
  public void setTittle(String tittle){
    this.tittle = tittle;
  }
  public void setMatch(Match match){currentMatch = match;}
  public String getTittle(){
    return tittle;
  }
  public Match getMatch(){return currentMatch;}

  public String[] getMatchPlayers()
  {
    return matchPlayers;
  }

  public void setMatchPlayers(String[] matchPlayers)
  {
    this.matchPlayers = matchPlayers;
  }
}
