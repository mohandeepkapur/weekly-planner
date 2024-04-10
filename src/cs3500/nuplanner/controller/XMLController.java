package cs3500.nuplanner.controller;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.strategies.SchedulingStrategies;

/**
 * Controller that takes in XML input and manipulates model(sched. sys)-state.
 */
public class XMLController implements SchedulingSystemController {

  private final SchedulingSystem model;

  /**
   * Constructs an XML Controller.
   * @param model         Scheduling System
   */
  public XMLController(SchedulingSystem model) {
    this.model = model;
  }

  /**
   * Runs scheduling system using user input.
   */
  @Override
  public void useSchedulingSystem(SchedulingSystem model, SchedulingStrategies strategy) {
    throw new IllegalArgumentException("XML Controller does not run on user input... ");
  }

  /**
   * Runs scheduling system using XML file.
   *
   * @param pathname                     path to XML file
   * @throws IllegalStateException       if unable to open or parse XML file
   */
  @Override
  public void useSchedulingSystem(String pathname, SchedulingStrategies strategy) {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(new File(pathname));
      xmlDoc.getDocumentElement().normalize();

      //looks for the data in the XML and calls the method to add the event
      List<Event> listOfAllXMLEvents = new ArrayList<>();

      //gets the list of all events
      NodeList listOfEvents = xmlDoc.getElementsByTagName("event");

      //runs through all events and gets their child nodes and also creates the schedule
      for (int item = 0; item < listOfEvents.getLength(); item++) {
        if (listOfEvents.item(item).getNodeType() != Node.ELEMENT_NODE) {
          throw new IllegalArgumentException("Invalid XML");
        }
        Element currentEventDetails = (Element) listOfEvents.item(item);

        String name = currentEventDetails.getElementsByTagName("name").item(0).getTextContent();
        String location = currentEventDetails.getElementsByTagName("place").item(0)
                .getTextContent();
        boolean isOnline = Boolean.parseBoolean(
                currentEventDetails.getElementsByTagName("online").item(0).getTextContent());
        DaysOfTheWeek startDay = DaysOfTheWeek.stringToDay(
                currentEventDetails.getElementsByTagName("start-day").item(0).getTextContent()
                        .toUpperCase());
        int startTime = Integer.parseInt(
                currentEventDetails.getElementsByTagName("start").item(0).getTextContent());
        DaysOfTheWeek endDay = DaysOfTheWeek.stringToDay(
                currentEventDetails.getElementsByTagName("end-day").item(0).getTextContent()
                        .toUpperCase());
        int endTime = Integer.parseInt(
                currentEventDetails.getElementsByTagName("end").item(0).getTextContent());
        List<String> invitees = new ArrayList<>();

        //creating a list of invitees for the method
        NodeList users = currentEventDetails.getElementsByTagName("users").item(0).getChildNodes();

        for (int indivudals = 0; indivudals < users.getLength(); indivudals++) {
          if (users.item(indivudals).getNodeType() == Node.ELEMENT_NODE) {
            invitees.add(users.item(indivudals).getTextContent());
          }
        }

        // create schedules for invitees that do not exist in scheduling system
        // addInviteesToSchedulingSystem(invitees);

        //create the event
        Event event = new NUEvent(invitees, name, location, isOnline, startDay, startTime, endDay,
                endTime);

        //add the event to the list of events
        listOfAllXMLEvents.add(event);
      }

      List<String> currentUsersInSchedSys = this.model.allUsers();
      List<String> xmlAddedUsersInSchedSys = new ArrayList<>();

      for (Event event : listOfAllXMLEvents) {
        for (String invitee : event.eventInvitees()) {
          if (!currentUsersInSchedSys.contains(
                  invitee)) { // current users list does not update w/ this loop info
            this.model.addUser(invitee);
            currentUsersInSchedSys.add(invitee);
            xmlAddedUsersInSchedSys.add(invitee);
          }
        }
      }

      // get list of all users xml wants to add into scheduling system
      // add them all pre-emptively
      // if xml upload becomes unsuccessful, remove all those users from the system

      for (Event event : listOfAllXMLEvents) {
        // if xml can't be uploaded due to one event w/ incompatible time
        if (this.model.eventConflict(event.host(),
                event.eventInvitees(), event.name(),
                event.location(), event.isOnline(),
                event.startDay(), event.startTime(),
                event.endDay(), event.endTime())) {
          // revert sched sys back to previous state
          for (String user : xmlAddedUsersInSchedSys) {
            this.model.removeUser(user);
          }
          throw new IllegalArgumentException("Invalid XML schedule uploaded");
        }
      }

      for (Event event : listOfAllXMLEvents) {
        this.model.addEvent(event.host(),
                event.eventInvitees(), event.name(),
                event.location(), event.isOnline(),
                event.startDay(), event.startTime(),
                event.endDay(), event.endTime());
      }

    } catch (ParserConfigurationException ex) {
      throw new IllegalStateException("Error in creating the builder");
    } catch (IOException ioEx) {
      throw new IllegalStateException("Error in opening the file");
    } catch (SAXException saxEx) {
      throw new IllegalStateException("Error in parsing the file");
    }

  }

}
