package cs3500.nuplanner.model.hw05;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// old knowledge:
// XML schedule canNOT overwrite an existing user (combo of removeUser + addUser to reset user sched)
// questions:
// can XML schedule overwrite existing user schedule
// yes, but
// can add event that has host that does not exist in database? no
// same question for non-host invitees? yesv
// XML CANNOT OVERWRITE EVENTS. XML USERS NOT EXISTING

public class NUPlannerModel implements SchedulingSystem {

  private final Map<String, Schedule> userSchedules;

  public NUPlannerModel() {
    userSchedules = new HashMap<>();
  }

  @Override
  public void addUser(String user) {
    // if user already exists in scheduling system
    if(this.userSchedules.containsKey(user)){
      throw new IllegalArgumentException("User already exists in scheduling system... ");
    }
    userSchedules.put(user, new NUSchedule(user));
  }

  @Override
  public void removeUser(String user) {
    checkIfUserExists(user);
    userSchedules.remove(user);
  }

  @Override // confirm user or provide all users?
  public List<String> allUsers() {

    List<String> allUsers = new ArrayList<>();

    for (Map.Entry<String, Schedule> entry : userSchedules.entrySet()) {
      allUsers.add(entry.getKey());
    }

    return allUsers;

  }

  // if XML has events w/ invitees that do not exist in planner, add them
  @Override
  public void addEventInSchedules(String user, List<String> invitees,
                                  String eventName, String location, boolean isOnline,
                                  DaysOfTheWeek startDay, int startTime,
                                  DaysOfTheWeek endDay, int endTime) {

    // BASIC CHECKS
    if(user == null || invitees == null || eventName == null || location == null ||
            startDay == null || endDay == null) {
      throw new IllegalArgumentException("Invalid event parameters... ");
    }

    // if user creates event manually, event can only invite existing users in system
    for (String invitee: invitees) {
      checkIfUserExists(invitee);
    }

    // create new Event -> Event constructor will inform model whether params valid
    Event event = new NUEvent(invitees, eventName, location, isOnline,
            startDay, startTime,
            endDay, endTime);

    // checks if every user has open space in their schedule for event
    checkForConflictsInviteeSchedules(invitees, event);

    // add event to all schedules
    addEventToInviteeSchedules(invitees, event);
  }

