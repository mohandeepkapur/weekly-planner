package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.GUI.SSGUIView;

/**
 *
 */
public class GUIController implements SchedulingSystemController, Features {

  private SchedulingSystem model;
  private SSGUIView view;

  /**
   *
   * @param ssView
   */
  public GUIController(SSGUIView ssView) {
    this.view = ssView;
  }

  /**
   *
   * @param user
   */
  @Override
  public void displayNewSchedule(String user) {
    view.displayUserSchedule(user);
  }

  /**
   *
   */
  @Override
  public void displayBlankEvent() {
    System.out.println("Should open blank event frame now...");
    view.displayEmptyEventWindow();
  }

  /**
   * @param day  day an event in user's schedule may contain
   * @param time time an event in user's schedule may contain
   */
  @Override
  public void requestExistingEventDetails(DaysOfTheWeek day, int time) {

    view.displayFilledEventWindow(day, time);

  }

  @Override
  public void requestCreateEvent() {
    // controller ensuring valid inputs in limited manner:
    // check that user has filled all necessary event fields
    // check that certain inputs can be parsed as desired types

    // if so, print out contents
    System.out.print("boop");
  }

  @Override
  public void requestXMLScheduleUpload(String pathname) {
    System.out.println(pathname);
  }

  @Override
  public void requestAllSchedulesDownload(String pathname) {
    System.out.println(pathname);
  }

  /**
   *
   */
  @Override
  public void requestScheduleEvent() {

  }

  @Override
  public void requestRemoveEvent() {

  }

  /**
   *
   */
  @Override
  public void requestModifyEvent() {

  }

  /**
   *
   */
  @Override
  public void requestExitProgram() {

  }

  @Override
  public void useSchedulingSystem(SchedulingSystem model) {
    this.model = model;
    view.addFeatures(this);
    view.makeVisible();
  }

  @Override
  public void useSchedulingSystem(String pathname) {

  }

}