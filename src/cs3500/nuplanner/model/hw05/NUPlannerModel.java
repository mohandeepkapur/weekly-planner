package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
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
    for (String user: invitees) {
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

    eventConflict(host, invitees, eventName, location, isOnline, startDay, startTime, endDay, endTime);

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
      throw new IllegalArgumentException("Cannot add this modified version of event in scheduling system... " + caught.getMessage());
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
    // orig invitees valid <-- it is known that this entire event construction below IS VALID
    this.addEvent(origEvent.host(),
            origInvitees, origEvent.name(),
            origEvent.location(), origEvent.isOnline(),
            origEvent.startDay(), origEvent.startTime(),
            origEvent.endDay(), origEvent.endTime());

    throw new IllegalArgumentException("Cannot add this modified version of event in scheduling system... ");

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

    switch(tokens[0]) {
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
        confirmUserExists(tokens[1]);
        event.addInvitee(tokens[1]);
        break;
      default:
        throw new IllegalArgumentException("Should not be reached... jk mod request weird ");
    }

  }

  private void confirmUserDoesNotExist(String user) throws IllegalArgumentException{
    if(this.userSchedules.containsKey(user)){
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

  //  @Override
  //  public List<Integer> eventIDsInSchedule(String user) {
  //
  //    confirmUserExists(user);
  //
  //    return this.userSchedules.get(user).eventIDs();
  //  }

  @Override
  public List<ReadableEvent> eventsInSchedule(String user) {

    confirmUserExists(user);

    // defensive copy and only readable version of events
    return new ArrayList<>(this.userSchedules.get(user).events());

  }

  //  @Override
  //  public Event eventAt(String user, int eventID) {
  //
  //    confirmUserExists(user);
  //
  //    return this.userSchedules.get(user).eventAt(eventID); // HERE DOWNCAST
  //  }


  private void confirmUserExists(String user) {
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

  // if XML has events w/ invitees that do not exist in planner, add them
  @Override
  public void convertXMLtoSchedule() { //throws IOException, SAXException, ParserConfigurationException

    //    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    //    Document xmlDoc = builder.parse(new File("tutorial.xml"));
    //    xmlDoc.getDocumentElement().normalize();
    //
    //    //looks for the data in the XML and calls the method to add the event
    //    List<Event> listOfAllXMLEvents = new ArrayList<>();
    //
    //    //gets the list of all events
    //    NodeList listOfEvents = xmlDoc.getElementsByTagName("event");
    //
    //    //runs through all events and gets their child nodes and also creates the schedule
    //    for (int i = 0; i < listOfEvents.getLength(); i++) {
    //      NodeList currentEventDetails = listOfEvents.item(i).getChildNodes();
    //
    //      //creating a list of invitees for the method
    //      NodeList users = currentEventDetails.item(3).getChildNodes();
    //      List<String> invitees = new ArrayList<>();
    //      for (int l = 0; l < users.getLength(); l++) {
    //        invitees.add(users.item(l).getNodeValue());
    //      }
    //
    //      String name =
    //              currentEventDetails.item(0).getAttributes().item(1).getNodeValue();
    //      String location =
    //              currentEventDetails.item(2).getAttributes().item(1).getNodeValue();
    //      boolean isOnline =
    //              Boolean.parseBoolean(currentEventDetails.item(2).getAttributes().item(0).getNodeValue());
    //      DaysOfTheWeek startDay =
    //              createDay(currentEventDetails.item(1).getAttributes().item(0).getNodeValue());
    //      int startTime =
    //              Integer.parseInt(currentEventDetails.item(1).getAttributes().item(1).getNodeValue());
    //      DaysOfTheWeek endDay =
    //              createDay(currentEventDetails.item(1).getAttributes().item(2).getNodeValue());
    //      int endTime =
    //              Integer.parseInt(currentEventDetails.item(1).getAttributes().item(3).getNodeValue());
    //
    //
    //      // create schedules for invitees that do not exist in scheduling system
    //      addInviteesToSchedulingSystem(invitees);
    //
    //      //create the event
    //      Event event = new NUEvent(invitees, name, location, isOnline, startDay, startTime, endDay,
    //              endTime);
    //
    //      //add the event to the list of events
    //      listOfAllXMLEvents.add(event);
    //    }
    //
    //    // checks if every user has open space in their schedule for event
    //    for (Event listOfAllXMLEvent : listOfAllXMLEvents) {
    //      try {
    //        checkForConflictsInviteeSchedules(listOfAllXMLEvent.eventInvitees(),
    //                listOfAllXMLEvent);
    //      } catch (IllegalArgumentException ex) {
    //        throw new IllegalArgumentException(ex.getMessage() + "Invalid XML schedule uploaded");
    //      }
    //    }
    //
    //    // add event to all schedules
    //    for (Event listOfAllXMLEvent : listOfAllXMLEvents) {
    //      addEventToInviteeSchedules(listOfAllXMLEvent.eventInvitees(),
    //              listOfAllXMLEvent);
    //    }
  }

  @Override
  public void convertScheduleToXML() {

    //    List<Event> listOfUsersEvents = new ArrayList<>();
    //
    //    try {
    //      Writer file = new FileWriter("sample-written.xml");
    //      file.write("<?xml version=\"1.0\"?>\n");
    //      file.write("<schedule id=\"SOMETHINGGOESHEREAGHHHH\">");
    //
    //      for (int i = 0; i < provideUserSchedule().numberOfEvents(); i++) {
    //        listOfUsersEvents.add(provideUserSchedule().provideSingleEvent(i));
    //      }
    //
    //      for (Event listOfUsersEvents : listOfUsersEvents) {
    //        file.write("<event>");
    //        file.write("<name>" + provideUserSchedule().event.name() + "</name>");
    //
    //        file.write("<time>");
    //        file.write("<start-day>" + provideUserSchedule().event.startDay().toString + "</start-day" +
    //                ">");
    //        file.write("<start>" + provideUserSchedule().event.startTime() + "</start>");
    //        file.write("<end-day>" + provideUserSchedule().event.endDay().toString + "</end-day>");
    //        file.write("<end>" + provideUserSchedule().event.endTime() + "</end>");
    //        file.write("</time>");
    //
    //        file.write("<location>");
    //        file.write("<online>" + provideUserSchedule().event.isOnline() + "</online>");
    //        file.write("<start>" + provideUserSchedule().event.location() + "</start>");
    //        file.write("</location>");
    //
    //        file.write("<users>");
    //        for (eventInvitees) {
    //          file.write("<uid>" + provideUserSchedule().event.invitees() + "</uid>");
    //        }
    //        file.write("</users>");
    //      }
    //
    //      file.write("</schedule>");
    //      file.close();
    //    } catch (IOException ex) {
    //      throw new RuntimeException(ex.getMessage());
    //    }
  }

  //  /**
  //   * Small factory method that returns the enum representing
  //   * the day of the week given a string.
  //   *
  //   * @param day the day of the week in string format
  //   * @return an enum representing the day of the week
  //   */
  //  private static DaysOfTheWeek createDay(String day) {
  //    if (day.equals(SUNDAY.toString())) {
  //      return SUNDAY;
  //    }
  //    if (day.equals(MONDAY.toString())) {
  //      return MONDAY;
  //    }
  //    if (day.equals(TUESDAY.toString())) {
  //      return TUESDAY;
  //    }
  //    if (day.equals(WEDNESDAY.toString())) {
  //      return WEDNESDAY;
  //    }
  //    if (day.equals(THURSDAY.toString())) {
  //      return THURSDAY;
  //    }
  //    if (day.equals(FRIDAY.toString())) {
  //      return FRIDAY;
  //    }
  //    if (day.equals(SATURDAY.toString())) {
  //      return SATURDAY;
  //    }
  //    throw new IllegalArgumentException("Not a day of the week!");
  //  }

  // public boolean checkEventCausingConflict() {} <-- so XML-stuff can be separated from model

  // if XML fails <-- remove newly added users <-- store that information so removal works
  // wait, how does removing user work

}

//removeEvent(String user, int eventID); // modify events -> constructing new event
//removeEvent(String user, DaysOfTheWeek day, int startTime);

// <-- moving to user start day start time to access event specifically
//