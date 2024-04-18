import cs3500.nuplanner.controller.GUIController;
import cs3500.nuplanner.controller.SchedulingSystemController;
import cs3500.nuplanner.controller.XMLController;
import cs3500.nuplanner.model.hw05.NUPlannerModel;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.adaptors.ClientToProviderModelAdaptor;
import cs3500.nuplanner.adaptors.ProviderToClientViewAdaptor;
import cs3500.nuplanner.provider.model.ReadOnlySystems;
import cs3500.nuplanner.provider.view.IView;
import cs3500.nuplanner.provider.view.PlannerFrame;
import cs3500.nuplanner.strategies.AnyTimeStrategy;
import cs3500.nuplanner.strategies.SchedulingStrategies;
import cs3500.nuplanner.strategies.WorkHoursStrategy;
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

    SchedulingSystem model = new NUPlannerModel();
    SchedulingSystemController xmlCont = new XMLController(model);
    xmlCont.useSchedulingSystem("XMLFiles/toRead/Prof. Lucia.xml");

    if (args[0].equals("default")) {
      SSGUIView view = new SSFrame(model);
      // TODO: remove Strategy input from controller constructor, it's redundant
      SchedulingSystemController controller = new GUIController(view, getStrategy(args[1]));
      controller.useSchedulingSystem(model, getStrategy(args[1]));
    }

    if (args[0].equals("provider")) {
      ReadOnlySystems providerModel = new ClientToProviderModelAdaptor(model);
      IView providerView = new PlannerFrame(providerModel);
      SSGUIView clientView = new ProviderToClientViewAdaptor(providerView);
      SchedulingSystemController controller = new GUIController(clientView, getStrategy(args[1]));
      controller.useSchedulingSystem(model, getStrategy(args[1]));
    }

  }

  /**
   * Scheduling Strategy factory based on CLA.
   * @param arg     cla
   * @return        Scheduling Strategy
   */
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
