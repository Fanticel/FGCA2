package viewModel;

public class ViewModelState
{
  private String eventTittle;
 // private String notificationMessage;
 // private boolean notificationColor;

  public ViewModelState()
  {
    this.eventTittle = null;
  }

  public String getEventTittle()
  {
    return eventTittle;
  }
  public void setEventTittle(String eventTittle)
  {
    this.eventTittle = eventTittle;
  }
  public void removeTittle()
  {
    this.eventTittle = null;
  }

 /* public String getNotificationMessage()
  {
    return notificationMessage;
  }

  public void setNotificationMessage(String notificationMessage)
  {
    this.notificationMessage = notificationMessage;
  }

  public boolean isNotificationColor()
  {
    return notificationColor;
  }

  public void setNotificationColor(boolean notificationColor)
  {
    this.notificationColor = notificationColor;
  }*/
}
