package cs3500.nuplanner.provider.adaptors;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.Schedule;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.ISchedule;

/**
 * Adaptor that converts a Schedule into an ISchedule. Full behavior of ISchedule has not been
 * implemented.
 * One reason: our Schedule does not deal with I/O. As it shouldn't.
 */
public class ClientToProviderScheduleAdaptor implements ISchedule {

  private Schedule schedule;
  private SchedulingSystem model;
  private List<ReadableEvent> schedEvents;

  /**
   * Creates the adaptor to bridge between the two schedules.
   *
   * @param user the schedule user
   * @param model our version of the model
   */
  public ClientToProviderScheduleAdaptor(String user, SchedulingSystem model) {
    this.model = model;
    this.schedEvents = model.eventsInSchedule(user);
  }

  @Override
  public void merge(IEvent event) {
    throw new IllegalArgumentException("Client-to-Prov Sched Adaptor doesn't support this feature");
  }

  @Override
  public boolean isOverlapping(IEvent newEvent, IEvent... exclude) {
    throw new IllegalArgumentException("Client-to-Prov Sched Adaptor doesn't support this feature");
  }

  @Override
  public List<IEvent> getAllEvents() {
    List<IEvent> ievents = new ArrayList<>();

    for (ReadableEvent event : this.schedEvents) {
      ievents.add(new ClientToProviderEventAdaptor(event, model));
    }

    return ievents;

  }

  @Override
  public Document toDocument() {
    throw new IllegalArgumentException("Client-to-Prov Sched Adaptor doesn't support this feature");
  }
}