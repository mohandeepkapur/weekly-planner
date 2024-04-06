package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of Scheduling System. Relies on hashmap to relate users and schedules,
 * since users can only contain one Schedule and all users unique.
 */
public class NUPlannerModel implements SchedulingSystem {

  private final Map<String, Schedule> userSchedules;

  /**
   * Constructs a Planner.
   */
  public NUPlannerModel() {
    userSchedules = new HashMap<>();
  }


  /**
   * Adds a user to the scheduling system.
   *
   * @param user                             name of new user
   * @throws IllegalArgumentException        if user already exists in scheduling-system
   */
  @Override
  public void addUser(String user) {
    confirmUserDoesNotExist(user);
    userSchedules.put(user, new NUSchedule(user));
  }

  /**
   * Removes a user from the scheduling system.
   *
   * @param user                                name of user
   * @throws IllegalArgumentException           if user does not exist in the scheduling-system
   */
  @Override
  public void removeUser(String user) {
    confirmUserExists(user);
    userSchedules.remove(user);
  }

  /**
   * Observes all users existing within the scheduling system.
   *
   * @return all users in scheduling system
   */
  @Override
  public List<String> allUsers() {
    List<String> allUsers = new ArrayList<>();
    for (Map.Entry<String, Schedule> entry : userSchedules.entrySet()) {
      allUsers.add(entry.getKey());
    }
    return allUsers;
  }

  /**
   * Creates and adds a new Event to the relevant schedules.
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
   * @throws IllegalArgumentException   if provided host does not exist in scheduling system
   * @throws IllegalArgumentException   if Event cannot be constructed due to invalid information
   * @throws IllegalArgumentException   if the Event's host is not first in its invitees list
   * @throws IllegalArgumentException   if Event conflicts with any Schedule within the Scheduling
   *                                    System
   */
  @Override
  public void addEvent(String host, List<String> invitees,
                       String eventName, String location, boolean isOnline,
                       DaysOfTheWeek startDay, int startTime,
                       DaysOfTheWeek endDay, int endTime) {


    confirmUserExists(host);

    // if event contains non-hosts that don't exist, invite them into the planning system
    for (String user : invitees) {
      try {
        confirmUserExists(user);
      } catch (IllegalArgumentException caught) {
        addUser(user);
      }
    }

    // check if user creating event sets some other user as the host
    if (!invitees.get(0).equals(host)) {
      throw new IllegalArgumentException("Unable to add event in sys where adder is not host... ");
    }

    // TODO: Check
    if (eventConflict(host, invitees, eventName, location, isOnline, startDay, startTime, endDay,
            endTime)) {
      throw new IllegalArgumentException(
              "Cannot add event, conflicts with an invitee schedule... ");
    }

    // create new Event -> Event constructor will inform model whether params valid
    Event event = new NUEvent(invitees, eventName, location, isOnline,
            startDay, startTime,
            endDay, endTime);

    placeEventRelevantSchedules(event);
  }

  /**
   * Adds Event into its invitees' Schedules.
   *
   * @param event                        Event to be added
   * @throws IllegalArgumentException    if the Event conflicts with Events within
   *                                     at least one invitee's Schedule
   */
  private void placeEventRelevantSchedules(Event event) {
    for (String invitee : event.eventInvitees()) {
      Schedule inviteeSchedule = this.userSchedules.get(invitee);
      inviteeSchedule.addEvent(event);
    }
  }


  /**
   * Removes an Event from specified user's Schedule. Event state is updated accordingly.
   * Assumption that no Event in a Schedule shares the same start day and time.
   *
   * @param user      name of user whose schedule holds the Event
   * @param startDay
   * @param startTime
   * @throws IllegalArgumentException if Event with above properties does not exist in Schedule
   */
  @Override
  public void removeEvent(String user, DaysOfTheWeek startDay, int startTime) {
    confirmUserExists(user);

    Event event = userSchedules.get(user).eventAt(startDay, startTime);

    // if user removing event from their schedule is host of the event
    if (event.eventInvitees().get(0).equals(user)) {
      removeEventFromEverySchedule(event.eventInvitees(), event);
    } else {
      removeEventFromSingleSchedule(user, event);
    }

  }

