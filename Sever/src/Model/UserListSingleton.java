package Model;

import Database.FileManger;
import Database.SQLFileManager;

import java.sql.SQLException;

public class UserListSingleton {
  private static UserListSingleton instance;
  private UserList userList;
  private UserListSingleton() throws SQLException {
    FileManger fileManger = new SQLFileManager();
    userList = new UserList(fileManger.getUsersFromFile());
  }
  public static UserListSingleton getInstance() throws SQLException {
    if (instance == null){
      instance = new UserListSingleton();
    }
    return instance;
  }
  public UserList getUserList(){
    return userList;
  }
}
