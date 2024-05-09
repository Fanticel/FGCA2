package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewModel.ViewModelFactory;

public class ViewHandler {
  private Scene currentScene;
  private Scene popupScene;
  private Stage primaryStage;
  private Stage popupStage;
  private EventDescriptionViewController eventDescriptionViewController;
  private EventListViewController eventListViewController;
  private NotificationPopupViewController notificationPopupViewController;
  private LogInViewController logInViewController;
  private EventTemplateViewController eventTemplateViewController;
  private BracketViewController bracketViewController;
  private ViewModelFactory viewModelFactory;

  public ViewHandler(ViewModelFactory viewModelFactory) {
    this.viewModelFactory = viewModelFactory;
    currentScene = new Scene(new Region());
    popupScene = new Scene(new Region());
  }

  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
//    openView("LogIn");
    openView("EventList");
  }

  public void startPopup(Stage popupStage) {
    this.popupStage = popupStage;
  }

  public void openPopupView(String id) {
    startPopup(new Stage());
    Region root = null;
    switch (id) {
      case "" -> {
        root = loadNotificationPopupViewController(
            "NotificationPopupView.fxml");
      }
    }
    popupScene.setRoot(root);
    String title = "";
    if (root.getUserData() != null) {
      title += root.getUserData();
    }
    popupStage.setTitle(title);
    popupStage.setScene(popupScene);
    popupStage.setWidth(root.getPrefWidth());
    popupStage.setHeight(root.getPrefHeight());
    popupStage.show();
  }

  public void openView(String id) {
    Region root = null;
    switch (id) {
      case "EventList" ->
          root = loadEventListViewController("EventListView.fxml");
      case "EventDetails" -> root = loadEventDescriptionViewController(
          "EventDescriptionView.fxml");
      case "LogIn" -> root = loadLogInViewController("LogInView.fxml");
    } currentScene.setRoot(root);
    String title = "";
    if (root.getUserData() != null) {
      title += root.getUserData();
    }
    primaryStage.setTitle(title);
    primaryStage.setScene(currentScene);
    primaryStage.setWidth(root.getPrefWidth());
    primaryStage.setHeight(root.getPrefHeight());
    primaryStage.show();
  }

  public void closeView() {
    primaryStage.close();
  }

  public void closePopupView() {
    popupStage.close();
  }

  public Region loadLogInViewController(String fxmlFile) {
    if (logInViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        logInViewController = loader.getController();
        logInViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      logInViewController.reset();
    }
    return logInViewController.getRoot();
  }

  private Region loadEventListViewController(String fxmlFile) {
    if (eventListViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        eventListViewController = loader.getController();
        eventListViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      eventListViewController.reset();
    }
    return eventListViewController.getRoot();
  }

  private Region loadEventDescriptionViewController(String fxmlFile) {
    if (eventDescriptionViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        eventDescriptionViewController = loader.getController();
        eventDescriptionViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      eventDescriptionViewController.reset();
    }
    return eventDescriptionViewController.getRoot();
  }

  private Region loadNotificationPopupViewController(String fxmlFile) {
    if (notificationPopupViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        notificationPopupViewController = loader.getController();
        notificationPopupViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      notificationPopupViewController.reset();
    }
    return notificationPopupViewController.getRoot();
  }

}
