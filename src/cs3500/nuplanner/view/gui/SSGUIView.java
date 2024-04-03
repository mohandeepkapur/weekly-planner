package cs3500.nuplanner.view.gui;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.DaysOfTheWeek;

/**
 * GUI View of the Scheduling System Program.
 */
public interface SSGUIView {

  /**
   * Displays the Schedule of an existing user in the Scheduling System.
   * Schedule displayed is considered current user of system.
   *
   * @param user              user in Scheduling System
   */
  void displayUserSchedule(String user);

  /**
   * Displays an empty Event-creation window for a user to interact with.
   */
  void displayBlankEvent();

  /**
   * Displays the details of the displayed Event that current user has selected.
   * (Events available within their schedule.)
   *
   * @param day           day on SSView user has clicked on
   * @param time          time on SSView user has clicked on
   *
   * @throws IllegalArgumentException         if no user has been selected/ no schedule displayed
   */
  void displayExistingEvent(DaysOfTheWeek day, int time);

  /**
   * Connects low-level events created by controls in GUI to high-level actions that affect rest
   * of codebase.
   *
   * @param features              program-specific events in response to low-level events
   */
  void addFeatures(Features features);

  /**
   * Makes GUI visible.
   */
  void makeVisible();

}
