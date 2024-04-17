package cs3500.nuplanner.providerp3.adaptors;

import java.time.LocalTime;
import java.util.List;

import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.providerp3.model.Day;
import cs3500.nuplanner.providerp3.model.IEvent;
import cs3500.nuplanner.providerp3.model.IUser;

public class ClientToProviderEventAdaptor implements IEvent {

  public ClientToProviderEventAdaptor(ReadableEvent event) {

  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public String getPlace() {
    return null;
  }

  @Override
  public boolean isOnline() {
    return false;
  }

  @Override
  public LocalTime getStartTime() {
    return null;
  }

  @Override
  public LocalTime getEndTime() {
    return null;
  }

  @Override
  public Day getStartDay() {
    return null;
  }

  @Override
  public Day getEndDay() {
    return null;
  }

  @Override
  public IUser getHostUser() {
    return null;
  }

  @Override
  public List<IUser> getInvitedUsers() {
    return null;
  }

  @Override
  public String invites() {
    return null;
  }
}
