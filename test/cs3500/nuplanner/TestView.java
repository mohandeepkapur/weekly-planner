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
import cs3500.nuplanner.view.SchedulingSystemTextView;
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


public class TestView {

  @Test
  public void ensureUserScheduleTextualRenderCorrect() {
    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Elaine");
    model.addEvent("Elaine", new ArrayList<>(List.of("Elaine", "Mia", "Mo")), "Tennis",
            "Carter Field", true,
            TUESDAY, 800, TUESDAY, 1000);

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



}