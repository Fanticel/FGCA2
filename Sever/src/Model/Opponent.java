package Model;

public class Opponent {
  private User user;
  private Integer minBRP;
  private Integer maxBRP;
  public Opponent(User opponent, Integer minBRP, Integer maxBRP){
    this.user = opponent;
    this.minBRP = minBRP;
    this.maxBRP = maxBRP;
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
  public boolean compareToAnotherOpponent(Opponent anotherOpponent){
    return user.getBRP() <= anotherOpponent.maxBRP && user.getBRP() >= anotherOpponent.minBRP;
  }

  @Override public String toString() {
    return user.toString() + ", minBRP" + minBRP + ", maxBRP" + maxBRP;
  }
}
