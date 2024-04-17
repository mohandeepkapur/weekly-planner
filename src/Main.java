import cs3500.nuplanner.controller.GUIController;
import cs3500.nuplanner.controller.SchedulingSystemController;
import cs3500.nuplanner.controller.XMLController;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.provider.adaptors.ClientToProviderModelAdaptor;
import cs3500.nuplanner.provider.adaptors.ProviderToClientViewAdaptor;
import cs3500.nuplanner.provider.model.ReadOnlySystems;
import cs3500.nuplanner.provider.view.PlannerFrame;
import cs3500.nuplanner.strategies.AnyTimeStrategy;
import cs3500.nuplanner.strategies.SchedulingStrategies;
import cs3500.nuplanner.strategies.WorkHoursStrategy;
import cs3500.nuplanner.view.gui.SSGUIView;

/**
 * Class to run Scheduling System.
 */
public final class Main {

  /**
   * Runs Scheduling System.
   */
  public static void main(String[] args) {


    SchedulingSystem model = new NUPlannerModel();

    SchedulingSystemController xmlCont = new XMLController(model);
    xmlCont.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");


    // loading in data into model
    ReadOnlySystems adaptModel = new ClientToProviderModelAdaptor(model);

    SSGUIView adaptView = new ProviderToClientViewAdaptor(new PlannerFrame(adaptModel));

    SchedulingSystemController controller = new GUIController(adaptView, getStrategy(args[0]));

    controller.useSchedulingSystem(model, getStrategy(args[0]));

  }

  private static SchedulingStrategies getStrategy(String arg) {
    if (arg.equals("anytime")) {
      return new AnyTimeStrategy();
    }
    if (arg.equals("workhours")) {
      return new WorkHoursStrategy();
    }
    throw new IllegalArgumentException("Not a game type!");
  }

}
