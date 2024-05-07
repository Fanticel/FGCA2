package viewModel;

public class ViewState {
  private static ViewState instance;
  private String tittle;
  private ViewState(){
    tittle = "Sample event";
  }
  public static ViewState getInstance(){
    if (instance == null) {
      instance = new ViewState();
    }
    return instance;
  }
  public void setTittle(String tittle){
    this.tittle = tittle;
  }
  public String getTittle(){
    return tittle;
  }
}
