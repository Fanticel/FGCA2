package viewModel;

import Model.Match;

public class ViewState {
  private static ViewState instance;
  private String tittle;
  private Match currentMatch;
  private ViewState(){
    tittle = "Sample event";
    currentMatch = null;
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
}
