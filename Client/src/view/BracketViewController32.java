package view;

import javafx.scene.layout.Region;
import viewModel.BracketViewModel32;
import viewModel.ViewModelFactory;

public class BracketViewController32 implements ViewController{
  private ViewHandler viewHandler;
  private BracketViewModel32 bracketViewViewModel;
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

