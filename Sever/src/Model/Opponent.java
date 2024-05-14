package Model;

public class Opponent {
  private User user;
  private Integer minBRP;
  private Integer maxBRP;
  private String game;
  public Opponent(User opponent, Integer minBRP, Integer maxBRP, String game){
    this.user = opponent;
    this.minBRP = minBRP;
    this.maxBRP = maxBRP;
    this.game = game;
  }

  public User getUser() {
    return user;
  }

  public Integer getMinBRP() {
    return minBRP;
  }

  public Integer getMaxBRP() {
    return maxBRP;
  }
  public String getGame(){
    return game;
  }
  public boolean compareToAnotherOpponent(Opponent anotherOpponent){
    return user.getBRP() <= anotherOpponent.maxBRP && user.getBRP() >= anotherOpponent.minBRP && game.equalsIgnoreCase(anotherOpponent.getGame());
  }

  @Override public String toString() {
    return user.toString() + ", minBRP" + minBRP + ", maxBRP" + maxBRP;
  }
}
