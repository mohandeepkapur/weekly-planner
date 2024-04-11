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
  private String host;

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
   * @throws IllegalArgumentException if any input is null
   * @throws IllegalArgumentException if times provided are not in military format
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
      this.host = null;
      //throw new IllegalArgumentException("Event cannot be init. with no invitees... ");
    } else {
      this.host = invitees.get(0);
    }

    this.invitees = invitees;
    this.name = eventName;
    this.location = location;
    this.isOnline = isOnline;
    this.startDay = startDay;
    this.startTime = startTime;
    this.endDay = endDay;
    this.endTime = endTime;

    ensureValidTimeSpan(this.startTime, this.endTime, this.startDay, this.endDay);

  }

  private void ensureProperMilitaryTime(int time) {
    if (time / 100 < 0 || time / 100 > 23 || time % 100 > 59) {
      throw new IllegalArgumentException("Event cannot be init. with invalid military time...  ");
    }
  }

  /**
   * Constructs a copy of another Event.
   *
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
   *
   * @return                           the host of event
   * @throws IllegalArgumentException  if Event is invitee-less, thus has no host
   */
  @Override
  public String host() {
    if (host == null) {
      throw new IllegalArgumentException("Event has no host... has no invitees... ");
    }
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
   * @param name new name of Event
   * @throws IllegalArgumentException if name provided is null
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
   * @param location new location of Event
   * @throws IllegalArgumentException if name provided is null
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
   * @param isOnline new online/offline status of Event
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
   * @param startDay new starting day of Event
   * @throws IllegalArgumentException if time-span of updated Event is invalid
   * @throws IllegalArgumentException if start day provided is null
   * @implNote Event can only span from 1 minute to 6 days, 23 hours, 59 minutes
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
   * @param endDay new end day of Event
   * @throws IllegalArgumentException if time-span of updated Event is invalid
   * @throws IllegalArgumentException if end day provided is null
   * @implNote Event can only span from 1 minute to 6 days, 23 hours, 59 minutes
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
   * Desired format of military time is enforced by implementation.
   *
   * @param startTime                     new start time of Event
   * @throws IllegalArgumentException     if time-span of updated Event is invalid
   * @throws IllegalArgumentException     if time provided isn't in implementation's desired format
   * @implNote Event can only span from 1 minute to 6 days, 23 hours, 59 minutes
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
   * Desired format of military time is enforced by implementation.
   *
   * @param endTime                     new end time of Event
   * @throws IllegalArgumentException   if time-span of updated Event is invalid
   * @throws IllegalArgumentException   if time provided isn't in implementation's desired format
   * @implNote                          Event can only span from 1 minute to 6 days,
   *                                    23 hours, 59 minutes
   */
  @Override
  public void updateEndTime(int endTime) {
    ensureProperMilitaryTime(endTime);
    ensureValidTimeSpan(this.startTime, endTime, this.startDay, this.endDay);
    this.endTime = endTime;
  }

  /**
   * Observes all invitees that are part of the Event.
   * The first invitee MUST be the host of the Event. Order MATTERS.
   *
   * @return list of invitees (first invitee MUST be host)
   */
  public List<String> eventInvitees() {
    return new ArrayList<>(invitees);
  }

  /**
   * Removes an invitee from the Event. If host of Event is removed, all other invitees of Event
   * are removed as consequence, creating invitee-less Event.
   *
   * @param invitee                     invitee to remove
   * @throws IllegalArgumentException   if no invitees left in Event
   * @throws IllegalArgumentException   if invitee to remove is not part of Event
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
    //HW7 addition
    if (host.equals(invitee)) {
      invitees.clear();
    }
    invitees.remove(invitee);
    if (invitees.isEmpty()) {
      host = null;
    }
  }

  /**
   * Adds an invitee to the Event. First invitee added to Event with no invitees becomes host.
   *
   * @param invitee                       invitee to add
   * @throws IllegalArgumentException     if invitee is already invited in Event
   */
  @Override
  public void addInvitee(String invitee) {

    if (host == null) {
      this.host = invitee;
      this.invitees.add(host);
    }

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
   * @param startTime start time of Event
   * @param endTime   end time of Event
   * @param startDay  start day of Event
   * @param endDay    end day of Event
   * @throws IllegalArgumentException if time span is invalid
   */
  private void ensureValidTimeSpan(int startTime, int endTime,
                                   DaysOfTheWeek startDay, DaysOfTheWeek endDay)
          throws IllegalArgumentException {
    if (startTime == endTime && startDay == endDay) {
      throw new IllegalArgumentException("Event cannot exist with invalid time-span... ");
    }
  }

  /**
   * Two different Event objects are considered the same if both have exactly the same state.
   *
   * @param other other Event
   * @return whether two different Events are equal
   */
  @Override
  public boolean equals(Object other) {

    if (!(other instanceof Event)) {
      throw new IllegalArgumentException("Event can only be compared with another Event... ");
    }

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

  /**
   * Overriden due to equals override. Produces unique hash of Event based on its state.
   *
   * @return hash of Event
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, location, invitees, isOnline, startTime, startDay, endTime, endDay);
  }

  /**
   * Checks whether provided time is contained within Event.
   *
   * @param day     day event suppos. contains
   * @param time    time event suppos. contains
   * @return        whether provided time is contained within Event
   */
  @Override
  public boolean containsTime(DaysOfTheWeek day, int time) {
    List<Integer> objValues = extractObjectiveTimePair();
    int providedDayTime = (day.val() * 60 * 24) + (time / 100 * 60) + (time % 100);

    //    System.out.println(objValues.get(0));
    //    System.out.println(providedDayTime);
    //    System.out.println(objValues.get(1));
    return providedDayTime <= objValues.get(1) && providedDayTime >= objValues.get(0);
  }

  /**
   * Returns start day, start time as one objective value, and end day, end time as the second.
   * Objective times are the # of minutes the start day, time past Sunday at 0am is, and the
   * # of minutes the end day, end time past Sunday at 0am is.
   * (Depending on whether the Event extends into the second week or not, the end day end time's
   * objective value will be different accordingly).
   *
   * @return        (start day, start time) mapped to first item as objective value (unit minutes)
   *                (end day, end time) mapped tp second item as objective value (unit minutes)
   */
  @Override
  public List<Integer> extractObjectiveTimePair() {
    int sDv = this.startDay().val();
    int sT = this.startTime();

    int eDv = this.endDay().val();
    int eT = this.endTime();

    int startVal;
    int endVal;

    // event that extends into next week
    if (eDv - sDv < 0 || (eDv - sDv == 0 && eT <= sT)) {
      endVal = ((eDv + 7) * 60 * 24) + (eT / 100 * 60) + (eT % 100);
    } else {
      // event contained within first week
      endVal = (eDv * 60 * 24) + (eT / 100 * 60) + (eT % 100);
    }
    // start day always within first week
    startVal = (sDv * 60 * 24) + (sT / 100 * 60) + (sT % 100);

    return new ArrayList<Integer>(List.of(startVal, endVal));
  }

}
