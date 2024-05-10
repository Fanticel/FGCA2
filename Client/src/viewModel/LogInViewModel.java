package viewModel;

import Model.EventListModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LogInViewModel {
  private EventListModel model;
  private StringProperty errorProperty;
  private StringProperty usernameProperty;
  private StringProperty passwordProperty;
  public LogInViewModel(EventListModel model){
    this.model = model;
    errorProperty = new SimpleStringProperty("");
    usernameProperty = new SimpleStringProperty("");
    passwordProperty = new SimpleStringProperty("");
  }
  public StringProperty getErrorProperty(){
    return errorProperty;
  }
  public StringProperty getUsernameProperty() {
    return usernameProperty;
  }
  public StringProperty getPasswordProperty() {
    return passwordProperty;
  }

  public boolean PressLoginButton(){
    if (usernameProperty.isEmpty().get()){
      errorProperty.set("Username cannot be empty");
    }
    else if (passwordProperty.isEmpty().get()) {
      errorProperty.set("Password cannot be empty");
    }else {
      String ans = model.login(usernameProperty.get(), passwordProperty.get());
      if (ans.equals("pog")){
        return true;
      }
      else {
        errorProperty.set(ans);
      }
    }
    return false;
  }
}
