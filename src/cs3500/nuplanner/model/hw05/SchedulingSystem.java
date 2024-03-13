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
   * Adds a custom Event to the relevant schedules.
   *
   * @throws IllegalArgumentException     if provided user does not exist
   *                                      (ok if invitee doesn't exist)
   * @implNote                            owner of event vs invitees important distinction
   *
   */
  void addEventInSchedules(String user, List<String> invitees,
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
  void removeEventInSchedules(String user, int eventID);

  /**
   * Modifies an event that the user chooses with whatever modification listed.
   *
   * @param user
   * @param eventIndex
   * @param modification
   */

  void modifyEventInSchedules(String user, int eventID, String modification);

  /**
   * Converts provided XML file into a Schedule.
   */
  void convertXMLtoSchedule();

  /**
   * Converts existing Schedule in ScheduleSystem into XML file.
   */
  void convertScheduleToXML();

  // view for SameGame only used model observation methods to display game
  // view could've just asked for 2D board (specific to FPSG impl) and then iterated over that
  // that is impl-specific solution though
  // regardless of how model is storing these schedules and events, view should be able to render user schedule

  //  /**
  //   * Provides Schedule to View --> View will then extract every Event from Schedule,
  //   * and then print out Event details.
  //   *
  //   * @throws IllegalArgumentException     if user does not exist
  //   */
  //  Schedule provideUserSchedule(String user);

  // event ids ordered from earliest event to latest event
  List<Integer> eventIDsInSchedule(String user);


  Event eventAt(String user, int eventID);



}
