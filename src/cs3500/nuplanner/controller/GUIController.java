package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.GUI.EventGUIView;
import cs3500.nuplanner.view.GUI.IGUIView;
import cs3500.nuplanner.view.GUI.SSGUIView;

public class GUIController implements SchedulingSystemController, Features {

  private SchedulingSystem model;
  private SSGUIView ssView;
  private EventGUIView eventView;

  public GUIController(IGUIView view) {
    this.ssView = view.accessSSPortion();
    this.eventView = view.accessEventPortion();
  }

  @Override
  public void provideEventDetails(int day, int time) {

  }

  @Override
  public void displayNewSchedule(String user) {
    ssView.displayNewSchedule(user);
  }

  @Override
  public void requestCreateEvent() {
    System.out.println("Should open blank event frame now...");
    ssView.displayEventGUIView();
  }

  @Override
  public void requestAddEvent() {
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
    ssView.makeVisible();
    ssView.addFeatures(this);
    eventView.addFeatures(this);
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