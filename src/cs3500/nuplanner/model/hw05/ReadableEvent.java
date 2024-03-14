package cs3500.nuplanner.model.hw05;

import java.util.List;

/**
 * Readable-only version of an Event.
 */
public interface ReadableEvent {

  String host();

  /**
   * Observes name of Event.
   */
  String name();

  /**
   * Observes Event location.
   */
  String location();

  /**
   * Observes information on whether Event is online or offline.
   */
  boolean isOnline();

  /**
   * Observes the start-day of the Event.
   */
  DaysOfTheWeek startDay();

  /**
   * Observes the end-day of the Event.
   */
  DaysOfTheWeek endDay();

  /**
   * Observes start time of Event.
   *
   * @implNote military time is used: 13:35 -> 1:35pm
   */
  int startTime();

  /**
   * Observes end time of Event.
   *
   * @implNote military time is used: 13:35 -> 1:35pm
   */
  int endTime();

  /**
   * Collection of Users that are part of the Event. The first user is always the owner. The
   * following users are invitees.
   * <p>
   * (issue of whether owner exists or not should've been taken care of upstream)
   *
   * @return
   * @implNote
   */
  List<String> eventInvitees();


}
