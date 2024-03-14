package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * NUEvent only works with military time.
 */
public class NUEvent implements Event {

  //  private static int count = 0;
  //  private final int ID;

  private String name;
  private String location;
  private boolean isOnline;
  private DaysOfTheWeek startDay;
  private int startTime;
  private DaysOfTheWeek endDay;
  private int endTime;
  // first user in invitees is host of event
  private final List<String> invitees;
  // host of an event cannot be changed
  private final String host;

  /**
   * Constructs an Event.
   */
  public NUEvent(List<String> invitees,
                 String eventName, String location, boolean isOnline,
                 DaysOfTheWeek startDay, int startTime,
                 DaysOfTheWeek endDay, int endTime) {

    if (eventName == null
            || location == null
            || invitees == null
            || startDay == null
            || endDay == null) {
      throw new IllegalArgumentException("Event cannot be init. with null argument(s)... ");
    }

    // ensures only proper military time accepted by Event
    ensureProperMilitaryTime(startTime);
    ensureProperMilitaryTime(endTime);

    if (invitees.isEmpty()) {
      throw new IllegalArgumentException("Event cannot be init. with no invitees... ");
    }

    this.invitees = invitees;
    this.name = eventName;
    this.location = location;
    this.isOnline = isOnline;
    this.startDay = startDay;
    this.startTime = startTime;
    this.endDay = endDay;
    this.endTime = endTime;
    this.host = invitees.get(0);

    ensureValidTimeSpan(this.startTime, this.endTime, this.startDay, this.endDay);

    //            // assign unique, immutable ID to every event
    //            ID = count;
    //            count++;
  }

  private void ensureProperMilitaryTime(int time) {
    if (time / 100 < 0 || time / 100 > 23 || time % 100 > 59) {
      throw new IllegalArgumentException("Event cannot be init. with invalid military time...  ");
    }
  }

  // copy constructor
  public NUEvent(Event other) {
    this.invitees = new ArrayList<>(other.eventInvitees());
    this.name = other.name();
    this.location = other.location();
    this.isOnline = other.isOnline();
    this.startDay = other.startDay();
    this.startTime = other.startTime();
    this.endDay = other.endDay();
    this.endTime = other.endTime();
    this.host = this.invitees.get(0);

    ensureValidTimeSpan(this.startTime, this.endTime, this.startDay, this.endDay);

    //            // assign unique, immutable ID to every event
    //            ID = count;
    //            count++;
  }

  @Override
  public String host() {
    return this.host;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public void updateName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Event cannot be constructed with null... ");
    }
    this.name = name;
  }

  @Override
  public String location() {
    return this.location;
  }

  @Override
  public void updateLocation(String location) {
    if (location == null) {
      throw new IllegalArgumentException("Event cannot be constructed with null... ");
    }
    this.location = location;
  }

  @Override
  public boolean isOnline() {
    return this.isOnline;
  }

  @Override
  public void updateIsOnline(boolean isOnline) {
    this.isOnline = isOnline;
  }

  @Override
  public DaysOfTheWeek startDay() {
    return this.startDay;
  }

  @Override
  public void updateStartDay(DaysOfTheWeek startDay) {
    if (startDay == null) {
      throw new IllegalArgumentException("Event cannot be constructed with null... ");
    }
    ensureValidTimeSpan(this.startTime, this.endTime, startDay, this.endDay);
    this.startDay = startDay;
  }

  @Override
  public DaysOfTheWeek endDay() {
    return this.endDay;
  }

  @Override
  public void updateEndDay(DaysOfTheWeek endDay) {
    if (endDay == null) {
      throw new IllegalArgumentException("Event cannot be constructed with null... ");
    }
    ensureValidTimeSpan(this.startTime, this.endTime, this.startDay, endDay);
    this.endDay = endDay;
  }

  @Override
  public int startTime() {
    return this.startTime;
  }

  @Override
  public void updateStartTime(int startTime) {
    ensureProperMilitaryTime(startTime);
    ensureValidTimeSpan(startTime, this.endTime, this.startDay, this.endDay);
    this.startTime = startTime;
  }

  @Override
  public int endTime() {
    return this.endTime;
  }

  @Override
  public void updateEndTime(int endTime) {
    ensureProperMilitaryTime(endTime);
    ensureValidTimeSpan(this.startTime, endTime, this.startDay, this.endDay);
    this.endTime = endTime;
  }

  public List<String> eventInvitees() {
    return new ArrayList<>(invitees);
  }

  @Override
  public void removeInvitee(String invitee) {
    // if invitee not contained within event
    if (invitees.isEmpty()) {
      throw new IllegalArgumentException("Cannot remove invitee from empty invitee list... ");
    }
    if (!invitees.contains(invitee)) {
      throw new IllegalArgumentException("Event does not contain user to remove... ");
    }
    invitees.remove(invitee);
  }

  @Override
  public void addInvitee(String invitee) {

    if (invitees.contains(invitee)) {
      throw new IllegalArgumentException("Invitee already exists in schedule... ");
    }

    //harmless, weird, impl-specific check <- BAD, impl-specific for one impl of sched sys
    if (invitees.isEmpty() && !host.equals(invitee)) {
      throw new IllegalArgumentException("First invitee of event can only be " + host + " ..");
    }

    // if at least one invitee <- add that mf in (one invitee will be host)
    invitees.add(invitee);
  }

  // also check to see that events that exceed one week do not exceed timespan!!
  private void ensureValidTimeSpan(int startTime, int endTime,
                                   DaysOfTheWeek startDay, DaysOfTheWeek endDay)
          throws IllegalArgumentException {

    // Wed 1335 to Thur 1456 -> 24 hours, 1 hour, 21 min -> 1521
    // Wed 1456 to Thur 1335 -> 24 hours, -1 hour, -21 min -> 1359

    int dayDiff = endDay.val() - startDay.val();

    // if this is an event that extends into the next week
    if (dayDiff < 0 || (dayDiff == 0 && endTime <= startTime)) {
      dayDiff = dayDiff + 7;
    }

    int dayRangeMin = dayDiff * 24 * 60;

    int min1 = (startTime % 100) + (startTime / 100) * 60;
    int min2 = (endTime % 100) + (endTime / 100) * 60;

    int timeRangeMin = min2 - min1;

    if (dayRangeMin + timeRangeMin > (6 * 24 * 60) + (23 * 60) + 59) {
      throw new IllegalArgumentException("Event cannot exist with invalid time-span... ");
    }

    // unit: minutes
    int timespan = dayRangeMin + timeRangeMin;
  }

  // in event impl <- can remove/add all invitees of an event <- but first invitee must be host
  // event will only be added into a schedule if it has invitees <- never when it doesn't
}
