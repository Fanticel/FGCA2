package Mediator;

import Model.Event;
import Model.Match;
import Model.Moderator;
import Model.User;

import java.util.ArrayList;

public class EventInformationPackage
{
  private String tittle;
  private String game;
  private int minBRP;
  private int maxBRP;
  private String status;
  private int maxParticipants;
  private String startDate;
  private int startingHour;
  private Moderator organizer;
  private ArrayList<MatchInformationPackage> matchPackages;
  private ArrayList<User> participants;

  public EventInformationPackage(String tittle, String game, int minBRP,
      int maxBRP, String status, int maxParticipants,
      String date, int startingHour, Moderator organizer, ArrayList<User> participants)
  {
    this.tittle = tittle;
    this.game = game;
    this.minBRP = minBRP;
    this.maxBRP = maxBRP;
    this.status = status;
    this.maxParticipants = maxParticipants;
    this.startDate = date;
    this.startingHour = startingHour;
    this.organizer = organizer;
    this.matchPackages = new ArrayList<>();
    this.participants = participants;
  }
  public EventInformationPackage(Event event){
    this(event.getTittle(), event.getGame(), event.getMinBRP(),
        event.getMaxBRP(), event.getStatus(), event.getMaxParticipants(),
        event.getStartDate(), event.getStartingHour(), event.getOrganizer(), event.getParticipants());
    for (Match m:event.getMatches()){
      matchPackages.add(new MatchInformationPackage(m));
    }
  }
  public String getTittle()
  {
    return tittle;
  }

  public String getGame()
  {
    return game;
  }

  public int getMinBRP()
  {
    return minBRP;
  }

  public int getMaxBRP()
  {
    return maxBRP;
  }

  public String getStatus()
  {
    return status;
  }

  public int getMaxParticipants()
  {
    return maxParticipants;
  }

  public String getStartDate()
  {
    return startDate;
  }

  public Moderator getOrganizer()
  {
    return organizer;
  }

  public ArrayList<MatchInformationPackage> getMatches()
  {
    return matchPackages;
  }
  public void addMatches(MatchInformationPackage match)
  {
    matchPackages.add(match);
  }

  public ArrayList<User> getParticipants()
  {
    return participants;
  }
  public Event convertToEvent(){
    return new Event(tittle, game, minBRP, maxBRP, status, maxParticipants, startDate, startingHour, organizer);
  }
  public Event convertToEvent(Moderator organizer){
    return new Event(tittle, game, minBRP, maxBRP, maxParticipants, startDate, startingHour, organizer);
  }
}
