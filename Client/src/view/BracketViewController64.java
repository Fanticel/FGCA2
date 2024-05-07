package view;

import javafx.scene.layout.Region;
import viewModel.BracketViewModel64;
import viewModel.ViewModelFactory;

public class BracketViewController64 implements ViewController

{
  private ViewHandler viewHandler;
  private BracketViewModel64 bracketViewModel64;
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
