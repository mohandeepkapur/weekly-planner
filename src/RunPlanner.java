import cs3500.nuplanner.controller.GUIController;
import cs3500.nuplanner.controller.SchedulingSystemController;
import cs3500.nuplanner.controller.XMLController;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.strategies.AnyTimeStrategy;
import cs3500.nuplanner.strategies.SchedulingStrategies;
import cs3500.nuplanner.view.gui.SSFrame;
import cs3500.nuplanner.view.gui.SSGUIView;

/**
 * Class to run Scheduling System.
 */
public final class RunPlanner {

  /**
   * Runs Scheduling System.
   */
  public static void main(String[] args) {

    // loading in data into model
    SchedulingSystem model = new NUPlannerModel();
    SchedulingSystemController xmlCont = new XMLController(model);
    xmlCont.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");

    // launching the model with user input
    SSGUIView view = new SSFrame(model);
    SchedulingSystemController controller = new GUIController(view);

    //    if (args[0].equals("anytime")) {
    //      SchedulingStrategies anytime = new AnyTimeStrategy();
    //    } else if (args[0].equals("workhours")) {
    //
    //    } else {
    //      throw new IllegalArgumentException("Invalid arg given... ");
    //    }

    controller.useSchedulingSystem(model);

  }

}
