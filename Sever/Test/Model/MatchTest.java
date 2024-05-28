package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTest
{
  private Match match;
  private User user;
  private User user2;
  @BeforeEach void setUp()
  {
    user = new User("Leo", "ThaMan", "666");
    user2 = new User("Vas", "DefinitionOfInsanity", "669");
    match = new Match(user, user2);
  }
  @Test void voteOnOutcomeOnce()
  {
    assertEquals("PN:Voted successfully!_;_false", match.voteOnOutcome(user, 3, 2));
  }

  @Test void voteOnOutcomeMultipleTimes()
  {
    match.voteOnOutcome(user, 1, 2);
    match.voteOnOutcome(user, 1, 2);
    assertEquals("PN:You have already voted!_;_true",match.voteOnOutcome(user, 1, 2));
  }
//No exceptions or boundaries exist

  @Test void activateMatchTimerOnce()
  {
    match.activateMatchTimer();
    assertTrue(match.getTimer().isActive());
  }
  @Test void activateMatchTimerMultipleTimes()
  {
    match.activateMatchTimer();
    match.activateMatchTimer();
    match.activateMatchTimer();
    assertTrue(match.getTimer().isActive());
  }
  //No exceptions or boundaries exist
}