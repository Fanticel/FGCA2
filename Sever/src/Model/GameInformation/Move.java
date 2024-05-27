package Model.GameInformation;

public class Move {
  private String name;
  private String damage;
  private String guard;
  private String smash;
  private String startup;
  private String active;
  private String recovery;
  private String onBlock;
  private String invulnerability;

  public Move(String name, String damage, String guard, String smash,
      String startup, String active, String recovery, String onBlock,
      String invulnerability) {
    this.name = name;
    this.damage = damage;
    this.guard = guard;
    this.smash = smash;
    this.startup = startup;
    this.active = active;
    this.recovery = recovery;
    this.onBlock = onBlock;
    this.invulnerability = invulnerability;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDamage() {
    return damage;
  }

  public void setDamage(String damage) {
    this.damage = damage;
  }

  public String getGuard() {
    return guard;
  }

  public void setGuard(String guard) {
    this.guard = guard;
  }

  public String getSmash() {
    return smash;
  }

  public void setSmash(String smash) {
    this.smash = smash;
  }

  public String getStartup() {
    return startup;
  }

  public void setStartup(String startup) {
    this.startup = startup;
  }

  public String getActive() {
    return active;
  }

  public void setActive(String active) {
    this.active = active;
  }

  public String getRecovery() {
    return recovery;
  }

  public void setRecovery(String recovery) {
    this.recovery = recovery;
  }

  public String getOnBlock() {
    return onBlock;
  }

  public void setOnBlock(String onBlock) {
    this.onBlock = onBlock;
  }

  public String getInvulnerability() {
    return invulnerability;
  }

  public void setInvulnerability(String invulnerability) {
    this.invulnerability = invulnerability;
  }

  @Override public String toString() {
    return name + ", " + damage + ", " + guard + ", " + smash + ", " + startup + ", " + active + ", " + recovery + ", " + onBlock + ", " + invulnerability;
  }
}
