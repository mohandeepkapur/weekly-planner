import java.io.IOException;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.controller.SchedulingSystemController;
import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.gui.SSGUIView;

public class MockController implements SchedulingSystemController, Features {
  private SSGUIView view;
  private final Appendable log;

  public MockController(Appendable log, SSGUIView ssView) {
    this.log = log;
    this.view = ssView;
  }

  @Override
  public void displayNewSchedule(String user) {
    try {
      this.log.append(user);
    } catch (IOException ignored) {
    }
  }

  @Override
  public void displayBlankEvent(String user) {

  }

  @Override
  public void displayBlankScheduleEvent(String user) {

  }

  @Override
  public void displayExistingEvent(String user, ReadableEvent event) {

  }

  @Override
  public void requestCreateEvent(String user, RawEventData event) {

  }

  @Override
  public void requestRemoveEvent(String user, RawEventData event) {

  }

  @Override
  public void requestModifyEvent(String user, RawEventData currEvent, RawEventData modEvent) {

  }

  @Override
  public void requestScheduleEvent() {

  }

  @Override
  public void requestXMLScheduleUpload(String pathname) {

  }

  @Override
  public void requestAllSchedulesDownload(String pathname) {

  }

  @Override
  public void requestExitProgram() {

  }

  @Override
  public void useSchedulingSystem(SchedulingSystem model) {
    view.addFeatures(this);
    view.makeVisible();
  }

  @Override
  public void useSchedulingSystem(String pathname) {

  }
}
