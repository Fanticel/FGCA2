package view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import viewModel.BracketViewModel;
import viewModel.ViewModelFactory;

import java.util.ArrayList;

public class BracketViewController implements ViewController
{

  @FXML private HBox startHbox;
  private ViewHandler viewHandler;
  private BracketViewModel bracketViewModel;
  private Region root;
  private ArrayList<HBox> hboxes;
  private ArrayList<VBox> vboxes;
  private ArrayList<Label> labels;
  private ArrayList<Button> buttons;

  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root)
  {
    this.viewHandler = viewHandler;
    this.root = root;
    bracketViewModel = viewModelFactory.getBracketViewModel8();
    hboxes = new ArrayList<>();
    vboxes = new ArrayList<>();
    labels = new ArrayList<>();
    buttons = new ArrayList<>();
    for (Node child : startHbox.getChildren())
    {
      vboxes.add((VBox) child);
    }
    for (int i = 0; i < vboxes.size(); i++)
    {
      int j = 1;
      for (Node child : vboxes.get(i).getChildren())
      {
        if (j == 0)
        {
          hboxes.add((HBox) child);
        }
        else
        {
          j--;
        }
      }
    }
    vboxes.clear();
    for (int i = 0; i < hboxes.size(); i++)
    {
      int j = 1;
      for (Node child : hboxes.get(i).getChildren())
      {
        if (j == 1)
        {
          vboxes.add((VBox) child);
          j--;
        }
        else {
          buttons.add((Button) child);
        }
      }
    }
    hboxes.clear();
    for (int i = 0; i < vboxes.size(); i++)
    {
      for (Node child : vboxes.get(i).getChildren())
      {
          hboxes.add((HBox) child);
        }
      }

    for (int i = 0; i < hboxes.size(); i++)
    {
      hboxes.get(i).setPadding(new Insets(5,5,5,5));
      for (Node child : hboxes.get(i).getChildren())
      {
        labels.add((Label) child);
      }
    }

    for (int i = 0; i < labels.size(); i++){
      if (i % 2 == 0){
        labels.get(i).setMinWidth(80);
      }
      labels.get(i).textProperty().bind(bracketViewModel.getLabels().get(i));
    }
    for (int i = 0; i < buttons.size(); i++){
      buttons.get(i).visibleProperty().bind(bracketViewModel.getVisibleButtons().get(i));
    }
  }

  @Override public void reset()
  {
    bracketViewModel.reset();
  }

  @Override public Region getRoot()
  {
    return root;
  }
}