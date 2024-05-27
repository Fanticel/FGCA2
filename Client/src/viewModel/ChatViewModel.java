package viewModel;

import Model.EventListModel;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChatViewModel implements PropertyChangeListener {
  private EventListModel model;
  private StringProperty chatName;
  private StringProperty previousLog;
  private StringProperty input;
  private String previousChatName;
  public ChatViewModel(EventListModel model){
    previousChatName = null;
    this.model = model;
    model.addListener(ViewState.getInstance().getDesiredChat(), this);
    chatName = new SimpleStringProperty();
    previousLog = new SimpleStringProperty();
    input = new SimpleStringProperty();
    reset();
  }
  public StringProperty getChatName(){
    return chatName;
  }
  public StringProperty getPreviousLog(){
    return previousLog;
  }
  public StringProperty getInput(){
    return input;
  }
  public void reset(){
    if (previousChatName != null){
      disconnect(previousChatName);
    }
    try {
      model.addToChat(ViewState.getInstance().getDesiredChat());
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
    chatName.set(ViewState.getInstance().getDesiredChat());
    previousLog.set(model.getChatLogByName(ViewState.getInstance().getDesiredChat()));
    try {
      model.addToChat(ViewState.getInstance().getDesiredChat());
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
    previousChatName = ViewState.getInstance().getDesiredChat();
  }
  public void disconnect(String previousChatName){
    try {
      model.removeFromChat(previousChatName);
      model.removeListener(previousChatName,this);
      model.addListener(ViewState.getInstance().getDesiredChat(), this);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  public void enterPress(){
    try {
      model.writeToChat(chatName.get(), input.get());
      input.set("");
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    Platform.runLater(()->{previousLog.set(previousLog.get() + "\n" + evt.getNewValue());});
  }
}
