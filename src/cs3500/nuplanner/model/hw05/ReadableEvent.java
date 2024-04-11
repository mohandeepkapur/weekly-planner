package cs3500.nuplanner.model.hw05;

import java.util.List;

/**
 * Readable-only version of an Event. This is so the Scheduling System can dole out Events to
 * non-model components while enforcing immutability. Defensive copies are already given out, but
 * from a design-standpoint, access to operators should be removed as well.
 */
public interface ReadableEvent {

  /**
   * Observes host of Event.
   *
   * @return                           the host of event
   * @throws IllegalArgumentException  if Event is invitee-less, thus has no host
   */
  String host();

  /**
   * Observes name of Event.
   *
   * @return the name of event
   */
  String name();

  /**
   * Observes location of Event.
   *
   * @return the location of event
   */
  String location();

  /**
   * Observes whether Event is online or offline.
   *
   * @return online/offline status
   */
  boolean isOnline();

  /**
   * Observes starting day of the Event.
   *
   * @return starting day of event
   */
  DaysOfTheWeek startDay();

  /**
   * Observes ending day of the Event.
   *
   * @return ending day of event
   */
  DaysOfTheWeek endDay();

  /**
   * Observes starting time of Event.
   *
   * @return starting time of event
   */
  int startTime();

  /**
   * Observes ending time of Event.
   *
   * @return ending time of event
   */
  int endTime();

  /**
   * Observes all invitees that are part of the Event.
   * The first invitee MUST be the host of the Event. Order MATTERS.
   *
   * @return list of invitees (first invitee MUST be host)
   */
  List<String> eventInvitees();

  /**
   * Checks whether provided time is contained within Event.
   *
   * @param day     day event supposedly contains
   * @param time    time event supposedly contains
   * @return        whether provided time is contained within Event
   */
  boolean containsTime(DaysOfTheWeek day, int time);

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
  List<Integer> extractObjectiveTimePair();

}
