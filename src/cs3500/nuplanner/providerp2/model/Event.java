package cs3500.nuplanner.providerp2.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates the class that represents an event with all the attributes that an event has.
 * The name of event, the place the event is being held are defined as String, Starting and Ending
 * days are defined by the enum class called Day, StartTime and EndTime are defined by the LocalTime
 * java class and the list of Users are represented as a list of users.
 */

// make read-only for the event???, value class?
public class Event implements IEvent{
  private final String eventName;
  private final Day startDay;
  private final LocalTime startTime;
  private final Day endDay;
  private final LocalTime endTime;
  private final boolean online;
  private final String place;
  private final IUser hostUser;
  private final List<IUser> invitedUsers;

  /**
   * Creates the constructor that creates the event.
   * @param name the name of the event
   * @param startDay the day the event starts
   * @param startTime the time the event starts
   * @param endDay the day the event ends
   * @param endTime the time the event ends
   * @param online if the event is online or not
   * @param place the place the event is occurring
   * @param hostUser the user hosting the event
   * @param invitedUsers the list of the invited users
   */
  public Event(String name, Day startDay, LocalTime startTime, Day endDay, LocalTime endTime,
               boolean online, String place, IUser hostUser, List<IUser> invitedUsers) {
    if (name == null || place == null || startDay == null || endDay == null || startTime == null
            || endTime == null || hostUser == null || invitedUsers == null) {
      throw new IllegalStateException("Cannot have null arguments.");
    }
    if (startDay.equals(endDay) && startTime.equals(endTime)) {
      throw new IllegalArgumentException("Event cannot start and end at the same time "
              + "on the same day.");
    }
    if (!isValidEventDuration(startDay, startTime, endDay, endTime)) {
      throw new IllegalArgumentException("The event duration exceeds the allowed maximum "
              + "of 6 days, 23 hours, and 59 minutes.");
    }
    this.eventName = name;
    this.place = place;
    this.online = online;
    this.startDay = startDay;
    this.startTime = startTime;
    this.endDay = endDay;
    this.endTime = endTime;
    this.hostUser = hostUser;
    this.invitedUsers = new ArrayList<>(invitedUsers);
  }

  /**
   * This calculates the event duration and returns it in minutes.
   */
  protected long calculateEventDurationInMinutes(Day startDay, LocalTime startTime,
                                                 Day endDay, LocalTime endTime) {
    int startDayIndex = startDay.ordinal();
    int endDayIndex = endDay.ordinal();
    int dayDifference = endDayIndex - startDayIndex;
    if (dayDifference < 0) {
      dayDifference += 7;
    }
    if (dayDifference == 0 && startTime.isAfter(endTime)) {
      dayDifference += 6;
    }
    long durationMinutes;
    if (endTime.isBefore(startTime)) {
      durationMinutes = Duration.ofDays(1).toMinutes()
              + Duration.between(startTime, endTime).toMinutes();
    } else {
      durationMinutes = Duration.between(startTime, endTime).toMinutes();
    }
    return dayDifference * 24 * 60 + durationMinutes;
  }

  /**
   * This ensures that an event does not span more than one week in duration, as per the
   * rules set out for the NUPlanner system.
   */
  private boolean isValidEventDuration(Day startDay,
                                       LocalTime startTime,
                                       Day endDay,
                                       LocalTime endTime) {
    final long maxDurationMinutes = 6 * 24 * 60 + 23 * 60 + 59;
    long eventDurationMinutes = calculateEventDurationInMinutes(startDay,
            startTime, endDay, endTime);
    return eventDurationMinutes <= maxDurationMinutes;
  }

  /**
   * This returns the name of the Event.
   */
  public String getName() {
    return eventName;
  }

  /**
   * This returns the place of the Event.
   */
  public String getPlace() {
    return place;
  }

  /**
   * This returns whether the Event is online or not.
   */
  public boolean isOnline() {
    return online;
  }

  /**
   * This returns the start time of the Event.
   */
  public LocalTime getStartTime() {
    return startTime;
  }

  /**
   * This returns the end time of the Event.
   */
  public LocalTime getEndTime() {
    return endTime;
  }

  /**
   * This returns the start day of the Event.
   */
  public Day getStartDay() {
    return startDay;
  }

  /**
   * This returns the end day of the Event.
   */
  public Day getEndDay() {
    return endDay;
  }

  /**
   * This returns the host user of the Event.
   */
  public IUser getHostUser() {
    return hostUser;
  }

  /**
   * This returns the invited users of the Event.
   */
  public List<IUser> getInvitedUsers() {
    return invitedUsers;
  }

  /**
   * The list of the users invited to the event.
   * @return the string of the users invited to the event
   */
  public String invites() {
    StringBuilder result = new StringBuilder();
    for (IUser invite: this.getInvitedUsers()) {
      result.append("\t").append(invite.getUid()).append("\n");
    }
    return result.toString();
  }
}