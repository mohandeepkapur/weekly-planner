package cs3500.nuplanner.model.hw05;

import java.util.List;

// client ease-of-use considerations? so many state-setters, annoying for client?
// not getters and setters <- intention not to return field, but state of obj

/**
 * Operations and observations necessary for an Event in a scheduling system.
 */
public interface Event {

  /**
   * Unique, Immutable ID of an Event.
   */
  int ID();

  /**
   * Observes name of Event.
   */
  String name();

  /**
   * Changes name of Event.
   */
  void updateName(String name);

  /**
   * Observes Event location.
   */
  String location();

  /**
   * Changes Event location.
   */
  void updateLocation(String location);

  /**
   * Observes information on whether Event is online or offline.
   */
  boolean isOnline();

  /**
   * Changes whether Event is online or offline.
   */
  void updateIsOnline(boolean isOnline);

  /**
   * Observes the start-day of the Event.
   */
  DaysOfTheWeek startDay();

  /**
   * Changes starting day of Event.
   *
   * @param day                          new starting day of Event
   * @throws IllegalArgumentException    if time-span of modified Event exceeds 6-days 23-hrs 59-min
   */
  void updateStartDay(DaysOfTheWeek day);

  /**
   * Observes the end-day of the Event.
   */
  DaysOfTheWeek endDay();

  /**
   * Changes end day of Event.
   *
   * @param day                          new end day of Event
   * @throws IllegalArgumentException    if time-span of modified Event exceeds 6-days 23-hrs 59-min
   */
  void updateEndDay(DaysOfTheWeek day);

  /**
   * Observes start time of Event.
   *
   * @implNote                           military time is used: 13:35 -> 1:35pm
   */
  int startTime();

  /**
   * Changes start time of Event.
   *
   * @param startTime                    new start time of Event
   * @throws IllegalArgumentException    if time-span of modified Event exceeds 6-days 23-hrs 59-min
   */
  void updateStartTime(int startTime);

  /**
   * Observes end time of Event.
   *
   * @implNote                           military time is used: 13:35 -> 1:35pm
   */
  int endTime();

  /**
   * Changes start time of Event.
   *
   * @param endTime                      new end time of Event
   * @throws IllegalArgumentException    if time-span of modified Event exceeds 6-days 23-hrs 59-min
   */
  void updateEndTime(int endTime);

  /**
   * Collection of Users that are part of the Event. The first user is always the owner. The
   * following users are invitees.
   *
   * (issue of whether owner exists or not should've been taken care of upstream)
   *
   * @implNote
   * @return
   */
  List<String> eventInvitees();

  /**
   * Removes invitee.
   *
   * @implNote              will only be used on an invitee <--
   */
  void removeInvitee(String invitee); //remove user from invited user list

  /**
   * Provides host of event.
   */
  String eventHost();

  // void updateInvitees();

}
