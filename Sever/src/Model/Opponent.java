package Model;

public class Opponent {
  private User opponent;
  private Integer minBRP;
  private Integer maxBRP;
  public Opponent(User opponent, Integer minBRP, Integer maxBRP){
    this.opponent = opponent;
    this.minBRP = minBRP;
    this.maxBRP = maxBRP;
  }

  public User getOpponent() {
    return opponent;
  }

  public Integer getMinBRP() {
    return minBRP;
  }

  public Integer getMaxBRP() {
    return maxBRP;
  }
  public boolean compareToAnotherOpponent(Opponent anotherOpponent){
    return opponent.getBRP() <= anotherOpponent.maxBRP && opponent.getBRP() >= anotherOpponent.minBRP;
  }

  @Override public String toString() {
    return opponent.toString() + ", minBRP" + minBRP + ", maxBRP" + maxBRP;
  }
}
