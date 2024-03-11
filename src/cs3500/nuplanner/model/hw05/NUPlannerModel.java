package cs3500.nuplanner.model.hw05;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NUPlannerModel implements SchedulingSystem {

  private final Map<String, Schedule> userSchedules;

  public NUPlannerModel() {
    userSchedules = new HashMap<>();
  }

  // XML schedule can overwrite an existing user (combo of removeUser + addUser to reset user sched)

  @Override
  public void addUser(String user) {
    // throw IAE if user already exists in hashmap <- no overwriting allowed
    if(this.userSchedules.containsKey(user)){
      throw new IllegalArgumentException("User already exists in scheduling system... ");
    }

    userSchedules.put(user, new NUSchedule(user));
  }

  @Override
  public void removeUser(String user) {
    if(!this.userSchedules.containsKey(user)){
      throw new IllegalArgumentException("User does not exist in scheduling system... ");
    }

    userSchedules.remove(user);
  }

  // questions:
  // can XML schedule overwrite existing user schedule
            // yes, but
  // can add event that has host that does not exist in database? no
  // same question for non-host invitees? yes

  // if XML has events w/ invitees that do not exist in planner, add them
  @Override
  public void addEventInSchedules(String user, List<String> invitees,
                                  String eventName, String location, boolean isOnline,
                                  DaysOfTheWeek startDay, int startTime,
                                  DaysOfTheWeek endDay, int endTime) {

    // if model removes a user --> user schedule events <-- remove all events from schedule <-- then delete user

    // BASIC CHECKS
    if(user == null || invitees == null || eventName == null || location == null ||
            startDay == null || endDay == null) {
      throw new IllegalArgumentException("Invalid event parameters... ");
    }

    // if user does not exist in scheduling system
    if (!this.userSchedules.containsKey(user)) {
      throw new IllegalArgumentException("Event host must be an existing user... ");
    }

    // XML specific check, if XML somehow has event where User is not first in invitees
    if (!user.equals(invitees.get(0))) {
      throw new IllegalArgumentException("Cannot create event on behalf of another user...  ");
    }

    // create new Event -> Event constructor will inform model whether params valid
    Event event = new NUEvent(invitees, eventName, location, isOnline,
            startDay, startTime,
            endDay, endTime);

    // create schedules for invitees that do not exist in scheduling system
    addInviteesToSchedulingSystem(invitees);

    // checks if every user has open space in their schedule for event
    // if not, throw an IAE
    checkForConflictsInviteeSchedules(invitees, event);

    // add event to all schedules
    addEventToInviteeSchedules(invitees, event);
  }

  // design decision: invited users not in planner will be forcefully added
  // why? to prevent bug where newly added user has many event conflicts in their schedule
  private void addInviteesToSchedulingSystem(List<String> invitees) {
    for (String invitee : invitees) {
      if (!this.userSchedules.containsKey(invitee)) this.addUser(invitee);
    }
  }

  private void checkForConflictsInviteeSchedules(List<String> invitees, Event event) {
    // for every invitee (including host)
    for (String invitee : invitees) {
      Schedule inviteeSchedule = this.userSchedules.get(invitee);
      if (inviteeSchedule.eventConflict(event)) {
        throw new IllegalArgumentException("Cannot add event... " +
                "scheduling conflict with a invitee... ");
      }
    }
  }

  private void addEventToInviteeSchedules(List<String> invitees, Event event) {
    for (String invitee : invitees) {
      Schedule inviteeSchedule = this.userSchedules.get(invitee);
      inviteeSchedule.addEvent(event);
    }
  }

  @Override
  public void removeEventInSchedules(String user, int eventIndex) {

    // extract event from relevant schedule
    Event eventToRemove = userSchedules.get(user).provideSingleEvent(eventIndex);

    List<String> invitees = eventToRemove.eventInvitees();

    // if user removing event from their schedule is host of the event
    if (invitees.get(0).equals(user)) {

      removeEventFromAllInviteeSchedules(invitees, eventToRemove);

    } else {

      removeEventFromSingleSchedule(user, eventToRemove);

    }

  }

  private void removeEventFromAllInviteeSchedules(List<String> invitees, Event eventToRemove) {
    for (String invitee : invitees) {
      Schedule inviteeSchedule = this.userSchedules.get(invitee);
      inviteeSchedule.removeEvent(eventToRemove); // method updates events invitee list
    }
  }

  private void removeEventFromSingleSchedule(String user, Event eventToRemove) {
    // remove event from single schedule
    Schedule inviteeSchedule = this.userSchedules.get(user);
    inviteeSchedule.removeEvent(eventToRemove); // method updates events invitee list

  }


  @Override
  public void modifyEventInSchedules(String user, int eventIndex, String modification) {

    // want to check that modification will not create time-conflicts in any invitee's schedules
    // if that happens, event cannot be modified in that manner/ IAE thrown

    // what if eventIndex (location) out of bounds?

    // remove event from all relevant schedules
    // modify copy --> see if copy can be added back
    // if can --> add modified copy to all events
    // if not --> add back copy event to schedules

    // get event to modify from view information (displaying userA schedule)
    Event eventToModify = userSchedules.get(user).provideSingleEvent(eventIndex);

    // copy of original event
    Event copyOfETM = new NUEvent(eventToModify);

    // modified version of copy of original event
    Event modifiedCopyOfETM = new NUEvent(copyOfETM);

    modifyEvent(modifiedCopyOfETM, modification);
    // Event job --> whenever event constructed/modified,
    // it should run checks on itself to verify
    // it is still valid event, otherwise throw error

    // remove eventToModify from ALL schedules
    removeEventFromAllInviteeSchedules(eventToModify.eventInvitees(), eventToModify);

    // check if modified event compatible w/ relevant schedules
    try {
      checkForConflictsInviteeSchedules(modifiedCopyOfETM.eventInvitees(), modifiedCopyOfETM);
    } catch (IllegalArgumentException caught) {
      // if modified event creates a conflict for other invitees
      // put back unmodified event into all schedules
      addEventToInviteeSchedules(copyOfETM.eventInvitees(), copyOfETM);
      throw new IllegalArgumentException("");
    }

    addEventToInviteeSchedules(modifiedCopyOfETM.eventInvitees(), modifiedCopyOfETM);
  }

  private void modifyEvent(Event modifiedCopyOfETM, String modification) {

    // how to modify an event?

  }

  @Override
  public void convertXMLtoSchedule() {

    // use addUser and addEvent methods above
    // will utilize oper. methods to construct new schedule
    // oper. methods will throw errors if given XML schedule invalid

  }

  @Override
  public void convertScheduleToXML() {

    // need to be able to get Events from Schedule, and get states from Events

  }

  @Override
  public Schedule provideUserSchedule(String user) {
    return null;
  }

}
