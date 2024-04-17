package cs3500.nuplanner.providerp3.model;

import org.w3c.dom.Document;
import java.util.List;

public interface ISchedule {

  /**
   * This method merges event inside the schedule.
   */
  void merge(IEvent event);

  /**
   * It checks for overlapping events; exclude self in case of modification.
   */
  boolean isOverlapping(IEvent newEvent, IEvent... exclude);

  /**
   * It returns the list of all events in the schedule.
   */
  List<IEvent> getAllEvents();

  /**
   * This method changes the schedule object to Document object.
   */
  Document toDocument();
}