  /**
   * Recursive method that updates every user schedule when host is removed from an Event.
   * Event is removed from every invitee's schedule.
   *
   * @param invitees                        current invitees in event (decreases recursively)
   * @param copyEventToRemove               copy of Event to remove (updates recursively too)
   */
  private void removeEventFromEverySchedule(List<String> invitees, Event copyEventToRemove) {
    // manipulate Event object contained within Schedule
    // (that Scheduling System cannot access bc observer method aliasing bad)
    // by using a copy of the contained Event object to access and modify original
    // updates copy while original modified to preserve equality and thus access to original Event
    if (!invitees.isEmpty()) {
      // HW7 change:
      // need to use last index, given updated Event behavior
      // removeInvitee now removes all attendees in Event if host removed
      int laIn = invitees.size() - 1;
      Schedule inviteeSchedule = this.userSchedules.get(invitees.get(laIn));
      inviteeSchedule.removeEvent(copyEventToRemove); // method updates event's invitee list
      copyEventToRemove.removeInvitee(invitees.get(laIn));
      removeEventFromEverySchedule(copyEventToRemove.eventInvitees(), copyEventToRemove);
    }

  }

  /**
   * Removes an Event from a single schedule.
   *
   * @param user                        invitee who is removing Event
   * @param eventToRemove               Event to remove
   */
  private void removeEventFromSingleSchedule(String user, Event eventToRemove) {
    Schedule inviteeSchedule = this.userSchedules.get(user);
    inviteeSchedule.removeEvent(eventToRemove); // method updates event's invitee list
  }

  /**
   * Modifies an Event within Scheduling System.
   *
   * @param user           user requesting modification
   * @param startDay       start day of event to modify in user's schedule
   * @param startTime      start time of event to modify in user's schedule
   * @param modEvent       modified event
   */
  @Override
  public void modifyEvent(String user, DaysOfTheWeek startDay, int startTime, Event modEvent) {

    Event origEvent = userSchedules.get(user).eventAt(startDay, startTime);
    List<String> origEventInvitees = origEvent.eventInvitees();

    // remove original event from all schedules --> all invitees within Event obj removed!
    // must store list of invitees before Event obj removed from model
    // if Event obj must be placed back into schedule in its before-removed state
    this.removeEvent(origEvent.host(), startDay, startTime);

    // check if modified event compatible with scheduling system
    if (!this.eventConflict(modEvent.host(),
            modEvent.eventInvitees(), modEvent.name(),
            modEvent.location(), modEvent.isOnline(),
            modEvent.startDay(), modEvent.startTime(),
            modEvent.endDay(), modEvent.endTime())) {

      // if so, add it
      this.addEvent(modEvent.host(),
              modEvent.eventInvitees(), modEvent.name(),
              modEvent.location(), modEvent.isOnline(),
              modEvent.startDay(), modEvent.startTime(),
              modEvent.endDay(), modEvent.endTime());

      return;
    }

    // if not, add back original event and throw exception
    this.addEvent(origEvent.host(),
            origEventInvitees, origEvent.name(),
            origEvent.location(), origEvent.isOnline(),
            origEvent.startDay(), origEvent.startTime(),
            origEvent.endDay(), origEvent.endTime());

    throw new IllegalArgumentException("Cannot modify event in this manner");

  }

