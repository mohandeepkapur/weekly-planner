import cs3500.nuplanner.controller.GUIController;
import cs3500.nuplanner.controller.SchedulingSystemController;
import cs3500.nuplanner.controller.XMLController;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.gui.SSFrame;
import cs3500.nuplanner.view.gui.SSGUIView;

/**
 * Class to run Scheduling System.
 */
public class Main {

  /**
   * Runs Scheduling System.
   */
  public static void main(String[] args) {

    // loading in data into model
    SchedulingSystem model = new NUPlannerModel();
    SchedulingSystemController xmlCont = new XMLController(model);
    xmlCont.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");
//    "/Users/mohandeepkapur/IdeaProjects/ood/group/Homework5/XMLFiles/toRead/Prof. Lucia.xml"
    SSGUIView view = new SSFrame(model);
    SchedulingSystemController controller = new GUIController(view);
    controller.useSchedulingSystem(model);

  }

}
