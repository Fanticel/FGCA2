package viewModel;

import Model.EventListModel;

public class ViewModelFactory
{
  private EventListViewModel eventListViewModel;
  private EventDescriptionViewModel eventDescriptionViewModel;
  private NotificationPopupViewModel notificationPopupViewModel;

  private SimpleEventViewModel simpleEventViewModel;
  private ViewModelState viewModelState;
  public ViewModelFactory(EventListModel model)
  {
    ViewModelState viewModelState = new ViewModelState();
    eventListViewModel = new EventListViewModel(model, viewModelState);
    eventDescriptionViewModel = new EventDescriptionViewModel(model, viewModelState); // <-
    notificationPopupViewModel = new NotificationPopupViewModel(model, viewModelState);
//    simpleEventViewModel = new SimpleEventViewModel(model);
  }

  public EventListViewModel getEventListViewModel()
  {
    return eventListViewModel;
  }

  public EventDescriptionViewModel getEventDetailsViewModel()
  {
    return eventDescriptionViewModel;
  }
  public NotificationPopupViewModel getNotificationPopupViewModel(){
    return notificationPopupViewModel;
  }

//  public SimpleEventViewModel getSimpleEventViewModel(){
//    return simpleEventViewModel;
//  }
  // change yay
}
