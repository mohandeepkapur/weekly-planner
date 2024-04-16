package cs3500.nuplanner.providerp2.view;

import cs3500.nuplanner.providerp2.controller.Features;

/**
 * Creates the interface for the view of the planner.
 */
public interface IView {
  /**
   * Make the view visible. This is usually called
   * after the view is constructed.
   */
  void makeVisible();

  /**
   * Transmit an error message to the view, in case
   * the command could not be processed correctly.
   * @param error the error message
   */
  void showErrorMessage(String error);

  /**
   * Signal the view to draw itself.
   */
  void refresh();

  /**
   * Sets the action listener to the buttons.
   * @param plannerController the controller that is used to update the model
   */
  void setListener(Features plannerController);
  //mo: your features interface is NOT your listener BRUH
  // --> lambdas are the listeners that then call features methods after low-level event occurs in View
  // -->
}
