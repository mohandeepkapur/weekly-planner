package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of an Event. The smallest unit of time this implementation can handle is MINUTES.
 * NUEvent represents and requires time in military format. This implementation of an Event can only
 * span from 1 minute to 6 days, 23 hours, 59 minutes
 */
public class NUEvent implements Event {

  // first user in invitees is host of event
  private final List<String> invitees;
  // host of an event cannot be changed
  private final String host;

  private String name;
  private String location;
  private boolean isOnline;
  private DaysOfTheWeek startDay;
  private int startTime;
  private DaysOfTheWeek endDay;
  private int endTime;

  /**
   * Constructs an Event.
   *
   * @throws IllegalArgumentException  if any input is null
   * @throws IllegalArgumentException  if times provided are not in military format
   * @throws IllegalArgumentException  if invitee list is empty (always at least one)
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

  }

  private void ensureProperMilitaryTime(int time) {
    if (time / 100 < 0 || time / 100 > 23 || time % 100 > 59) {
      throw new IllegalArgumentException("Event cannot be init. with invalid military time...  ");
    }
  }

  /**
   * Constructs a copy of another Event.
   * @param other Event to be copied
   */
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

  }

  /**
   * Observes host of Event.
   * Event host is fixed/cannot be updated.
   *
   * @return the host of event
   */
  @Override
  public String host() {
    return this.host;
  }

  /**
   * Observes name of Event.
   *
   * @return the name of event
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Updates name of Event.
   *
   * @param name                          new name of Event
   * @throws IllegalArgumentException     if name provided is null
   */
  @Override
  public void updateName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Event cannot be constructed with null... ");
    }
    this.name = name;
  }

  /**
   * Observes location of Event.
   *
   * @return the location of event
   */
  @Override
  public String location() {
    return this.location;
  }

  /**
   * Updates location of Event.
   *
   * @param location                      new location of Event
   * @throws IllegalArgumentException     if name provided is null
   */
  @Override
  public void updateLocation(String location) {
    if (location == null) {
      throw new IllegalArgumentException("Event cannot be constructed with null... ");
    }
    this.location = location;
  }

  /**
   * Observes whether Event is online or offline.
   *
   * @return online/offline status
   */
  @Override
  public boolean isOnline() {
    return this.isOnline;
  }

  /**
   * Updates Event online or offline status.
   *
   * @param isOnline                      new online/offline status of Event
   */
  @Override
  public void updateIsOnline(boolean isOnline) {
    this.isOnline = isOnline;
  }

  /**
   * Observes starting day of the Event.
   *
   * @return starting day of event
   */
  @Override
  public DaysOfTheWeek startDay() {
    return this.startDay;
  }

  /**
   * Updates day the Event starts.
   * The minimum and maximum time an Event can span is enforced by implementation.
   *
   * @param startDay                      new starting day of Event
   * @throws IllegalArgumentException     if time-span of updated Event is invalid
   * @throws IllegalArgumentException     if start day provided is null
   *
   * @implNote                            Event can only span from 1 minute to 6 days, 23 hours,
   *                                      59 minutes
   */
  @Override
  public void updateStartDay(DaysOfTheWeek startDay) {
    if (startDay == null) {
      throw new IllegalArgumentException("Event cannot be constructed with null... ");
    }
    ensureValidTimeSpan(this.startTime, this.endTime, startDay, this.endDay);
    this.startDay = startDay;
  }

  /**
   * Observes ending day of the Event.
   *
   * @return ending day of event
   */
  @Override
  public DaysOfTheWeek endDay() {
    return this.endDay;
  }

  /**
   * Updates day the Event ends.
   * The minimum and maximum time an Event can span is enforced by implementation.
   *
   * @param endDay                        new end day of Event
   * @throws IllegalArgumentException     if time-span of updated Event is invalid
   * @throws IllegalArgumentException     if end day provided is null
   *
   * @implNote                            Event can only span from 1 minute to 6 days, 23 hours,
   *                                      59 minutes
   */
  @Override
  public void updateEndDay(DaysOfTheWeek endDay) {
    if (endDay == null) {
      throw new IllegalArgumentException("Event cannot be constructed with null... ");
    }
    ensureValidTimeSpan(this.startTime, this.endTime, this.startDay, endDay);
    this.endDay = endDay;
  }

  /**
   * Observes starting time of Event.
   *
   * @return starting time of event
   */
  @Override
  public int startTime() {
    return this.startTime;
  }

  /**
   * Updates time the Event starts.
   * The minimum and maximum time an Event can span is enforced by implementation.
   * Desired format of time is enforced by implementation.
   *
   * @param startTime                     new start time of Event
   * @throws IllegalArgumentException     if time-span of updated Event is invalid
   * @throws IllegalArgumentException     if time provided isn't in implementation's desired format
   *
   * @implNote                            Event can only span from 1 minute to 6 days, 23 hours,
   *                                      59 minutes
   *                                      time provided must be in military format
   */
  @Override
  public void updateStartTime(int startTime) {
    ensureProperMilitaryTime(startTime);
    ensureValidTimeSpan(startTime, this.endTime, this.startDay, this.endDay);
    this.startTime = startTime;
  }

  /**
   * Observes ending time of Event.
   *
   * @return ending time of event
   */
  @Override
  public int endTime() {
    return this.endTime;
  }

  /**
   * Updates time the Event ends.
   * The minimum and maximum time an Event can span is enforced by implementation.
   * Desired format of time is enforced by implementation.
   *
   * @param endTime                       new end time of Event
   * @throws IllegalArgumentException     if time-span of updated Event is invalid
   * @throws IllegalArgumentException     if time provided isn't in implementation's desired format
   *
   * @implNote                            Event can only span from 1 minute to 6 days, 23 hours,
   *                                      59 minutes
   *                                      time provided must be in military format
   */
  @Override
  public void updateEndTime(int endTime) {
    ensureProperMilitaryTime(endTime);
    ensureValidTimeSpan(this.startTime, endTime, this.startDay, this.endDay);
    this.endTime = endTime;
  }

  /**
   * Observes all invitees that are part of the Event.
   * The first invitee MUST be the host of the Event.
   *
   * @return list of invitees (first invitee MUST be host)
   */
  public List<String> eventInvitees() {
    return new ArrayList<>(invitees);
  }

  /**
   * Removes an invitee from the Event.
   *
   * @param invitee                       invitee to remove
   * @throws IllegalArgumentException     if no invitees left in Event
   * @throws IllegalArgumentException     if invitee to remove is not part of Event
   */
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

  /**
   * Adds an invitee to the Event.
   *
   * @param invitee                       invitee to add
   * @throws IllegalArgumentException     if invitee is already part of Event
   * @throws IllegalArgumentException     if first invitee is not host of Event
   */
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


  /**
   * Confirms that the time Event spans is within specified constraints.
   *
   * @param startTime                     start time of Event
   * @param endTime                       end time of Event
   * @param startDay                      start day of Event
   * @param endDay                        end day of Event
   * @throws IllegalArgumentException     if time span is invalid
   */
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

  /**
   * Two different Event objects are considered the same if both have exactly the same state.
   * @param other
   * @return
   */
  @Override
  public boolean equals(Object other) {

    if (!(other instanceof Event)) throw new IllegalArgumentException("Event can only be compared with another Event... ");

    Event obj = (Event) other;

    return this.name.equals(obj.name())
            && this.location.equals(obj.location())
            && this.invitees.equals(obj.eventInvitees())
            && this.isOnline == obj.isOnline()
            && this.startTime == obj.startTime()
            && this.endTime == obj.endTime()
            && this.startDay == obj.startDay()
            && this.endDay == obj.endDay();
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, location, invitees, isOnline, startTime, startDay, endTime, endDay);
  }

}
