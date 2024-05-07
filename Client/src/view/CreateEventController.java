package view;

import javafx.scene.layout.Region;
import viewModel.CreateEventViewModel;
import viewModel.ViewModelFactory;

public class CreateEventController implements ViewController {

  private ViewHandler viewHandler;
  private CreateEventViewModel createEventViewModel;
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


