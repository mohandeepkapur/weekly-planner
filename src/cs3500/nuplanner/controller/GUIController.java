package cs3500.nuplanner.controller;

import java.util.ArrayList;
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
  public void requestCreateEvent() {
    System.out.println("Should open blank event frame now...");
    view.displayBlankEvent();
  }

  @Override
  public void requestExistingEvent(String user, DaysOfTheWeek day, int time) {
    System.out.print(day + ", " + time);
    List<ReadableEvent> userEvents = model.eventsInSchedule(user);

//    // for every event in user schedule
//    for(ReadableEvent event : userEvents) {
//      if ()
//    }

    // check if an event that conflicts with this time exists, for specific user
    // if so, displayEventWindowWithCorrectDetails()
  }

  @Override
  public void requestAddEvent(String name, String location, String isOnline, String startDay,
                              String endDay, String startTime, String endTime, String host,
                              List<String> invitees) {
    // controller ensuring valid inputs in limited manner:
    // check that user has put down options for everything
    // check that certain things can be parsed as desired types
    // if so, print out contents, if not, do not do anything (will not check if event conflicts rn)

    System.out.print("boop");
  }

  @Override
  public void requestScheduleUpload(String pathname) {
    System.out.println(pathname);
  }

  @Override
  public void requestScheduleDownload(String pathname) {
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

/*

public class Controller() {


  public void go(model, view) {

  }


}

 */