package viewModel;

import Model.EventListModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainPageViewModel {
  private EventListModel model;
  private StringProperty welcomeProperty;
  public MainPageViewModel(EventListModel model){
    this.model = model;
    welcomeProperty = new SimpleStringProperty("Welcome " + model.getUser().getDisplayName() + "!");
  }
  public StringProperty getWelcomeProperty(){
    return welcomeProperty;
  }
}
