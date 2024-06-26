package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import viewModel.EventDescriptionViewModel;
import viewModel.ViewModelFactory;
import viewModel.ViewState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class EventDescriptionViewController implements ViewController,
    PropertyChangeListener
{
  @FXML private Label lblEventTitle;
  @FXML private TabPane tabPane;
  private ViewHandler viewHandler;
  private EventDescriptionViewModel eventDescriptionViewModel;
  private DescriptionViewGeneralController descriptionViewGeneralController;
  private BracketViewController bracketViewController;
  private ChatViewController chatViewController;
  private ViewModelFactory viewModelFactory;
  private Region root;
  private Tab tab1;

  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    //viewModelFactory.addListener("EventChange", this);
    eventDescriptionViewModel = viewModelFactory.getEventDetailsViewModel();
    eventDescriptionViewModel.addListener("Change", this);
    this.root = root;
    lblEventTitle.textProperty()
        .bind(eventDescriptionViewModel.getTittleProperty());
    tab1 = new Tab("Details");
    Tab tab2 = new Tab("Bracket");
    Tab tab3 = new Tab("Chat");
    tab1.setContent(loadEventDescriptionViewController("DescriptionViewGeneral.fxml"));
    tabPane.getTabs().add(tab1);
    if (eventDescriptionViewModel.getStatusProperty().get().contains("In progress") || eventDescriptionViewModel.getStatusProperty().get().contains("Finished")){
      switch (eventDescriptionViewModel.getMaxParticipants()) {
        case "8" -> tab2.setContent(loadBracketViewController("8_BracketView.fxml"));
        case "16" -> tab2.setContent(loadBracketViewController("16_BracketView.fxml"));
        case "32" -> tab2.setContent(loadBracketViewController("32_BracketView.fxml"));
        case "64" -> tab2.setContent(loadBracketViewController("64_BracketView.fxml"));
      }
    }
    else {
      tab2.disableProperty().set(true);
    }
    ViewState.getInstance().setDesiredChat(lblEventTitle.getText() + "Chat");
    tab3.setContent(loadChatView("ChatView.fxml"));
    tabPane.getTabs().add(tab2);
    tabPane.getTabs().add(tab3);
  }

  @Override public void reset() {
    tabPane.getSelectionModel().select(tab1);
    descriptionViewGeneralController.reset();
    eventDescriptionViewModel.reset();
    if (bracketViewController != null){
      bracketViewController.reset();
    }
    Tab bracket = tabPane.getTabs().get(1);
    bracket.disableProperty().set(false);
    if (eventDescriptionViewModel.getStatusProperty().get().contains("In progress") || eventDescriptionViewModel.getStatusProperty().get().contains("Finished")){
      switch (eventDescriptionViewModel.getMaxParticipants()) {
        case "8" -> bracket.setContent(loadBracketViewController("8_BracketView.fxml"));
        case "16" -> bracket.setContent(loadBracketViewController("16_BracketView.fxml"));
        case "32" -> bracket.setContent(loadBracketViewController("32_BracketView.fxml"));
        case "64" -> bracket.setContent(loadBracketViewController("64_BracketView.fxml"));
      }
    }
    else {
      bracket.disableProperty().set(true);
    }
    ViewState.getInstance().setDesiredChat(lblEventTitle.getText() + "Chat");
    chatViewController.reset();
  }

  @Override public Region getRoot() {
    return root;
  }

  @FXML void pressBackButton() {
    viewHandler.openView("EventList");
  }
  private Region loadChatView(String fxmlFile){
    if (chatViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        chatViewController = loader.getController();
        chatViewController.init(viewHandler, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      chatViewController.reset();
    }
    return chatViewController.getRoot();
  }

  private Region loadEventDescriptionViewController(String fxmlFile) {
    if (descriptionViewGeneralController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        descriptionViewGeneralController = loader.getController();
        descriptionViewGeneralController.init(viewHandler, viewModelFactory,
            root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      descriptionViewGeneralController.reset();
    }
    return descriptionViewGeneralController.getRoot();
  }

  private Region loadBracketViewController(String fxmlFile){
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        bracketViewController = loader.getController();
        bracketViewController.init(viewHandler, viewModelFactory, root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    return bracketViewController.getRoot();
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("Change"))
    {
      Platform.runLater(() -> {
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        reset();
        tabPane.getSelectionModel().select(tabIndex);
      });
    }
  }
}
