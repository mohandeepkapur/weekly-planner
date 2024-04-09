import java.io.IOException;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.view.gui.SSGUIView;

public class MockView implements SSGUIView {
  private final Appendable log;

  public MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void displayUserSchedule(String user) {
    try {
      this.log.append(user);
    } catch (IOException ignored) {
    }
  }

  @Override
  public void displayBlankEvent() {

  }

  @Override
  public void displayBlankScheduleEvent() {

  }

  @Override
  public void displayExistingEvent(String user, ReadableEvent event) {
    try {
      this.log.append(user);
      this.log.append(event.eventInvitees() + event.name() + event.location() + event.isOnline()
              + event.startDay() + event.startTime() + event.endDay() + event.endTime());
    } catch (IOException ignored) {
    }
  }

  @Override
  public void addFeatures(Features features) {

  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    try {
      this.log.append(errorMessage);
    } catch (IOException ignored) {
    }
  }
}