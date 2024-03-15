package cs3500.nuplanner.view;

import java.util.List;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

/**
 * A class that creates and prints a simple textual view of a user's schedule in the console.
 */

public class SchedulingSystemTextView implements SchedulingSystemView {

  private final SchedulingSystem model;

  /**
   * Creates the view using information from the model.
   *
   * @param model the model used to create the view
   */
  public SchedulingSystemTextView(SchedulingSystem model) {
    this.model = model;
  }

  @Override
  public void render(String user) {

    List<ReadableEvent> events = this.model.eventsInSchedule(user);

    System.out.print("User: " + user + "\n");
    System.out.print("Sunday: \n");
    runOverEvents(user, events, "SUNDAY");
    System.out.print("Monday: \n");
    runOverEvents(user, events, "MONDAY");
    System.out.print("Tuesday: \n");
    runOverEvents(user, events, "TUESDAY");
    System.out.print("Wednesday: \n");
    runOverEvents(user, events, "WEDNESDAY");
    System.out.print("Thursday: \n");
    runOverEvents(user, events, "THURSDAY");
    System.out.print("Friday: \n");
    runOverEvents(user, events, "FRIDAY");
    System.out.print("Saturday: \n");
    runOverEvents(user, events, "SATURDAY");
  }

  /**
   * Checks to see if the day of the week matches and then prints all the events for that day.
   *
   * @param user   the schedule owner
   * @param events a list of events
   * @param day    the day to check
   */
  private void runOverEvents(String user, List<ReadableEvent> events, String day) {
    for (ReadableEvent event : events) {
      if (event.startDay().toString().equals(day)) {
        displayEventDetails(event);
      }
    }
  }

  /**
   * Prints an event in a simple textual view in the console.
   *
   * @param event the event whose state will be printed
   */
  private void displayEventDetails(ReadableEvent event) {

    String indent = "       ";
    System.out.print(indent + "name: " + event.name() + "\n");
    String start = event.startDay() + ": " + event.startTime();
    String end = event.endDay() + ": " + event.endTime();
    System.out.print(indent + "time: " + start + " -> " + end + "\n");
    System.out.print(indent + "location: " + event.location() + "\n");
    System.out.print(indent + "online: " + event.isOnline() + "\n");
    System.out.print(indent + "invitees: " + event.eventInvitees() + "\n");
  }
}
