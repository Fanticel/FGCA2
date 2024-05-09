package viewModel;

import Model.Event;
import Model.EventListModel;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimplePlayerViewModel
{
  private StringProperty playerDisplayName;
  private StringProperty skillLevel;
  private EventListModel model;

  public SimplePlayerViewModel(User user, EventListModel model)
  {
    this.model = model;
    playerDisplayName = new SimpleStringProperty(user.getDisplayName());
    skillLevel = new SimpleStringProperty(String.valueOf(user.getBRP()));
  }

  public StringProperty getPlayerDisplayName()
  {
    return playerDisplayName;
  }

  public StringProperty getSkillLevel()
  {
    return skillLevel;
  }
}
