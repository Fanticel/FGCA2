package Model.GameInformation;

import java.util.ArrayList;

public class Game {
  private String name;
  private String description;
  private ArrayList<Character> characterList;

  public Game(String name, String description,
      ArrayList<Character> characterList) {
    this.name = name;
    this.description = description;
    this.characterList = characterList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ArrayList<Character> getCharacterList() {
    return characterList;
  }

  public void addCharacter(Character character){
    characterList.add(character);
  }
  public void removeCharacter(Character character){
    characterList.remove(character);
  }

  @Override public String toString() {
    String ans = name + ", " + description  + ", [\n";
    for (Character c:characterList){
      ans += c.toString() + "\n";
    }
    ans += "]";
    return ans;
  }
}
