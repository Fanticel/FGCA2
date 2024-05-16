package Model;

import java.util.ArrayList;

public interface BracketGeneration
{
  public ArrayList<Match> generateBracket(ArrayList<User> participants, ArrayList<Match> matches, int maxParticipants);
  public ArrayList<Match> generateBracketDB(ArrayList<User> participants, ArrayList<Match> matches, int maxParticipants);
}
