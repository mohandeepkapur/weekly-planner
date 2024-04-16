package cs3500.nuplanner.providerp2.model;

import org.w3c.dom.Document;
import java.util.List;

public interface ISchedule {

  /**
   * This method merges event inside the schedule.
   */
  void merge(Event event);

  /**
   * It checks for overlapping events; exclude self in case of modification.
   */
  boolean isOverlapping(Event newEvent, Event... exclude);

  /**
   * It returns the list of all events in the schedule.
   */
  List<Event> getAllEvents();

  /**
   * This method changes the schedule object to Document object.
   */
  Document toDocument();
}
