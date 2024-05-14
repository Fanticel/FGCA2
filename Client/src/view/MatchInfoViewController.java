package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import viewModel.ViewModelFactory;

public class MatchInfoViewController implements ViewController
{

  @FXML TableView<String> playerName;
  @FXML TableView<String> playerSkill;
  @FXML Button backButton;
  @FXML Button acceptButton;

  private ViewHandler viewHandler;
  private Region root;

  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root)
  {
    this.viewHandler = viewHandler;
    this.root = root;

    TableColumn <String,String> playerName = new TableColumn<>("Player name");
    TableColumn <String,String> playerSkill = new TableColumn<>("Skill level");






  }

  @Override public void reset()
  {

  }

  @Override public Region getRoot()
  {
    return root;
  }
}
