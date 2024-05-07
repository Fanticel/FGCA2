package Model;

import java.util.ArrayList;

public class UserList {
  private ArrayList<User> userList;
  public UserList(){
    userList = new ArrayList<>();
  }
  public UserList(ArrayList<User> userList){
    this();
    this.userList.addAll(userList);
  }
  public User getUserByUsername(String userName){
    for (User u: userList) {
      if (u.getUsername().equals(userName)){
        return u;
      }
    }
    return null;
  }
  public void addUser(User u){
    userList.add(u);
  }
  public void addUser(ArrayList<User> userArrayList){
    userList.addAll(userArrayList);
  }
}
