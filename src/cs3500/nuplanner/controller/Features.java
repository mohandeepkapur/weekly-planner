package cs3500.nuplanner.controller;

import java.util.List;

/**
 * User Requests made through GUI. Typically involves manip. model, view, or both, which is
 * controller's job.
 */
public interface Features {

  /**
   * Request an Event's details to be shown if selected on SSView.
   * @param day
   * @param time
   */
  void displayEventDetails(int day, int time);

  /**
   * Request for a new schedule to be shown.
   * @param user
   */
  void displayNewSchedule(String user); // should be view operational method

  /**
   * User request to open up blank new Event window.
   */
  void displayBlankEventWindow();


  /**
   *
   */
  void requestAddEvent(String name, String location, String isOnline, String startDay,
                       String endDay, String startTime, String endTime, String host,
                       List<String> invitees);

  /**
   * Request for an XML file to be uploaded.
   * @param pathname
   */
  void requestScheduleUpload(String pathname);

  /**
   * Request for model state to be saved into multiple XML schedules.
   * @param pathname
   */
  void requestScheduleDownload(String pathname);

  /**
   * Request to "schedule an event".
   */
  void requestScheduleEvent(); // do nothing

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