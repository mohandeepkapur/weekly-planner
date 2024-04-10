import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.controller.GUIController;
import cs3500.nuplanner.controller.SchedulingSystemController;
import cs3500.nuplanner.controller.XMLController;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.strategies.AnyTimeStrategy;
import cs3500.nuplanner.view.gui.SSGUIView;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

/**
 * Testing the capability of the controller by looking at its inputs.
 */

//TODO: Write a mock model and mock view to test the controller
//TODO: Create integration tests so that it tests all things, such as make visible, click all
// buttons in the program, etc.

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
  public void TestValidRequestXMLScheduleUpload() {
    //tested in specific TestXMLFunctions test class
  }

  @Test
  public void TestValidRequestAllSchedulesDownload() {
    //tested in specific TestXMLFunctions test class
  }

  @Test
  public void TestValidDRequestCreateEvent() {
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

    controller.requestRemoveEvent("Elise", event);
    assertThrows(IllegalArgumentException.class,  () -> eventInvitees());

  }

//
//  @Test
//  public void TestValidAddFeatures() {
//    StringBuilder log = new StringBuilder();
//    SSGUIView mockView = new MockView(log);
//    Features features = new GUIController(mockView, new AnyTimeStrategy());
//
//    mockView.addFeatures(features);
//    assertEquals(2, 1 + 1); //placeholder
//  }
//
//  @Test
//  public void TestValidMakeVisible() {
//    StringBuilder log = new StringBuilder();
//    SSGUIView mockView = new MockView(log);
//
//    mockView.makeVisible();
//    assertEquals(2, 1 + 1); //placeholder
//  }
//
//  @Test
//  public void TestValidDisplayErrorMessage() {
//    StringBuilder log = new StringBuilder();
//    SSGUIView mockView = new MockView(log);
//
//    mockView.displayErrorMessage("This code is wrong!");
//    String[] logLines = log.toString().split(System.lineSeparator());
//    assertEquals("This code is wrong!", logLines[0]);
//  }
//

}
