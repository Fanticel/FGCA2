package viewModel;

import Model.EventListModel;
import javafx.application.Platform;
import javafx.beans.property.*;
import utility.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OneVsOneSearchingViewModel implements PropertyChangeListener,
    NamedPropertyChangeSubject
{
  private IntegerProperty BRPProperty;
  private StringProperty opponentNameProperty;
  private BooleanProperty opponentFoundProperty;
  private BooleanProperty visibleSearchProperty;
  private EventListModel model;
  private String opponentUsername;
  private PropertyChangeListener listener;
  private ViewState viewState;
  public OneVsOneSearchingViewModel(EventListModel model){
    this.model = model;
    this.model.addListener("OpponentFound",this);
    viewState = ViewState.getInstance();
    opponentNameProperty = new SimpleStringProperty();
    BRPProperty = new SimpleIntegerProperty();
    opponentFoundProperty = new SimpleBooleanProperty(false);
    visibleSearchProperty = new SimpleBooleanProperty(true);
    opponentUsername = null;
    listener = null;
  }
  public void reset(){
    opponentNameProperty.set("");
    BRPProperty.set(0);
    opponentFoundProperty.set(false);
    visibleSearchProperty.set(true);
    opponentUsername = null;
  }

  public IntegerProperty getBRPProperty()
  {
    return BRPProperty;
  }

  public StringProperty getOpponentNameProperty()
  {
    return opponentNameProperty;
  }

  public BooleanProperty isOpponentFoundProperty()
  {
    return opponentFoundProperty;
  }

  public BooleanProperty isVisibleSearchProperty()
  {
    return visibleSearchProperty;
  }

  public void cancel(){
    model.removeOpponent();
  }
  public void decline(){
    model.declineOpponent(opponentUsername);
  }
  public void accept(){
    model.acceptOpponent(opponentUsername);
  }
  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("OpponentFound")){
      Platform.runLater(()->{
        Object[] opponentInfo = (Object[]) evt.getOldValue();
      opponentNameProperty.set((String) opponentInfo[0]);
      BRPProperty.set((int) opponentInfo[1]);
      opponentUsername = (String) evt.getNewValue();
      opponentFoundProperty.set(true);
      visibleSearchProperty.set(false);
      });
      //listener.propertyChange(new PropertyChangeEvent(this, "Opponent", "", ""));
    }
    else if (evt.getPropertyName().equals("OpponentRefused")){
      listener.propertyChange(new PropertyChangeEvent(this, "Refused", "", ""));
    }
    else if (evt.getPropertyName().equals("opponentAccepted")){
      String[] playerInfo = {(String) evt.getNewValue(), opponentNameProperty.get(), opponentUsername};
      viewState.setMatchPlayers(playerInfo);
      listener.propertyChange(new PropertyChangeEvent(this, "Accepted", "", ""));
    }
  }

  @Override public void addListener(String propertyName,
      PropertyChangeListener l)
  {
    listener = l;
  }

  @Override public void removeListener(String propertyName,
      PropertyChangeListener listener)
  {
    listener = null;
  }
}
