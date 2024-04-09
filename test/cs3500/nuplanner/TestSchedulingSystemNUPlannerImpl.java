package cs3500.nuplanner;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.FRIDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SUNDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.THURSDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.WEDNESDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the functionality of the overarching scheduling system.
 */

public class TestSchedulingSystemNUPlannerImpl {

  @Test
  public void testValidAddingNewUser() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addUser("Mia");
    List<String> users = new ArrayList<>(List.of("Mia", "Elaine"));
    assertEquals(users, model.allUsers());
  }

  @Test
  public void testInvalidAddingNewUser() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addUser("Mia");
    List<String> users = new ArrayList<>(List.of("Mia", "Elaine"));
    assertEquals(users, model.allUsers());
    assertThrows(IllegalArgumentException.class, () -> model.addUser("Mia"));
  }

  @Test
  public void testValidRemovingUser() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addUser("Mia");
    model.removeUser("Elaine");
    List<String> users = new ArrayList<>(List.of("Mia"));
    assertEquals(users, model.allUsers());
  }

  @Test
  public void testInvalidRemovingUser() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addUser("Mia");
    List<String> users = new ArrayList<>(List.of("Mia", "Elaine"));
    assertEquals(users, model.allUsers());
    assertThrows(IllegalArgumentException.class, () -> model.removeUser("Christina"));
  }

  @Test
  public void testValidAddingEventThatGoesToSecondWeek() {
    SchedulingSystem model = new NUPlannerModel();
    List<String> users = new ArrayList<>(List.of("Elaine", "Mia"));
    model.addUser("Elaine");
    model.addUser("Mia");

    model.addEvent("Elaine", users, "Tennis", "Carter Field", true,
            WEDNESDAY, 800, SUNDAY, 1000);
    assertEquals(new ArrayList<String>(List.of("Mia", "Elaine")), model.allUsers());
  }

  @Test
  public void testValidAddingTwoEventsNoConflict() {
    SchedulingSystem model = new NUPlannerModel();
    List<String> users = new ArrayList<>(List.of("Elaine", "Mia"));
    model.addUser("Elaine");
    model.addUser("Mia");

    model.addEvent("Elaine", users, "Tennis", "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertFalse(model.eventConflict("Elaine", users, "Badminton", "Marino Recreation", true,
            TUESDAY, 1400, TUESDAY, 1600));
    model.addEvent("Elaine", users, "Badminton", "Marino Recreation", true,
            TUESDAY, 1400, TUESDAY, 1600);
  }

  @Test
  public void testValidAddingTwoEventsNextToAnotherNoConflict() {
    SchedulingSystem model = new NUPlannerModel();
    List<String> users = new ArrayList<>(List.of("Elaine", "Mia"));
    model.addUser("Elaine");
    model.addUser("Mia");

    model.addEvent("Elaine", users, "Tennis", "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertFalse(model.eventConflict("Elaine", users, "Badminton", "Marino Recreation", true,
            TUESDAY, 1000, TUESDAY, 1200));
    model.addEvent("Elaine", users, "Badminton", "Marino Recreation", true,
            TUESDAY, 1000, TUESDAY, 1200);
  }

  @Test
  public void testValidAddingEventWithInviteeNotInSystem() {
    SchedulingSystem model = new NUPlannerModel();
    List<String> users = new ArrayList<>(List.of("Elaine", "Mia"));
    model.addUser("Elaine");

    model.addEvent("Elaine", users, "Tennis", "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertEquals(new ArrayList<String>(List.of("Mia", "Elaine")), model.allUsers());
  }

  @Test
  public void testInvalidAddingEventWithHostNotInSystem() {
    SchedulingSystem model = new NUPlannerModel();
    List<String> users = new ArrayList<>(List.of("Elaine", "Mia"));

    assertThrows(IllegalArgumentException.class, () -> model.addEvent("Elaine", users,
            "Tennis", "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000));
  }

  @Test
  public void testInvalidAddingTwoEventsWithConflict() {
    SchedulingSystem model = new NUPlannerModel();
    List<String> users = new ArrayList<>(List.of("Elaine", "Mia"));
    model.addUser("Elaine");
    model.addUser("Mia");

    model.addEvent("Elaine", users, "Tennis", "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1200);
    assertTrue(model.eventConflict("Elaine", users, "Badminton", "Marino Recreation", true,
            TUESDAY, 1000, TUESDAY, 1200));
    assertThrows(IllegalArgumentException.class, () -> model.addEvent("Elaine", users, "Badminton",
            "Marino Recreation", true,
            TUESDAY, 1000, TUESDAY, 1200));
  }

  @Test
  public void testValidRemoveInviteeFromEvent() {
    SchedulingSystem model = new NUPlannerModel();
    List<String> users = new ArrayList<>(List.of("Elaine", "Mia"));
    List<String> onlyElaine = new ArrayList<>(List.of("Elaine"));
    List<String> onlyMia = new ArrayList<>(List.of("Mia"));
    model.addUser("Elaine");
    model.addUser("Mia");

    model.addEvent("Elaine", users, "Badminton",
            "Marino Recreation", true,
            TUESDAY, 1000, TUESDAY, 1200);
    model.removeEvent("Mia", TUESDAY, 1000);
    assertFalse(model.eventConflict("Mia", onlyMia, "Shower",
            "Loftman Hall", false, TUESDAY, 1000, TUESDAY, 1030));
    assertTrue(model.eventConflict("Elaine", onlyElaine, "Shower",
            "Loftman Hall", false, TUESDAY, 1000, TUESDAY, 1030));
  }

  @Test
  public void testValidRemoveHostFromEvent() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.removeEvent("Elaine", TUESDAY, 800);
    assertFalse(model.eventConflict("Elaine", new ArrayList<>(List.of("Elaine", "Mia")),
            "Shower", "Loftman Hall", false, TUESDAY,
            800, TUESDAY, 830));
  }

  @Test
  public void testInvalidRemoveUserDoesntExist() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.removeEvent("Leia", TUESDAY, 800));
  }

  @Test
  public void testValidMultipleModifications() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);

    eventToMod.updateName("Broomball");
    eventToMod.updateLocation("Matthews Arena");
    eventToMod.updateIsOnline(false);
    eventToMod.updateStartTime(2000);
    eventToMod.updateEndTime(2100);
    eventToMod.updateEndDay(THURSDAY);
    eventToMod.updateStartDay(THURSDAY);
    eventToMod.addInvitee("Brandon");
    eventToMod.removeInvitee("Mia");

    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);

    Event eventAfterMod = (Event) model.eventAt("Brandon", THURSDAY, 2000);

    assertEquals("Broomball", eventAfterMod.name());
    assertEquals("Matthews Arena", eventAfterMod.location());
    assertFalse(eventAfterMod.isOnline());
    assertEquals(2000, eventAfterMod.startTime());
    assertEquals(2100, eventAfterMod.endTime());
    assertEquals(THURSDAY, eventAfterMod.endDay());
    assertEquals(THURSDAY, eventAfterMod.startDay());
    assertEquals(new ArrayList<>(List.of("Elaine", "Brandon")), eventAfterMod.eventInvitees());
  }

  @Test
  public void testValidModifyEventName() {
    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Elaine");

    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);

    // extract Elaine event from model (model will give me defensive copy)
    Event eventToMod = (Event) model.eventAt("Mia", TUESDAY, 800);
    // modify defensive copy in line with string modification in test "name Baseball"
    eventToMod.updateName("Baseball");
    // try to "modify" relevant event in model
    // Mia could also request
    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);

    assertEquals("Baseball", model.eventAt("Elaine", TUESDAY, 800).name());
  }

  @Test
  public void testValidModifyEventLocation() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.updateLocation("Squashbusters");
    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);
    assertEquals("Squashbusters", model.eventAt("Elaine", TUESDAY, 800).location());
  }

  @Test
  public void testValidModifyEventOnline() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.updateIsOnline(false);
    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);
    assertFalse(model.eventAt("Elaine", TUESDAY, 800).isOnline());
  }

  @Test
  public void testValidModifyEventStartTime() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.updateStartTime(730);
    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);
    assertEquals(730, model.eventAt("Elaine", TUESDAY, 730).startTime());
  }

  @Test
  public void testInvalidModifyEventWrongStartTime() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    assertThrows(IllegalArgumentException.class, () -> eventToMod.updateStartTime(819465));
  }

  //TODO: Modification provided is actually valid. Needs to be fixed.
  @Test
  public void testInvalidModifyEventStartTimeAfter() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.updateStartTime(1030);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, eventToMod));
  }

  @Test
  public void testValidModifyEventEndTime() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.updateEndTime(1030);
    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);
    assertEquals(1030, model.eventAt("Elaine", TUESDAY, 800).endTime());
  }

  @Test
  public void testInvalidModifyEventWrongEndTime() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    assertThrows(IllegalArgumentException.class, () -> eventToMod.updateEndTime(819465));
  }

  //TODO: Modification provided is actually valid. Needs to be fixed.
  @Test
  public void testInvalidModifyEventEndTimeBefore() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.updateEndTime(730);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, eventToMod));
    //doesn't throw an exception, but it looks like it rejects it anyway
  }

  @Test
  public void testValidModifyEventStartDay() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.updateStartDay(MONDAY);
    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);
    assertEquals(MONDAY, model.eventAt("Elaine", MONDAY, 800).startDay());
  }

  //TODO: Modification provided is actually valid. Needs to be fixed.
  @Test
  public void testInvalidModifyEventStartDayAfter() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.updateStartDay(FRIDAY);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, eventToMod));
  }

  @Test
  public void testValidModifyEventEndDay() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.updateEndDay(WEDNESDAY);
    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);
    assertEquals(WEDNESDAY, model.eventAt("Elaine", TUESDAY, 800).endDay());
  }

  //TODO: Modification provided is actually valid. Needs to be fixed.
  @Test
  public void testInvalidModifyEventEndDayBefore() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.updateEndDay(MONDAY);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, eventToMod));
  }

  @Test
  public void testValidModifyEventAddInviteeInSystem() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addUser("Lisa");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.addInvitee("Lisa");
    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);
    assertEquals(new ArrayList<>(List.of("Elaine", "Mia", "Lisa")),
            model.eventAt("Elaine", TUESDAY, 800).eventInvitees());
  }

  @Test
  public void testValidModifyEventAddInviteeNotInSystem() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.addInvitee("Lisa");
    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);
    assertEquals(new ArrayList<>(List.of("Elaine", "Mia", "Lisa")),
            model.eventAt("Elaine", TUESDAY, 800).eventInvitees());
  }

  @Test
  public void testInvalidModifyEventInviteeAlreadyInEvent() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    assertThrows(IllegalArgumentException.class, () -> eventToMod.addInvitee("Mia"));
  }

  @Test
  public void testValidModifyEventRemoveInvitee() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.removeInvitee("Mia");
    model.modifyEvent("Elaine", TUESDAY, 800, eventToMod);
    assertEquals(new ArrayList<>(List.of("Elaine")),
            model.eventAt("Elaine", TUESDAY, 800).eventInvitees());
  }

  @Test
  public void testValidModifyEventRemoveHost() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    Event eventToMod = (Event) model.eventAt("Elaine", TUESDAY, 800);
    eventToMod.removeInvitee("Elaine");
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, eventToMod));
  }

  @Test
  public void testValidEventsInSchedule() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertEquals("Tennis", model.eventsInSchedule("Elaine").get(0).name());
    assertEquals("Carter Field", model.eventsInSchedule("Elaine").get(0).location());
    assertEquals(TUESDAY, model.eventsInSchedule("Elaine").get(0).startDay());
    assertEquals(800, model.eventsInSchedule("Elaine").get(0).startTime());
    assertThrows(IndexOutOfBoundsException.class, () ->
            model.eventsInSchedule("Elaine").get(1));
  }

  @Test
  public void testValidEventsInScheduleNoEvents() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    assertThrows(IndexOutOfBoundsException.class, () ->
            model.eventsInSchedule("Elaine").get(0));
  }

  @Test
  public void testValidEventsInScheduleMultipleEvents() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    List<String> users = new ArrayList<>(List.of("Elaine", "Mia"));
    model.addEvent("Elaine", users, "Tennis", "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.addEvent("Elaine", users, "Badminton", "Marino Recreation", true,
            TUESDAY, 1000, TUESDAY, 1200);
    assertEquals("Tennis", model.eventsInSchedule("Elaine").get(0).name());
    assertEquals(800, model.eventsInSchedule("Elaine").get(0).startTime());
    assertEquals("Badminton", model.eventsInSchedule("Elaine").get(1).name());
    assertEquals(1000, model.eventsInSchedule("Elaine").get(1).startTime());
    assertThrows(IndexOutOfBoundsException.class, () ->
            model.eventsInSchedule("Elaine").get(2));
  }

  @Test
  public void testValidEventAt() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertEquals("Tennis", model.eventAt("Elaine", TUESDAY, 800).name());
    assertEquals("Carter Field", model.eventAt("Elaine", TUESDAY, 800).location());
  }
}
