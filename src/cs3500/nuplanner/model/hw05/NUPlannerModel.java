package cs3500.nuplanner.model.hw05;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.FRIDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SATURDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SUNDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.THURSDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.WEDNESDAY;

public class NUPlannerModel implements SchedulingSystem {

  private final Map<String, Schedule> userSchedules;

  public NUPlannerModel() {
    userSchedules = new HashMap<>();
  }

  // XML schedule can overwrite an existing user (combo of removeUser + addUser to reset user sched)

  @Override
  public void addUser(String user) {
    // throw IAE if user already exists in hashmap <- no overwriting allowed
    if (this.userSchedules.containsKey(user)) {
      throw new IllegalArgumentException("User already exists in scheduling system... ");
    }

    userSchedules.put(user, new NUSchedule(user));
  }

  @Override
  public void removeUser(String user) {
    if (!this.userSchedules.containsKey(user)) {
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
    if (user == null || invitees == null || eventName == null || location == null ||
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

  @Override
  public Schedule provideUserSchedule(String user) {
    if (!this.userSchedules.containsKey(user)) {
      throw new IllegalArgumentException("User does not exist in scheduling system... ");
    }

    return this.userSchedules.get(user);
  }

}
