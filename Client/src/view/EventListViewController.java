package view;

import Model.Event;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import viewModel.EventListViewModel;
import viewModel.SimpleEventViewModel;
import viewModel.ViewModelFactory;

import java.util.ArrayList;

public class EventListViewController implements ViewController
{
  private ViewHandler viewHandler;
  private EventListViewModel eventListViewModel;
  private Region root;
  private ViewModelFactory viewModelFactory;
  @FXML private ScrollPane scrollPane;
  @FXML private TextField searchBar;

  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root)
  {
    this.viewModelFactory = viewModelFactory;
    this.viewHandler = viewHandler;
    eventListViewModel = viewModelFactory.getEventListViewModel();
    this.root = root;
    ArrayList<Event> allEvents = eventListViewModel.getAllEvents();
    VBox container = new VBox();
    for (Event event : allEvents)
    {
      loadEventView(viewModelFactory.getSimpleEventViewModel(event), container);
    }

    // Add event listener to search bar
    searchBar.setOnKeyReleased(event -> {
      String query = searchBar.getText().toLowerCase();
      filterEvents(query);
    });
  }

  private void loadEventView(SimpleEventViewModel viewModel, VBox container)
  {
    try
    {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("EventTempalteView.fxml"));

      Region eventViewRoot = loader.load();
      EventTemplateViewController controller = loader.getController();

      controller.init(viewHandler, viewModel, eventViewRoot);
      container.getChildren().add(eventViewRoot);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    scrollPane.setContent(container);
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);
  }

  private void filterEvents(String query)
  {
    ArrayList<Event> allEvents = eventListViewModel.getAllEvents();
    VBox container = new VBox();
    for (Event event : allEvents)
    {
      if (event.getTittle().toLowerCase().contains(query))
      {
        loadEventView(viewModelFactory.getSimpleEventViewModel(event), container);
      }
    }
    scrollPane.setContent(container);
  }

  @Override public void reset()
  {
    eventListViewModel.clear();
  }

  @Override public Region getRoot()
  {
    return root;
  }

  @FXML void backButtonPressed()
  {
    viewHandler.openPopupView("");
  }
}
