package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewModel.ViewModelFactory;
import viewModel.ViewState;

public class ViewHandler {
  private Scene currentScene;
  private Scene popupScene;
  private Scene notificationScene;
  private Stage primaryStage;
  private Stage popupStage;
  private Stage notificationStage;
  private EventDescriptionViewController eventDescriptionViewController;
  private EventListViewController eventListViewController;
  private NotificationPopupViewController notificationPopupViewController;
  private LogInViewController logInViewController;
  private RegisterViewController registerViewController;
  private CreateEventController createEventController;
  private ReportScoreViewController reportScoreViewController;
  private MainPageViewController mainPageViewController;
  private OneVsOneViewController oneVsOneViewController;
  private OneVsOneSearchingViewController oneVsOneSearchingViewController;
  private OpponentFoundViewController opponentFoundViewController;
  private ChatViewController chatViewController;
  private GameInfoViewController gameInfoViewController;
  private CharacterInfoController characterInfoController;
  private MoveInfoController moveInfoController;
  private EventTemplateViewController eventTemplateViewController;
  private BracketViewController bracketViewController;
  private ViewModelFactory viewModelFactory;

  public ViewHandler(ViewModelFactory viewModelFactory) {
    this.viewModelFactory = viewModelFactory;
    currentScene = new Scene(new Region());
    popupScene = new Scene(new Region());
    notificationScene = new Scene(new Region());
  }

  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    openView("Login");
//    ViewState.getInstance().setGameName("Tvoja mama");
//    openView("CharacterInfo");
  }

  public void startPopup(Stage popupStage) {
    this.popupStage = popupStage;
  }
  public void startNotification(Stage notificationStage){this.notificationStage = notificationStage;}

  public void openPopupView(String id) {
    Region root = null;
    boolean isItNotification = false;
    switch (id) {
      case "" -> {
        startNotification(new Stage());
        root = loadNotificationPopupViewController(
            "NotificationPopupView.fxml");
        isItNotification = true;
        notificationScene.setRoot(root);
        String title = "";
        if (root.getUserData() != null) {
          title += root.getUserData();
        }
        notificationStage.setTitle(title);
        notificationStage.setScene(notificationScene);
        notificationStage.setWidth(root.getPrefWidth());
        notificationStage.setHeight(root.getPrefHeight());
        notificationStage.show();
      }
      case "Report" -> root = loadReportScoreView("ReportScoreView.fxml");
      case "SearchingOpponent" -> root = loadOneVsOneSearchingPopup("1v1SearchingView.fxml");
      case "OpponentFound" -> root = loadOpponentFoundView("OpponentFoundView.fxml");
    }
    if (!isItNotification){
      startPopup(new Stage());
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
  }

  public void openView(String id) {
    Region root = null;
    switch (id) {
      case "EventList" ->
          root = loadEventListViewController("EventListView.fxml");
      case "EventDetails" -> root = loadEventDescriptionViewController("EventDescriptionView.fxml");
      case "Login" -> root = loadLogInViewController("LogInView.fxml");
      case "Register" -> root = loadRegisterViewController("RegisterView.fxml");
      case "CreateEvent" -> root = loadCreateEventView("CreateEventView.fxml");
      case "1v1" -> root = loadOneVsOneView("1v1view.fxml");
      case "Chat" -> root = loadChatView("ChatView.fxml");
      case "GameInfo" -> root = loadGameInfoView("GameInfoView.fxml");
      case "CharacterInfo" -> root = loadCharacterInfoView("CharacterInfo.fxml");
      case "MoveInfo" -> root = loadMoveInfoController("MoveInfo.fxml");
      default -> root = loadMainPageView("MainPageView.fxml");
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
  public void closeNotificationView(){
    notificationStage.close();
  }
  public Region loadMoveInfoController(String fxmlFile){
    if (moveInfoController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        moveInfoController = loader.getController();
        moveInfoController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      moveInfoController.reset();
    }
    return moveInfoController.getRoot();
  }
  public Region loadCharacterInfoView(String fxmlFile){
    if (characterInfoController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        characterInfoController = loader.getController();
        characterInfoController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      characterInfoController.reset();
    }
    return characterInfoController.getRoot();
  }
  public Region loadGameInfoView(String fxmlFile){
    if (chatViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        gameInfoViewController = loader.getController();
        gameInfoViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      gameInfoViewController.reset();
    }
    return gameInfoViewController.getRoot();
  }
  public Region loadChatView(String fxmlFile){
    if (chatViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        chatViewController = loader.getController();
        chatViewController.init(this, viewModelFactory, root);
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
  public Region loadOpponentFoundView(String fxmlFile){
    if (opponentFoundViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        opponentFoundViewController = loader.getController();
        opponentFoundViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      opponentFoundViewController.reset();
    }
    return opponentFoundViewController.getRoot();
  }
  public Region loadOneVsOneSearchingPopup(String fxmlFile){
    if (oneVsOneSearchingViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        oneVsOneSearchingViewController = loader.getController();
        oneVsOneSearchingViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      oneVsOneSearchingViewController.reset();
    }
    return oneVsOneSearchingViewController.getRoot();
  }
  public Region loadOneVsOneView(String fxmlFile){
    if (oneVsOneViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        oneVsOneViewController = loader.getController();
        oneVsOneViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      oneVsOneViewController.reset();
    }
    return oneVsOneViewController.getRoot();
  }
  public Region loadMainPageView(String fxmlFile){
    if (mainPageViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        mainPageViewController = loader.getController();
        mainPageViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      mainPageViewController.reset();
    }
    return mainPageViewController.getRoot();
  }
  public Region loadReportScoreView(String fxmlFile){
    if (reportScoreViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        reportScoreViewController = loader.getController();
        reportScoreViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      reportScoreViewController.reset();
    }
    return reportScoreViewController.getRoot();
  }
  public Region loadCreateEventView(String fxmlFile){
    if (createEventController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        createEventController = loader.getController();
        createEventController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      createEventController.reset();
    }
    return createEventController.getRoot();
  }
  public Region loadRegisterViewController(String fxmlFile) {
    if (registerViewController == null) {
      try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        registerViewController = loader.getController();
        registerViewController.init(this, viewModelFactory, root);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      registerViewController.reset();
    }
    return registerViewController.getRoot();
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
