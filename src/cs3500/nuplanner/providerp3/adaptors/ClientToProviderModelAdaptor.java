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

  //  @Override
  //  public void uploadXMLFileToSystem(String filePath) {
  //    // last
  //  }
  //
  //  @Override
  //  public void saveXMLFileFromSystem(String filePath) {
  //    // last
  //  }
  //
  //  @Override
  //  public void addUser(IUser user) {
  //    this.delegate.addUser(user.getUid());
  //  }
  //
  //  @Override
  //  public void removeUser(IUser user) {
  //    this.delegate.removeUser(user.getUid());
  //  }
  //
  //  @Override
  //  public void createEvent(String uid, IEvent event) {
  //    cs3500.nuplanner.model.hw05.Event dEvent = new ProviderToClientEventAdaptor(event);
  //    this.delegate.addEvent(uid, dEvent.eventInvitees(), dEvent.name(),
  //            dEvent.location(), dEvent.isOnline(), dEvent.startDay(),
  //            dEvent.startTime(), dEvent.endDay(), dEvent.endTime());
  //  }
  //
  //  @Override
  //  public void modifyEvent(String uid, IEvent event, IEvent modifiedEvent) {
  //
  //  }
  //
  //  @Override
  //  public void deleteEvent(IEvent event) {
  //
  //  }
  //
  //  @Override
  //  public void createEvent(String uid, Event event) {
  //    int startTime = event.getStartTime().getHour() * 100
  //            + event.getStartTime().getMinute();
  //    int endTime = event.getEndTime().getHour() * 100
  //            + event.getEndTime().getMinute();
  //
  //    List<String> listOfUsers = new ArrayList<>();
  //
  //    for (int user = 0; user < event.getInvitedUsers().size(); user++) {
  //      listOfUsers.add(event.getInvitedUsers().get(user).getUid());
  //    }
  //
  //    this.delegate.addEvent(uid, listOfUsers, event.getName(), event.getPlace(),
  //            event.isOnline(), DaysOfTheWeek.stringToDay(event.getStartDay().toString()), startTime,
  //            DaysOfTheWeek.stringToDay(event.getEndDay().toString()), endTime);
  //  }
  //
  //  @Override
  //  public void modifyEvent(String uid, Event event, Event modifiedEvent) {
  //    int startTime = event.getStartTime().getHour() * 100
  //            + event.getStartTime().getMinute();
  //
  //    int newStartTime = modifiedEvent.getStartTime().getHour() * 100
  //            + modifiedEvent.getStartTime().getMinute();
  //    int newEndTime = modifiedEvent.getEndTime().getHour() * 100
  //            + modifiedEvent.getEndTime().getMinute();
  //
  //    List<String> listOfUsers = new ArrayList<>();
  //
  //    for (int user = 0; user < modifiedEvent.getInvitedUsers().size(); user++) {
  //      listOfUsers.add(modifiedEvent.getInvitedUsers().get(user).getUid());
  //    }
  //
  //    NUEvent newModifiedEvent = new NUEvent(listOfUsers, modifiedEvent.getName(),
  //            modifiedEvent.getPlace(), modifiedEvent.isOnline(),
  //            DaysOfTheWeek.stringToDay(modifiedEvent.getStartDay().toString()),
  //            newStartTime, DaysOfTheWeek.stringToDay(modifiedEvent.getEndDay().toString()),
  //            newEndTime);
  //
  //    this.delegate.modifyEvent(uid, DaysOfTheWeek.stringToDay(event.getStartDay().toString()),
  //            startTime, newModifiedEvent);
  //  }
  //
  //  @Override
  //  public void deleteEvent(Event event) {
  //    int startTime = event.getStartTime().getHour() * 100
  //            + event.getStartTime().getMinute();
  //    DaysOfTheWeek startDay = DaysOfTheWeek.stringToDay(event.getStartDay().toString());
  //
  //    this.delegate.removeEvent(event.getHostUser().getUid(), startDay, startTime);
  //  }

}
