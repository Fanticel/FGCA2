package view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import viewModel.SimpleEventViewModel;
import viewModel.ViewState;

public class EventTemplateViewController {

  @FXML private Text eventTitle;
  @FXML private Text eventTime;
  @FXML private Text skillLevel;
  @FXML private Text eventGame;
  @FXML private ImageView gameImage;
  private ViewHandler viewHandler;
  private SimpleEventViewModel eventTemplateViewModel;
  private Region root;

  public void init(ViewHandler viewHandler, SimpleEventViewModel viewModel,
      Region eventViewRoot) {
    this.viewHandler = viewHandler;
    eventTemplateViewModel = viewModel;
    this.root = eventViewRoot;

    eventTitle.textProperty()
        .bindBidirectional(eventTemplateViewModel.getTittleProperty());
    eventTime.textProperty()
        .bindBidirectional(eventTemplateViewModel.getDateProperty());
    skillLevel.textProperty()
        .bindBidirectional(eventTemplateViewModel.getBRPProperty());
    eventGame.textProperty()
        .bindBidirectional(eventTemplateViewModel.getGameProperty());

    if (eventGame.getText().toUpperCase().contains("TEKKEN")) {
      Image image = new Image("images/tekken.png");
      gameImage.setImage(image);
    }
    else if (eventGame.getText().toUpperCase().contains("STREET FIGHTER")) {
      Image image = new Image("images/streetFighter.png");
      gameImage.setImage(image);
    }
    else if (eventGame.getText().toUpperCase().contains("GUILTY GEAR STRIVE")) {
      Image image = new Image("images/ggst.png");
      gameImage.setImage(image);
    }
    else if (eventGame.getText().toUpperCase()
        .contains("SUPER SMASH BROS ULTIMATE")) {
      Image image = new Image("images/ssbu.png");
      gameImage.setImage(image);
    }
    else if (eventGame.getText().toUpperCase().contains("MORTAL KOMBAT")) {
      Image image = new Image("images/mortalKombat.png");
      gameImage.setImage(image);
    }
    else {
      Image image = new Image("images/fgcaProfile.png");
      gameImage.setImage(image);
    }
  }

  public void reset() {

  }

  public Region getRoot() {
    return root;
  }

  @FXML void pressDetailsButton() {
    ViewState.getInstance().setTittle(eventTitle.getText());
    System.out.println(ViewState.getInstance().getTittle());
    viewHandler.openView("EventDetails");
  }
  @FXML void pressRegisterButton(){
    eventTemplateViewModel.RegisterButtonPress();
  }
}
