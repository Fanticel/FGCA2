package view;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import viewModel.LogInViewModel;
import viewModel.ViewModelFactory;


public class LogInViewController implements ViewController{
  @FXML private TextField userNameField;
  @FXML private TextField passwordField;
  @FXML private Label errorLabel;
  @FXML private Hyperlink linkLabel;
  private ViewHandler viewHandler;
  private LogInViewModel logInViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    logInViewModel = viewModelFactory.getLogInViewModel();
    this.root = root;
    errorLabel.textProperty().bind(logInViewModel.getErrorProperty());
    userNameField.textProperty().bindBidirectional(logInViewModel.getUsernameProperty());
    passwordField.textProperty().bindBidirectional(logInViewModel.getPasswordProperty());
    linkLabel.setVisited(false);
  }

  @Override public void reset() {

  }

  @Override public Region getRoot() {
    return root;
  }
  @FXML void OnEnter(){
    passwordField.requestFocus();
  }
  @FXML void OnEnterPass(){
    PressLoginButton();
  }
  @FXML void PressLoginButton(){
    if (logInViewModel.PressLoginButton()){
      viewHandler.openView("");
    }
  }
  @FXML void CreateAccount(){
    linkLabel.setVisited(false);
    viewHandler.openView("Register");
  }
}
