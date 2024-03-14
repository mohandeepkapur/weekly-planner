package cs3500.nuplanner.model.hw05;

import java.util.List;

/**
 * All the operations and observations that can be performed on a Scheduling System.
 */
public interface SchedulingSystem {

  // eventIndexs are essentially index of whatever is storing events in schedule
  // eventIndexs change as in, whatever stores events in schedule will sort itself
  // but that is ok --> all that matters is extracting reference of relevant
  //      Event in model

  /**
   * Adds a user to the scheduling system.
   *
   * @param user                          name of new user
   * @throws IllegalArgumentException     if user already exists in planner-system
   */
  void addUser(String user);

  /**
   * Removes a user from the scheduling system.
   *
   * @throws IllegalArgumentException if user does not exist in the planner-system
   * @param user name of user
   */
  void removeUser(String user);

  List<String> allUsers();

  /**
   * Checks whether an Event can be added into Model given current model-state.
   * Checks whether Event can be added into scheduling system given sched. sys state.
   */
  boolean eventConflict(String host, List<String> invitees,
                        String eventName, String location, boolean isOnline,
                        DaysOfTheWeek startDay, int startTime,
                        DaysOfTheWeek endDay, int endTime);

  /**
   * Adds a custom Event to the relevant schedules.
   *
   * @throws IllegalArgumentException     if provided user does not exist
   *                                      (ok if invitee doesn't exist)
   * @implNote                            owner of event vs invitees important distinction
   *
   */
  void addEvent(String host, List<String> invitees,
                String eventName, String location, boolean isOnline,
                DaysOfTheWeek startDay, int startTime,
                DaysOfTheWeek endDay, int endTime);

  /**
   * Removes an Event from specified user's Schedule.
   * @param user                        name of user
   * @param eventIndex                     represents the Event to be removed
   *
   * @throws IllegalArgumentException   if user does not exist in scheduling system
   *
   * @implNote                          if owner is removing event, delete Event from
   *                                    every schedule
   *                                    if invitee is removing event, update Event with
   *                                    removed invitee
   *
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

  List<ReadableEvent> eventsInSchedule(String user);

  ReadableEvent eventAt(String user, DaysOfTheWeek startDay, int startTime);



  /*
  Sys:
  <- remove eventAt
  <- remove eventIDsInSchedule

  List<ReadableEvent> eventsInSchedule(String user); // events in order <-- given to view

  void removeEvent(String user, DaysOfTheWeek startDay, int startTime);

  void modifyEvent(String user, DaysOfTheWeek startDay, int startTime, String modification);

  Schedule:
  Event eventAt(int eventID); --> Event eventAt(DaysOfTheWeek startDay, int startTime);
  List<Integer> eventIDs(); --> List<Event> events(); <-- all events within schedule

  Event:
  remove eventID() from interface

  Then:
  change tests a bit

  Example:
  Event eventToRemove = userSchedules.get(user).eventAt(eventID); --> .eventAt(startDay, startTime) --> eventAt throws error if sD, sT !found


  Why moving away from ID system: <-- using startDay and startTime accomp same thing without weird side effects
  ID system <-- weird thing where modifying event would create new ID
            <-- anyone using event interface would need to use an ID system

  Then:
  finish modify event <-- clear path to victory

   */

  /*
  functionality of
   */

}