  //  /**
  //   * Tries to add modified copy of Event into Scheduling System. If not possible, add back
  //   * original event to scheduling system and inform client of failure.
  //   *
  //   * @param copyOfEvent             modified copy of event-to-modify
  //   * @param origEvent               event-to-modify removed from scheduling system
  //   * @param origInvitees            invitees of event-to-modify
  //   */
  //  private void tryToAddModCopyToSchedulingSystem(Event copyOfEvent, Event origEvent,
  //                                                 List<String> origInvitees) {
  //    // check if modified copy is compatible with scheduling system
  //    if (!this.eventConflict(copyOfEvent.host(),
  //            copyOfEvent.eventInvitees(), copyOfEvent.name(),
  //            copyOfEvent.location(), copyOfEvent.isOnline(),
  //            copyOfEvent.startDay(), copyOfEvent.startTime(),
  //            copyOfEvent.endDay(), copyOfEvent.endTime())) {
  //      // if so, add it! this is the "modified" event aka replaced
  //      this.addEvent(copyOfEvent.host(),
  //              copyOfEvent.eventInvitees(), copyOfEvent.name(),
  //              copyOfEvent.location(), copyOfEvent.isOnline(),
  //              copyOfEvent.startDay(), copyOfEvent.startTime(),
  //              copyOfEvent.endDay(), copyOfEvent.endTime());
  //      return;
  //    }
  //
  //    // otherwise, add a copy of original event back into the schedule, with original invitees
  //    // orig invitees valid <-- it is prev. known that this entire event construction below IS VALID
  //    this.addEvent(origEvent.host(),
  //            origInvitees, origEvent.name(),
  //            origEvent.location(), origEvent.isOnline(),
  //            origEvent.startDay(), origEvent.startTime(),
  //            origEvent.endDay(), origEvent.endTime());
  //
  //    throw new IllegalArgumentException(
  //            "Cannot add this modified version of event in scheduling system... ");
  //  }
  //

  /**
   * Checks whether an Event can be added into Scheduling System given its current state.
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
  @Override
  public boolean eventConflict(String host, List<String> invitees,
                               String eventName, String location, boolean isOnline,
                               DaysOfTheWeek startDay, int startTime,
                               DaysOfTheWeek endDay, int endTime) {

    // create new Event -> Event constructor will inform model whether params valid
    Event event = new NUEvent(invitees, eventName, location, isOnline,
            startDay, startTime,
            endDay, endTime);

    // just using Event obj internally

    try {
      checkOpenSpaceRelevantSchedules(event);
      return false;
    } catch (IllegalStateException caught) {
      return true;
    }
  }

  /**
   * Checks whether Event can be added into its invitees' Schedules.
   *
   * @param event                     Event to be added
   * @throws IllegalStateException    if the Event conflicts with Events within Schedule
   */
  private void checkOpenSpaceRelevantSchedules(Event event) {
    // for every invitee (including host)
    for (String invitee : event.eventInvitees()) {
      Schedule inviteeSchedule = this.userSchedules.get(invitee);
      if (inviteeSchedule.eventConflict(event)) {
        throw new IllegalStateException("Event cannot be added into scheduling system... "
               + "scheduling conflict with at least one invitee's schedule... ");
      }
    }
  }

  /**
   * Updates the given Event object's state.
   *
   * @param event                         event to be modified
   * @param stateToUpdate                 single-state of Event that will be updated
   * @param update                        new version of that state
   * @throws IllegalArgumentException     if single-state to update is not part of Event
   * @implNote                            modification to be performed must be given in the form
   *                                      "single-state-of-Event updated-single-state"
   */
  private void performOtherModifications(Event event, String stateToUpdate, String update) {
    switch (stateToUpdate) {
      case "name":
        event.updateName(update);
        break;
      case "location":
        event.updateLocation(update);
        break;
      case "online":
        if (!Boolean.parseBoolean(update) && !update.equals("false")) {
          throw new IllegalArgumentException("Invalid modification for event online status...  ");
        }
        event.updateIsOnline(Boolean.parseBoolean(update));
        break;
      case "starttime":
        try {
          event.updateStartTime(Integer.parseInt(update));
          break;
        } catch (NumberFormatException caught) {
          throw new IllegalArgumentException("Invalid modification for event start time...  ");
        }
      case "endtime":
        try {
          event.updateEndTime(Integer.parseInt(update));
          break;
        } catch (NumberFormatException caught) {
          throw new IllegalArgumentException("Invalid modification for event online status...  ");
        }
      case "startday":
        event.updateStartDay(convertStringToDay(update));
        break;
      case "endday":
        event.updateEndDay(convertStringToDay(update));
        break;
      case "addinvitee":
        try {
          confirmUserExists(update);
        } catch (IllegalArgumentException caught) {
          this.addUser(update);
        }
        event.addInvitee(update);
        break;
      default:
        throw new IllegalArgumentException("Param to update event with DNE for an event...  ");
    }
  }

