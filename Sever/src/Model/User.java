package Model;

public class User
{
  private String username;
  private String displayName;
  private int BRP;
  private String password;

  public User(String username, String displayName, String password)
  {
    this.username = username;
    this.displayName = displayName;
    BRP = 500;
    this.password = password;
  }
  public User(String username, String displayName, String password, int BRP)
  {
    this.username = username;
    this.displayName = displayName;
    this.BRP = BRP;
    this.password = password;
  }

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getDisplayName()
  {
    return displayName;
  }

  public void setDisplayName(String displayName)
  {
    this.displayName = displayName;
  }

  public int getBRP()
  {
    return BRP;
  }

  public void setBRP(int BRP)
  {
    this.BRP = BRP;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }
  public boolean isModerator(){
    return this instanceof Moderator;
  }
  @Override public boolean equals(Object obj){
    if (obj == null || getClass() != obj.getClass()){
      return false;
    }
    User other = (User) obj;
    return username.equals(other.username) && password.equals(other.password) && BRP == other.BRP && displayName.equals(other.displayName);
  }
  @Override public String toString() {
    return username + ", " + displayName + ", " + password + ", " + BRP;
  }
}
