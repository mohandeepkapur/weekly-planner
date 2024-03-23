import cs3500.nuplanner.controller.GUIController;
import cs3500.nuplanner.controller.SchedulingSystemController;
import cs3500.nuplanner.controller.XMLController;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.GUI.EventFrame;
import cs3500.nuplanner.view.GUI.EventGUIView;
import cs3500.nuplanner.view.GUI.GUIView;
import cs3500.nuplanner.view.GUI.IGUIView;
import cs3500.nuplanner.view.GUI.SSFrame;
import cs3500.nuplanner.view.GUI.SSGUIView;

public class Main {

  public static void main(String[] args) {

    // loading in data into model
    SchedulingSystem model = new NUPlannerModel();
    SchedulingSystemController xmlCont = new XMLController(model);
    xmlCont.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");

    // creating views
    SSGUIView ssView = new SSFrame(model);
    EventGUIView eventView = new EventFrame(model);
    IGUIView fullView = new GUIView(ssView, eventView);

    // run shit
    SchedulingSystemController controller = new GUIController(fullView);
    controller.useSchedulingSystem(model); //impl of view hardcoded in rn. BAD

  }

}
