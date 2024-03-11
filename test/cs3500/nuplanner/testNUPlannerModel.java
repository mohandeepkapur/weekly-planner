package cs3500.nuplanner;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

public class testNUPlannerModel {

  // basic throws: IAE if specified user doesn't exist (when it should)
  // prevent overwrites since using map
  // throw IAE if event name doesn't exist

  @Test
  public void bruh() {

    /*
    example:

    NUPlanner model = new NUPlanner();

    View view = new View(model);

    model.addUser("Mohandeep"); <-- any existing events that specify Mohandeep must be added to his schedule <- some checks in the right places

    model.addEvent("Mohandeep", {"Bob", "Dylan"}, DinDin, --------);

    view.renderUserSchedule("Mohandeep");

    model.modifyEvent();

    view.renderUserSchedule();

    model.removeEvent("Mohandeep", DinDin);

    When parsing XML, use methods above to add events to new user schedule
    These methods will check for conflicts, everything
      XML parser just needs to rely on above methods to throw errors if given schedule is illegal

     */

    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Mo");

    model.addEventInSchedules("Mo", new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    model.addEventInSchedules("Jo", new ArrayList<String>(List.of("Jo", "Ko")),
            "Chess", "Shillman Hall", true,
            DaysOfTheWeek.WEDNESDAY, 1030, DaysOfTheWeek.WEDNESDAY, 1130);




  }


}
