import java.io.IOException;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

/**
 * A mock model used for testing the controller.
 */

public class MockModel implements SchedulingSystem {
  private final Appendable log;

  public MockModel(Appendable log) {
    this.log = log;
  }

  @Override
  public List<String> allUsers() {
    return null;
  }

  @Override
  public List<ReadableEvent> eventsInSchedule(String user) {
    return null;
  }

  @Override
  public ReadableEvent eventAt(String user, DaysOfTheWeek startDay, int startTime) {
    return null;
  }

  @Override
  public void addUser(String user) {
    try {
      this.log.append(user);
    } catch (IOException ignored) {
    }
  }

  @Override
  public void removeUser(String user) {
    //not currently used for testing
  }

  @Override
  public void addEvent(String user, List<String> invitees, String eventName,
                       String location, boolean isOnline, DaysOfTheWeek startDay,
                       int startTime, DaysOfTheWeek endDay, int endTime) {
    //not currently used for testing
  }

  @Override
  public void removeEvent(String user, DaysOfTheWeek startDay, int startTime) {
    //not currently used for testing
  }

  @Override
  public void modifyEvent(String user, DaysOfTheWeek startDay, int startTime, Event modEvent) {
    //not currently used for testing
  }

  @Override
  public boolean eventConflict(String host, List<String> invitees, String eventName,
                               String location, boolean isOnline, DaysOfTheWeek startDay,
                               int startTime, DaysOfTheWeek endDay, int endTime) {
    return false;
  }
}
