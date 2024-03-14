package cs3500.nuplanner;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestEvent {

  @Test
  public void checkEventStatesValidConstruction() {

    Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    // comparing elements within Lists
    assertEquals(new ArrayList<String>(List.of("Mo", "Ko", "Jo")), event1.eventInvitees());
    assertEquals("Tennis", event1.name());
    assertEquals("Krentzman Quad", event1.location());
    assertTrue(event1.isOnline());
    assertEquals(DaysOfTheWeek.WEDNESDAY, event1.startDay());
    assertEquals(1000, event1.startTime());
    assertEquals(DaysOfTheWeek.WEDNESDAY, event1.endDay());
    assertEquals(1100, event1.endTime());

  }

  @Test
  public void checkEventInvalidConstructionTime() {

    try {
      Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              DaysOfTheWeek.WEDNESDAY, 962, DaysOfTheWeek.WEDNESDAY, 1100);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }

    try {
      Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1192);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }

    try {
      Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              DaysOfTheWeek.WEDNESDAY, 3612, DaysOfTheWeek.WEDNESDAY, 1100);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }

    try {
      Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 4812);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }

  }

  @Test
  public void testValidTimeSpanEvent() {

  }

  @Test
  public void testInvalidTimeSpanEvent() {

    try {
      Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              DaysOfTheWeek.WEDNESDAY, 1100, DaysOfTheWeek.WEDNESDAY, 1100);
      Assert.fail("Should not be able to construct event with invalid time span... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }

  }

  @Test
  public void testMultipleEvents() {

    Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    Event event2 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Basketball", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1100, DaysOfTheWeek.WEDNESDAY, 1200);

    Event event3 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Baseball", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1200, DaysOfTheWeek.WEDNESDAY, 1300);

  }

  @Test
  public void testInvalidModificationRejectedEvent() {
    // invalid time-span now
  }

  @Test
  public void testValidModificationPassedEvent() {
    // valid time span

  }

  @Test
  public void testInvalidConstructorInputs() {

  }

}

/*
  separating view from model
  separating view from controller

 command callback design --> separated actual command from how it is carried out

 controller cares about echo


 c -> only takes in user inputs, delegates to model and view

 features concept --> echo text, exit program, capitalize text, make it lowercase, toggle highlighting --> all Features

 review of ccd ^

 going into design from pre-spring-break friday in more detail

 Features is a thing outside of view

 Controller and View interface with each other through Features

 Problem: I want a GUI that reacts to user input (clicking a button, entering text, key commands,
 changing something on a list, etc.)
 AND I want to keep my controller and view separate.
 Solution: Command Callback Design
 1. Determine what reactions/features you want. Put those in an interface.
 2. Add an addFeatures(Features) method to the view and any related classes/interfaces.
 3. In each class, implement the addFeatures method to use the features when some UI element is used.
 4. Implement the Features interface SOMEWHERE in the controller component (can be the controller itself).
 5. Have the controller call addFeatures with the features before the view is made visible.


 view --> frames --> panels

 key listener has anonymous class vs method bc need to override/implement three methods instead of one

 action listener --> listens for action events --> like clicking a button,

 ActionEvent --> something happened with the UI element (but only some of them) <- buttons can be clicked
 KeyEvent --> you typed a thing in the UI element <- "focus" concept
 ItemEvent --> you messed with an item in a UI list

 */
