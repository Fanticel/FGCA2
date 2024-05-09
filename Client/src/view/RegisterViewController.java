package view;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import viewModel.RegisterViewModel;
import viewModel.ViewModelFactory;

public class RegisterViewController implements ViewController{
  @FXML private TextField userNameField;
  @FXML private TextField displayNameField;
  @FXML private TextField passwordField;
  @FXML private Label errorLabel;
  @FXML private Hyperlink linkLabel;
  private ViewHandler viewHandler;
  private RegisterViewModel registerViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    registerViewModel = viewModelFactory.getRegisterViewModel();
    this.root = root;
    errorLabel.textProperty().set("");
  }

  @Override public void reset() {

  }

  @Override public Region getRoot() {
    return root;
  }
  @FXML void LoginAccount(){
    linkLabel.setVisited(false);
    viewHandler.openView("Login");
  }
  @FXML void PressRegisterButton(){
    System.err.println("Going to implement the register logic");
  }
  @FXML void OnEnterUser(){
    displayNameField.requestFocus();
  }
  @FXML void OnEnterDisplay(){
    passwordField.requestFocus();
  }
  @FXML void OnEnterPass() {
    PressRegisterButton();
  }
}
