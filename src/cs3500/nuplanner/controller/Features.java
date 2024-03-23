package cs3500.nuplanner.controller;

/**
 * User Requests made through GUI.
 */
public interface Features {

  /**
   * Request an Event's details to be shown if selected on SSView.
   * @param day
   * @param time
   */
  void provideEventDetails(int day, int time);

  /**
   * Request for a new schedule to be shown.
   * @param user
   */
  void displayNewSchedule(String user); // should be view operational method

  /**
   * User request to create an event given information they provide.
   */
  void requestCreateEvent();

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