  private void checkForConflictsInviteeSchedules(List<String> invitees, Event event) {
    // for every invitee (including host)
    for (String invitee : invitees) {
      Schedule inviteeSchedule = this.userSchedules.get(invitee);
      if (inviteeSchedule.eventConflict(event)) {
        throw new IllegalArgumentException("Cannot add event... " +
                "scheduling conflict with at least one user... ");
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
  public void removeEventInSchedules(String user, int eventID) {

    checkIfUserExists(user);

    // extract Event from relevant schedule
    Event eventToRemove = userSchedules.get(user).eventAt(eventID);

    List<String> invitees = eventToRemove.eventInvitees();

    // if user removing event from their schedule is host of the event
    if (invitees.get(0).equals(user)) {

      removeEventFromAllInviteeSchedules(invitees, eventToRemove);

    } else {

      removeEventFromSingleSchedule(user, eventToRemove);

    }

  }

  private void removeEventFromAllInviteeSchedules(List<String> invitees, Event eventToRemove) {
    //    for (String invitee : invitees) { // can't remove invitees from list I'm currently iterating through
    //      Schedule inviteeSchedule = this.userSchedules.get(invitee);
    //      inviteeSchedule.removeEvent(eventToRemove); // method updates event's invitee list
    //    }
    if (!invitees.isEmpty()) {
      Schedule inviteeSchedule = this.userSchedules.get(invitees.get(0));
      inviteeSchedule.removeEvent(eventToRemove); // method updates event's invitee list
      removeEventFromAllInviteeSchedules(invitees, eventToRemove);
    }
  }

  private void removeEventFromSingleSchedule(String user, Event eventToRemove) {
    // remove event from single schedule
    Schedule inviteeSchedule = this.userSchedules.get(user);
    inviteeSchedule.removeEvent(eventToRemove); // method updates event's invitee list
  }

  @Override
  public void modifyEventInSchedules(String user, int eventID, String modification) {

    // want to check that modification will not create time-conflicts in any invitee's schedules
    // if that happens, event cannot be modified in that manner/ IAE thrown

    // bc event to modify exists in model, assuming all event invitees exist/ event is valid and can exist in all schedules accor to model cons

    // remove event from all relevant schedules
    // modify copy --> see if copy can be added back
    // if can --> add modified copy to all events
    // if not --> add back event to schedules

    //issue: running this event will cause an event's ID to change

    // get event to modify from view information (displaying userA schedule)
    Event eventToModify = userSchedules.get(user).eventAt(eventID);

    // modified version of copy of original event
    Event modifiedEvent = new NUEvent(eventToModify);

    modifyEvent(modifiedEvent, modification);

    // remove eventToModify from ALL schedules
    // invitees param redundant
    removeEventFromAllInviteeSchedules(eventToModify.eventInvitees(), eventToModify);

    // check if modified event compatible w/ relevant schedules
    try {
      checkForConflictsInviteeSchedules(modifiedEvent.eventInvitees(), modifiedEvent);
    } catch (IllegalArgumentException caught) {
      // if modified event creates a conflict for other invitees
      // put back unmodified event into all schedules

      // bug: unmodified event had all of its invitees removed
      eventToModify.eventInvitees();
      addEventToInviteeSchedules(eventToModify.eventInvitees(), eventToModify);
      throw new IllegalArgumentException("Modification to event not possible given relevant schedules... ");
    }

    addEventToInviteeSchedules(modifiedEvent.eventInvitees(), modifiedEvent);


  }

  /*
  private method in schedule --> if checking same event, then ignore that event
  if event objs are all same --> no way for me to

   */

  private void modifyEvent(Event modifiedCopyOfETM, String modification) {

    // assumption:
    // modification string will look like: "name Sleep" "location Trueblue" "startday TUESDAY" "endtime 1200"

    String[] tokens = modification.split("\\s+");
    if (tokens.length != 2) throw new IllegalArgumentException("Invalid modification request...  provide only param to change and change");

    switch(tokens[0]) {
      case "name":
        modifiedCopyOfETM.updateName(tokens[1]);
        break;
      case "location":
        modifiedCopyOfETM.updateLocation(tokens[1]);
        break;
      case "online":
        try {
          modifiedCopyOfETM.updateIsOnline(Boolean.parseBoolean(tokens[1]));
          break;
        } catch (NumberFormatException caught) {
          throw new IllegalArgumentException("Invalid modification for event online status...  ");
        }
      case "starttime":
        try {
          modifiedCopyOfETM.updateStartTime(Integer.parseInt(tokens[1]));
          break;
        } catch (NumberFormatException caught) {
          throw new IllegalArgumentException("Invalid modification for event start time...  ");
        }
      case "endtime":
        try {
          modifiedCopyOfETM.updateEndTime(Integer.parseInt(tokens[1]));
          break;
        } catch (NumberFormatException caught) {
          throw new IllegalArgumentException("Invalid modification for event online status...  ");
        }
      case "startday":
        modifiedCopyOfETM.updateStartDay(convertStringToDay(tokens[1]));
        break;
      case "endday":
        modifiedCopyOfETM.updateEndDay(convertStringToDay(tokens[1]));
        break;
      default:
        throw new IllegalArgumentException("Invalid modification request... ");
    }

  }

  private DaysOfTheWeek convertStringToDay(String string) {
    String wed = DaysOfTheWeek.WEDNESDAY.toString();

    if (string.equalsIgnoreCase(DaysOfTheWeek.SUNDAY.toString())) return DaysOfTheWeek.SUNDAY;
    if (string.equalsIgnoreCase(DaysOfTheWeek.MONDAY.toString())) return DaysOfTheWeek.MONDAY;
    if (string.equalsIgnoreCase(DaysOfTheWeek.TUESDAY.toString())) return DaysOfTheWeek.TUESDAY;
    if (string.equalsIgnoreCase(DaysOfTheWeek.WEDNESDAY.toString())) return DaysOfTheWeek.WEDNESDAY;
    if (string.equalsIgnoreCase(DaysOfTheWeek.THURSDAY.toString())) return DaysOfTheWeek.THURSDAY;
    if (string.equalsIgnoreCase(DaysOfTheWeek.FRIDAY.toString())) return DaysOfTheWeek.FRIDAY;
    if (string.equalsIgnoreCase(DaysOfTheWeek.SATURDAY.toString())) return DaysOfTheWeek.SATURDAY;

    throw new IllegalArgumentException("Invalid modification request... ");

  }




  @Override
  public List<Integer> eventIDsInSchedule(String user) {

    checkIfUserExists(user);

    return this.userSchedules.get(user).eventIDs();
  }

  @Override
  public Event eventAt(String user, int eventID) {

    checkIfUserExists(user);

    return this.userSchedules.get(user).eventAt(eventID);
  }

  private void checkIfUserExists(String user) {
    if (!this.userSchedules.containsKey(user))
      throw new IllegalArgumentException(user + " does not exist in system... ");
  }

  //  // invited users not in planner but mentioned in XML will be forcefully added
  //  private void addInviteesToSchedulingSystem(List<String> invitees) {
  //    for (String invitee : invitees) {
  //      if (!this.userSchedules.containsKey(invitee)) this.addUser(invitee);
  //    }
  //  }


  //-----------------------------------------------------------------------------------------------
  //-----------------------------------------------------------------------------------------------
  //-----------------------------------------------------------------------------------------------

  @Override
  public void convertXMLtoSchedule() throws IOException, SAXException, ParserConfigurationException {

    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document xmlDoc = builder.parse(new File("tutorial.xml"));
    xmlDoc.getDocumentElement().normalize();

    //looks for the data in the XML and calls the method to add the event
    List<Event> listOfAllXMLEvents = new ArrayList<>();

    //gets the list of all events
    NodeList listOfEvents = xmlDoc.getElementsByTagName("event");

    //runs through all events and gets their child nodes and also creates the schedule
    for (int i = 0; i < listOfEvents.getLength(); i++) {
      NodeList currentEventDetails = listOfEvents.item(i).getChildNodes();

      //creating a list of invitees for the method
      NodeList users = currentEventDetails.item(3).getChildNodes();
      List<String> invitees = new ArrayList<>();
      for (int l = 0; l < users.getLength(); l++) {
        invitees.add(users.item(l).getNodeValue());
      }

      String name =
              currentEventDetails.item(0).getAttributes().item(1).getNodeValue();
      String location =
              currentEventDetails.item(2).getAttributes().item(1).getNodeValue();
      boolean isOnline =
              Boolean.parseBoolean(currentEventDetails.item(2).getAttributes().item(0).getNodeValue());
      DaysOfTheWeek startDay =
              createDay(currentEventDetails.item(1).getAttributes().item(0).getNodeValue());
      int startTime =
              Integer.parseInt(currentEventDetails.item(1).getAttributes().item(1).getNodeValue());
      DaysOfTheWeek endDay =
              createDay(currentEventDetails.item(1).getAttributes().item(2).getNodeValue());
      int endTime =
              Integer.parseInt(currentEventDetails.item(1).getAttributes().item(3).getNodeValue());


      // create schedules for invitees that do not exist in scheduling system
      addInviteesToSchedulingSystem(invitees);

      //create the event
      Event event = new NUEvent(invitees, name, location, isOnline, startDay, startTime, endDay,
              endTime);

      //add the event to the list of events
      listOfAllXMLEvents.add(event);
    }

    // checks if every user has open space in their schedule for event
    for (Event listOfAllXMLEvent : listOfAllXMLEvents) {
      try {
        checkForConflictsInviteeSchedules(listOfAllXMLEvent.eventInvitees(),
                listOfAllXMLEvent);
      } catch (IllegalArgumentException ex) {
        throw new IllegalArgumentException(ex.getMessage() + "Invalid XML schedule uploaded");
      }
    }

    // add event to all schedules
    for (Event listOfAllXMLEvent : listOfAllXMLEvents) {
      addEventToInviteeSchedules(listOfAllXMLEvent.eventInvitees(),
              listOfAllXMLEvent);
    }
  }

  @Override
  public void convertScheduleToXML() {

    List<Event> listOfUsersEvents = new ArrayList<>();

    try {
      Writer file = new FileWriter("sample-written.xml");
      file.write("<?xml version=\"1.0\"?>\n");
      file.write("<schedule id=\"SOMETHINGGOESHEREAGHHHH\">");

      for (int i = 0; i < provideUserSchedule().numberOfEvents(); i++) {
        listOfUsersEvents.add(provideUserSchedule().provideSingleEvent(i));
      }

      for (Event listOfUsersEvents : listOfUsersEvents) {
        file.write("<event>");
        file.write("<name>" + provideUserSchedule().event.name() + "</name>");

        file.write("<time>");
        file.write("<start-day>" + provideUserSchedule().event.startDay().toString + "</start-day" +
                ">");
        file.write("<start>" + provideUserSchedule().event.startTime() + "</start>");
        file.write("<end-day>" + provideUserSchedule().event.endDay().toString + "</end-day>");
        file.write("<end>" + provideUserSchedule().event.endTime() + "</end>");
        file.write("</time>");

        file.write("<location>");
        file.write("<online>" + provideUserSchedule().event.isOnline() + "</online>");
        file.write("<start>" + provideUserSchedule().event.location() + "</start>");
        file.write("</location>");

        file.write("<users>");
        for (eventInvitees) {
          file.write("<uid>" + provideUserSchedule().event.invitees() + "</uid>");
        }
        file.write("</users>");
      }

      file.write("</schedule>");
      file.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  /**
   * Small factory method that returns the enum representing
   * the day of the week given a string.
   *
   * @param day the day of the week in string format
   * @return an enum representing the day of the week
   */
  private static DaysOfTheWeek createDay(String day) {
    if (day.equals(SUNDAY.toString())) {
      return SUNDAY;
    }
    if (day.equals(MONDAY.toString())) {
      return MONDAY;
    }
    if (day.equals(TUESDAY.toString())) {
      return TUESDAY;
    }
    if (day.equals(WEDNESDAY.toString())) {
      return WEDNESDAY;
    }
    if (day.equals(THURSDAY.toString())) {
      return THURSDAY;
    }
    if (day.equals(FRIDAY.toString())) {
      return FRIDAY;
    }
    if (day.equals(SATURDAY.toString())) {
      return SATURDAY;
    }
    throw new IllegalArgumentException("Not a day of the week!");
  }

}
