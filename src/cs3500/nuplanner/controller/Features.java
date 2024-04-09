package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.RawEventData;

/**
 * User Requests made through GUI. Typically involves manip. model, view, or both (which is
 * controller's job).
 *
 * Unfortunately, for View to utilize this Features interface, View must do parsing of user
 * input to make sense of it, before trying to place it into an Event.
 * steps require interpreting user input and catching errors/responding to them. both
 * controller job. can use raweventdata class.
 */
public interface Features {

  /**
   * Request for a new user's schedule to be shown.
   * @param user        user whose schedule to be shown
   */
  void displayNewSchedule(String user); // should be view operational method

  /**
   * Request to create a new Event.
   */
  void displayBlankEvent(String user);

  /**
   * Request to create a new scheduled event.
   */
  void displayBlankScheduleEvent(String user);

  /**
   * Request for an Event's details to be shown. Event must belong in displayed user's schedule.
   *
   * @param user      user requesting event's details to be shown
   * @param event event object to be shown
   */
  void requestExistingEventDetails(String user, Event event);

  /**
   * Request to add an Event into requester's schedule.
   */
  void requestCreateEvent(String user, RawEventData event);

  /**
   * User request to remove an event they've selected from scheduling system.
   */
  void requestRemoveEvent(String user, RawEventData event);

  /**
   * User request to modify an existing event based on how its manipulated event in GUI.
   */
  void requestModifyEvent(String user, RawEventData currEvent, RawEventData modEvent);

  /**
   * Request to "schedule an event".
   */
  void requestScheduleEvent();

  /**
   * Request for an XML file to be uploaded.
   *
   * @param pathname    path of XML file
   */
  void requestXMLScheduleUpload(String pathname);

  /**
   * Request for model state to be saved into multiple XML schedules.
   *
   * @param pathname       path of XML directory
   */
  void requestAllSchedulesDownload(String pathname);


  /**
   * User request to exit program.
   */
  void requestExitProgram();

}