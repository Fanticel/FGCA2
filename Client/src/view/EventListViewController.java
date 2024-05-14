package view;

import Model.Event;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
  @FXML private Text filterError;
  @FXML private Text searchError;
  @FXML private Text noEventsError;
  @FXML private Button createButton;

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
    filterError.setVisible(false);
    searchError.setVisible(false);
    searchBar.setPromptText("Search...");
    // Add event listener to search bar
    searchBar.setOnKeyReleased(event -> {
      String query = searchBar.getText().toLowerCase();
      searchEvents(query);
    });
    if (eventListViewModel.isModerator()){
      createButton.visibleProperty().set(true);
    }
  }
  @Override public void reset()
  {
    ArrayList<Event> allEvents = eventListViewModel.getAllEvents();
    VBox container = new VBox();
    for (Event event : allEvents)
    {
      loadEventView(viewModelFactory.getSimpleEventViewModel(event), container);
    }
    eventListViewModel.clear();
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

  private void searchEvents(String query)
  {
    ArrayList<Event> allEvents = eventListViewModel.getAllEvents();
    VBox container = new VBox();
    for (Event event : allEvents)
    {
      if (event.getTittle().toLowerCase().contains(query))
      {
        loadEventView(viewModelFactory.getSimpleEventViewModel(event),
            container);
        searchError.setVisible(false);
      }
      else
      {
        searchError.setVisible(true);
      }
    }
    scrollPane.setContent(container);
  }

  private void filterEvents(ArrayList<Event> eventsToFilter)
  {
    VBox container = new VBox();
    for (Event event : eventsToFilter)
    {
      loadEventView(viewModelFactory.getSimpleEventViewModel(event), container);
    }
    scrollPane.setContent(container);
  }

  @FXML private void filterByGame(ActionEvent event)
  {
    MenuItem menuItem = (MenuItem) event.getSource();
    String game = menuItem.getText();
    eventListViewModel.getEventsByGame(game);
    ArrayList<Event> gameEvents = eventListViewModel.getEventsByGame(game);
    filterError.setVisible(gameEvents.isEmpty());
    filterEvents(gameEvents);
  }

  @FXML private void filterBySkillLevel(ActionEvent event)
  {
    MenuItem menuItem = (MenuItem) event.getSource();
    String skillLevel = menuItem.getText();
    ArrayList<Event> skillLevelEvents = eventListViewModel.getEventsBySkillLevel(
        skillLevel);
    filterError.setVisible(skillLevelEvents.isEmpty());
    filterEvents(skillLevelEvents);
  }

  @FXML private void filterByStatus(ActionEvent event)
  {
    MenuItem menuItem = (MenuItem) event.getSource();
    String status = menuItem.getText();
    ArrayList<Event> statusEvents = eventListViewModel.getEventsByStatus(
        status);
    filterError.setVisible(statusEvents.isEmpty());
    filterEvents(statusEvents);
  }

  @FXML private void filterAny()
  {
    ArrayList<Event> allEvents = eventListViewModel.getAllEvents();
    VBox container = new VBox();
    if (!allEvents.isEmpty())
    {
      filterError.setVisible(false);
      for (Event event : allEvents)
      {
        loadEventView(viewModelFactory.getSimpleEventViewModel(event),
            container);
      }
    }
    else
    {
      filterError.setVisible(true);
    }
  }

  @Override public Region getRoot()
  {
    return root;
  }

  @FXML void backButtonPressed()
  {
    viewHandler.openView("");
  }
  @FXML void createButtonPressed(){
    viewHandler.openView("CreateEvent");
  }
}
