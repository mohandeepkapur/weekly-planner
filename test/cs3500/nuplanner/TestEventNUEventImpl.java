package cs3500.nuplanner;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.FRIDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SATURDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SUNDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.WEDNESDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * A test class that checks functionality of the NUEvent.
 */
public class TestEventNUEventImpl {

  @Test
  public void checkEventStatesValidConstruction() {

    Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            WEDNESDAY, 1000, WEDNESDAY, 1100);

    // comparing elements within Lists
    assertEquals(new ArrayList<>(List.of("Mo", "Ko", "Jo")), event1.eventInvitees());
    assertEquals("Tennis", event1.name());
    assertEquals("Krentzman Quad", event1.location());
    assertTrue(event1.isOnline());
    assertEquals(WEDNESDAY, event1.startDay());
    assertEquals(1000, event1.startTime());
    assertEquals(WEDNESDAY, event1.endDay());
    assertEquals(1100, event1.endTime());
    assertEquals("Mo", event1.host());
  }

  @Test
  public void checkEventCanCreateMultipleEvents() {

    Event event1 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            WEDNESDAY, 1000, WEDNESDAY, 1100);

    Event event2 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Basketball", "Krentzman Quad", true,
            WEDNESDAY, 1100, WEDNESDAY, 1200);

    Event event3 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Baseball", "Krentzman Quad", true,
            WEDNESDAY, 1200, WEDNESDAY, 1300);

    assertEquals("Mo", event1.host());
  }

  @Test
  public void checkEventValidTimeSpanCanGoToSecondWeek() {
    Event twoWeekEvent = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Soccer", "Carter Field", false,
            WEDNESDAY, 1000, SUNDAY, 1200);
    assertEquals(SUNDAY, twoWeekEvent.endDay());
  }

  @Test
  public void checkEventValidTimeSpanLongestPossibleEvent() {
    Event longEvent = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Soccer", "Carter Field", false,
            WEDNESDAY, 0, DaysOfTheWeek.TUESDAY, 2359);
    assertEquals(TUESDAY, longEvent.endDay());
  }

  @Test
  public void checkEventInvalidTimeSpanLongerThanOneWeek() {
    Event longEvent = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
            "Soccer", "Carter Field", false,
            WEDNESDAY, 1200, WEDNESDAY, 1201);
    assertEquals(WEDNESDAY, longEvent.endDay());
  }

  @Test
  public void checkEventInvalidStartMinutes() {

    try {
      Event event1 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              WEDNESDAY, 962, WEDNESDAY, 1100);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void checkEventInvalidEndMinutes() {
    try {
      Event event1 = new NUEvent(new ArrayList<>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              WEDNESDAY, 1000, WEDNESDAY, 1192);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void checkEventIllegalStartHours() {
    try {
      Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              WEDNESDAY, 3612, WEDNESDAY, 1100);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void checkEventIllegalEndHours() {
    try {
      Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              WEDNESDAY, 1000, WEDNESDAY, 4812);
      Assert.fail("Should not be able to construct event with invalid military time... ");
    } catch (IllegalArgumentException ignore) {
      //do nothing
    }
  }

  @Test
  public void checkEventSameStartAndEndTime() {
    try {
      Event event1 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
              "Tennis", "Krentzman Quad", true,
              WEDNESDAY, 1100, WEDNESDAY, 1100);
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
              WEDNESDAY, 1100, WEDNESDAY, 1200);
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
              WEDNESDAY, 1100, WEDNESDAY, 1200);
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
              null, 1100, WEDNESDAY, 1200);
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
              WEDNESDAY, 1100, null, 1200);
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
              WEDNESDAY, 1100, null, 1200);
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
              WEDNESDAY, 1100, null, 1200);
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
    assertThrows(IllegalArgumentException.class, () -> event.updateStartDay(null));
  }

  @Test
  public void testInValidEventModificationUpdateStartDayTooLong() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            FRIDAY, 1200, FRIDAY, 1159);
    assertEquals(FRIDAY, event.startDay());
    event.updateStartDay(SATURDAY);
    assertEquals(SATURDAY, event.startDay());
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
  public void testInValidEventModificationUpdateEndDayNull() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    assertEquals(MONDAY, event.endDay());
    assertThrows(IllegalArgumentException.class, () -> event.updateEndDay(null));
  }

  @Test
  public void testInValidEventModificationUpdateEndDayTooLong() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            FRIDAY, 1200, FRIDAY, 1159);
    assertEquals(FRIDAY, event.endDay());
    event.updateEndDay(SATURDAY);
    assertEquals(SATURDAY, event.endDay());
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
  public void testValidEventModificationUpdateEndTimeToNextWeek() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1130);
    assertEquals(1100, event.startTime());
    event.updateEndTime(1000);
    assertEquals(1100, event.startTime());
    assertEquals(1000, event.endTime());
  }

  @Test
  public void testValidEventModificationRemoveInvitee() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    List<String> original = new ArrayList<>(List.of("John", "Ella"));
    assertEquals(original, event.eventInvitees());
    event.removeInvitee("Ella");
    List<String> updated = new ArrayList<>(List.of("John"));
    assertEquals(updated, event.eventInvitees());
  }

  @Test
  public void testValidEventModificationRemoveHost() {
    Event event = new NUEvent(new ArrayList<>(List.of("John", "Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    List<String> original = new ArrayList<>(List.of("John", "Ella"));
    assertEquals(original, event.eventInvitees());
    event.removeInvitee("John");
    List<String> updated = new ArrayList<>(List.of());
    assertEquals(updated, event.eventInvitees());
  }

  @Test
  public void testInvalidEventModificationRemoveInvitee() { //TODO: IDK if this should pass
    Event event = new NUEvent(new ArrayList<>(List.of("Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    List<String> original = new ArrayList<>(List.of("Ella"));
    assertEquals(original, event.eventInvitees());
    event.removeInvitee("Ella");
    List<String> updated = new ArrayList<>(List.of());
    assertEquals(updated, event.eventInvitees());
  }

  @Test
  public void testInvalidEventModificationRemoveInviteeDoesntExist() {
    Event event = new NUEvent(new ArrayList<>(List.of("Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    List<String> original = new ArrayList<>(List.of("Ella"));
    assertEquals(original, event.eventInvitees());
    assertThrows(IllegalArgumentException.class, () -> event.removeInvitee("Abby"));
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

  @Test
  public void testInvalidEventModificationAddInviteeHostAlreadyExists() {
    Event event = new NUEvent(new ArrayList<>(List.of("Ella")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    List<String> original = new ArrayList<>(List.of("Ella"));
    assertEquals(original, event.eventInvitees());
    assertThrows(IllegalArgumentException.class, () -> event.addInvitee("Ella"));
  }

  @Test
  public void testInvalidEventModificationAddInviteeAlreadyExists() {
    Event event = new NUEvent(new ArrayList<>(List.of("Ella", "Caesar")),
            "Soccer", "Carter Field", false,
            MONDAY, 1100, MONDAY, 1200);
    List<String> original = new ArrayList<>(List.of("Ella", "Caesar"));
    assertEquals(original, event.eventInvitees());
    assertThrows(IllegalArgumentException.class, () -> event.addInvitee("Caesar"));
  }
}