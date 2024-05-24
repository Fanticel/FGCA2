package view;

import javafx.scene.layout.Region;
import viewModel.GameInfoViewModel;
import viewModel.ViewModelFactory;

public class GameInfoViewController implements ViewController{
  private ViewHandler viewHandler;
  private GameInfoViewModel gameInfoViewModel;
  private Region root;
  @Override public void init(ViewHandler viewHandler,
      ViewModelFactory viewModelFactory, Region root) {
    this.viewHandler = viewHandler;
    gameInfoViewModel = viewModelFactory.getGameInfoViewModel();
    this.root = root;
  }

  @Override public void reset() {
    gameInfoViewModel.reset();
  }

  @Override public Region getRoot() {
    return root;
  }
}
