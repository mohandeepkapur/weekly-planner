package cs3500.nuplanner.view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

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

  @Override
  public void render(String user) {
    List<ReadableEvent> events = this.model.eventsInSchedule(user);
    try {
      Writer file = new FileWriter("src/XMLFiles/toWrite/" + user + ".xml");
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
        file.write("<start-day>" + event.startDay().toString() + "</start-day" +
                ">");
        file.write("<start>" + event.startTime() + "</start>");
        file.write("<end-day>" + event.endDay().toString() + "</end" +
                "-day>");
        file.write("<end>" + event.endTime() + "</end>");
        file.write("\n");
        file.write("</time>");
        file.write("\n");
        file.write("<location>");
        file.write("<online>" + event.isOnline() + "</online>");
        file.write("<start>" + event.location() + "</start>");
        file.write("</location>");
        file.write("\n");
        file.write("<users>");
        file.write("<uid>" + event.eventInvitees() + "</uid>");
        file.write("</users>");
        file.write("\n");
        file.write("</event>");
        file.write("\n");
      }

      file.write("</schedule>");
      file.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }
}
