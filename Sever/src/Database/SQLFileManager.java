package Database;

import Model.*;
import Model.GameInformation.Character;
import Model.GameInformation.Game;
import Model.GameInformation.Move;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SQLFileManager implements FileManger {
//  private UserList userList;
  public SQLFileManager() throws SQLException {
    DriverManager.registerDriver(new org.postgresql.Driver());
//    userList = new UserList(getUsersFromFile());
  }

  @Override public void saveEventToFile(Event event) {
    try (Connection connection = getConnected()){
      PreparedStatement saveEvent = connection.prepareStatement(
          "INSERT INTO fgcadb.Event(title, game, minBRP, maxBRP, status, maxParticipants, date, startingHour, organiserName) VALUES (?, ?, ?, ?, ?, ?, date(?), ?, ?)");
      saveEvent.setString(1, event.getTittle());
      saveEvent.setString(2, event.getGame());
      saveEvent.setInt(3, event.getMinBRP());
      saveEvent.setInt(4, event.getMaxBRP());
      saveEvent.setString(5, event.getStatus());
      saveEvent.setInt(6, event.getMaxParticipants());
//      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//      LocalDate date = LocalDate.parse(event.getStartDate(),formatter);
//      Date dbDate = Date.valueOf(date);
      saveEvent.setString(7, event.getStartDate());
      saveEvent.setInt(8, event.getStartingHour());
      saveEvent.setString(9, event.getOrganizer().getUsername());
      saveEvent.executeUpdate();
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override public void saveParticipantToFile(String eventTitle, String username)
  {
    try (Connection connection = getConnected()){
      PreparedStatement saveParticipant = connection.prepareStatement(
          "INSERT INTO fgcadb.Participants(username, eventTitle, checkInStatus) VALUES (?, ?, ?)");
      saveParticipant.setString(1, username);
      saveParticipant.setString(2, eventTitle);
      saveParticipant.setBoolean(3, false);
      saveParticipant.executeUpdate();
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override public void saveMatchToFile(String eventTitle, Match match, String score)
  {
    try (Connection connection = getConnected()){
      PreparedStatement saveMatch = connection.prepareStatement(
          "INSERT INTO fgcadb.Match(eventTitle, userName1, userName2, user1Score, user2Score) VALUES (?, ?, ?, ?, ?)");
      saveMatch.setString(1, eventTitle);
      saveMatch.setString(2, match.getPlayers().get(0).getUsername());
      saveMatch.setString(3, match.getPlayers().get(1).getUsername());
      String[] scores = score.split("-");
      saveMatch.setInt(4, Integer.valueOf(scores[0]));
      saveMatch.setInt(6, Integer.valueOf(scores[1]));
      saveMatch.executeUpdate();
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override public void updateEvent(Event event)
  {
    try (Connection connection = getConnected()){
      PreparedStatement updateEvent = connection.prepareStatement(
          "UPDATE fgcadb.Event SET game = ?, minBRP = ?, maxBRP = ?, status = ?, maxParticipants = ?, date = date(?), startingHour = ?, organiserName = ? WHERE title = ?");
      updateEvent.setString(1, event.getGame());
      updateEvent.setInt(2, event.getMinBRP());
      updateEvent.setInt(3, event.getMaxBRP());
      updateEvent.setString(4, event.getStatus());
      updateEvent.setInt(5, event.getMaxParticipants());
//      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//      LocalDate date = LocalDate.parse(event.getStartDate(),formatter);
//      Date dbDate = Date.valueOf(date);
      updateEvent.setString(6, event.getStartDate());
      updateEvent.setInt(7, event.getStartingHour());
      updateEvent.setString(8, event.getOrganizer().getUsername());
      updateEvent.setString(9, event.getTittle());
      updateEvent.executeUpdate();
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override public void updateParticipant(String eventTitle, User user)
  {
    try (Connection connection = getConnected())
    {
      PreparedStatement updateParticipant = connection.prepareStatement(
          "UPDATE fgcadb.Participants SET checkInStatus = ? WHERE userName = ? AND eventTitle = ?");
      updateParticipant.setBoolean(1, true);
      updateParticipant.setString(2, user.getUsername());
      updateParticipant.setString(3, eventTitle);
      updateParticipant.executeUpdate();
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void removeParticipant(String eventTitle, User user) {
    try (Connection connection = getConnected()) {
      PreparedStatement deleteParticipant = connection.prepareStatement(
          "DELETE FROM fgcadb.Participants WHERE userName = ? AND eventTitle = ?");

      deleteParticipant.setString(1, user.getUsername());
      deleteParticipant.setString(2, eventTitle);

      deleteParticipant.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  @Override public ArrayList<Event> getEventsFromFile() {
    ArrayList<Event> ans = new ArrayList<>();
    try (Connection connection = getConnected()) {
      PreparedStatement psEvent = connection.prepareStatement(
          "SELECT * FROM fgcadb.Event");
      PreparedStatement psMatch = connection.prepareStatement(
          "SELECT * FROM fgcadb.match WHERE eventTitle = ? ORDER BY arrayposition");
      PreparedStatement psParticipant = connection.prepareStatement(
          "SELECT * FROM fgcadb.participants WHERE eventTitle = ?");
      ResultSet rsEvent = psEvent.executeQuery();
      while (rsEvent.next()) {
        String title = rsEvent.getString("title");
        Event event = new Event(title, rsEvent.getString("game"), rsEvent.getInt("minBRP")
            , rsEvent.getInt("maxBRP"), rsEvent.getString("status"), rsEvent.getInt("maxParticipants")
            , rsEvent.getString("date"), rsEvent.getInt("startingHour")
            , new Moderator(UserListSingleton.getInstance().getUserList().getUserByUsername(rsEvent.getString("organiserName"))));
        psMatch.setString(1, title);
        ResultSet rsMatch = psMatch.executeQuery();
        psParticipant.setString(1, title);
        ResultSet rsParticipant = psParticipant.executeQuery();
        while (rsMatch.next()) {
          String score = rsMatch.getString("user1score") + "-" + rsMatch.getString("user2score");
          User user1 = UserListSingleton.getInstance().getUserList().getUserByUsername(rsMatch.getString("username1"));
          User user2 = UserListSingleton.getInstance().getUserList().getUserByUsername(rsMatch.getString("username2"));
          event.addMatch(user1, user2, score);
        }
        while (rsParticipant.next()) {
          User user = UserListSingleton.getInstance().getUserList().getUserByUsername(rsParticipant.getString("userName"));
          event.addParticipant(user);
          if (rsParticipant.getBoolean("checkInStatus")){
            event.checkIn(user);
          }
        }
        ans.add(event);
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return ans;
  }

  @Override public void saveUserToFile(User user) {
    try (Connection connection = getConnected()){
      PreparedStatement saveUser = connection.prepareStatement(
          "INSERT INTO fgcadb.UserTable(userName, password, BRP, displayName, role) VALUES (?, ?, ?, ?, ?)");
      saveUser.setString(1, user.getUsername());
      saveUser.setString(2, user.getPassword());
      saveUser.setInt(3, user.getBRP());
      saveUser.setString(4, user.getDisplayName());
      if (user instanceof Moderator){
        saveUser.setString(5, "Moderator");
      }
      else {
        saveUser.setString(5, "User");
      }
      saveUser.executeUpdate();
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override public ArrayList<User> getUsersFromFile() {
    ArrayList<User> ans = new ArrayList<>();
    try (Connection connection = getConnected()) {
      PreparedStatement psUser = connection.prepareStatement(
          "SELECT * FROM fgcadb.usertable");
      ResultSet rsUser = psUser.executeQuery();
      while (rsUser.next()){
        if (rsUser.getString("role").equalsIgnoreCase("user")){
          ans.add(new User(rsUser.getString("userName"), rsUser.getString("displayName"), rsUser.getString("password"), rsUser.getInt("BRP")));
        }
        else if (rsUser.getString("role").equals("moderator")){
          ans.add(new Moderator(rsUser.getString("userName"), rsUser.getString("displayName"), rsUser.getString("password"), rsUser.getInt("BRP")));
        }
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return ans;
  }

  @Override public ArrayList<Match> getPlayerMatchesFromFile()
  {
    ArrayList<Match> ans = new ArrayList<>();
    try (Connection connection = getConnected()) {
      PreparedStatement psMatch = connection.prepareStatement(
          "SELECT * FROM fgcadb.PlayerMatches");
      ResultSet rsMatch = psMatch.executeQuery();
      while (rsMatch.next()){
        ans.add(new Match(UserListSingleton.getInstance().getUserList()
            .getUserByUsername(rsMatch.getString("userName1")), UserListSingleton.getInstance().getUserList()
            .getUserByUsername(rsMatch.getString("userName2")), rsMatch.getString("user1Score") + "-" + rsMatch.getString("user2Score")));
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return ans;
  }

  @Override public void saveMatchToFile(String eventTitle, Match match, int position) {
    try(Connection connection = getConnected()){ //UPDATE fgcadb.Participants SET checkInStatus = ? WHERE userName = ? AND eventTitle = ?
      PreparedStatement ps = connection.prepareStatement("INSERT INTO fgcadb.Match(eventTitle, userName1, userName2, user1Score, user2Score, arrayPosition) VALUES (?, ?, ?, ?, ?, ?)");
      ps.setString(1, eventTitle);
      ps.setString(2, match.getPlayers().get(0).getUsername());
      ps.setString(3, match.getPlayers().get(1).getUsername());
      ps.setInt(4, match.getPlayerOneScore());
      ps.setInt(5, match.getPlayerTwoScore());
      ps.setInt(6, position);
      ps.executeUpdate();
    }
    catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override public void savePlayerMatchToFile(Match match)
  {
    try(Connection connection = getConnected()){ //UPDATE fgcadb.Participants SET checkInStatus = ? WHERE userName = ? AND eventTitle = ?
      PreparedStatement ps = connection.prepareStatement("INSERT INTO fgcadb.PlayerMatches(userName1, userName2, user1Score, user2Score) VALUES (?, ?, ?, ?)");
      ps.setString(1, match.getPlayers().get(0).getUsername());
      ps.setString(2, match.getPlayers().get(1).getUsername());
      ps.setInt(3, match.getPlayerOneScore());
      ps.setInt(4, match.getPlayerTwoScore());
      ps.executeUpdate();
    }
    catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override public void updateUserToModerator(User user) {
    try (Connection connection = getConnected()){
      PreparedStatement ps = connection.prepareStatement("UPDATE fgcadb.usertable SET role='moderator' WHERE username = ?");
      ps.setString(1, user.getUsername());
      ps.executeUpdate();
    }
    catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override public void updateModeratorToUser(User user) {
    try (Connection connection = getConnected()){
      PreparedStatement ps = connection.prepareStatement("UPDATE fgcadb.usertable SET role='user' WHERE username = ?");
      ps.setString(1, user.getUsername());
      ps.executeUpdate();
    }
    catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override public ArrayList<Game> loadAllMovesCharacterGames() {
    ArrayList<Game> ans = new ArrayList<>();
    try(Connection connection = getConnected()) {
      PreparedStatement gamePS = connection.prepareStatement("SELECT * FROM fgcadb.games");
      PreparedStatement characterPS = connection.prepareStatement("SELECT * FROM fgcadb.characters WHERE origingamename = ?");
      PreparedStatement movesPS = connection.prepareStatement("SELECT * FROM fgcadb.charactermoves WHERE characterid=?");
      ResultSet gameRS = gamePS.executeQuery();
      while (gameRS.next()){
        Game thisGame = new Game(gameRS.getString(1), gameRS.getString(2), new ArrayList<>());
        characterPS.setString(1, gameRS.getString(1));
        ResultSet characterRS = characterPS.executeQuery();
        while (characterRS.next()){
          Character thisCharacter = new Character(characterRS.getString(2),characterRS.getString(3),
              characterRS.getString(4), characterRS.getString(5), new ArrayList<>());
          movesPS.setInt(1, characterRS.getInt(1));
          ResultSet movesRS = movesPS.executeQuery();
          while (movesRS.next()){
            Move thisMove = new Move(movesRS.getString(2), movesRS.getString(3),
                movesRS.getString(4), movesRS.getString(5), movesRS.getString(6),
                movesRS.getString(7) ,movesRS.getString(8), movesRS.getString(9),
                movesRS.getString(10));
            thisCharacter.addToMoveList(thisMove);
          }
          thisGame.addCharacter(thisCharacter);
        }
        ans.add(thisGame);
      }
    }
    catch (SQLException e){
      throw new RuntimeException(e);
    }
    return ans;
  }

  private Connection getConnected() throws SQLException {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
  }
}
