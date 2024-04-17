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

/**
 * Adaptor class that converts an Event (client interface) into an IEvent (provider interface).
 */
public class ClientToProviderEventAdaptor implements IEvent {

  private final IEvent ievent;
  private final SchedulingSystem model;

  /**
   * Creates the adaptor.
   *
   * @param event the ReadableEvent passed in
   * @param model our version of the model passed in
   */

  public ClientToProviderEventAdaptor(ReadableEvent event, SchedulingSystem model) {

    this.ievent = convertEventIntoIEvent(event);
    this.model = model;

  }

  /**
   * Converts our version of an event into the providers IEvent.
   *
   * @param event the event to convert
   * @return an IEvent in the providers style
   */

  private IEvent convertEventIntoIEvent(ReadableEvent event) {

    LocalTime startTime = convertEventMilitaryTimeToLocalTime(event.startTime());
    LocalTime endTime = convertEventMilitaryTimeToLocalTime(event.endTime());
    Day startDay = convertDaysOfTheWeekToDay(event.startDay());
    Day endDay = convertDaysOfTheWeekToDay(event.startDay());

    List<IUser> iEventUsers = new ArrayList<>();
    for (String user : event.eventInvitees()) {
      iEventUsers.add(new ClientToProviderUserAdaptor(user, model));
    }

    // TODO: same host object for both host param and invitees param <- assuming this is how their IEvent is formatted
    return new Event(event.name(), startDay, startTime,
            endDay, endTime, event.isOnline(),
            event.location(), iEventUsers.get(0), iEventUsers);

  }

  /**
   * Converts our version of DaysOfTheWeek into the providers Day.
   *
   * @param day our version of DaysOfTheWeek
   * @return the day for the providers version
   */
  private Day convertDaysOfTheWeekToDay(DaysOfTheWeek day) {
    String dayRep = day.toString().toLowerCase();
    return Day.toDay(Character.toUpperCase(dayRep.charAt(0)) + dayRep.substring(1));
  }

  /**
   * Converts between our version of military time and the providers local time.
   *
   * @param militaryTime the military time from our version
   * @return local time that the provider can use
   */
  private LocalTime convertEventMilitaryTimeToLocalTime(int militaryTime) {
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
