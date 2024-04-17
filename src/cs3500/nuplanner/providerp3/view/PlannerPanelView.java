package cs3500.nuplanner.providerp3.view;

import java.util.List;

import cs3500.nuplanner.providerp3.controller.Features;
import cs3500.nuplanner.providerp3.model.IEvent;

/**
 * Creates the interface for the schedule panel view.
 */
public interface PlannerPanelView {
  // this is so shit
  /**
   * This is a method that handles when the panel is clicked.
   * @param controller the controller passed into the listener
   */
  void addClickListener(Features controller);

  /**
   * The method for the events in the planner panel.
   * @return the list of the events
   */
  List<IEvent> getEvents();
}
