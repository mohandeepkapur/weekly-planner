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
      file.write("<?xml version=\"1.0\"?>\n");
      file.write("<schedule id=\"" + user + "\">\n");

      for (ReadableEvent event : events) {
        file.write("<event>\n");
        file.write("<name>\n" + event.name() + "\n</name>\n");

        file.write("<time>\n");
        file.write("<start-day>\n" + event.startDay().toString() + "\n</start-day" +
                ">\n");
        file.write("<start>\n" + event.startTime() + "\n</start>\n");
        file.write("<end-day>\n" + event.endDay().toString() + "\n</end" +
                "-day>\n");
        file.write("<end>\n" + event.endTime() + "\n</end>\n");
        file.write("\n</time>\n");

        file.write("<location>\n");
        file.write("<online>\n" + event.isOnline() + "\n</online>\n");
        file.write("<start>\n" + event.location() + "\n</start>\n");
        file.write("\n</location>\n");

        file.write("<users>\n");
//        for (eventInvitees) {
        file.write("<uid>\n" + event.eventInvitees() + "\n</uid>\n");
//        }
        file.write("\n</users>\n");
      }

      file.write("\n</schedule>\n");
      file.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

}
