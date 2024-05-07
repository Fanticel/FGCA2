package view;

import javafx.scene.layout.Region;
import viewModel.ViewModelFactory;

public interface ViewController {
  void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory,
      Region root);
  void reset();
  Region getRoot();
}
