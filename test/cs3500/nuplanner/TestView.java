package cs3500.nuplanner;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.SchedulingSystemView;
import cs3500.nuplanner.view.SchedulingSystemXMLView;

public class TestView {

  @Test
  public void testXMLView() {
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
