package viewModel;

import Model.EventListModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RegisterViewModel {
  private EventListModel model;
  private StringProperty username;
  private StringProperty displayname;
  private StringProperty password;
  private StringProperty error;
  public RegisterViewModel(EventListModel model){
    this.model = model;
    username = new SimpleStringProperty();
    displayname = new SimpleStringProperty();
    password = new SimpleStringProperty();
    error = new SimpleStringProperty("");
  }
  public boolean PressRegisterButton(){
    String ans = model.register(username.get(), displayname.get(), password.get());
    if (ans.equals("pog")){
      return true;
    }
    else {
      error.set(ans);
    }
    return false;
  }
  public StringProperty getUsername(){
    return username;
  }
  public StringProperty getDisplayname(){
    return displayname;
  }
  public StringProperty getPassword(){
    return password;
  }
  public StringProperty getError(){
    return error;
  }
}
