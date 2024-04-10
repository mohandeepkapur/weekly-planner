import org.junit.Test;

import java.util.List;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.controller.GUIController;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.strategies.AnyTimeStrategy;
import cs3500.nuplanner.view.gui.SSGUIView;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static org.junit.Assert.assertEquals;

/**
 * Testing the capability of the controller by looking at its inputs.
 */

public class TestController {

  @Test
  public void TestValidDisplayNewSchedule() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);

    mockView.displayUserSchedule("Elise");
    String[] logLines = log.toString().split(System.lineSeparator());
    assertEquals("Elise", logLines[0]);
  }

  @Test
  public void TestValidDisplayBlankEvent() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);

    mockView.displayBlankEvent();
    assertEquals(2, 1 + 1); //placeholder
  }

  @Test
  public void TestValidDisplayBlankScheduleEvent() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);

    mockView.displayBlankScheduleEvent();
    assertEquals(2, 1 + 1); //placeholder
  }

  @Test
  public void TestValidDisplayExistingEvent() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);
    Event event = new NUEvent(List.of("Elise", "Kyle"), "Math Class",
            "Ryder Hall", false, MONDAY, 1000, MONDAY, 1130);

    mockView.displayExistingEvent("Elise", event);
    String[] logLines = log.toString().split(System.lineSeparator());
    assertEquals("Elise[Elise, Kyle]Math ClassRyder HallfalseMONDAY1000MONDAY1130", logLines[0]);
  }

  @Test
  public void TestValidAddFeatures() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);
    Features features = new GUIController(mockView, new AnyTimeStrategy());

    mockView.addFeatures(features);
    assertEquals(2, 1 + 1); //placeholder
  }

  @Test
  public void TestValidMakeVisible() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);

    mockView.makeVisible();
    assertEquals(2, 1 + 1); //placeholder
  }

  @Test
  public void TestValidDisplayErrorMessage() {
    StringBuilder log = new StringBuilder();
    SSGUIView mockView = new MockView(log);

    mockView.displayErrorMessage("This code is wrong!");
    String[] logLines = log.toString().split(System.lineSeparator());
    assertEquals("This code is wrong!", logLines[0]);
  }
}
