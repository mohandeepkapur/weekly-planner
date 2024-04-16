package cs3500.nuplanner.providerp1.view;

import java.util.List;

import nuplanner.controller.Features;
import nuplanner.model.Event;

/**
 * Creates the interface for the schedule panel view.
 */
public interface PlannerPanelView {
  /**
   * This is a method that handles when the panel is clicked.
   * @param controller the controller passed into the listener
   */
  void addClickListener(Features controller);

  /**
   * The method for the events in the planner panel.
   * @return the list of the events
   */
  List<Event> getEvents();
}
