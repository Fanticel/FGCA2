package viewModel;

import Model.Event;
import Model.EventListModel;
import Model.EventListModelManager;
import view.CreateEventController;
import view.MainPageViewController;

public class ViewModelFactory {
  private EventListViewModel eventListViewModel;
  private EventDescriptionViewModel eventDescriptionViewModel;
  private NotificationPopupViewModel notificationPopupViewModel;
  private BracketViewModel bracketViewModel;

  private SimpleEventViewModel simpleEventViewModel;
  private DescriptionViewGeneralViewModel descriptionViewGeneralViewModel;
  private LogInViewModel logInViewModel;
  private RegisterViewModel registerViewModel;
  private CreateEventViewModel createEventViewModel;
  private ReportScoreViewModel reportScoreViewModel;
  private MainPageViewModel mainPageViewModel;
  private OneVsOneViewModel oneVsOneViewModel;
  private OneVsOneSearchingViewModel oneVsOneSearchingViewModel;
  private OpponentFoundViewModel opponentFoundViewModel;
  private GameInfoViewModel gameInfoViewModel;
  private CharacterInfoViewModel characterInfoViewModel;
  private MoveInfoViewModel moveInfoViewModel;
  private ChatViewModel chatViewModel;
  private ViewState viewState;
  private EventListModel model;

  public ViewModelFactory(EventListModel model) {
    this.model = model;
    viewState = ViewState.getInstance();
  }
  public LogInViewModel getLogInViewModel(){
    logInViewModel = new LogInViewModel(model);
    return logInViewModel;
  }

  public EventListViewModel getEventListViewModel() {
    eventListViewModel = new EventListViewModel(model, viewState);
    return eventListViewModel;
  }

  public EventDescriptionViewModel getEventDetailsViewModel() {
    eventDescriptionViewModel = new EventDescriptionViewModel(model,
        viewState);
    return eventDescriptionViewModel;
  }

  public DescriptionViewGeneralViewModel getDescriptionViewGeneralViewModel() {
    descriptionViewGeneralViewModel = new DescriptionViewGeneralViewModel(model,
        viewState);
    return descriptionViewGeneralViewModel;
  }

  public NotificationPopupViewModel getNotificationPopupViewModel() {
    notificationPopupViewModel = new NotificationPopupViewModel(model,
        viewState);
    return notificationPopupViewModel;
  }

  public SimpleEventViewModel getSimpleEventViewModel(Event event) {
    simpleEventViewModel = new SimpleEventViewModel(event, model);
    return simpleEventViewModel;
  }

  public BracketViewModel getBracketViewModel8() {
    bracketViewModel = new BracketViewModel(model, viewState);
    return bracketViewModel;
  }
  public RegisterViewModel getRegisterViewModel(){
    registerViewModel = new RegisterViewModel(model);
    return registerViewModel;
  }
  public CreateEventViewModel getCreateEventViewModel(){
    createEventViewModel = new CreateEventViewModel(model);
    return createEventViewModel;
  }
  public ReportScoreViewModel getReportScoreViewModel(){
    reportScoreViewModel = new ReportScoreViewModel(model);
    return reportScoreViewModel;
  }
  public MainPageViewModel getMainPageViewModel(){
    mainPageViewModel = new MainPageViewModel(model);
    return mainPageViewModel;
  }
  public OneVsOneViewModel getOneVsOneViewModel()
  {
    oneVsOneViewModel = new OneVsOneViewModel(model);
    return oneVsOneViewModel;
  }
  public OneVsOneSearchingViewModel oneVsOneSearchingViewModel(){
    oneVsOneSearchingViewModel = new OneVsOneSearchingViewModel(model);
    return oneVsOneSearchingViewModel;
  }
  public OpponentFoundViewModel getOpponentFoundViewModel(){
    opponentFoundViewModel = new OpponentFoundViewModel(model);
    return opponentFoundViewModel;
  }
  public ChatViewModel getChatViewModel(){
    chatViewModel = new ChatViewModel(model);
    return chatViewModel;
  }
  public GameInfoViewModel getGameInfoViewModel(){
    gameInfoViewModel = new GameInfoViewModel(model);
    return gameInfoViewModel;
  }
  public CharacterInfoViewModel getCharacterInfoViewModel(){
    characterInfoViewModel = new CharacterInfoViewModel(model);
    return characterInfoViewModel;
  }
  public MoveInfoViewModel getMoveInfoViewModel(){
    moveInfoViewModel = new MoveInfoViewModel(model);
    return moveInfoViewModel;
  }
}