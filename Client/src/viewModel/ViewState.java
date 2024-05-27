package viewModel;

import Model.Match;
import mediator.CharacterInfoPackage;

public class ViewState {
  private static ViewState instance;
  private String tittle;
  private Match currentMatch;
  private String desiredChat;
  private String[] matchPlayers;//for 1v1 matches when going from searching view to opponentFound view
  private String gameName;
  private String characterName;
  private CharacterInfoPackage localCharacterInfoPackage;
  private ViewState(){
    desiredChat = "";
    tittle = "Sample event";
    localCharacterInfoPackage = null;
    gameName = null;
    characterName = null;
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
  public void setDesiredChat(String chatName){desiredChat = chatName;}
  public String getTittle(){
    return tittle;
  }
  public Match getMatch(){return currentMatch;}
  public String getDesiredChat(){return desiredChat;}

  public String[] getMatchPlayers()
  {
    return matchPlayers;
  }

  public void setMatchPlayers(String[] matchPlayers)
  {
    this.matchPlayers = matchPlayers;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public String getCharacterName() {
    return characterName;
  }

  public void setCharacterName(String characterName) {
    this.characterName = characterName;
  }

  public CharacterInfoPackage getLocalCharacterInfoPackage() {
    return localCharacterInfoPackage;
  }

  public void setLocalCharacterInfoPackage(
      CharacterInfoPackage localCharacterInfoPackage) {
    this.localCharacterInfoPackage = localCharacterInfoPackage;
  }
}
