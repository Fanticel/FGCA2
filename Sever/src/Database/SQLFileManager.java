package Database;

import Model.*;

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
          "SELECT * FROM fgcadb.match WHERE eventTitle = ?");
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
        /*if (event.getStatus().equals("In progress")){
          ArrayList<User> participants = event.getParticipants();
          ArrayList<Match> matches = event.getMatches();
          int participantNumber = participants.size();
          if (matches.size() != 0){
            for (int i = event.getMatches().size() * 2 - 1; i < event.getParticipants().size(); i+=2){

              if (i + 1 != participantNumber){
                event.addMatch(event.getParticipants().get(i), event.getParticipants().get(i + 1));
              }
            }
          }
          else {
            for (int i = 0; i < participantNumber; i += 2){
              if (i + 1 != participantNumber){
                matches.add(new Match(participants.get(i), participants.get(i + 1)));
              }
            }
          }
          while (event.getMatches().size() < event.getMaxParticipants() - 1){
            event.addMatch(null, null);
          }
          for (Match match: event.getMatches())
          {
            if (!match.getScore().equals(" - ")){
              String[] strings = match.getScore().split("-");
              int matchIndex = matches.indexOf(match);
              if (Integer.valueOf(strings[0]) > Integer.valueOf(strings[1])){
                //gets index for last match of next round
                matches.get(event.nextMatch(event.getMaxParticipants(), matchIndex))
                    .setPlayer(matchIndex % 2, match.getPlayers().get(0));
              }
              else {
                matches.get(event.nextMatch(event.getMaxParticipants(), matchIndex))
                    .setPlayer(matchIndex % 2, match.getPlayers().get(1));
              }
            }
          }
          if (event.getParticipants().size() % 2 != 0)
          {
            // matches.add(new Match(confirmedParticipants.get(confirmedParticipants.size()-1), null));
            User oddPlayer = participants.get(participantNumber - 1);
            matches.get((int) Math.ceil(participantNumber / 2)).setPlayer(0, oddPlayer);
            //gets index for last match of next round
            int matchIndex = matches.indexOf(matches.get((int) Math.ceil(participantNumber / 2) - 1));
            matches.get(event.nextMatch(event.getMaxParticipants(), matchIndex))
                .setPlayer(matchIndex % 2, oddPlayer);
          }
        }*/
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

  @Override public void saveMatchToFile(String eventTitle, Match match) {
    try(Connection connection = getConnected()){ //UPDATE fgcadb.Participants SET checkInStatus = ? WHERE userName = ? AND eventTitle = ?
      PreparedStatement ps = connection.prepareStatement("INSERT INTO fgcadb.Match(eventTitle, userName1, userName2, user1Score, user2Score) VALUES (?, ?, ?, ?, ?)");
      ps.setString(1, eventTitle);
      ps.setString(2, match.getPlayers().get(0).getUsername());
      ps.setString(3, match.getPlayers().get(1).getUsername());
      ps.setInt(4, match.getPlayerOneScore());
      ps.setInt(5, match.getPlayerTwoScore());
      ps.executeUpdate();
    }
    catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  private Connection getConnected() throws SQLException {
    return DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
  }
}
