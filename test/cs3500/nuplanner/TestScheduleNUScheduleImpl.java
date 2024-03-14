package cs3500.nuplanner;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.NUSchedule;
import cs3500.nuplanner.model.hw05.Schedule;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SUNDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.WEDNESDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the functionality of the schedule.
 */

public class TestScheduleNUScheduleImpl {

  @Test
  public void testValidAddTwoEventsNoConflict() {
    Schedule schedule = new NUSchedule("Elaine");
    Event soccer = new NUEvent(new ArrayList<>(List.of("Elaine", "Mia")),
            "Soccer Practice", "Carter Field", false,
            MONDAY, 1400, MONDAY, 1500);
    Event broomball = new NUEvent(new ArrayList<>(List.of("Elaine", "Mia")),
            "Broomball Game", "Carter Field", false,
            MONDAY, 2000, MONDAY, 2100);
    schedule.addEvent(soccer);
    assertFalse(schedule.eventConflict(broomball));
    schedule.addEvent(broomball);
  }

  @Test
  public void testInvalidAddTwoEventsWithConflict() {
    Schedule schedule = new NUSchedule("Elaine");
    Event soccer = new NUEvent(new ArrayList<>(List.of("Elaine", "Mia")),
            "Soccer Practice", "Carter Field", false,
            MONDAY, 1400, MONDAY, 1500);
    Event broomball = new NUEvent(new ArrayList<>(List.of("Elaine", "Mia")),
            "Broomball Game", "Carter Field", false,
            MONDAY, 1430, MONDAY, 1530);
    schedule.addEvent(soccer);
    assertTrue(schedule.eventConflict(broomball));
    assertThrows(IllegalArgumentException.class, () -> schedule.addEvent(broomball));
  }

  @Test
  public void testValidRemoveOneEvent() {
    Schedule schedule = new NUSchedule("Elaine");
    Event soccer = new NUEvent(new ArrayList<>(List.of("Elaine", "Mia")),
            "Soccer Practice", "Carter Field", false,
            MONDAY, 1400, MONDAY, 1500);
    schedule.addEvent(soccer);
    assertEquals(1, schedule.numberOfEvents());
    schedule.removeEvent(soccer);
    assertEquals(0, schedule.numberOfEvents());
  }

  @Test
  public void testInvalidRemoveEventWhenThereAreNone() {
    Schedule schedule = new NUSchedule("Elaine");
    Event soccer = new NUEvent(new ArrayList<>(List.of("Elaine", "Mia")),
            "Soccer Practice", "Carter Field", false,
            MONDAY, 1400, MONDAY, 1500);
    assertEquals(0, schedule.numberOfEvents());
    assertThrows(IllegalArgumentException.class, () -> schedule.removeEvent(soccer));
  }

  @Test
  public void testValidEventAt() {
    Schedule schedule = new NUSchedule("Elaine");
    Event soccer = new NUEvent(new ArrayList<>(List.of("Elaine", "Mia")),
            "Soccer Practice", "Carter Field", false,
            MONDAY, 1400, MONDAY, 1500);
    schedule.addEvent(soccer);
    assertEquals(soccer, schedule.eventAt(MONDAY, 1400));
  }

  @Test
  public void testInvalidEventAt() {
    Schedule schedule = new NUSchedule("Elaine");
    Event soccer = new NUEvent(new ArrayList<>(List.of("Elaine", "Mia")),
            "Soccer Practice", "Carter Field", false,
            MONDAY, 1400, MONDAY, 1500);
    schedule.addEvent(soccer);
    assertThrows(IllegalArgumentException.class, () ->
            assertEquals(soccer, schedule.eventAt(TUESDAY, 1900)));
  }

  @Test
  public void testValidEventListTwoEvents() {
    Schedule schedule = new NUSchedule("Elaine");
    Event soccer = new NUEvent(new ArrayList<>(List.of("Elaine", "Mia")),
            "Soccer Practice", "Carter Field", false,
            MONDAY, 1400, MONDAY, 1500);
    Event broomball = new NUEvent(new ArrayList<>(List.of("Elaine", "Mia")),
            "Broomball Game", "Carter Field", false,
            MONDAY, 2000, MONDAY, 2100);
    schedule.addEvent(soccer);
    schedule.addEvent(broomball);
    List<Event> events = new ArrayList<>(List.of(soccer, broomball));
    assertEquals(events, schedule.events());
  }

  @Test
  public void testValidEventListNoEvents() {
    Schedule schedule = new NUSchedule("Elaine");
    List<Event> events = new ArrayList<>();
    assertEquals(events, schedule.events());
  }
}