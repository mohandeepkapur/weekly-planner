package cs3500.nuplanner;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SUNDAY;
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
  public void testValidModifyEventName() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.modifyEvent("Elaine", TUESDAY, 800, "name Baseball");
    assertEquals("Baseball", model.eventAt("Elaine", TUESDAY, 800).name());
  }

  @Test
  public void testValidModifyEventLocation() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.modifyEvent("Elaine", TUESDAY, 800, "location Squashbusters");
    assertEquals("Squashbusters", model.eventAt("Elaine", TUESDAY, 800).location());
  }

  @Test
  public void testValidModifyEventOnline() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.modifyEvent("Elaine", TUESDAY, 800, "online false");
    assertFalse(model.eventAt("Elaine", TUESDAY, 800).isOnline());
  }

  @Test
  public void testInvalidModifyEventOnlineProvidedString() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "online Nissan"));
  }

  @Test
  public void testInvalidModifyEventOnlineProvidedNumber() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "online 666"));
  }

  @Test
  public void testValidModifyEventStartTime() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.modifyEvent("Elaine", TUESDAY, 800, "starttime 730");
    assertEquals(730, model.eventAt("Elaine", TUESDAY, 730).startTime());
  }

  @Test
  public void testInvalidModifyEventWrongStartTime() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "starttime 819465"));
  }

  @Test
  public void testInvalidModifyEventStringStartTime() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "starttime Nissan"));
  }

  @Test
  public void testValidModifyEventEndTime() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.modifyEvent("Elaine", TUESDAY, 800, "endtime 1030");
    assertEquals(1030, model.eventAt("Elaine", TUESDAY, 800).endTime());
  }

  @Test
  public void testInvalidModifyEventWrongEndTime() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "endtime 819465"));
  }

  @Test
  public void testInvalidModifyEventStringEndTime() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "endtime Nissan"));
  }

  @Test
  public void testValidModifyEventStartDay() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.modifyEvent("Elaine", TUESDAY, 800, "startday MONDAY");
    assertEquals(MONDAY, model.eventAt("Elaine", MONDAY, 800).startDay());
  }

  @Test
  public void testInvalidModifyEventStringStartDay() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "startday nissan"));
  }

  @Test
  public void testInvalidModifyEventNumberStartDay() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "startday 666"));
  }

  @Test
  public void testValidModifyEventEndDay() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.modifyEvent("Elaine", TUESDAY, 800, "endday WEDNESDAY");
    assertEquals(WEDNESDAY, model.eventAt("Elaine", TUESDAY, 800).endDay());
  }

  @Test
  public void testInvalidModifyEventStringEndDay() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "endday nissan"));
  }

  @Test
  public void testInvalidModifyEventNumberEndDay() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "endday 666"));
  }

  @Test
  public void testValidModifyEventAddInviteeInSystem() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addUser("Lisa");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.modifyEvent("Elaine", TUESDAY, 800, "addinvitee Lisa");
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
    model.modifyEvent("Elaine", TUESDAY, 800, "addinvitee Lisa");
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
    assertThrows(IllegalArgumentException.class, () ->
            model.modifyEvent("Elaine", TUESDAY, 800, "addinvitee Mia"));
  }

  @Test
  public void testValidModifyEventRemoveInvitee() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);
    model.modifyEvent("Elaine", TUESDAY, 800, "removeinvitee Mia");
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
    model.modifyEvent("Elaine", TUESDAY, 800, "removeinvitee Elaine");
    assertThrows(IllegalArgumentException.class, () ->
            model.eventAt("Elaine", TUESDAY, 800));
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
    assertThrows(IndexOutOfBoundsException.class,
            () -> model.eventsInSchedule("Elaine").get(1));
  }

  @Test
  public void testValidEventsInScheduleNoEvents() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elaine");
    assertThrows(IndexOutOfBoundsException.class,
            () -> model.eventsInSchedule("Elaine").get(0));
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
    assertThrows(IndexOutOfBoundsException.class,
            () -> model.eventsInSchedule("Elaine").get(2));
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
