package viewModel;

import Model.EventListModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class OneVsOneViewModel
{
  private EventListModel model;
  private BooleanProperty disableSearch;
  private ObjectProperty<String> gameProperty;
  private ObjectProperty<String> skillProperty;

  public OneVsOneViewModel(EventListModel model){
    this.model = model;
    disableSearch = new SimpleBooleanProperty(true);
    gameProperty = new SimpleObjectProperty<>();
    skillProperty = new SimpleObjectProperty<>();
  }

  public ObjectProperty<String> getGameProperty(){
    return gameProperty;
  }

  public ObjectProperty<String> getSkillProperty()
  {
    return skillProperty;
  }

  public BooleanProperty disableSearchProperty() {
    return disableSearch;
  }

  public boolean isDisableSearch() {
    return disableSearch.get();
  }

  public void updateDisableSearch(boolean disable) {
    disableSearch.set(disable);
  }

  public void updateDisableSearch(boolean disableGameButton, boolean disableSkillButton) {
    disableSearch.set(disableGameButton || disableSkillButton);
  }

  public void pressSearchButton() {
    String gameSelected = gameProperty.get();
    String skillSelected = skillProperty.get();
    System.out.println("Selected game: " + gameSelected + "skill selected:" + skillSelected);
  }
}
