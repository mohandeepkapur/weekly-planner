import org.junit.Test;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.controller.SchedulingSystemController;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.gui.SSFrame;
import cs3500.nuplanner.view.gui.SSGUIView;

import static org.junit.Assert.assertEquals;

public class TestController {

  @Test
  public void TestDisplayNewSchedule() {
    SchedulingSystem model = new NUPlannerModel();
    SSGUIView view = new SSFrame(model);
    StringBuilder log = new StringBuilder();
    Features features = new MockController(log, view);
    SchedulingSystemController mock = new MockController(log, view);

    mock.useSchedulingSystem(model);
    features.displayNewSchedule("Elise");
    String[] logLines = log.toString().split(System.lineSeparator());
    assertEquals("Elise", logLines[0]);
  }
}
