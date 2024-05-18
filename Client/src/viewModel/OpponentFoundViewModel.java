package viewModel;

import Model.EventListModel;
import Model.Match;
import Model.User;

public class OpponentFoundViewModel {
  private EventListModel model;
  private ViewState viewState;
  public OpponentFoundViewModel(EventListModel model){
    this.model = model;
    this.viewState = ViewState.getInstance();
  }
}
