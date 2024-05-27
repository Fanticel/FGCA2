package utility;

import Model.EventListModel;
import Model.GameInformation.Move;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleMovesProperties {
  private StringProperty move;
  private StringProperty damage;
  private StringProperty guard;
  private StringProperty smash;
  private StringProperty startup;
  private StringProperty active;
  private StringProperty recovery;
  private StringProperty onBlock;
  private StringProperty invu;
  public SimpleMovesProperties(Move actualMove)
  {
    move = new SimpleStringProperty(actualMove.getName());
    damage = new SimpleStringProperty(actualMove.getDamage());
    guard = new SimpleStringProperty(actualMove.getGuard());
    smash = new SimpleStringProperty(actualMove.getSmash());
    startup = new SimpleStringProperty(actualMove.getStartup());
    active = new SimpleStringProperty(actualMove.getActive());
    recovery = new SimpleStringProperty(actualMove.getRecovery());
    onBlock = new SimpleStringProperty(actualMove.getOnBlock());
    invu = new SimpleStringProperty(actualMove.getInvulnerability());
  }
  public StringProperty getMove(){return move;}
  public StringProperty getDamage(){return damage;}
  public StringProperty getGuard(){return guard;}
  public StringProperty getSmash(){return smash;}
  public StringProperty getStartup(){return startup;}
  public StringProperty getActive(){return active;}
  public StringProperty getRecovery(){return recovery;}
  public StringProperty getOnBlock(){return onBlock;}
  public StringProperty getInvu(){return invu;}
}
