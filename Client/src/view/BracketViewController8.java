package view;

import javafx.scene.layout.Region;
import viewModel.BracketViewModel8;
import viewModel.ViewModelFactory;

public class BracketViewController8 implements ViewController{

  private ViewHandler viewHandler;
  private BracketViewModel8 bracketViewModel8;
  private Region root;


  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root)
  {

  }

  @Override public void reset()
  {


  }

  @Override public Region getRoot()
  {
    return root;
  }
}
