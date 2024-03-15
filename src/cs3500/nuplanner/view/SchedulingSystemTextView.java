package cs3500.nuplanner.view;

import java.io.IOException;
import java.util.List;

import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

/**
 * A class that creates and prints a simple textual view of a user's schedule in the console.
 */

public class SchedulingSystemTextView implements SchedulingSystemView {

  private final SchedulingSystem model;
  private final Appendable out;

  public SchedulingSystemTextView(SchedulingSystem model) {
    this.model = model;
    out = System.out;
  }
  
  /**
   * Creates the view using information from the model.
   *
   * @param model the model used to create the view
   */
  public SchedulingSystemTextView(SchedulingSystem model, Appendable log) {
    this.model = model;
    this.out = log;
  }

  @Override
  public void render(String user) throws IOException {
    out.append(toStringUserSchedule(user));
  }

  private String toStringUserSchedule (String user) {
    List<ReadableEvent> events = this.model.eventsInSchedule(user);
    StringBuilder sched = new StringBuilder();

    sched.append("User: " + user + "\n");
    sched.append("Sunday: \n");
    runOverEvents(events, "SUNDAY", sched);
    sched.append("Monday: \n");
    runOverEvents(events, "MONDAY", sched);
    sched.append("Tuesday: \n");
    runOverEvents(events, "TUESDAY", sched);
    sched.append("Wednesday: \n");
    runOverEvents(events, "WEDNESDAY", sched);
    sched.append("Thursday: \n");
    runOverEvents(events, "THURSDAY", sched);
    sched.append("Friday: \n");
    runOverEvents(events, "FRIDAY", sched);
    sched.append("Saturday: \n");
    runOverEvents(events, "SATURDAY", sched);

    sched = new StringBuilder(sched.substring(0, sched.length()-1));

    return sched.toString();
  }

  /**
   * Checks to see if the day of the week matches and then prints all the events for that day.
   *
   * @param events a list of events
   * @param day    the day to check
   * @param aUs
   */
  private void runOverEvents(List<ReadableEvent> events, String day, StringBuilder sched) {
    for (ReadableEvent event : events) {
      if (event.startDay().toString().equals(day)) {
        eventDetails(event, sched);
      }
    }
  }

  /**
   * Prints an event in a simple textual view in the console.
   *
   * @param event the event whose state will be printed
   */
  private void eventDetails(ReadableEvent event, StringBuilder sched) {
    String indent = "       ";
    sched.append(indent + "name: " + event.name() + "\n");
    String start = event.startDay() + ": " + event.startTime();
    String end = event.endDay() + ": " + event.endTime();
    sched.append(indent + "time: " + start + " -> " + end + "\n");
    sched.append(indent + "location: " + event.location() + "\n");
    sched.append(indent + "online: " + event.isOnline() + "\n");
    sched.append(indent + "invitees: " );

    for (String invitee : event.eventInvitees()) {
      sched.append("\n" + indent + indent + invitee);
    }

    sched.append("\n");
  }
  
}
