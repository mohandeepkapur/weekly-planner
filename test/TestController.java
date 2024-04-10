import org.junit.Test;

import java.util.List;

import cs3500.nuplanner.controller.GUIController;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.strategies.AnyTimeStrategy;
import cs3500.nuplanner.strategies.WorkHoursStrategy;
import cs3500.nuplanner.view.gui.SSGUIView;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

/**
 * Testing the capability of the controller by looking at its inputs.
 */

public class TestController {

  @Test
  public void testValidUseSchedulingSystem() {
    StringBuilder logView = new StringBuilder();
    StringBuilder logModel = new StringBuilder();
    SSGUIView mockView = new MockView(logView);
    SchedulingSystem mockModel = new MockModel(logModel);
    GUIController controller = new GUIController(mockView, new AnyTimeStrategy());

    controller.useSchedulingSystem(mockModel, new AnyTimeStrategy());

    controller.displayNewSchedule("Elise");
    mockModel.addUser("Elise");

    String[] logLines = logView.toString().split(System.lineSeparator());
    assertEquals("Elise", logLines[0]);

    String[] logLines2 = logModel.toString().split(System.lineSeparator());
    assertEquals("Elise", logLines2[0]);
  }

  @Test
  public void TestValidDisplayNewSchedule() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);
    GUIController controller = new GUIController(mockView, new AnyTimeStrategy());

    controller.displayNewSchedule("Elise");
    String[] logLines = log.toString().split(System.lineSeparator());
    assertEquals("Elise", logLines[0]);
  }

  @Test
  public void TestValidDisplayBlankEvent() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);
    GUIController controller = new GUIController(mockView, new AnyTimeStrategy());

    controller.displayBlankEvent("Elise");
    String[] logLines = log.toString().split(System.lineSeparator());
    assertEquals("Ends up here correctly", logLines[0]);
  }

  @Test
  public void TestValidDisplayExistingEvent() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);
    GUIController controller = new GUIController(mockView, new AnyTimeStrategy());
    Event event = new NUEvent(List.of("Elise", "Kyle"), "Math Class",
            "Ryder Hall", false, MONDAY, 1000, MONDAY, 1130);

    controller.displayExistingEvent("Elise", event);
    String[] logLines = log.toString().split(System.lineSeparator());
    assertEquals("Elise[Elise, Kyle]Math ClassRyder HallfalseMONDAY1000MONDAY1130", logLines[0]);
  }

  @Test
  public void TestValidDisplayBlankScheduleEvent() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);
    GUIController controller = new GUIController(mockView, new AnyTimeStrategy());

    controller.displayBlankScheduleEvent("Elaine");
    String[] logLines = log.toString().split(System.lineSeparator());
    assertEquals("Ends up here correctly", logLines[0]);
  }

  @Test
  public void TestValidDManipulatingEvents() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);
    GUIController controller = new GUIController(mockView, new AnyTimeStrategy());
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elise");
    controller.useSchedulingSystem(model, new AnyTimeStrategy());

    RawEventData event = new RawEventData(List.of("Elise", "Kyle"), "Math Class",
            "Ryder Hall", "false", "MONDAY",
            "1000", "MONDAY", "1130");

    controller.requestCreateEvent("Elise", event);

    assertEquals(List.of("Elise", "Kyle"),
            model.eventAt("Elise", MONDAY, 1000).eventInvitees());
    assertEquals("Math Class",
            model.eventAt("Elise", MONDAY, 1000).name());
    assertEquals("Ryder Hall",
            model.eventAt("Elise", MONDAY, 1000).location());
    assertFalse(model.eventAt("Elise", MONDAY, 1000).isOnline());
    assertEquals(MONDAY,
            model.eventAt("Elise", MONDAY, 1000).startDay());
    assertEquals(1000,
            model.eventAt("Elise", MONDAY, 1000).startTime());
    assertEquals(MONDAY,
            model.eventAt("Elise", MONDAY, 1000).endDay());
    assertEquals(1130,
            model.eventAt("Elise", MONDAY, 1000).endTime());

    RawEventData event2 = new RawEventData(List.of("Elise", "Kyle", "Brandon"),
            "Volleyball",
            "Willis Hall Volleyball Court", "false", "TUESDAY",
            "900", "TUESDAY", "1200");

    controller.requestModifyEvent("Elise", event, event2);

    ReadableEvent readable2 = model.eventAt("Elise", TUESDAY, 900);

    assertEquals(List.of("Elise", "Kyle", "Brandon"), readable2.eventInvitees());
    assertEquals("Volleyball", readable2.name());
    assertEquals("Willis Hall Volleyball Court", readable2.location());
    assertFalse(readable2.isOnline());
    assertEquals(TUESDAY, readable2.startDay());
    assertEquals(900, readable2.startTime());
    assertEquals(TUESDAY, readable2.endDay());
    assertEquals(1200, readable2.endTime());

    controller.requestRemoveEvent("Elise", event2);

    assertThrows(IllegalArgumentException.class, () -> model.eventAt("Elise",
            TUESDAY, 900));
  }

  @Test
  public void TestValidDRequestScheduleEvent() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);
    GUIController controller = new GUIController(mockView, new WorkHoursStrategy());
    SchedulingSystem model = new NUPlannerModel();
    model.addUser("Elise");
    controller.useSchedulingSystem(model, new WorkHoursStrategy());

    controller.requestScheduleEvent("Elise", "Math Class", "Ryder Hall",
            "false", "60", List.of("Elise", "Kyle"));

    System.out.println(model.eventsInSchedule("Elise").size());

    ReadableEvent event = model.eventAt("Elise", MONDAY, 900);

    assertEquals("Math Class", event.name());
  }

}
