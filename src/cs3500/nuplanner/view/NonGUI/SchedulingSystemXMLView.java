package cs3500.nuplanner.view.NonGUI;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

/**
 * A class that creates and prints a XML view of a user's
 * schedule into a file.
 */
public class SchedulingSystemXMLView implements SchedulingSystemView {

  private final SchedulingSystem model;

  /**
   * Creates the view using information from the model.
   *
   * @param model the model used to create the view
   */
  public SchedulingSystemXMLView(SchedulingSystem model) {
    this.model = model;
  }

  /**
   * Renders view of a user's schedule.
   *
   * @throws IOException if unable to render view
   */
  @Override
  public void render(String user) throws IOException {

    List<ReadableEvent> events = this.model.eventsInSchedule(user);
    Writer file = new FileWriter("XMLFiles/toWrite/" + user + ".xml");
    file.write("<?xml version=\"1.0\"?>");
    file.write("\n");
    file.write("<schedule id=\"" + user + "\">");

    for (ReadableEvent event : events) {
      file.write("\n");
      file.write("<event>");
      file.write("\n");
      file.write("<name>" + event.name() + "</name>");
      file.write("\n");
      file.write("<time>");
      file.write("\n");
      file.write("<start-day>" + event.startDay().toString() + "</start-day>");
      file.write("<start>" + event.startTime() + "</start>");
      file.write("<end-day>" + event.endDay().toString() + "</end-day>");
      file.write("<end>" + event.endTime() + "</end>");
      file.write("\n");
      file.write("</time>");
      file.write("\n");
      file.write("<location>");
      file.write("<online>" + event.isOnline() + "</online>");
      file.write("<place>" + event.location() + "</place>");
      file.write("</location>");
      file.write("\n");
      file.write("<users>");
      file.write("\n");
      for (String invitee : event.eventInvitees()) {
        file.write("<uid>" + invitee + "</uid>");
        file.write("\n");
      }
      file.write("</users>");
      file.write("\n");
      file.write("</event>");
      file.write("\n");
    }

    file.write("</schedule>");
    file.close();

  }

}
