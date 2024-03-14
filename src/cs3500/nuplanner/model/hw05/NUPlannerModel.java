package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entire system works through aliasing.
 */
public class NUPlannerModel implements SchedulingSystem {

  private final Map<String, Schedule> userSchedules;

  /**
   *
   */
  public NUPlannerModel() {
    userSchedules = new HashMap<>();
  }

  @Override
  public void addUser(String user) {
    confirmUserDoesNotExist(user);
    userSchedules.put(user, new NUSchedule(user));
  }

  @Override
  public void removeUser(String user) {
    confirmUserExists(user);
    userSchedules.remove(user);
  }

  @Override
  public List<String> allUsers() {
    List<String> allUsers = new ArrayList<>();
    for (Map.Entry<String, Schedule> entry : userSchedules.entrySet()) {
      allUsers.add(entry.getKey());
    }
    return allUsers;
  }

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

    eventConflict(host, invitees, eventName, location, isOnline, startDay, startTime, endDay,
            endTime);

    // create new Event -> Event constructor will inform model whether params valid
    Event event = new NUEvent(invitees, eventName, location, isOnline,
            startDay, startTime,
            endDay, endTime);


    placeEventRelevantSchedules(event);
  }

  private void checkOpenSpaceRelevantSchedules(Event event) {
    // for every invitee (including host)
    for (String invitee : event.eventInvitees()) {
      Schedule inviteeSchedule = this.userSchedules.get(invitee);
      if (inviteeSchedule.eventConflict(event)) {
        throw new IllegalStateException("Event cannot be added into scheduling system... " +
                "scheduling conflict with at least one invitee's schedule... ");
      }
    }
  }

  private void placeEventRelevantSchedules(Event event) {
    for (String invitee : event.eventInvitees()) {
      Schedule inviteeSchedule = this.userSchedules.get(invitee);
      inviteeSchedule.addEvent(event);
    }
  }

  @Override
  public void removeEvent(String user, DaysOfTheWeek startDay, int startTime) {
    confirmUserExists(user);

    // extract Event from relevant schedule
    Event eventToRemove = userSchedules.get(user).eventAt(startDay, startTime);

    List<String> invitees = eventToRemove.eventInvitees();

    // if user removing event from their schedule is host of the event
    if (invitees.get(0).equals(user)) {

      removeEventFromEverySchedule(invitees, eventToRemove);

    } else {

      removeEventFromSingleSchedule(user, eventToRemove);

    }

  }

  /**
   * Recursive.
   *
   * @param invitees
   * @param eventToRemove
   */
  private void removeEventFromEverySchedule(List<String> invitees, Event eventToRemove) {
    if (!invitees.isEmpty()) {
      Schedule inviteeSchedule = this.userSchedules.get(invitees.get(0));
      inviteeSchedule.removeEvent(eventToRemove); // method updates event's invitee list
      removeEventFromEverySchedule(eventToRemove.eventInvitees(), eventToRemove);
    }
  }

  private void removeEventFromSingleSchedule(String user, Event eventToRemove) {
    Schedule inviteeSchedule = this.userSchedules.get(user);
    inviteeSchedule.removeEvent(eventToRemove); // method updates event's invitee list
  }

  @Override
  public void modifyEvent(String user, DaysOfTheWeek startDay, int startTime, String modification) {

    // extract event-to-modify from user's schedule in scheduling system
    Event origEvent = userSchedules.get(user).eventAt(startDay, startTime);
    List<String> origInvitees = origEvent.eventInvitees();

    // create copy of event-to-modify to modify
    Event copyOfEvent = new NUEvent(origEvent);

    // interpret the modification request
    String[] tokens = modification.split("\\s+", 2);

    // if modification request wants to remove an invitee from the event-to-modify
    if (tokens[0].equals("removeinvitee")) {
      // confirm that the invitee to remove is spelled correctly (invitees all in event will exist)
      confirmUserExists(tokens[1]);
      // if non-host's request wants to remove host of an event, OK
      this.removeEvent(tokens[1], origEvent.startDay(), origEvent.startTime());
      return;
    }

    // remove event-to-modify from all user schedules - required for all other modifications
    // side-effect: event-to-modify has its internal invitees wiped <- workaround
    this.removeEvent(origEvent.host(), origEvent.startDay(), origEvent.startTime());

    // modify copy of event-to-remove
    try {
      performOtherModifications(copyOfEvent, modification);
    } catch (IllegalArgumentException | IllegalStateException caught) { // anything throws ISE???
      throw new IllegalArgumentException(
              "Cannot add this modified version of event in scheduling system... " + caught.getMessage());
    }

    // check if modified copy is compatible with scheduling system
    if (!this.eventConflict(copyOfEvent.host(),
            copyOfEvent.eventInvitees(), copyOfEvent.name(),
            copyOfEvent.location(), copyOfEvent.isOnline(),
            copyOfEvent.startDay(), copyOfEvent.startTime(),
            copyOfEvent.endDay(), copyOfEvent.endTime())) {
      // if so, add it! this is the "modified" event aka replaced
      this.addEvent(copyOfEvent.host(),
              copyOfEvent.eventInvitees(), copyOfEvent.name(),
              copyOfEvent.location(), copyOfEvent.isOnline(),
              copyOfEvent.startDay(), copyOfEvent.startTime(),
              copyOfEvent.endDay(), copyOfEvent.endTime());
      return;
    }

    // otherwise, add a copy of original event back into the schedule, with original invitees
    // orig invitees valid <-- it is prev. known that this entire event construction below IS VALID
    this.addEvent(origEvent.host(),
            origInvitees, origEvent.name(),
            origEvent.location(), origEvent.isOnline(),
            origEvent.startDay(), origEvent.startTime(),
            origEvent.endDay(), origEvent.endTime());

    throw new IllegalArgumentException(
            "Cannot add this modified version of event in scheduling system... ");

  }

  /*
  private method in schedule --> if checking same event, then ignore that event
  if event objs are all same --> no way for me to
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
    try {
      checkOpenSpaceRelevantSchedules(event);
      return false;
    } catch (IllegalStateException caught) {
      return true;
    }

  }

  private void performOtherModifications(Event event, String modification) {

    // assumption: modification string will look like
    // "name Sleep" "location Trueblue" "startday TUESDAY" "endtime 1200"

    String[] tokens = modification.split("\\s+", 2);

    switch (tokens[0]) {
      case "name":
        event.updateName(tokens[1]);
        break;
      case "location":
        event.updateLocation(tokens[1]);
        break;
      case "online":
        try {
          event.updateIsOnline(Boolean.parseBoolean(tokens[1]));
          break;
        } catch (NumberFormatException caught) {
          throw new IllegalArgumentException("Invalid modification for event online status...  ");
        }
      case "starttime":
        try {
          event.updateStartTime(Integer.parseInt(tokens[1]));
          break;
        } catch (NumberFormatException caught) {
          throw new IllegalArgumentException("Invalid modification for event start time...  ");
        }
      case "endtime":
        try {
          event.updateEndTime(Integer.parseInt(tokens[1]));
          break;
        } catch (NumberFormatException caught) {
          throw new IllegalArgumentException("Invalid modification for event online status...  ");
        }
      case "startday":
        event.updateStartDay(convertStringToDay(tokens[1]));
        break;
      case "endday":
        event.updateEndDay(convertStringToDay(tokens[1]));
        break;
      case "addinvitee":
        try {
          confirmUserExists(tokens[1]);
        } catch (IllegalArgumentException caught ) {
          this.addUser(tokens[1]);
        }
        event.addInvitee(tokens[1]);
        break;
      default:
        throw new IllegalArgumentException("Should not be reached... jk mod request weird ");
    }

  }

  private void confirmUserDoesNotExist(String user) throws IllegalArgumentException {
    if (this.userSchedules.containsKey(user)) {
      throw new IllegalArgumentException("User already exists in scheduling system... ");
    }
  }

  private DaysOfTheWeek convertStringToDay(String string) {

    switch (string.toUpperCase()) {
      case "SUNDAY":
        return DaysOfTheWeek.SUNDAY;
      case "MONDAY":
        return DaysOfTheWeek.MONDAY;
      case "TUESDAY":
        return DaysOfTheWeek.TUESDAY;
      case "WEDNESDAY":
        return DaysOfTheWeek.WEDNESDAY;
      case "THURSDAY":
        return DaysOfTheWeek.THURSDAY;
      case "FRIDAY":
        return DaysOfTheWeek.FRIDAY;
      case "SATURDAY":
        return DaysOfTheWeek.SATURDAY;
      default:
        throw new IllegalArgumentException("Invalid modification request... ");

    }

  }

  @Override
  public List<ReadableEvent> eventsInSchedule(String user) {

    confirmUserExists(user);

    // defensive copy and only readable version of events
    return new ArrayList<>(this.userSchedules.get(user).events());

  }

  private void confirmUserExists(String user) {
    if (!this.userSchedules.containsKey(user)) {
      throw new IllegalArgumentException(user + " does not exist in system... ");
    }
  }

  @Override
  public ReadableEvent eventAt(String user, DaysOfTheWeek startDay, int startTime) {
    confirmUserExists(user);
    return this.userSchedules.get(user).eventAt(startDay, startTime);
  }

}