  /**
   * Observes all the Events contained within a user's Schedule.
   *
   * @param user                        name of user whose Schedule to return
   * @throws IllegalArgumentException   if user does not exist in scheduling system
   *
   * @return                            Schedule belonging to that user
   */
  @Override
  public List<ReadableEvent> eventsInSchedule(String user) {
    confirmUserExists(user);

    // defensive copy and only readable version of events
    return new ArrayList<>(this.userSchedules.get(user).events());
  }

  /**
   * Observes a unique Event contained within a user's schedule.
   *
   * @param user                        name of user whose Event to return
   * @throws IllegalArgumentException   if user does not exist in scheduling system
   *
   * @return                            Event belonging to that user
   */
  @Override
  public ReadableEvent eventAt(String user, DaysOfTheWeek startDay, int startTime) {
    confirmUserExists(user);
    return this.userSchedules.get(user).eventAt(startDay, startTime);

  }

  /**
   * Confirms that a user exists in the Scheduling System.
   *
   * @param user                       user name
   * @throws IllegalArgumentException  if user DNE in SS
   */
  private void confirmUserExists(String user) {
    if (user == null) {
      throw new IllegalArgumentException("Invalid input, user cannot be null...");
    }
    if (!this.userSchedules.containsKey(user)) {
      throw new IllegalArgumentException(user + " does not exist in system... ");
    }
  }

  /**
   * Confirms that a user does not exist within the Scheduling System.
   *
   * @param user                              name of user
   * @throws IllegalArgumentException         if user exists within the Scheduling System
   */
  private void confirmUserDoesNotExist(String user) throws IllegalArgumentException {
    if (user == null) {
      throw new IllegalArgumentException("Invalid input, please provide a user name... ");
    }
    if (this.userSchedules.containsKey(user)) {
      throw new IllegalArgumentException("User already exists in scheduling system... ");
    }
  }

  /**
   * Converts provided string into a day of the week, if possible.
   *
   * @param string                        string to convert into day
   * @return                              DaysOfTheWeek enum constant
   * @throws IllegalArgumentException     if string cannot be converted into a day
   */
  private DaysOfTheWeek convertStringToDay(String string) {

    if (DaysOfTheWeek.SUNDAY.toString().equals(string)) {
      return DaysOfTheWeek.SUNDAY;
    }
    if (DaysOfTheWeek.MONDAY.toString().equals(string)) {
      return DaysOfTheWeek.MONDAY;
    }
    if (DaysOfTheWeek.TUESDAY.toString().equals(string)) {
      return DaysOfTheWeek.TUESDAY;
    }
    if (DaysOfTheWeek.WEDNESDAY.toString().equals(string)) {
      return DaysOfTheWeek.WEDNESDAY;
    }
    if (DaysOfTheWeek.THURSDAY.toString().equals(string)) {
      return DaysOfTheWeek.THURSDAY;
    }
    if (DaysOfTheWeek.FRIDAY.toString().equals(string)) {
      return DaysOfTheWeek.FRIDAY;
    }
    if (DaysOfTheWeek.SATURDAY.toString().equals(string)) {
      return DaysOfTheWeek.SATURDAY;
    }

    throw new IllegalArgumentException("Invalid modification request... ");

  }

}
