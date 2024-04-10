package cs3500.nuplanner.view.gui;


import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableEvent;


/**
 * GUI View of the Scheduling System Program.
 */
public interface SSGUIView {

  /**
   * Displays the Schedule of an existing user in the Scheduling System.
   * Schedule displayed is considered current user of system.
   *
   * @param user user in Scheduling System
   */
  void displayUserSchedule(String user);

  /**
   * Displays an empty Event-creation window for a user to interact with.
   */
  void displayBlankEvent();

  /**
   * Displays an empty Event-scheduling window for a user to interact with.
   *
   * @throws IllegalArgumentException if a user's schedule is not currently displayed
   */
  void displayBlankScheduleEvent();

  /**
   * Displays the details of the displayed Event that current user has selected.
   * (Events available within their schedule.)
   *
   * @param user  the current user
   * @param event the event to be displayed
   * @throws IllegalArgumentException if no user has been selected/ no schedule displayed
   * @throws IllegalArgumentException something about event and schedule mismatch
   */
  void displayExistingEvent(String user, ReadableEvent event);

  /**
   * Connects low-level events created by controls in GUI to high-level actions that affect rest
   * of codebase.
   *
   * @param features program-specific events in response to low-level events
   */
  void addFeatures(Features features);

  /**
   * Makes GUI visible.
   */
  void makeVisible();

  /**
   * Displays error message to User after failed request.
   *
   * @param errorMessage error message
   */
  void displayErrorMessage(String errorMessage);

  /**
   * Refreshes relevant controls of View according to updated Scheduling System state.
   */
  void refresh();

}