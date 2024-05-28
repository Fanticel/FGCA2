package Model;

import Mediator.ServerMaster;
import Model.Event;
import Model.EventList;
import Model.Moderator;
import Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EventListTest
{
  public EventList eventList;
  @BeforeEach void setUp()
  {
    eventList = new EventList();
  }
  @Test void getAllEventsWhenItsEmpty()
  {
    ArrayList<Event> result = new ArrayList<>();
    assertEquals(result, eventList.getAllEvents());
  }
  @Test void getAllEventsWhenThereIsOneEvent()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    ArrayList<Event> result = new ArrayList<>(Arrays.asList(event));
    eventList.addEvent(event);
    assertEquals(result, eventList.getAllEvents());
  }
  @Test void getAllEventsWhenThereIsMultipleEvents()
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

  @Test void getEventWhenListIsEmpty()
  {
    assertNull(eventList.getEvent("Title"));
  }
  @Test void getEventWhenListHasOneEvent()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    assertEquals(event, eventList.getEvent("Title"));
  }
  @Test void getEventWhenListHasMultipleEvents()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    Event event2 = new Event("Title2","Tekken", 2000, 5000, 32, "2024-07-05", 8, moderator);
    Event event3 = new Event("Title3","Mortal kombat", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addEvent(event2);
    eventList.addEvent(event3);
    assertEquals(event2, eventList.getEvent("Title2"));
  }
  @Test void getEventWhenElementNotInList()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    Event event2 = new Event("Title2","Tekken", 2000, 5000, 32, "2024-07-05", 8, moderator);
    Event event3 = new Event("Title3","Mortal kombat", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addEvent(event2);
    eventList.addEvent(event3);
    assertEquals(null, eventList.getEvent("Title5"));
  }
  @Test void addEventWhenListEmpty()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    ArrayList<Event> result = new ArrayList<>(Arrays.asList(event));
    eventList.addEvent(event);
    assertEquals(result, eventList.getAllEvents());
  }
  @Test void addEventWhenThereIsOne()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    Event event2 = new Event("Title2","Tekken", 2000, 5000, 32, "2024-07-05", 8, moderator);
    ArrayList<Event> result = new ArrayList<>(Arrays.asList(event, event2));
    eventList.addEvent(event);
    eventList.addEvent(event2);
    assertEquals(result, eventList.getAllEvents());
  }
  @Test void addMultipleEvents()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    ArrayList<Event> events = new ArrayList<>();
    for(int i = 0; i < 5; i++){
      Event event = new Event("Title" + i,"Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
      events.add(event);
      eventList.addEvent(event);
    }
    assertEquals(events, eventList.getAllEvents());
  }
  @Test void addParticipantWhenThereIsNone()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    ArrayList<User> participants = new ArrayList<>(Arrays.asList(user));
    assertEquals(participants, event.getParticipants() );
  }

  @Test void addNullParticipant()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    assertThrows(IllegalArgumentException.class, ()-> eventList.addParticipant("Title", null));
  }
  @Test void addMultipleParticipants()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    User user3 = new User("Bas", "DumDumDum", "769");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    Event event2 = new Event("Title2","Tekken", 2000, 5000, 32, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addEvent(event2);
    eventList.addParticipant("Title", user);
    eventList.addParticipant("Title", user2);
    eventList.addParticipant("Title", user3);
    ArrayList<User> participants = new ArrayList<>(Arrays.asList(user, user2, user3));
    assertEquals(participants, event.getParticipants());
  }
  @Test void addParticipantWhenEventFull()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    ArrayList<User> users = new ArrayList<>();
    for (int i = 0; i < 8; i++){
      users.add(new User("Leo" + i, "ThaMan", "666"));
    }
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    Event event2 = new Event("Title2","Tekken", 2000, 5000, 32, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addEvent(event2);
    for (int i = 0; i < 8; i++){
      eventList.addParticipant("Title", users.get(i));
    }
    ArrayList<Object> response = new ArrayList<>(Arrays.asList("Maximum number of participants reached!", true));
    assertEquals(response, eventList.addParticipant("Title", user2));
  }
  @Test void checkInWhenUserIsNull()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    assertThrows(IllegalArgumentException.class, ()-> eventList.checkIn("Title", null));

  }
  @Test void checkInWhenOnce()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    assertEquals("Check in successful_;_false", eventList.checkIn("Title", user));
  }
  @Test void checkInMultipleParticipants()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    User user2 = new User("Vas", "DefinitionOfInsanity", "669");
    User user3 = new User("Bas", "DumDumDum", "769");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    eventList.addParticipant("Title", user2);
    eventList.addParticipant("Title", user3);
    eventList.checkIn("Title", user);
    eventList.checkIn("Title", user2);
    eventList.checkIn("Title", user3);
    ArrayList<User> confParticipants = new ArrayList<>(Arrays.asList(user, user2, user3));
    assertEquals(confParticipants, event.getConfirmedParticipants());
  }
  @Test void checkInMultipleTimes()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    eventList.checkIn("Title", user);
    eventList.checkIn("Title", user);
    assertEquals("You are already checked in_;_true", eventList.checkIn("Title", user));
  }
  @Test void checkInWhenNotParticipant()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    assertEquals("You didn't joined this event_;_true", eventList.checkIn("Title", user));
  }
  @Test void checkInWhenEventNotAvailable()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    assertEquals("Event not found_;_true", eventList.checkIn("Title5", user));
  }

  /*@Test void activateMatchTimer()
  {

  }*/

  @Test void removeParticipantWhenThereIsNone()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    assertFalse(()-> eventList.removeParticipant("Title", user));
  }
  @Test void removeNullParticipant()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    eventList.removeParticipant("Title", null);
    assertFalse(()-> eventList.removeParticipant("Title", null));
  }
  @Test void removeParticipantWhenThereIsOne()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    User user = new User("Leo", "ThaMan", "666");
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    eventList.addParticipant("Title", user);
    assertTrue(()-> eventList.removeParticipant("Title", user));
  }
  @Test void removeMultipleParticipants()
  {
    Moderator moderator = new Moderator("Mod", "DispName", "1234");
    ArrayList<User> users = new ArrayList<>();
    for (int i = 0; i < 8; i++){
      users.add(new User("Leo" + i, "ThaMan", "666"));
    }
    Event event = new Event("Title","Tekken", 0, 1000, 8, "2024-07-05", 8, moderator);
    eventList.addEvent(event);
    for (int i = 0; i < 8; i++){
      eventList.addParticipant("Title", users.get(i));
    }
    User user1 = users.get(2);
    User user2 = users.get(3);
    User user3 = users.get(5);
    eventList.removeParticipant("Title", user1);
    eventList.removeParticipant("Title", user2);
    eventList.removeParticipant("Title", user3);
    users.remove(user1);
    users.remove(user2);
    users.remove(user3);
    assertEquals(users, event.getParticipants());
  }


}