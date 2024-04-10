package cs3500.nuplanner.view.gui;


import cs3500.nuplanner.model.hw05.ReadableEvent;

/**
 * Represents the actions and observations desired from the EventGUI.
 * The purpose of the EventGUIView is to MANUALLY schedule an Event
 * through creation, modification (or removal).
 * (Makes sense to extend from Interface that does automatic scheduling.)
 */
public interface EventGUIView extends ScheduleEventGUIView {

  /**
   * Returns the start day of an event.
   *
   * @return the start day of an event
   */
  String startDayInput();

  /**
   * Sets the start date of an event.
   *
   * @param startDay the start day of event
   */
  void displayStartDay(String startDay);

  /**
   * Returns the start time of an event.
   *
   * @return the start time of an event
   */
  String startTimeInput();

  /**
   * Sets the start time of an event.
   *
   * @param startTime the start time of an event
   */
  void displayStartTime(String startTime);

  /**
   * Returns the end day of an event.
   *
   * @return the end day of an event
   */
  String endDayInput();

  /**
   * Sets the end day of an event.
   *
   * @param endDay the end day of an event
   */
  void displayEndDay(String endDay);

  /**
   * Returns the end time of an event.
   *
   * @return the end time of an event
   */
  String endTimeInput();

  /**
   * Sets the end time of an event.
   *
   * @param endTime the end time of an event
   */
  void displayEndTime(String endTime);

  /**
   * Displays an Event that exists within Scheduling System to user.
   *
   * @param user the current user
   * @param event event to be displayed
   */
  void displayExistingEvent(String user, ReadableEvent event);

}
