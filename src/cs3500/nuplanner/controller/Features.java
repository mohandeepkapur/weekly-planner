package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;

/**
 * User Requests made through GUI. Typically involves manip. model, view, or both (which is
 * controller's job).
 */
public interface Features {

  /**
   * Request for a new user's schedule to be shown.
   * @param user
   */
  void displayNewSchedule(String user); // should be view operational method

  /**
   * Request to create a new Event.
   */
  void requestCreateNewEvent();

  /**
   * Request for an Event's details to be shown. Event must belong in user's schedule.
   * @param day             day an event in user's schedule may contain
   * @param time            time an event in user's schedule may contain
   */
  void requestExistingEventDetails(String user, DaysOfTheWeek day, int time);

  /**
   * Request to add an Event into requester's schedule.
   */
  void requestAddEvent();

  /**
   * Request for an XML file to be uploaded.
   * @param pathname
   */
  void requestXMLScheduleUpload(String pathname);

  /**
   * Request for model state to be saved into multiple XML schedules.
   * @param pathname
   */
  void requestAllSchedulesDownload(String pathname);

  /**
   * Request to "schedule an event".
   */
  void requestScheduleEvent();

  /**
   * User request to remove an event they've selected from scheduling system.
   */
  void removeEvent();

  /**
   * User request to modify an existing event based on how its manipulated event in GUI.
   */
  void modifyEvent();

  /**
   * User request to exit program.
   */
  void exitProgram();

}