package cs3500.nuplanner;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.SchedulingSystemTextView;
import cs3500.nuplanner.view.SchedulingSystemView;
import cs3500.nuplanner.view.SchedulingSystemXMLView;

import static org.junit.Assert.assertTrue;
//shsss
public class TestNUPlannerModel {

  // basic throws: IAE if specified user doesn't exist (when it should)
  // prevent overwrites since using map

  @Test
  public void addSchedulesIntoSchedSystem() {

    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Mo"); // Ko and Jo implicitly added into system through Mo

    model.addEvent("Mo", new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    model.addEvent("Jo", new ArrayList<String>(List.of("Jo", "Ko")),
            "Chess", "Shillman Hall", true,
            DaysOfTheWeek.WEDNESDAY, 1030, DaysOfTheWeek.WEDNESDAY, 1130);

  }

  @Test
  public void checkViewWorks() {

    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Mo");

    model.addEvent("Mo", new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    model.addEvent("Jo", new ArrayList<String>(List.of("Jo", "Ko")),
            "Chess", "Shillman Hall", true,
            DaysOfTheWeek.THURSDAY, 1030, DaysOfTheWeek.THURSDAY, 1130);

    SchedulingSystemView view = new SchedulingSystemTextView(model);

    view.render("Jo");

    view.render("Mo");

  }

  @Test
  public void testRemoveEventAndModifyEventWorking() {

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

    SchedulingSystemView view = new SchedulingSystemTextView(model);

    // model.modifyEvent("Mo", DaysOfTheWeek.WEDNESDAY, 1000, "startday FRIDAY");

    view.render("Jo"); System.out.println();

    model.removeEvent("Mo", DaysOfTheWeek.TUESDAY, 1100);

    view.render("Jo"); System.out.println();

    model.removeEvent("Ko", DaysOfTheWeek.THURSDAY, 1030);

    view.render("Jo"); System.out.println("\nKo should have one event\n");

    view.render("Ko");

    model.modifyEvent("Jo", DaysOfTheWeek.WEDNESDAY, 1000, "removeinvitee Ko");

    view.render("Ko"); System.out.println();

  }


}
