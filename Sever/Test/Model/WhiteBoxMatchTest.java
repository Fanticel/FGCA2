package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhiteBoxMatchTest
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

  @Test void voteOnOutcomeWhenMatchAlreadyOver()
  {
    match.voteOnOutcome(user, 1, 2);
    match.voteOnOutcome(user, 1, 2);
    match.voteOnOutcome(user, 1, 2);
    match.voteOnOutcome(user2, 1, 2);
    assertEquals("PN:The voting for this event is already over!_;_true",match.voteOnOutcome(user, 1, 2));
  }

  @Test void voteOnOutcomeWhenAlreadyVoted()
  {
    match.voteOnOutcome(user, 1, 2);
    match.voteOnOutcome(user, 1, 2);
    assertEquals("PN:You have already voted!_;_true",match.voteOnOutcome(user, 1, 2));
  }

  @Test void voteOnOutcomeNormally()
  {
    assertEquals("PN:Voted successfully!_;_false", match.voteOnOutcome(user, 3, 2));
  }

  @Test void voteOnOutcomeWhenUsersReportDifferentResults()
  {
    match.voteOnOutcome(user, 1, 2);
    assertEquals("BMN:There was a discrepancy in voting!_;_true", match.voteOnOutcome(user2, 2, 1));
  }

  @Test void voteOnOutcomeNormallyForBothUsers()
  {
    match.voteOnOutcome(user, 1, 2);
    assertEquals("BN:Match ended with score 1-2_;_false", match.voteOnOutcome(user2, 1, 2));
  }

  @Test void activateMatchTimer()
  {
    match.activateMatchTimer();
    assertTrue(match.getTimer().isActive());
  }
}