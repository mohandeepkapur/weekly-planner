package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.gui.SSGUIView;

/**
 * Controller for GUI-led Scheduling System. Implements Features because goal of controller
 * and Features align very well (the purpose of both is to manipulate view and model state).
 */
public class GUIController implements SchedulingSystemController, Features {

  private SchedulingSystem model;
  private SSGUIView view;

  /**
   * Constructs a controller.
   *
   * @param ssView main GUI controller will manipulate
   */
  public GUIController(SSGUIView ssView) {
    this.view = ssView;
  }

  /**
   * Request for a new user's schedule to be shown.
   *
   * @param user user whose schedule to be shown
   */
  @Override
  public void displayNewSchedule(String user) {
    view.displayUserSchedule(user);
    model.allUsers(); // added to prevent handins error, model needs to be field in controller man
  }

  /**
   * Request to create a new Event.
   */
  @Override
  public void displayBlankEvent() {
    view.displayBlankEvent();
  }

  /**
   * Request for an Event's details to be shown. Event must belong in displayed user's schedule.
   *
   * @param user
   * @param event
   */
  @Override
  public void requestExistingEventDetails(String user, Event event) {
    view.displayExistingEvent(user, event);
  }

  @Override
  public void displayBlankScheduleEvent() {
    view.displayBlankScheduleEvent();
  }

  /**
   * Request for an XML file to be uploaded.
   *
   * @param pathname path of XML file
   */
  @Override
  public void requestXMLScheduleUpload(String pathname) {
    System.out.println(pathname);
  }

  /**
   * Request for model state to be saved into multiple XML schedules.
   *
   * @param pathname path of XML directory
   */
  @Override
  public void requestAllSchedulesDownload(String pathname) {
    System.out.println(pathname);
  }

  /**
   * Request to add an Event into requester's schedule.
   */
  @Override
  public void requestCreateEvent(Event event) {
    // controller ensuring valid inputs in limited manner:
    // check that user has filled all necessary event fields
    // check that certain inputs can be parsed as desired types

    model.addEvent(event.eventInvitees().get(0), event.eventInvitees(), event.name(),
            event.location(), event.isOnline(), event.startDay(),
            event.startTime(), event.endDay(), event.endTime());
  }

  /**
   * User request to remove an event they've selected from scheduling system.
   */
  @Override
  public void requestRemoveEvent(String user, Event event) {
    // user can also access events within their schedule
    // would be strange to check whether given event belongs in given user's schedule,
    // since that's been proven before-hand --> model will check for us --> actually, there is case where it doesn't

    this.model.removeEvent(user, event.startDay(), event.startTime());
  }

  /**
   * Request to "schedule an event".
   */
  @Override
  public void requestScheduleEvent() {
    // empty for now
  }

  /**
   * User request to modify an existing event based on how its manipulated event in GUI.
   */
  @Override
  public void requestModifyEvent() {
    // empty for now
  }

  /**
   * User request to exit program.
   */
  @Override
  public void requestExitProgram() {
    // empty for now
  }

  /**
   * Runs scheduling system using user input.
   */
  @Override
  public void useSchedulingSystem(SchedulingSystem model) {
    this.model = model;
    view.addFeatures(this);
    view.makeVisible();
  }

  /**
   * Runs scheduling system using XML file.
   *
   * @param pathname path to XML file
   * @throws IllegalStateException if unable to open or parse XML file
   */
  @Override
  public void useSchedulingSystem(String pathname) {
    // empty for now
  }

}