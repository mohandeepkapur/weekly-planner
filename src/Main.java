import cs3500.nuplanner.controller.GUIController;
import cs3500.nuplanner.controller.SchedulingSystemController;
import cs3500.nuplanner.controller.XMLController;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.GUI.SSFrame;
import cs3500.nuplanner.view.GUI.SSGUI;

public class Main {

  public static void main(String[] args) {

    // loading in data into model
    SchedulingSystem model = new NUPlannerModel();
    SchedulingSystemController xmlCont = new XMLController(model);
    xmlCont.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");

    // controller cannot change model-state rn
    SchedulingSystemController controller = new GUIController();

    controller.useSchedulingSystem(model); //impl of view hardcoded in rn. BAD



  }

}
