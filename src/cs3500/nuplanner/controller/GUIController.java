package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.GUI.SSGUIView;

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
   * @param ssView      main GUI controller will manipulate
   */
  public GUIController(SSGUIView ssView) {
    this.view = ssView;
  }

  /**
   * Request for a new user's schedule to be shown.
   * @param user
   */
  @Override
  public void displayNewSchedule(String user) {
    view.displayUserSchedule(user);
  }

  /**
   * Request to create a new Event.
   */
  @Override
  public void displayBlankEvent() {
    //System.out.println("Should open blank event frame now...");
    view.displayEmptyEventWindow();
  }

  /**
   * Request for an Event's details to be shown. Event must belong in displayed user's schedule.
   *
   * @param day  day an event in user's schedule may contain
   * @param time time an event in user's schedule may contain
   */
  @Override
  public void requestExistingEventDetails(DaysOfTheWeek day, int time) {

    view.displayFilledEventWindow(day, time);

  }

  /**
   * Request to add an Event into requester's schedule.
   */
  @Override
  public void requestCreateEvent() {
    // controller ensuring valid inputs in limited manner:
    // check that user has filled all necessary event fields
    // check that certain inputs can be parsed as desired types

    // if so, print out contents
    System.out.print("boop");
  }

  /**
   * Request for an XML file to be uploaded.
   *
   * @param pathname
   */
  @Override
  public void requestXMLScheduleUpload(String pathname) {
    System.out.println(pathname);
  }

  /**
   * Request for model state to be saved into multiple XML schedules.
   *
   * @param pathname
   */
  @Override
  public void requestAllSchedulesDownload(String pathname) {
    System.out.println(pathname);
  }

  /**
   * Request to "schedule an event".
   */
  @Override
  public void requestScheduleEvent() {

  }

  /**
   * User request to remove an event they've selected from scheduling system.
   */
  @Override
  public void requestRemoveEvent() {

  }

  /**
   * User request to modify an existing event based on how its manipulated event in GUI.
   */
  @Override
  public void requestModifyEvent() {

  }

  /**
   * User request to exit program.
   */
  @Override
  public void requestExitProgram() {

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
   * @param pathname                     path to XML file
   * @throws IllegalStateException       if unable to open or parse XML file
   */
  @Override
  public void useSchedulingSystem(String pathname) {

  }

}