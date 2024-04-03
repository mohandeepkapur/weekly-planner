package cs3500.nuplanner;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.SchedulingSystemTextView;
import cs3500.nuplanner.view.SchedulingSystemView;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.FRIDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SATURDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.WEDNESDAY;
import static org.junit.Assert.assertEquals;

/**
 * A class to test the functionality of the simple textual view for the scheduling system.
 */

public class TestView {

  @Test
  public void ensureUserScheduleTextualRenderCorrect() {
    SchedulingSystem model = new NUPlannerModel();

    //check that Elaine as a host can be displayed
    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia", "Mo")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);

    //check that events next to each other can be displayed
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Badminton",
            "Marino Recreation", false,
            TUESDAY, 1000, TUESDAY, 1100);

    //check that Elaine can be displayed but is not the host
    //check that events can span multiple days but show on start day
    model.addEvent("Mo", new ArrayList<>(List.of("Mo", "Elaine")), "Basketweaving",
            "Carter Space", true,
            FRIDAY, 800, SATURDAY, 1000);

    Appendable out = new StringBuilder();
    SchedulingSystemView view = new SchedulingSystemTextView(model, out);

    try {
      view.render("Elaine");
    } catch (IOException caught) {
      // do nothing. assertEquals will catch inability to write
    }

    String expected = "User: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "       name: Tennis\n" +
            "       time: TUESDAY: 800 -> TUESDAY: 1000\n" +
            "       location: Carter Field\n" +
            "       online: true\n" +
            "       invitees: \n" +
            "              Elaine\n" +
            "              Mia\n" +
            "              Mo\n" +
            "       name: Badminton\n" +
            "       time: TUESDAY: 1000 -> TUESDAY: 1100\n" +
            "       location: Marino Recreation\n" +
            "       online: false\n" +
            "       invitees: \n" +
            "              Elaine\n" +
            "              Mia\n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "       name: Basketweaving\n" +
            "       time: FRIDAY: 800 -> SATURDAY: 1000\n" +
            "       location: Carter Space\n" +
            "       online: true\n" +
            "       invitees: \n" +
            "              Mo\n" +
            "              Elaine\n" +
            "Saturday: ";

    assertEquals(expected, out.toString());
  }

  @Test
  public void testValidTextualViewEventGoesIntoNextWeek() {
    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            SATURDAY, 800, WEDNESDAY, 1000);

    Appendable out = new StringBuilder();
    SchedulingSystemView view = new SchedulingSystemTextView(model, out);

    try {
      view.render("Elaine");
    } catch (IOException caught) {
      // do nothing. assertEquals will catch inability to write
    }
    String expected = "User: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n" +
            "       name: Tennis\n" +
            "       time: SATURDAY: 800 -> WEDNESDAY: 1000\n" +
            "       location: Carter Field\n" +
            "       online: true\n" +
            "       invitees: \n" +
            "              Elaine\n" +
            "              Mia";

    assertEquals(expected, out.toString());
  }

  @Test
  public void testValidTextualViewNoEventsInSchedule() {
    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Elaine");

    Appendable out = new StringBuilder();
    SchedulingSystemView view = new SchedulingSystemTextView(model, out);

    try {
      view.render("Elaine");
    } catch (IOException caught) {
      // do nothing. assertEquals will catch inability to write
    }
    String expected = "User: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: ";

    assertEquals(expected, out.toString());
  }

  @Test
  public void testValidTextualViewAddEvent() {
    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Elaine");

    Appendable out = new StringBuilder();
    SchedulingSystemView view = new SchedulingSystemTextView(model, out);

    try {
      view.render("Elaine");
    } catch (IOException caught) {
      // do nothing. assertEquals will catch inability to write
    }
    String original = "User: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: ";

    assertEquals(original, out.toString());

    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            SATURDAY, 800, WEDNESDAY, 1000);

    try {
      view.render("Elaine");
    } catch (IOException caught) {
      // do nothing. assertEquals will catch inability to write
    }
    String updated = "User: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: User: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n" +
            "       name: Tennis\n" +
            "       time: SATURDAY: 800 -> WEDNESDAY: 1000\n" +
            "       location: Carter Field\n" +
            "       online: true\n" +
            "       invitees: \n" +
            "              Elaine\n" +
            "              Mia";

    assertEquals(updated, out.toString());
  }

  @Test
  public void testValidTextualViewRemoveEvent() {
    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            SATURDAY, 800, WEDNESDAY, 1000);

    Appendable out = new StringBuilder();
    SchedulingSystemView view = new SchedulingSystemTextView(model, out);

    try {
      view.render("Elaine");
    } catch (IOException caught) {
      // do nothing. assertEquals will catch inability to write
    }
    String original = "User: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n" +
            "       name: Tennis\n" +
            "       time: SATURDAY: 800 -> WEDNESDAY: 1000\n" +
            "       location: Carter Field\n" +
            "       online: true\n" +
            "       invitees: \n" +
            "              Elaine\n" +
            "              Mia";

    assertEquals(original, out.toString());

    model.removeEvent("Elaine", , SATURDAY);

    try {
      view.render("Elaine");
    } catch (IOException caught) {
      // do nothing. assertEquals will catch inability to write
    }
    String updated = "User: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n" +
            "       name: Tennis\n" +
            "       time: SATURDAY: 800 -> WEDNESDAY: 1000\n" +
            "       location: Carter Field\n" +
            "       online: true\n" +
            "       invitees: \n" +
            "              Elaine\n" +
            "              MiaUser: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: ";

    assertEquals(updated, out.toString());
  }

  @Test
  public void testValidTextualViewModifyEvent() {
    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia")), "Tennis",
            "Carter Field", true,
            SATURDAY, 800, WEDNESDAY, 1000);

    Appendable out = new StringBuilder();
    SchedulingSystemView view = new SchedulingSystemTextView(model, out);

    try {
      view.render("Elaine");
    } catch (IOException caught) {
      // do nothing. assertEquals will catch inability to write
    }
    String original = "User: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n" +
            "       name: Tennis\n" +
            "       time: SATURDAY: 800 -> WEDNESDAY: 1000\n" +
            "       location: Carter Field\n" +
            "       online: true\n" +
            "       invitees: \n" +
            "              Elaine\n" +
            "              Mia";

    assertEquals(original, out.toString());

    model.modifyEvent("Elaine", SATURDAY, 800, "starttime 700");

    try {
      view.render("Elaine");
    } catch (IOException caught) {
      // do nothing. assertEquals will catch inability to write
    }
    String updated = "User: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n" +
            "       name: Tennis\n" +
            "       time: SATURDAY: 800 -> WEDNESDAY: 1000\n" +
            "       location: Carter Field\n" +
            "       online: true\n" +
            "       invitees: \n" +
            "              Elaine\n" +
            "              MiaUser: Elaine\n" +
            "Sunday: \n" +
            "Monday: \n" +
            "Tuesday: \n" +
            "Wednesday: \n" +
            "Thursday: \n" +
            "Friday: \n" +
            "Saturday: \n" +
            "       name: Tennis\n" +
            "       time: SATURDAY: 700 -> WEDNESDAY: 1000\n" +
            "       location: Carter Field\n" +
            "       online: true\n" +
            "       invitees: \n" +
            "              Elaine\n" +
            "              Mia";

    assertEquals(updated, out.toString());
  }
}