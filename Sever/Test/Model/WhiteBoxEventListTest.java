package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WhiteBoxEventListTest
{
  public EventList eventList;
  @BeforeEach void setUp()
  {
    eventList = new EventList();
  }

  @Test void getAllEvents()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    Event event2 = new Event("Title2","Tekken", 2000, 5000, 32, "2024-07-05", 8, moderator);
    Event event3 = new Event("Title3","Mortal kombat", 0, 1000, 8, "2024-07-05", 8, moderator);
    ArrayList<Event> result = new ArrayList<>(Arrays.asList(event, event2, event3));
    eventList.addEvent(event);
    eventList.addEvent(event2);
    eventList.addEvent(event3);
    assertEquals(result, eventList.getAllEvents());
  }

  @Test void getEventIfItsNotInList()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    Event event2 = new Event("Title2","Tekken", 2000, 5000, 32, "2024-07-05", 8, moderator);
    Event event3 = new Event("Title3","Mortal kombat", 0, 1000, 8, "2024-07-05", 8, moderator);
    ArrayList<Event> result = new ArrayList<>(Arrays.asList(event, event2, event3));
    eventList.addEvent(event);
    eventList.addEvent(event2);
    eventList.addEvent(event3);
    assertNull(eventList.getEvent("Not here"));
  }

  @Test void getEventIfItIsInTheList()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    Event event2 = new Event("Title2","Tekken", 2000, 5000, 32, "2024-07-05", 8, moderator);
    Event event3 = new Event("Title3","Mortal kombat", 0, 1000, 8, "2024-07-05", 8, moderator);
    ArrayList<Event> result = new ArrayList<>(Arrays.asList(event, event2, event3));
    eventList.addEvent(event);
    eventList.addEvent(event2);
    eventList.addEvent(event3);
    assertEquals(event, eventList.getEvent("Title"));
  }

  @Test void addEventWhenTitleIsNotUnique()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    Event event2 = new Event("Title","Mortal kombat", 2000, 5000, 32, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    assertThrows(IllegalArgumentException.class, ()-> eventList.addEvent(event2));
  }

  @Test void addEventWhenTitleIsUnique()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    Event event2 = new Event("Title2","Mortal kombat", 2000, 5000, 32, "2024-07-05", 8, moderator);
    ArrayList<Event> result = new ArrayList<>(Arrays.asList(event, event2));
    eventList.addEvent(event);
    eventList.addEvent(event2);
    assertEquals(result, eventList.getAllEvents());;
  }

  @Test void addParticipantIfEventNotFound()
  {
    User user = new User("Leo", "ThaMan", "666");
    ArrayList<Object> response = new ArrayList<>();
    response.add("Something went wrong, try joining again later.");
    response.add(true);
    assertEquals(response, eventList.addParticipant("Title", user));
  }
  @Test void addParticipantIfEventFound()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    User user = new User("Leo", "ThaMan", "666");
    eventList.addEvent(event);
    ArrayList<Object> response = new ArrayList<>();
    response.add("Successfully joined");
    response.add(false);
    assertEquals(response, eventList.addParticipant("Title", user));
  }
  @Test void addParticipantIfUserIsNull()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    ArrayList<Object> response = new ArrayList<>();
    response.add("Successfully joined");
    response.add(false);
    assertThrows(IllegalArgumentException.class, ()-> eventList.addParticipant("Title", null));
  }

  @Test void addParticipantWhenHeIsAlreadyAdded()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    User user = new User("Leo", "ThaMan", "666");
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    ArrayList<Object> response = new ArrayList<>();
    response.add("You are already registered for this event!");
    response.add(true);
    assertEquals(response, eventList.addParticipant("Title", user));
  }
  @Test void addParticipantWhenMaxParticipantAmountReached()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    ArrayList<User> users = new ArrayList<>();
    for (int i = 0; i < 8; i++){
      users.add(new User("Leo" + i, "ThaMan", "666"));
    }
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    for (int i = 0; i < 8; i++){
      eventList.addParticipant("Title", users.get(i));
    }
    ArrayList<Object> response = new ArrayList<>(Arrays.asList("Maximum number of participants reached!", true));
    assertEquals(response, eventList.addParticipant("Title", user2));
  }
  @Test void addParticipantIfQualificationsNotMet()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 600, 1000, 8, "2024-07-05", 8, moderator);
    User user = new User("Leo", "ThaMan", "666");
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    ArrayList<Object> response = new ArrayList<>();
    response.add("You do not meet the BRP requirements!");
    response.add(true);
    assertEquals(response, eventList.addParticipant("Title", user));
  }
  @Test void checkInWhenEventNotFound()
  {
    User user = new User("Leo", "ThaMan", "666");
    assertEquals("Event not found_;_true", eventList.checkIn("Title", user));
  }

  @Test void checkInWhenEventFoundButUserIsNull()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    assertThrows(IllegalArgumentException.class, ()-> eventList.checkIn("Title", null));
  }

  @Test void checkInWhenEventFoundButUserNotParticipant()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    assertEquals("You didn't joined this event_;_true", eventList.checkIn("Title", user));
  }

  @Test void checkInWhenEventFoundAndAlreadyCheckedIn()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    eventList.checkIn("Title", user);
    assertEquals("You are already checked in_;_true", eventList.checkIn("Title", user));
  }

  @Test void checkInWhenEventFoundAndAllIsGood()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    assertEquals("Check in successful_;_false", eventList.checkIn("Title", user));
  }

  @Test void removeParticipantWhenEventNotFound()
  {
    User user = new User("Leo", "ThaMan", "666");
    assertFalse(eventList.removeParticipant("Title",user));
  }
  @Test void removeParticipantWhenEventFound()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    assertTrue(()-> eventList.removeParticipant("Title", user));
  }

  //Methods that aren't tested are property change, addListener, removeListener and toString
}