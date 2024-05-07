package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import viewModel.EventDescriptionViewModel;
import viewModel.ViewModelFactory;

import java.io.IOException;

public class EventDescriptionViewController implements ViewController {
  @FXML private Label lblEventTitle;
  @FXML private TabPane tabPane;
  private ViewHandler viewHandler;
  private EventDescriptionViewModel eventDescriptionViewModel;
  private DescriptionViewGeneralController descriptionViewGeneralController;
  private ViewModelFactory viewModelFactory;
  private Region root;

  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    eventDescriptionViewModel = viewModelFactory.getEventDetailsViewModel();
    this.root = root;
    lblEventTitle.textProperty()
        .bind(eventDescriptionViewModel.getTittleProperty());
    Tab tab1 = new Tab("Details");
    tab1.setContent(loadEventDescriptionViewController("DescriptionViewGeneral.fxml"));
    tabPane.getTabs().add(tab1);
  }

  @Override public void reset() {
    descriptionViewGeneralController.reset();
  }

  @Override public Region getRoot() {
    return root;
  }

  @FXML void pressBackButton() {
    viewHandler.openView("EventList");
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
}
