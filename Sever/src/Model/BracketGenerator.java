package Model;

import java.util.ArrayList;

public class BracketGenerator implements BracketGeneration
{

  @Override public ArrayList<Match> generateBracket(
      ArrayList<User> participants, ArrayList<Match> matches, int maxParticipants)
  {
    int participantNumber = participants.size();
    //generates the first round of matches with participants
    for (int i = 0; i < participantNumber; i += 2)
    {
      if (i + 1 != participantNumber)
      {
        matches.add(new Match(participants.get(i), participants.get(i + 1)));
      }
    }
    //generates empty matches for the following rounds and the last match of first round if participants are odd number
    while (matches.size() < maxParticipants - 1)
    {
      matches.add(new Match(null, null));
    }
    //If the number of players is odd the last match in the first round only has one player who is automatically assigned to the next round
    if (participantNumber % 2 != 0)
    {
      // matches.add(new Match(confirmedParticipants.get(confirmedParticipants.size()-1), null));
      User oddPlayer = participants.get(participantNumber - 1);
      int matchIndex = (int) Math.ceil((double) participantNumber / 2) - 1;
      matches.get((int) (int) Math.ceil((double) participantNumber / 2) - 1).setPlayer(0, oddPlayer);
      //gets index for last match of next round
      matches.get(nextMatch(maxParticipants, matchIndex)).setPlayer(matchIndex % 2, oddPlayer);
    }
    return matches;
  }

  @Override public ArrayList<Match> generateBracketDB(
      ArrayList<User> participants, ArrayList<Match> matches,
      int maxParticipants)
  {

    int participantNumber = participants.size();
    if (matches.size() != 0){
      for (int i = matches.size() * 2 - 1; i < participants.size(); i+=2){
        if (i + 1 != participantNumber){
          matches.add(new Match(participants.get(i), participants.get(i + 1)));
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
    while (matches.size() < maxParticipants - 1){
      matches.add(new Match(null, null));
    }
    for (Match match: matches)
    {
      if (!match.getScore().equals(" - ")){
        String[] strings = match.getScore().split("-");
        int matchIndex = matches.indexOf(match);
        //System.out.println("Match: " + matches.get(matchIndex));
        //System.out.println("Index: " + matchIndex);
        if (Integer.valueOf(strings[0]) > Integer.valueOf(strings[1])){
          //gets index for last match of next round
          matches.get(nextMatch(maxParticipants, matchIndex)).setPlayer(matchIndex % 2, match.getPlayers().get(0));
        }
        else {
          matches.get(nextMatch(maxParticipants, matchIndex)).setPlayer(matchIndex % 2, match.getPlayers().get(1));
        }
      }
    }
    if (participants.size() % 2 != 0)
    {
      // matches.add(new Match(confirmedParticipants.get(confirmedParticipants.size()-1), null));
      User oddPlayer = participants.get(participantNumber - 1);
      matches.get((int) Math.ceil(participantNumber / 2)).setPlayer(0, oddPlayer);
      //gets index for last match of next round
      int matchIndex = (int) Math.ceil((double) participantNumber / 2) - 1;
      matches.get(nextMatch(maxParticipants, matchIndex))
          .setPlayer(matchIndex % 2, oddPlayer);
    }
    //System.out.println(matches);
    return matches;
  }

  public int nextMatch(int maxParticipants, int currentMatch)
  {
    System.out.println((maxParticipants / 2) + (int) Math.ceil((double) currentMatch / 2));
    return (maxParticipants / 2) + (int) Math.floor((double) currentMatch / 2);
  }
}
