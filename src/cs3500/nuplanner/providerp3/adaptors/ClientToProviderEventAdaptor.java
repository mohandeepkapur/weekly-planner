package cs3500.nuplanner.providerp3.adaptors;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.providerp3.model.Day;
import cs3500.nuplanner.providerp3.model.Event;
import cs3500.nuplanner.providerp3.model.IEvent;
import cs3500.nuplanner.providerp3.model.IUser;

public class ClientToProviderEventAdaptor implements IEvent {

  private final IEvent ievent;
  private final SchedulingSystem model;

  public ClientToProviderEventAdaptor(ReadableEvent event, SchedulingSystem model) {

    this.ievent = convertEventIntoIEvent(event);
    this.model = model;

  }

  // assuming host user is subset of invited users
  // putting host user first wouldn't hurt
  private IEvent convertEventIntoIEvent(ReadableEvent event) {

    LocalTime startTime = convertBetweenEventMilitaryTimeToLocalTime(event.startTime());
    LocalTime endTime = convertBetweenEventMilitaryTimeToLocalTime(event.endTime());
    Day startDay = convertDaysOfTheWeekToDay(event.startDay());
    Day endDay = convertDaysOfTheWeekToDay(event.startDay());

    List<IUser> iEventUsers = new ArrayList<>();
    for (String user : event.eventInvitees()) {
      iEventUsers.add(new ClientToProviderUserAdaptor(user, model));
    }

    // TODO: same host object for both host param and invitees param <- pot issue? dont see how
    return new Event(event.name(), startDay, startTime,
            endDay, endTime, event.isOnline(),
            event.location(), iEventUsers.get(0), iEventUsers);

  }

  private Day convertDaysOfTheWeekToDay(DaysOfTheWeek day) {
    String dayRep = day.toString().toLowerCase();
    return Day.toDay(Character.toUpperCase(dayRep.charAt(0)) + dayRep.substring(1));
  }

  private LocalTime convertBetweenEventMilitaryTimeToLocalTime(int militaryTime) {
    int hour = militaryTime / 100;
    int minute = militaryTime % 100;
    return LocalTime.of(hour, minute);
  }

  @Override
  public String getName() {
    return this.ievent.getName();
  }

  @Override
  public String getPlace() {
    return this.ievent.getPlace();
  }

  @Override
  public boolean isOnline() {
    return this.ievent.isOnline();
  }

  @Override
  public LocalTime getStartTime() {
    return this.ievent.getStartTime();
  }

  @Override
  public LocalTime getEndTime() {
    return this.ievent.getEndTime();
  }

  @Override
  public Day getStartDay() {
    return this.ievent.getStartDay();
  }

  @Override
  public Day getEndDay() {
    return this.ievent.getEndDay();
  }

  @Override
  public IUser getHostUser() {
    return this.ievent.getHostUser();
  }

  @Override
  public List<IUser> getInvitedUsers() {
    return this.ievent.getInvitedUsers();
  }

  @Override
  public String invites() {
    return this.ievent.invites();
  }

}
