package Model;

public class Moderator extends User
{
  public Moderator(String username, String displayName, String password)
  {
    super(username, displayName, password);
  }
  public Moderator(String username, String displayName, String password, int BRP)
  {
    super(username, displayName, password, BRP);
  }
  public Moderator(User user)
  {
    this(user.getUsername(), user.getDisplayName(), user.getPassword(),
        user.getBRP());
  }
}
