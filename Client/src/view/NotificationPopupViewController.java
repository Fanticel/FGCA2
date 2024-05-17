package view;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import viewModel.NotificationPopupViewModel;
import viewModel.ViewModelFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class NotificationPopupViewController implements ViewController,
    PropertyChangeListener {
  @FXML private Label NotificationLabel;
  private Region root;
  private NotificationPopupViewModel notificationPopupViewModel;
  private ViewHandler viewHandler;

  public NotificationPopupViewController() {
  }

  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    init(viewHandler, viewModelFactory.getNotificationPopupViewModel(), root);
  }

  public void init(ViewHandler viewHandler,
      NotificationPopupViewModel notificationPopupViewModel, Region root) {
    this.notificationPopupViewModel = notificationPopupViewModel;
    this.viewHandler = viewHandler;
    this.root = root;
    NotificationLabel.textProperty().bindBidirectional(notificationPopupViewModel.getNotificationProperty());
    Color green = new Color(0.051, 0.7882, 0.1098, 1.0);
    Color red = new Color(1.0, 0.0, 0.0, 1.0);
    NotificationLabel.textFillProperty().bind(notificationPopupViewModel.getColorProperty());
    notificationPopupViewModel.addListener("", this);
  }

  @Override public void reset() {
//    notificationPopupViewModel.reset();
  }

  @Override public Region getRoot() {
    return root;
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals(""))
    {
      Platform.runLater(() -> {
        viewHandler.openPopupView("");
      });
    }
  }
}

