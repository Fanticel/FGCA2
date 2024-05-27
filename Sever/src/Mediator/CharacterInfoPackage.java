package Mediator;

import Model.GameInformation.Character;

import java.util.ArrayList;

public class CharacterInfoPackage {
  private ArrayList<Character> characterList;
  private String desc;
  public CharacterInfoPackage(ArrayList<Character> characterList, String desc){
    this.desc = desc;
    this.characterList = characterList;
  }
  public ArrayList<Character> getCharacterList() {
    return characterList;
  }
  public String getDesc(){
    return desc;
  }
}
