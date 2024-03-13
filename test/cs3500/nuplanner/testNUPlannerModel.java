package cs3500.nuplanner;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

import static org.junit.Assert.assertTrue;
//shsss
public class TestNUPlannerModel {

  // basic throws: IAE if specified user doesn't exist (when it should)
  // prevent overwrites since using map

  @Test
  public void bruh() {

    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Mo");

    model.addEventInSchedules("Mo", new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    model.addEventInSchedules("Jo", new ArrayList<String>(List.of("Jo", "Ko")),
            "Chess", "Shillman Hall", true,
            DaysOfTheWeek.WEDNESDAY, 1030, DaysOfTheWeek.WEDNESDAY, 1130);

  }

  @Test
  public void bruh2() {

    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Mo");
    model.addUser("Ko");
    model.addUser("Jo");

    model.addEventInSchedules("Mo", new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    // Jo, Jo fails bc addEvent for Schedule has implicit eventConflict check
    model.addEventInSchedules("Jo", new ArrayList<String>(List.of("Jo", "Ko")),
            "Chess", "Shillman Hall", true,
            DaysOfTheWeek.THURSDAY, 1030, DaysOfTheWeek.THURSDAY, 1130);

    System.out.print(model.eventIDsInSchedule("Mo"));
    System.out.print(model.eventIDsInSchedule("Jo"));
    System.out.print(model.eventIDsInSchedule("Ko"));

    System.out.print("\n");

    assertTrue(model.eventAt("Jo", 0)==model.eventAt("Ko", 0));

    // now check removing events condition

    model.removeEventInSchedules("Mo", 0);

    System.out.print(model.eventIDsInSchedule("Mo"));
    System.out.print(model.eventIDsInSchedule("Jo"));
    System.out.print(model.eventIDsInSchedule("Ko"));

    System.out.print("\n");
    System.out.print(model.eventAt("Jo", 1).eventInvitees() + "\n");

    model.removeEventInSchedules("Ko", 1);

    System.out.print(model.eventIDsInSchedule("Mo"));
    System.out.print(model.eventIDsInSchedule("Jo"));
    System.out.print(model.eventIDsInSchedule("Ko"));

    System.out.print("\n");

    System.out.print(model.eventAt("Jo", 1).eventInvitees());


  }

  // currently debugging...
  @Test
  public void bruh4() {

    SchedulingSystem model = new NUPlannerModel();

    model.addUser("Mo");
    model.addUser("Ko");
    model.addUser("Jo");

    // EVENT ID 0
    model.addEventInSchedules("Mo", new ArrayList<String>(List.of("Mo", "Ko", "Jo")),
            "Tennis", "Krentzman Quad", true,
            DaysOfTheWeek.WEDNESDAY, 1000, DaysOfTheWeek.WEDNESDAY, 1100);

    // EVENT ID 1
    model.addEventInSchedules("Jo", new ArrayList<String>(List.of("Jo", "Ko")),
            "Chess", "Shillman Hall", true,
            DaysOfTheWeek.THURSDAY, 1030, DaysOfTheWeek.THURSDAY, 1130);


    //model.eventAt("Jo", 1);

    try {
      model.modifyEventInSchedules("Jo", 1, "startday wednesday");
    } catch (IllegalArgumentException ignore){}

    model.modifyEventInSchedules("Jo", 1, "endday fridAy");

  }

}
