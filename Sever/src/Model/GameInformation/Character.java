package Model.GameInformation;

import java.util.ArrayList;

public class Character {
  private String name;
  private String pros;
  private String cons;
  private String overview;
  private ArrayList<Move> moveList;

  public Character(String name, String pros, String cons, String overview,
      ArrayList<Move> moveList) {
    this.name = name;
    this.pros = pros;
    this.cons = cons;
    this.overview = overview;
    this.moveList = moveList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPros() {
    return pros;
  }

  public void setPros(String pros) {
    this.pros = pros;
  }

  public String getCons() {
    return cons;
  }

  public void setCons(String cons) {
    this.cons = cons;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public ArrayList<Move> getMoveList() {
    return moveList;
  }
  public void addToMoveList(Move move){
    moveList.add(move);
  }
  public void removeFromMoveList(Move move){
    moveList.remove(move);
  }

  @Override public String toString() {
    String ans = name + ", " + pros + ", " + cons + ", " + overview + ", {\n";
    for (Move m : moveList) {
      ans += m.toString() + "\n";
    }
    ans += '}';
    return ans;
  }
}
