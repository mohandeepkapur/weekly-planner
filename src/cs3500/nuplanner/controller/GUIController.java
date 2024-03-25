package cs3500.nuplanner.controller;

import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.GUI.SSGUIView;

public class GUIController implements SchedulingSystemController, Features {

  private SchedulingSystem model;
  private SSGUIView view;

  public GUIController(SSGUIView ssView) {
    this.view = ssView;
  }

  @Override
  public void displayNewSchedule(String user) {
    view.displayNewSchedule(user);
  }

  @Override
  public void requestCreateNewEvent() {
    System.out.println("Should open blank event frame now...");
    view.displayBlankEvent();
  }

  @Override
  public void requestExistingEventDetails(String user, DaysOfTheWeek day, int time) {
    System.out.println(day + ", Military Time: " + time);

    // extract relevant user schedule
    List<ReadableEvent> userEvents = model.eventsInSchedule(user);

    // if clicked day/time did land on a user's event, display those event details
    for (ReadableEvent event : userEvents) {
      System.out.println("ran");
      if (event.containsTime(day, time)) {
        System.out.println("success");
        view.displayFilledEvent(event); // think about this...
      }
    }
  }

  /*
      Schedule sched = new NUSchedule("mo");
    sched.eventConflict();
  */

  @Override
  public void requestAddEvent() {
    // controller ensuring valid inputs in limited manner:
    // check that user has put down options for everything
    // check that certain things can be parsed as desired types
    // if so, print out contents, if not, do not do anything (will not check if event conflicts rn)

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

  @Override
  public void requestScheduleEvent() {

  }

  @Override
  public void removeEvent() {

  }

  @Override
  public void modifyEvent() {

  }

  @Override
  public void exitProgram() {

  }

  @Override
  public void useSchedulingSystem(SchedulingSystem model) {
    this.model = model;
    view.makeVisible();
    view.addFeatures(this);
  }

  @Override
  public void useSchedulingSystem(String pathname) {

  }

}