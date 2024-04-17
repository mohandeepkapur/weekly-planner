package cs3500.nuplanner.provider.view;

import java.util.List;

import cs3500.nuplanner.provider.controller.Features;
import cs3500.nuplanner.provider.model.IEvent;

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
