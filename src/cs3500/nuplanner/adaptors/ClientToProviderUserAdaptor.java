package cs3500.nuplanner.adaptors;

import org.w3c.dom.NodeList;

import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.ISchedule;
import cs3500.nuplanner.provider.model.IUser;

/**
 * Wraps existing user and their schedule in client-model into an IUser.
 */
public class ClientToProviderUserAdaptor implements IUser {

  private String clientUser;
  private SchedulingSystem clientModel;

  /**
   * Creates the adaptor between client-User and provider-User.
   *
   * @param clientUser client-user
   * @param clientModel client-model
   *
   * @implNote need to compose with client-model to extract Schedule behavior for provider-User
   */
  public ClientToProviderUserAdaptor(String clientUser, SchedulingSystem clientModel) {
    if (clientUser == null || clientModel == null) {
      throw new IllegalArgumentException("Adaptor cannot be constructed will null args... ");
    }

    this.clientUser = clientUser;
    this.clientModel = clientModel;
  }

  @Override
  public ISchedule mergeSchedule(NodeList eventList) {
    throw new IllegalArgumentException("Client-to-Prov User Adaptor doesn't support this feature");
  }

  @Override
  public void setSchedule(ISchedule s) {
    throw new IllegalArgumentException("Client-to-Prov User Adaptor doesn't support this feature");
  }

  @Override
  public String getUid() {
    return this.clientUser;
  }

  @Override
  public ISchedule getSchedule() {
    return new ClientToProviderScheduleAdaptor(this.clientUser, this.clientModel);
  }

  @Override
  public void removeEventFromSchedule(IEvent event) {
    throw new IllegalArgumentException("Client-to-Prov User Adaptor doesn't support this feature");
  }

}