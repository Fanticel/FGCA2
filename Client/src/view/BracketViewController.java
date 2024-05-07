package view;

import javafx.scene.layout.Region;
import viewModel.BracketViewModel;
import viewModel.ViewModelFactory;

public class BracketViewController implements ViewController{
  private ViewHandler viewHandler;
  private BracketViewModel bracketViewViewModel;
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

