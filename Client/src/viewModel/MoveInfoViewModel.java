package viewModel;

import Model.EventListModel;
import Model.GameInformation.Character;
import Model.GameInformation.Move;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mediator.CharacterInfoPackage;
import utility.SimpleMovesProperties;

import java.util.Arrays;

public class MoveInfoViewModel {
  private EventListModel model;
  private StringProperty mainProperty;
  private StringProperty overviewProperty;
  private StringProperty prosProperty;
  private StringProperty consProperty;
  private CharacterInfoPackage localCharacterInfoPackage;
  private ObservableList<SimpleMovesProperties> moveList;
  public MoveInfoViewModel(EventListModel model){
    this.model = model;
    mainProperty = new SimpleStringProperty();
    overviewProperty = new SimpleStringProperty();
    prosProperty = new SimpleStringProperty();
    consProperty = new SimpleStringProperty();
    moveList = FXCollections.observableArrayList();
    reset();
  }
  public void reset(){
    localCharacterInfoPackage = ViewState.getInstance().getLocalCharacterInfoPackage();
    mainProperty.set("Inspecting: "+ViewState.getInstance().getCharacterName());
    Character thisCharacter = null;
    for (Character c:localCharacterInfoPackage.getCharacterList()){
      if (c.getName().equals(ViewState.getInstance().getCharacterName())){
        thisCharacter = c;
        break;
      }
    }
    overviewProperty.set(thisCharacter.getOverview());
    prosProperty.set("Pros: \n"+thisCharacter.getPros().replaceAll(" \\| ", "\n"));
    consProperty.set("Cons: \n"+thisCharacter.getCons().replaceAll(" \\| ", "\n"));
    for (Move m: thisCharacter.getMoveList()){
      moveList.add(new SimpleMovesProperties(m));
    }

  }
  public StringProperty getMainProperty(){return mainProperty;}
  public StringProperty getOverviewProperty(){return overviewProperty;}
  public StringProperty getProsProperty(){return prosProperty;}
  public StringProperty getConsProperty(){return consProperty;}
  public ObservableList getMoveList(){return moveList;}
}
