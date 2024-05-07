package viewModel;

import Model.Event;
import Model.EventListModel;
import Model.EventListModelManager;

public class ViewModelFactory {
  private EventListViewModel eventListViewModel;
  private EventDescriptionViewModel eventDescriptionViewModel;
  private NotificationPopupViewModel notificationPopupViewModel;
  private BracketViewModel bracketViewModel;

  private SimpleEventViewModel simpleEventViewModel;
  private DescriptionViewGeneralViewModel descriptionViewGeneralViewModel;
  private ViewModelState viewModelState;
  private EventListModel model;

  public ViewModelFactory(EventListModel model) {
    this.model = model;
    ViewModelState viewModelState = new ViewModelState();
    eventListViewModel = new EventListViewModel(model, viewModelState);
    eventDescriptionViewModel = new EventDescriptionViewModel(model,
        viewModelState); // <-
    notificationPopupViewModel = new NotificationPopupViewModel(model,
        viewModelState);
    descriptionViewGeneralViewModel = new DescriptionViewGeneralViewModel(model,
        viewModelState);
    bracketViewModel = new BracketViewModel(model, viewModelState);
//    simpleEventViewModel = new SimpleEventViewModel(model);
  }

  public EventListViewModel getEventListViewModel() {
    return eventListViewModel;
  }

  public EventDescriptionViewModel getEventDetailsViewModel() {
    return eventDescriptionViewModel;
  }

  public DescriptionViewGeneralViewModel getDescriptionViewGeneralViewModel() {
    return descriptionViewGeneralViewModel;
  }

  public NotificationPopupViewModel getNotificationPopupViewModel() {
    return notificationPopupViewModel;
  }

  public SimpleEventViewModel getSimpleEventViewModel(Event event) {
    simpleEventViewModel = new SimpleEventViewModel(event, model);
    return simpleEventViewModel;
  }

  public BracketViewModel getBracketViewModel8() {
    return bracketViewModel;
  }
}