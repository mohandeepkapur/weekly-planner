package cs3500.nuplanner.providerp3.adaptors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.providerp3.model.IEvent;
import cs3500.nuplanner.providerp3.model.ISchedule;
import cs3500.nuplanner.providerp3.model.IUser;
import cs3500.nuplanner.providerp3.model.MutableSystems;
import cs3500.nuplanner.providerp3.model.ReadOnlySystems;

/**
 * Adapts client model to provider model their View expects. (read-only)
 */
public class ClientToProviderModelAdaptor implements ReadOnlySystems {

  private SchedulingSystem delegate;

  /**
   * Creates the adapter between the different models.
   *
   * @param delegate our version of the scheduling system passed in
   */

  public ClientToProviderModelAdaptor(SchedulingSystem delegate) {
    this.delegate = delegate;
  }

  @Override
  public Map<String, IUser> getAllUsers() {
    List<String> delUsers = this.delegate.allUsers();
    Map<String, IUser> users = new HashMap<>();
    for (String user : delUsers) {
      users.put(user, new ClientToProviderUserAdaptor(user, delegate));
    }
    return users;
  }

  @Override
  public IUser getUser(String uid) {
    // adaptor: takes in uid -> extracts schedule from model -> uses schedule
    return new ClientToProviderUserAdaptor(uid, delegate);
  }

  @Override
  public ISchedule getUserSchedule(String uid) {
    return new ClientToProviderScheduleAdaptor(uid, delegate);
  }

  @Override
  public boolean isEventConflicting(IEvent event) {

    Event dEvent = new ProviderToClientEventAdaptor(event);

    return this.delegate.eventConflict(dEvent.host(),
            dEvent.eventInvitees(), dEvent.name(),
            dEvent.location(), dEvent.isOnline(),
            dEvent.startDay(), dEvent.startTime(),
            dEvent.endDay(), dEvent.endTime());

  }
}