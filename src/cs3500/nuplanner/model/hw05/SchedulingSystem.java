package cs3500.nuplanner.model.hw05;

import java.util.List;

/**
 * All the operations and observations that can be performed on a Scheduling System.
 */
public interface SchedulingSystem {

  // ADD THROWS FOR ILLEGAL EVENT CONSTRUCTION

  /**
   * Adds a user to the scheduling system.
   *
   * @param user                             name of new user
   * @throws IllegalArgumentException        if user already exists in scheduling-system
   */
  void addUser(String user);

  /**
   * Removes a user from the scheduling system.
   *
   * @param user                                name of user
   * @throws IllegalArgumentException           if user does not exist in the scheduling-system
   */
  void removeUser(String user);

  /**
   * Observes all users existing within the scheduling system
   *
   * @return all users in scheduling system
   */
  List<String> allUsers();

  /**
   * Creates and adds a new Event to the relevant schedules.
   *
   * @throws IllegalArgumentException   if provided host does not exist in scheduling system
   * @throws IllegalArgumentException   if Event cannot be constructed due to invalid information
   * @throws IllegalArgumentException   if the Event's host is not first in its invitees list
   * @throws IllegalArgumentException   if Event conflicts with any Schedule within the Scheduling
   *                                    System
   */
  void addEvent(String host, List<String> invitees,
                String eventName, String location, boolean isOnline,
                DaysOfTheWeek startDay, int startTime,
                DaysOfTheWeek endDay, int endTime);

  /**
   * Removes an Event from specified user's Schedule.
   *
   * @param user                    name of user whose schedule holds the Event
   * @param startDay
   * @param startTime
   */
  void removeEvent(String user, DaysOfTheWeek startDay, int startTime);

  /**
   * Modifies an event that the user chooses with whatever modification listed.
   *
   * @param user
   * @param eventIndex
   * @param modification
   */

  void modifyEvent(String user, DaysOfTheWeek startDay, int startTime, String modification);

  /**
   * Checks whether an Event can be added into Scheduling System given its current state.
   *
   * @param host
   * @param invitees
   * @param eventName
   * @param location
   * @param isOnline
   * @param startDay
   * @param startTime
   * @param endDay
   * @param endTime
   * @return
   */
  boolean eventConflict(String host, List<String> invitees,
                        String eventName, String location, boolean isOnline,
                        DaysOfTheWeek startDay, int startTime,
                        DaysOfTheWeek endDay, int endTime);

  /**
   * Observes all the Events contained within a user's Schedule.
   *
   * @param user                        name of user whose Schedule to return
   * @return                            Schedule belonging to that user
   * @throws IllegalArgumentException   if user does not exist in scheduling system
   */
  List<ReadableEvent> eventsInSchedule(String user);

  /**
   * Observes a unique Event contained within a user's schedule
   *
   * @param user                        name of user whose Event to return
   * @return                            Event belonging to that user
   * @throws IllegalArgumentException   if user does not exist in scheduling system
   */
  ReadableEvent eventAt(String user, DaysOfTheWeek startDay, int startTime);




}
