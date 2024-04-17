package cs3500.nuplanner.providerp3.adaptors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.providerp3.model.Event;
import cs3500.nuplanner.providerp3.model.ISchedule;
import cs3500.nuplanner.providerp3.model.IUser;
import cs3500.nuplanner.providerp3.model.MutableSystems;

public class ClientToProviderModelAdaptor implements MutableSystems {
  SchedulingSystem delegate;

  public ClientToProviderModelAdaptor(SchedulingSystem delegate) {
    this.delegate = delegate;
  }

  @Override
  public void uploadXMLFileToSystem(String filePath) {

  }

  @Override
  public void saveXMLFileFromSystem(String filePath) {

  }

  @Override
  public void addUser(IUser user) {
    this.delegate.addUser(user.getUid());
  }

  @Override
  public void removeUser(IUser user) {
    this.delegate.removeUser(user.getUid());
  }

  @Override
  public void createEvent(String uid, Event event) {
    int startTime = event.getStartTime().getHour() * 100
            + event.getStartTime().getMinute();
    int endTime = event.getEndTime().getHour() * 100
            + event.getEndTime().getMinute();

    List<String> listOfUsers = new ArrayList<>();

    for (int user = 0; user < event.getInvitedUsers().size(); user++) {
      listOfUsers.add(event.getInvitedUsers().get(user).getUid());
    }

    this.delegate.addEvent(uid, listOfUsers, event.getName(), event.getPlace(),
            event.isOnline(), DaysOfTheWeek.stringToDay(event.getStartDay().toString()), startTime,
            DaysOfTheWeek.stringToDay(event.getEndDay().toString()), endTime);
  }

  @Override
  public void modifyEvent(String uid, Event event, Event modifiedEvent) {
    int startTime = event.getStartTime().getHour() * 100
            + event.getStartTime().getMinute();

    int newStartTime = modifiedEvent.getStartTime().getHour() * 100
            + modifiedEvent.getStartTime().getMinute();
    int newEndTime = modifiedEvent.getEndTime().getHour() * 100
            + modifiedEvent.getEndTime().getMinute();

    List<String> listOfUsers = new ArrayList<>();

    for (int user = 0; user < modifiedEvent.getInvitedUsers().size(); user++) {
      listOfUsers.add(modifiedEvent.getInvitedUsers().get(user).getUid());
    }

    NUEvent newModifiedEvent = new NUEvent(listOfUsers, modifiedEvent.getName(),
            modifiedEvent.getPlace(), modifiedEvent.isOnline(),
            DaysOfTheWeek.stringToDay(modifiedEvent.getStartDay().toString()),
            newStartTime, DaysOfTheWeek.stringToDay(modifiedEvent.getEndDay().toString()),
            newEndTime);

    this.delegate.modifyEvent(uid, DaysOfTheWeek.stringToDay(event.getStartDay().toString()),
            startTime, newModifiedEvent);
  }

  @Override
  public void deleteEvent(Event event) {
    int startTime = event.getStartTime().getHour() * 100
            + event.getStartTime().getMinute();
    DaysOfTheWeek startDay = DaysOfTheWeek.stringToDay(event.getStartDay().toString());

    this.delegate.removeEvent(event.getHostUser().getUid(), startDay, startTime);
  }

  @Override
  public Map<String, IUser> getAllUsers() {
    return null;
  }

  @Override
  public IUser getUser(String uid) {
    return null;
  }

  @Override
  public ISchedule getUserSchedule(String uid) {
    return null;
  }

  @Override
  public boolean isEventConflicting(Event event) {

    String host = event.getHostUser().getUid();

    Boolean isOnline = event.isOnline();

    int startTime = event.getStartTime().getHour() * 100
            + event.getStartTime().getMinute();
    int endTime = event.getEndTime().getHour() * 100
            + event.getEndTime().getMinute();

    DaysOfTheWeek startDay = DaysOfTheWeek.stringToDay(event.getStartDay().toString());
    DaysOfTheWeek endDay = DaysOfTheWeek.stringToDay(event.getEndDay().toString());


    List<String> listOfUsers = new ArrayList<>();

    for (int user = 0; user < event.getInvitedUsers().size(); user++) {
      listOfUsers.add(event.getInvitedUsers().get(user).getUid());
    }

    return delegate.eventConflict(host, listOfUsers, event.getName(), event.getPlace(), isOnline,
            startDay, startTime, endDay, endTime);
  }
}
