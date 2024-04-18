package cs3500.nuplanner.provider.adaptors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.ISchedule;
import cs3500.nuplanner.provider.model.IUser;
import cs3500.nuplanner.provider.model.ReadOnlySystems;

/**
 * Adapts client model to read-only provider-model provider-View expects. (read-only)
 */
public class ClientToProviderModelAdaptor implements ReadOnlySystems {

  private final SchedulingSystem clientModel;

  /**
   * Creates the adapter between the different models.
   *
   * @param clientModel our version of the scheduling system passed in
   */
  public ClientToProviderModelAdaptor(SchedulingSystem clientModel) {
    if (clientModel == null) {
      throw new IllegalArgumentException("Adaptor cannot be constructed will null args... ");
    }

    this.clientModel = clientModel;
  }

  @Override
  public Map<String, IUser> getAllUsers() {
    // save all client-model users (in client-model representation)
    List<String> delUsers = this.clientModel.allUsers();

    Map<String, IUser> users = new HashMap<>();

    // convert client-model representation of a user into provider-model-representation
    for (String user : delUsers) {
      users.put(user, new ClientToProviderUserAdaptor(user, clientModel));
    }

    return users;
  }

  @Override
  public IUser getUser(String uid) {
    // adaptor: takes in uid -> extracts schedule from model -> uses schedule
    return new ClientToProviderUserAdaptor(uid, clientModel);
  }

  @Override
  public ISchedule getUserSchedule(String uid) {
    return new ClientToProviderScheduleAdaptor(uid, clientModel);
  }

  @Override
  public boolean isEventConflicting(IEvent event) {
    // convert IEvent into Event
    Event dEvent = new ProviderToClientEventAdaptor(event);

    return this.clientModel.eventConflict(dEvent.host(),
            dEvent.eventInvitees(), dEvent.name(),
            dEvent.location(), dEvent.isOnline(),
            dEvent.startDay(), dEvent.startTime(),
            dEvent.endDay(), dEvent.endTime());

  }
}