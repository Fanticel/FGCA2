package viewModel;

import Model.EventListModel;
import Model.GameInformation.Character;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import mediator.CharacterInfoPackage;
import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class CharacterInfoViewModel implements NamedPropertyChangeSubject {
  private EventListModel model;
  private StringProperty mainProperty;
  private StringProperty descProperty;
  private CharacterInfoPackage characterInfoPackage;
  private PropertyChangeListener listener;
  public CharacterInfoViewModel(EventListModel model){
    this.model = model;
    mainProperty = new SimpleStringProperty();
    descProperty = new SimpleStringProperty();
    characterInfoPackage = null;
    reset();
  }
  public void reset(){
    characterInfoPackage = model.getAllCharMovesFromGame(ViewState.getInstance().getGameName());
    ViewState.getInstance().setLocalCharacterInfoPackage(characterInfoPackage);
    mainProperty.set("Inspecting: "+ViewState.getInstance().getGameName());
    descProperty.set(characterInfoPackage.getDesc());
  }
  public StringProperty getMainProperty(){
    return mainProperty;
  }
  public StringProperty getDescProperty() {return descProperty;}
  public ArrayList<HBox> generateCharacterButtons(){
    ArrayList<HBox> ans = new ArrayList<>();
    ArrayList<Character> characterList = characterInfoPackage.getCharacterList();
    int gamesPerRow = (int) Math.ceil(characterList.size()/5d);
    for (int i = 0; i < characterList.size(); i++) {
      HBox thisHbox = new HBox();
      for (int j = 0; j < gamesPerRow; j++) {
        Button thisButton = new Button(characterList.get(i).getName());
        thisButton.setPrefSize(300,50);
        thisButton.setFont(new Font(25));
        thisButton.setOnMouseClicked((event)->{
          ViewState.getInstance().setCharacterName(thisButton.textProperty().get());
          listener.propertyChange(new PropertyChangeEvent(this, null, null, null));
        });
        thisHbox.getChildren().add(thisButton);
        thisHbox.setAlignment(Pos.CENTER);
        thisHbox.spacingProperty().set(10);
        if (j!=gamesPerRow-1){
          i++;
        }
      }
      ans.add(thisHbox);
    }
    return ans;
  }
  @Override public void addListener(String propertyName,
      PropertyChangeListener listener) {
    this.listener = listener;
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener) {
    if (this.listener.equals(listener)){
      listener = null;
    }
  }
}
