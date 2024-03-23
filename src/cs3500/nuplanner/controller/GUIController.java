package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.GUI.SSFrame;
import cs3500.nuplanner.view.GUI.SSGUI;

public class GUIController implements SchedulingSystemController, Features {

  private SSGUI view;
  private SchedulingSystem model;

  public GUIController() {

  }

  @Override
  public void provideEventDetails(int day, int time) {

  }

  @Override
  public void displayNewSchedule(String user) {
    view.displayNewSchedule(user);
  }

  @Override
  public void requestCreateEvent() {
    System.out.println("Should open blank event frame now...");
  }

  @Override
  public void requestScheduleUpload(String pathname) {

  }

  @Override
  public void requestScheduleDownload(String pathname) {

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
    this.view = new SSFrame(this.model); // BAD. NEVER DO. BEING FIN LAZY RN
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