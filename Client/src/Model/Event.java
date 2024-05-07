package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event {
  private String tittle;
  private String game;
  private int minBRP;
  private int maxBRP;
  private String status;
  private int maxParticipants;
  private String startDate;
  private int startingHour;
  private Moderator Organizer;
  private ArrayList<Match> matches;
  private ArrayList<User> participants;

  public Event(String tittle, String game, int minBRP, int maxBRP,
      String status, int maxParticipants, String date, int startingHour,
      Moderator organizer) {
    this.tittle = tittle;
    this.game = game;
    this.minBRP = minBRP;
    this.maxBRP = maxBRP;
    this.status = status;
    this.maxParticipants = maxParticipants;
    this.startDate = date;
    this.startingHour = startingHour;
    Organizer = organizer;
    matches = new ArrayList<>();
    participants = new ArrayList<>(maxParticipants);
  }

  public Event(String tittle, String game, int minBRP, int maxBRP,
      int maxParticipants, String date, int startingHour, Moderator organizer) {
    this(tittle, game, minBRP, maxBRP, "Created", maxParticipants, date,
        startingHour, organizer);
  }

  public Event(String tittle, String game, int minBRP, int maxBRP,
      String status, int maxParticipants, String date, int startingHour,
      Moderator organizer, ArrayList<Match> matches,
      ArrayList<User> participants) {
    this(tittle, game, minBRP, maxBRP, status, maxParticipants, date,
        startingHour, organizer);
    this.matches = matches;
    this.participants = participants;
  }

  public String getTittle() {
    return tittle;
  }

  public String getGame() {
    return game;
  }

  public int getMinBRP() {
    return minBRP;
  }

  public int getMaxBRP() {
    return maxBRP;
  }

  public String getStatus() {
    return status;
  }

  public int getMaxParticipants() {
    return maxParticipants;
  }

  public String getStartDate() {
    return startDate;
  }

  public int getStartingHour() {
    return startingHour;
  }

  public Moderator getOrganizer() {
    return Organizer;
  }

  public ArrayList<Match> getMatches() {
    return matches;
  }

  public ArrayList<User> getParticipants() {
    return participants;
  }

  @Override public String toString() {
    return "Event{" + "tittle='" + tittle + '\'' + ", game='" + game + '\''
        + ", minBRP=" + minBRP + ", maxBRP=" + maxBRP + ", status='" + status
        + '\'' + ", maxParticipants=" + maxParticipants + ", startDate='"
        + startDate + '\'' + ", startingHour=" + startingHour + ", Organizer="
        + Organizer + ", matches=" + matches + ", participants=" + participants
        + '}';
  }
}
