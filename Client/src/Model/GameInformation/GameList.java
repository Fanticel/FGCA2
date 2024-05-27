package Model.GameInformation;

import java.util.ArrayList;

public class GameList {
  private ArrayList<Game> gameList;
  public GameList(ArrayList<Game> gameList){
    this.gameList = gameList;
  }
  public GameList(){
    this.gameList = new ArrayList<>();
  }
  public ArrayList<Game> getAllGames(){
    return gameList;
  }
  public ArrayList<Character> getAllCharMovesFromGame(String gameName){
    for (Game g : gameList) {
      if (g.getName().equals(gameName)){
        return g.getCharacterList();
      }
    }
    return null;
  }
  public ArrayList<String> getAllGameNames(){
    ArrayList<String> ans = new ArrayList<>();
    for (Game g:gameList){
      ans.add(g.getName());
    }
    return ans;
  }
}
