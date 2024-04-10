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
   * @param user name of new user
   * @throws IllegalArgumentException if user already exists in scheduling-system
   */
  @Override
  public void addUser(String user) {
    confirmUserDoesNotExist(user);
    userSchedules.put(user, new NUSchedule(user));
  }

  /**
   * Removes a user from the scheduling system.
   *
   * @param user name of user
   * @throws IllegalArgumentException if user does not exist in the scheduling-system
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
  @Override
  public void addEvent(String user, List<String> invitees,
                       String eventName, String location, boolean isOnline,
                       DaysOfTheWeek startDay, int startTime,
                       DaysOfTheWeek endDay, int endTime) {

    confirmUserExists(user);

    if (invitees.isEmpty()) {
      throw new IllegalArgumentException("Cannot construct Event with no invitees... ");
    }

    // check if user creating event sets some other user as the host
    if (!invitees.get(0).equals(user)) {
      throw new IllegalArgumentException("Unable to add event in sys where adder is not host... ");
    }

    // TODO: Check
    if (eventConflict(user, invitees, eventName, location, isOnline, startDay, startTime, endDay,
            endTime)) {
      throw new IllegalArgumentException(
              "Cannot add event, conflicts with an invitee schedule... ");
    }

    // if event contains non-hosts that don't exist, invite them into the planning system
    for (String invitee : invitees) {
      try {
        confirmUserExists(invitee);
      } catch (IllegalArgumentException caught) {
        addUser(invitee);
      }
    }

    // create new Event -> Event constructor will inform model whether params valid
    // hardcoded model to work with military time <- bad <- change
    Event event = new NUEvent(invitees, eventName, location, isOnline,
            startDay, startTime,
            endDay, endTime);

    placeEventRelevantSchedules(event);
  }

  /**
   * Adds Event into its invitees' Schedules.
   *
   * @param event Event to be added
   * @throws IllegalArgumentException if the Event conflicts with Events within
   *                                  at least one invitee's Schedule
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
   * @param startDay  the start day of the event
   * @param startTime the start time of the event
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
   * @param invitees          current invitees in event (decreases recursively)
   * @param copyEventToRemove copy of Event to remove (updates recursively too)
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
   * @param user          invitee who is removing Event
   * @param eventToRemove Event to remove
   */
  private void removeEventFromSingleSchedule(String user, Event eventToRemove) {
    Schedule inviteeSchedule = this.userSchedules.get(user);
    inviteeSchedule.removeEvent(eventToRemove); // method updates event's invitee list
  }

  /**
   * Modifies an Event within Scheduling System.
   *
   * @param user      user requesting modification
   * @param startDay  start day of event to modify in user's schedule
   * @param startTime start time of event to modify in user's schedule
   * @param modEvent  modified event
   */
  @Override
  public void modifyEvent(String user, DaysOfTheWeek startDay, int startTime, Event modEvent) {
    Event origEvent = userSchedules.get(user).eventAt(startDay, startTime);
    List<String> origEventInvitees = origEvent.eventInvitees();

    // if modified Event comes in with no invitees <-- signal that host of orig event removed
    // thus, remove origEvent from model <-- removal will never create conflict
    if (modEvent.eventInvitees().isEmpty()) {
      this.removeEvent(origEvent.host(), startDay, startTime);
      return;
    }

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

  /**
   * Checks whether an Event can be added into Scheduling System given its current state.
   * Event invitees that do not exist in model assumed
   * to have blank schedules, but are not actually added into model.
   *
   * @param host      host of Event
   * @param invitees  users added to Event (includes host)
   * @param eventName name of Event
   * @param location  location of Event
   * @param isOnline  online/offline status of Event
   * @param startDay  start day of event
   * @param startTime start time of Event
   * @param endDay    end day of Event
   * @param endTime   end time of Event
   * @return whether event can exist within scheduling system or not
   * @throws IllegalArgumentException if Event cannot be constructed due to invalid information
   */
  @Override
  public boolean eventConflict(String host, List<String> invitees,
                               String eventName, String location, boolean isOnline,
                               DaysOfTheWeek startDay, int startTime,
                               DaysOfTheWeek endDay, int endTime) {

    // allows eventConflict method to check Events that contain invitees that don't exist in model
    // those invitees assumed to have blank schedules
    List<String> notAddedUsers = new ArrayList<>();
    for (String user : invitees) {
      try {
        confirmUserExists(user);
      } catch (IllegalArgumentException caught) {
        addUser(user);
        notAddedUsers.add(user);
      }
    }

    // create new Event -> Event constructor will inform model whether params valid
    Event event = new NUEvent(invitees, eventName, location, isOnline,
            startDay, startTime,
            endDay, endTime);

    boolean isConflict;
    try {
      // just using Event obj internally
      checkOpenSpaceRelevantSchedules(event);
      isConflict = false;
    } catch (IllegalStateException caught) {
      isConflict = true;
    }

    // remove event invitees that do not exist in model yet from model
    for (String modEventUser : notAddedUsers) {
      this.removeUser(modEventUser);
    }

    return isConflict;

  }

  /**
   * Checks whether Event can be added into its invitees' Schedules.
   *
   * @param event Event to be added
   * @throws IllegalStateException if the Event conflicts with Events within Schedule
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
   * Observes all the Events contained within a user's Schedule.
   *
   * @param user name of user whose Schedule to return
   * @return Schedule belonging to that user
   * @throws IllegalArgumentException if user does not exist in scheduling system
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
   * @param user name of user whose Event to return
   * @return Event belonging to that user
   * @throws IllegalArgumentException if user does not exist in scheduling system
   */
  @Override
  public ReadableEvent eventAt(String user, DaysOfTheWeek startDay, int startTime) {
    confirmUserExists(user);
    return this.userSchedules.get(user).eventAt(startDay, startTime);

  }

  //  @Override
  //  public ReadableEvent eventAt(String user, Event eventToExtract) {
  //    confirmUserExists(user);
  //    return this.userSchedules.get(user).eventAt(eventToExtract);
  //  }

  /**
   * Confirms that a user exists in the Scheduling System.
   *
   * @param user user name
   * @throws IllegalArgumentException if user DNE in SS
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
   * @param user name of user
   * @throws IllegalArgumentException if user exists within the Scheduling System
   */
  private void confirmUserDoesNotExist(String user) throws IllegalArgumentException {
    if (user == null) {
      throw new IllegalArgumentException("Invalid input, please provide a user name... ");
    }
    if (this.userSchedules.containsKey(user)) {
      throw new IllegalArgumentException("User already exists in scheduling system... ");
    }
  }

}