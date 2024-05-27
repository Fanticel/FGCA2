package viewModel;

import Model.EventListModel;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import utility.NamedPropertyChangeSubject;
import view.ViewHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

public class GameInfoViewModel implements NamedPropertyChangeSubject {
  private EventListModel model;
  private PropertyChangeListener listener;
  public GameInfoViewModel(EventListModel model){
    this.model = model;
    listener = null;
  }
  public void reset(){

  }
  public ArrayList<HBox> generateGameButtons(){
    ArrayList<HBox> ans = new ArrayList<>();
    ArrayList<String> gameNames = model.getAllGameNames();
    int gamesPerRow = (int) Math.ceil(gameNames.size()/5d);
    for (int i = 0; i < gameNames.size(); i++) {
      HBox thisHbox = new HBox();
      for (int j = 0; j < gamesPerRow; j++) {
        Button thisButton = new Button(gameNames.get(i));
        thisButton.setPrefSize(450,50);
        thisButton.setFont(new Font(25));
        thisButton.setOnMouseClicked((event)->{
          ViewState.getInstance().setGameName(thisButton.textProperty().get());
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
