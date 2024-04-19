package cs3500.nuplanner.adaptors;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.ISchedule;

/**
 * Adaptor that converts a Schedule into an ISchedule. Full behavior of ISchedule has not been
 * implemented, justified in Peer Review.
 */
public class ClientToProviderScheduleAdaptor implements ISchedule {

  private SchedulingSystem clientModel;
  private List<ReadableEvent> scheduleEvents;

  /**
   * Creates the adaptor to bridge between the two schedules.
   *
   * @param user the schedule user
   * @param clientModel our version of the model
   */
  public ClientToProviderScheduleAdaptor(String user, SchedulingSystem clientModel) {
    if (user == null || clientModel == null) {
      throw new IllegalArgumentException("Adaptor cannot be constructed will null args... ");
    }

    this.scheduleEvents = clientModel.eventsInSchedule(user);
    this.clientModel = clientModel;

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

    for (ReadableEvent event : this.scheduleEvents) {
      ievents.add(new ClientToProviderEventAdaptor(event, clientModel));
    }

    return ievents;

  }

  @Override
  public Document toDocument() {
    throw new IllegalArgumentException("Client-to-Prov Sched Adaptor doesn't support this feature");
  }
}