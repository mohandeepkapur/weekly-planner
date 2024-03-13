package cs3500.nuplanner.view;

import java.io.IOException;
import java.util.List;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

public class SchedulingSystemTextView implements SchedulingSystemView {

  private SchedulingSystem model;

  public SchedulingSystemTextView(SchedulingSystem model) {
    this.model = model;
  }

  @Override
  public void render(String user) {

    List<Integer> eventIDs = this.model.eventIDsInSchedule(user);

    for (Integer eventID : eventIDs) {

      Event userEvent = this.model.eventAt(user, eventID);
      displayEventDetails(userEvent);

    }

  }

  private void displayEventDetails(Event userEvent) {


    System.out.print("EVENT ID: " + userEvent.ID());


  }


}
