package cs3500.nuplanner.providerp3.adaptors;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.model.hw05.Schedule;
import cs3500.nuplanner.providerp3.model.Event;
import cs3500.nuplanner.providerp3.model.ISchedule;

public class ClientToProviderScheduleAdaptor implements ISchedule {
  Schedule delegate;

  public ClientToProviderScheduleAdaptor(Schedule delegate) {
    this.delegate = delegate;
  }

  @Override
  public void merge(Event event) {

    cs3500.nuplanner.model.hw05.Event properEvent = extractUsersAndTime(event);

    this.delegate.addEvent(properEvent);
  }

  @Override
  public boolean isOverlapping(Event newEvent, Event... exclude) {

    cs3500.nuplanner.model.hw05.Event properEvent = extractUsersAndTime(newEvent);

    return this.delegate.eventConflict(properEvent);
  }

  private cs3500.nuplanner.model.hw05.Event extractUsersAndTime(Event newEvent) {
    int startTime = newEvent.getStartTime().getHour() * 100
            + newEvent.getStartTime().getMinute();
    int endTime = newEvent.getEndTime().getHour() * 100
            + newEvent.getEndTime().getMinute();

    List<String> listOfUsers = new ArrayList<>();

    for (int user = 0; user < newEvent.getInvitedUsers().size(); user++) {
      listOfUsers.add(newEvent.getInvitedUsers().get(user).getUid());
    }

    return new NUEvent(listOfUsers,
            newEvent.getName(),
            newEvent.getPlace(), newEvent.isOnline(),
            DaysOfTheWeek.stringToDay(newEvent.getStartDay().toString()), startTime,
            DaysOfTheWeek.stringToDay(newEvent.getEndDay().toString()), endTime);
  }

  @Override
  public List<Event> getAllEvents() {
    return null;
//    return delegate.events();
  }

  @Override
  public Document toDocument() {
    return null;
  }
}
