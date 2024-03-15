package cs3500.nuplanner.model.hw05;

import java.util.List;

/**
 * Operations and observations necessary for a Schedule in a Scheduling System.
 */
public interface Schedule {

  /**
   * Observes the owner of the schedule.
   *
   * @return schedule owner
   */
  String scheduleOwner();

  /**
   * Adds an Event into Schedule.
   *
   * @param newEvent                        Event to be added
   * @throws IllegalArgumentException       if Event is null
   * @throws IllegalArgumentException       if Event to be added conflicts with at least one
   *                                        Event within schedule
   * @throws IllegalArgumentException       if Event already exists in Schedule
   */
  void addEvent(Event newEvent);

  /**
   * Removes an Event from Schedule.
   *
   * If an Event is removed from a Schedule, the invitee list of the Event should be updated
   * to reflect that change. 
   *
   * @param eventToRemove                   Event to be removed
   * @throws IllegalArgumentException       if Event is null
   * @throws IllegalArgumentException       if the given Event does not exist in Schedule
   */
  void removeEvent(Event eventToRemove);

  /**
   * Observes number of Events contained within Schedule.
   *
   * @return number of Events within Schedule
   */
  int numberOfEvents();

  /**
   * Checks whether given Event would conflict with Events currently within schedule.
   *
   * @param outerEvent                     Event that will be checked against Schedule's Events
   * @return                               boolean that signals whether Event can be added
   * @throws IllegalArgumentException      if given Event is null
   */
  boolean eventConflict(Event outerEvent);

  /**
   * Observes an Event contained within the Schedule.
   *
   * @return                              an Event within Schedule
   * @throws IllegalArgumentException     if no Event at given start day and time exists
   */
  Event eventAt(DaysOfTheWeek startDay, int startTime);

  /**
   * Observes all the Events contained within the Schedule.
   *
   * @return                             all Events within Schedule
   */
  List<Event> events();

}