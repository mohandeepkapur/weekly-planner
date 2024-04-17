package cs3500.nuplanner.providerp3.adaptors;

import org.w3c.dom.NodeList;

import cs3500.nuplanner.model.hw05.NUSchedule;
import cs3500.nuplanner.model.hw05.Schedule;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.providerp3.model.Event;
import cs3500.nuplanner.providerp3.model.IEvent;
import cs3500.nuplanner.providerp3.model.ISchedule;
import cs3500.nuplanner.providerp3.model.IUser;

/**
 * Wraps existing user and their schedule in client-model into an IUser.
 */
public class ClientToProviderUserAdaptor implements IUser {

  private String user;
  private SchedulingSystem model;
  private Schedule schedule;

  public ClientToProviderUserAdaptor(String user, SchedulingSystem model) {
    this.user = user;
    this.model = model;
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
    return this.user;
  }

  @Override
  public ISchedule getSchedule() {
    return new ClientToProviderScheduleAdaptor(this.user, this.model);
  }

  @Override
  public void removeEventFromSchedule(IEvent event) {
    throw new IllegalArgumentException("Client-to-Prov User Adaptor doesn't support this feature");
  }

}