package cs3500.nuplanner;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SUNDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * A test class that checks functionality of the event class.
 */
public class TestEvent {

  @Test
  public void checkEventValidUsingGetters() {

    Event event1 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    // comparing elements within Lists
    assertEquals(new ArrayList<>(List.of("Mo", "Ko", "Jo")), event1.eventInvitees());
    assertEquals("Tennis", event1.name());
    assertEquals("Krentzman Quad", event1.location());
    assertTrue(event1.isOnline());
    assertEquals(DaysOfTheWeek.WEDNESDAY, event1.startDay());
    assertEquals(1000, event1.startTime());
    assertEquals(DaysOfTheWeek.WEDNESDAY, event1.endDay());
    assertEquals(1100, event1.endTime());
    assertEquals("Mo", event1.host());
  }

  @Test
  public void checkEventCanCreateMultipleEvents() {

    Event event1 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    Event event2 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Basketball", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1100, DaysOfTheWeek.WEDNESDAY, 1200);

    Event event3 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Baseball", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1200, DaysOfTheWeek.WEDNESDAY, 1300);
  }

  @Test
  public void checkEventValidTimeSpanLongestPossibleEvent() {
    Event longEvent = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Soccer", "Carter Field", false,
            DaysOfTheWeek.WEDNESDAY, 0, DaysOfTheWeek.TUESDAY, 2359);
  }

  @Test
  public void checkEventInvalidTimeSpanLongerThanOneWeek() { //TODO: How to test longer than one week?
    Event longEvent = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Soccer", "Carter Field", false,
            DaysOfTheWeek.WEDNESDAY, 1200, DaysOfTheWeek.WEDNESDAY, 1201);
  }

  @Test
  public void checkEventInvalidTimeSpanIllegalStartMinutes() {

    try {
      Event event1 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              DaysOfTheWeek.WEDNESDAY, 962, DaysOfTheWeek.WEDNESDAY, 1100);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void checkEventInvalidTimeSpanIllegalEndMinutes() {
    try {
      Event event1 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1192);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void checkEventInvalidTimeSpanIllegalStartHours() {
    try {
      Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              DaysOfTheWeek.WEDNESDAY, 3612, DaysOfTheWeek.WEDNESDAY, 1100);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void checkEventInvalidTimeSpanIllegalEndHours() {
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
  public void checkEventInvalidTimeSpanSameStartAndEndTime() {
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
  public void testInvalidConstructorInputsNameIsNull() {
    try {
      Event invalidEvent = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
              null, "Carter Field", false,
              DaysOfTheWeek.WEDNESDAY, 1100, DaysOfTheWeek.WEDNESDAY, 1200);
      Assert.fail("Event cannot be init. with null argument(s)... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void testInvalidConstructorInputsLocationIsNull() {
    try {
      Event invalidEvent = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
              "Soccer", null, false,
              DaysOfTheWeek.WEDNESDAY, 1100, DaysOfTheWeek.WEDNESDAY, 1200);
      Assert.fail("Event cannot be init. with null argument(s)... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void testInvalidConstructorInputsStartDayIsNull() {
    try {
      Event invalidEvent = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
              "Soccer", "Carter Field", false,
              null, 1100, DaysOfTheWeek.WEDNESDAY, 1200);
      Assert.fail("Event cannot be init. with null argument(s)... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void testInvalidConstructorInputsEndDayIsNull() {
    try {
      Event invalidEvent = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
              "Soccer", "Carter Field", false,
              DaysOfTheWeek.WEDNESDAY, 1100, null, 1200);
      Assert.fail("Event cannot be init. with null argument(s)... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void testInvalidConstructorInputsInviteesIsNull() {
    try {
      Event invalidEvent = new NUEvent(null,
              "Soccer", "Carter Field", false,
              DaysOfTheWeek.WEDNESDAY, 1100, null, 1200);
      Assert.fail("Event cannot be init. with null argument(s)... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void testInvalidConstructorInputsEmptyInviteeList() {
    try {
      Event invalidEvent = new NUEvent(new ArrayList<>(List.of()),
              "Soccer", "Carter Field", false,
              DaysOfTheWeek.WEDNESDAY, 1100, null, 1200);
      Assert.fail("Event cannot be init. with no invitees... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void testValidEventModificationUpdateName() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals("Soccer", event.name());
    event.updateName("Hockey");
    assertEquals("Hockey", event.name());
  }

  @Test
  public void testInValidEventModificationUpdateName() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals("Soccer", event.name());
    assertThrows(IllegalArgumentException.class, () -> event.updateName(null));
  }

  @Test
  public void testValidEventModificationUpdateLocation() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals("Carter Field", event.location());
    event.updateLocation("Marino");
    assertEquals("Marino", event.location());
  }

  @Test
  public void testInValidEventModificationUpdateLocation() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals("Carter Field", event.location());
    assertThrows(IllegalArgumentException.class, () -> event.updateLocation(null));
  }

  @Test
  public void testValidEventModificationUpdateOnline() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Gaming", "Loftman Hall", false,
            MONDAY, 1100, MONDAY, 1200);
    assertFalse(event.isOnline());
    event.updateIsOnline(true);
    assertTrue(event.isOnline());
  }

  @Test
  public void testInValidEventModificationUpdateOnline() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals(false, event.isOnline());
    //assertThrows(IllegalArgumentException.class, () -> event.updateIsOnline(null));
    //Cannot insert null
  }

  @Test
  public void testValidEventModificationUpdateStartDay() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals(MONDAY, event.startDay());
    event.updateStartDay(SUNDAY);
    assertEquals(SUNDAY, event.startDay());
  }

  @Test
  public void testInValidEventModificationUpdateStartDayNull() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals(MONDAY, event.startDay());
    assertThrows(NullPointerException.class, () -> event.updateStartDay(null));
  }

  @Test
  public void testInValidEventModificationUpdateStartDayTooLong() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, TUESDAY, 1200); //TODO: Should be the next week
    assertEquals(MONDAY, event.startDay());
    assertThrows(NullPointerException.class, () -> event.updateStartDay(null));
  }

  @Test
  public void testValidEventModificationUpdateEndDay() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals(MONDAY, event.endDay());
    event.updateEndDay(TUESDAY);
    assertEquals(TUESDAY, event.endDay());
  }

  @Test
  public void testValidEventModificationUpdateStartTime() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals(1100, event.startTime());
    event.updateStartTime(1130);
    assertEquals(1130, event.startTime());
  }

  @Test
  public void testValidEventModificationUpdateEndTime() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals(1200, event.endTime());
    event.updateEndTime(1230);
    assertEquals(1230, event.endTime());
  }

  @Test
  public void testValidEventModificationRemoveInvitee() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    List<String> original = new ArrayList<>(List.of("John", "Ella"));
    assertEquals(original, event.eventInvitees());
    event.removeInvitee("John");
    List<String> updated = new ArrayList<>(List.of("Ella"));
    assertEquals(updated, event.eventInvitees());
  }

  @Test
  public void testValidEventModificationAddInvitee() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    List<String> original = new ArrayList<>(List.of("John", "Ella"));
    assertEquals(original, event.eventInvitees());
    event.addInvitee("Karina");
    List<String> updated = new ArrayList<>(List.of("John", "Ella", "Karina"));
    assertEquals(updated, event.eventInvitees());
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
