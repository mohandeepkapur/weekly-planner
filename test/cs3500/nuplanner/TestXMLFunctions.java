package cs3500.nuplanner;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import cs3500.nuplanner.controller.SchedulingSystemController;
import cs3500.nuplanner.controller.SchedulingSystemXMLController;
import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.Schedule;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.SchedulingSystemView;
import cs3500.nuplanner.view.SchedulingSystemXMLView;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.FRIDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SATURDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SUNDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.WEDNESDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the functionality of XML related commands in the scheduling system.
 */

public class TestXMLFunctions {

  private SchedulingSystem model;

  private SchedulingSystemView xmlView;

  private SchedulingSystemController xmlController;

  @Before
  public void setFields() {

    model = new NUPlannerModel();
    xmlController = new SchedulingSystemXMLController(model);
    xmlView = new SchedulingSystemXMLView(model);

  }

  @Test
  public void testXMLToScheduleCheckValuesOfFirstEvent() {

    xmlController.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");
    ReadableEvent first = model.eventAt("Prof. Lucia", TUESDAY, 950);
    assertEquals("CS3500 Morning Lecture", first.name());
    assertEquals(TUESDAY, first.startDay());
    assertEquals(950, first.startTime());
    assertEquals(TUESDAY, first.endDay());
    assertEquals(1130, first.endTime());
    assertFalse(first.isOnline());
    assertEquals("Churchill Hall 101", first.location());
    List<String> invitees = Arrays.asList("Prof. Lucia", "Student Anon", "Chat");
    assertEquals(invitees, first.eventInvitees());

  }

  @Test
  public void testXMLToScheduleCheckValuesOfThirdEvent() {
    xmlController.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");
    ReadableEvent event = model.eventAt("Prof. Lucia", FRIDAY, 1800);

    assertEquals("Sleep", event.name());
    assertEquals(FRIDAY, event.startDay());
    assertEquals(1800, event.startTime());
    assertEquals(SUNDAY, event.endDay());
    assertEquals(1200, event.endTime());
    assertTrue(event.isOnline());
    assertEquals("Home", event.location());
    List<String> invitees = List.of("Prof. Lucia");
    assertEquals(invitees, event.eventInvitees());
  }

  @Test
  public void testXMLToScheduleAddedCorrectNumberOfUsers() {

    assertEquals(0, model.allUsers().size());

    xmlController.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");

    assertEquals(3, model.allUsers().size());

  }

  @Test
  public void testXMLToScheduleAddedCorrectNumberOfEvents() {

  }


  @Test
  public void testXMLToScheduleHostHasTimeConflict() {

    model.addUser("Prof. Lucia");

    model.addEvent("Prof. Lucia", new ArrayList<>(List.of("Prof. Lucia", "Mo")),
            "BasketWeaving", "Sea", true, FRIDAY, 1000, SATURDAY, 1000);

    try {
      xmlController.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");
    } catch (IllegalArgumentException ignore) {
    }

    assertEquals(2, model.allUsers().size());

  }

  @Test
  public void testXMLToScheduleHostHasNoTimeConflict() {
    model.addUser("Prof. Lucia");

    model.addEvent("Prof. Lucia", new ArrayList<>(List.of("Prof. Lucia", "Mo")),
            "BasketWeaving", "Sea", true, SUNDAY, 900, SUNDAY, 1000);

    xmlController.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");

    assertEquals(4, model.allUsers().size());
  }

  @Test
  public void testScheduleToXML() {
    model.addUser("Prof. Lucia");

    model.addEvent("Prof. Lucia", new ArrayList<>(List.of("Prof. Lucia", "Mo")),
            "BasketWeaving", "Sea", true, SUNDAY, 900, SUNDAY, 1000);

    xmlController.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");

    assertEquals(4, model.allUsers().size());

    xmlView.render("Prof. Lucia");

  }

  @Test
  public void testScheduleToXMLWritesToMiaFile() {
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Mia");
    model.addEvent("Mia", new ArrayList<String>(List.of("Mia", "Christina")),
            "FINA 4412", "Forsyth", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    assertEquals("FINA 4412", model.eventAt("Mia", WEDNESDAY, 1000).name());

    SchedulingSystemView xmlView = new SchedulingSystemXMLView(model);

    xmlView.render("Mia");
  }

  @Test
  public void testScheduleToXMLReadsFromMiaFile() {
    xmlController.useSchedulingSystem("XMLFiles/toWrite/Mia.xml");
    System.out.print(model.allUsers());
    ReadableEvent event = model.eventAt("Mia", WEDNESDAY, 1000);

    assertEquals("FINA 4412", event.name());
  }

  @Test
  public void testAllPreEmptivelyAddedUsersRemovedFromSchedulingSystemOnceXMLUploadFails() {
    // implicitly tested above
  }

  @Test
  public void renderExistingUserScheduleInXML() {
    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Mo");

    model.addEvent("Mo", new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    model.addEvent("Mo", new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Basketball", "Bruh Quad", true,
            DaysOfTheWeek.TUESDAY, 1100, DaysOfTheWeek.TUESDAY, 1140);

    model.addEvent("Jo", new ArrayList<String>(List.of("Jo", "Ko")),
            "Chess", "Shillman Hall", true,
            DaysOfTheWeek.THURSDAY, 1030, DaysOfTheWeek.THURSDAY, 1130);

    SchedulingSystemView xmlView = new SchedulingSystemXMLView(model);

    xmlView.render("Mo");
  }

}
