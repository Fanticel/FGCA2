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
  private ViewState viewState;
  private EventListModel model;

  public ViewModelFactory(EventListModel model) {
    this.model = model;
    viewState = ViewState.getInstance();
    eventListViewModel = new EventListViewModel(model, viewState);
    eventDescriptionViewModel = new EventDescriptionViewModel(model,
        viewState); // <-
    notificationPopupViewModel = new NotificationPopupViewModel(model,
        viewState);
    descriptionViewGeneralViewModel = new DescriptionViewGeneralViewModel(model,
        viewState);
    bracketViewModel = new BracketViewModel(model, viewState);
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