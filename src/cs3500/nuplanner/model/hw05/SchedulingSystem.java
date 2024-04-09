package cs3500.nuplanner.model.hw05;

import java.util.List;

/**
 * All the operations and observations that can be performed on a Scheduling System.
 * <p>
 * Permissions are very trusting, as mentioned by assignment. every invitee is client and can remove
 * and add anyone. If non-host invitee of an Event is not in system, will be added automatically.
 * </p>
 */
public interface SchedulingSystem extends ReadableSchedulingSystem {

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
   * Creates and adds a new Event to the relevant schedules. Invitees not existing in
   * scheduling system automatically added.
   *
   * @param user      host of Event
   * @param invitees  users added to Event (includes host)
   * @param eventName name of Event
   * @param location  location of Event
   * @param isOnline  online/offline status of Event
   * @param startDay  start day of event
   * @param startTime start time of Event
   * @param endDay    end day of Event
   * @param endTime   end time of Event
   * @throws IllegalArgumentException if user does not exist in scheduling system
   * @throws IllegalArgumentException if Event cannot be constructed due to invalid information
   * @throws IllegalArgumentException if user creating event is not host of Event
   * @throws IllegalArgumentException if Event conflicts with any Schedule within the Scheduling
   *                                  System
   */
  void addEvent(String user, List<String> invitees,
                String eventName, String location, boolean isOnline,
                DaysOfTheWeek startDay, int startTime,
                DaysOfTheWeek endDay, int endTime);

  /**
   * Removes an Event from specified user's Schedule. Event state is updated accordingly.
   * Assumption that no Event in a Schedule shares the same start day and time.
   * <p>
   * If the host of the Event request to removeEvent, remove event from all schedules and update
   * Event accordingly. If a non-host, then remove Event just from their own
   * schedule and update Event accordingly.
   * </p>
   *
   * @param user      name of user whose schedule holds the Event
   * @param startDay
   * @param startTime
   * @throws IllegalArgumentException if Event with above properties does not exist in Schedule
   */
  void removeEvent(String user, DaysOfTheWeek startDay, int startTime);
  // feel like providing a ReadableEvent is incorrect
  // internally, in model implementation... well, should impl details ever
  //   influence interface? no
  // feel like providing most general-type implies below, and
  //
  // through providing readableEvent, I imply to client that to access
  //    an event to remove, use model obs method that produces readable event
  //      does that even really matter though


  /**
   * Modifies an Event within Scheduling System.
   *
   * @param user           user requesting modification
   * @param startDay       start day of event to modify in user's schedule
   * @param startTime      start time of event to modify in user's schedule
   * @param modEvent       modified event
   */
  void modifyEvent(String user, DaysOfTheWeek startDay, int startTime, Event modEvent);

  /**
   * Checks whether an Event can be added into Scheduling System given its current state.
   * Event invitees that do not exist in model assumed
   * to have blank schedules, but are not actually added into model.
   *
   * @param host                        host of Event
   * @param invitees                    users added to Event (includes host)
   * @param eventName                   name of Event
   * @param location                    location of Event
   * @param isOnline                    online/offline status of Event
   * @param startDay                    start day of event
   * @param startTime                   start time of Event
   * @param endDay                      end day of Event
   * @param endTime                     end time of Event
   *
   * @return                            whether event can exist within scheduling system or not
   *
   * @throws IllegalArgumentException   if Event cannot be constructed due to invalid information
   */
  boolean eventConflict(String host, List<String> invitees,
                        String eventName, String location, boolean isOnline,
                        DaysOfTheWeek startDay, int startTime,
                        DaysOfTheWeek endDay, int endTime);

}
