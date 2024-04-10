import java.io.IOException;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.view.gui.SSGUIView;

/**
 * A mock of the view that the controller will use to test inputs.
 */

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
    try {
      this.log.append("Ends up here correctly");
    } catch (IOException ignored) {
    }
  }

  @Override
  public void displayBlankScheduleEvent() {
    try {
      this.log.append("Ends up here correctly");
    } catch (IOException ignored) {
    }
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
    //not currently used for testing
  }

  @Override
  public void makeVisible() {
    //not currently used for testing
  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    try {
      this.log.append(errorMessage);
    } catch (IOException ignored) {
    }
  }

  @Override
  public void refresh() {
    //not currently used for testing
  }
}