package cs3500.nuplanner;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.NUSchedule;
import cs3500.nuplanner.model.hw05.Schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class TestSchedule {

  @Test
  public void bruh() {

    Event event1 = new NUEvent (new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    Event event2 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Basketball", "Krentzman Quad", true,
            DaysOfTheWeek.TUESDAY, 900, DaysOfTheWeek.TUESDAY, 1000);

    Schedule schedule = new NUSchedule("Mo");

    assertFalse(schedule.eventConflict(event1));

    schedule.addEvent(event1);

    //System.out.print(schedule.eventIDs());

    assertFalse(schedule.eventConflict(event2));

    schedule.addEvent(event2);

    //System.out.print(schedule.eventIDs());

    assertSame(event1, schedule.eventAt(event1.startDay(), event1.startTime()));

  }

  @Test
  public void bruh2() {

    Event event1 = new NUEvent (new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    Event event2 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Basketball", "Krentzman Quad", true,
            DaysOfTheWeek.TUESDAY, 900, DaysOfTheWeek.FRIDAY, 1000);

    Schedule schedule = new NUSchedule("Mo");

    assertFalse(schedule.eventConflict(event1));
    schedule.addEvent(event1);

    assertTrue(schedule.eventConflict(event2));

  }

  @Test
  public void bruh3() { // and am unable to construct event of invalid time span!

    Event event1 = new NUEvent (new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    Event event2 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Sleep", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1100, DaysOfTheWeek.FRIDAY, 1000);

    Event event3 = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Baseball", "Krentzman Quad", true,
            DaysOfTheWeek.FRIDAY, 1030, DaysOfTheWeek.FRIDAY, 1230);

    Schedule schedule = new NUSchedule("Mo");

    schedule.addEvent(event2);
    schedule.addEvent(event3);
    schedule.addEvent(event1);
    //System.out.println(schedule.eventIDs()); // earliest to latest event

    Event incompatibleEvent = new NUEvent(new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Party", "Krentzman Quad", true,
            DaysOfTheWeek.THURSDAY, 2200, DaysOfTheWeek.FRIDAY, 0);

    System.out.print(schedule.eventConflict(incompatibleEvent));
    schedule.addEvent(incompatibleEvent);

  }


}
