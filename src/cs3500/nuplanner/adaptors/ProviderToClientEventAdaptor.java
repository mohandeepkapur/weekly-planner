package cs3500.nuplanner.adaptors;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.provider.model.Day;
import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.model.IUser;

// there's something I'm conceptually missing rn...
// Event interface should really say its coupled with military time now
// was able to avoid mentioning this with placing objtime as Event method, but now, idts

/*
FOR TAs:
bc providers send IEvent to features -> means their View created IEvent <- which should be controller's job
this means adapter adapting their IEvent into an Event needed <- adaptor not aware of impl-type of IEvent yet
but they've made their IEvent immutable -> which means anytime an Event-adaptor needs to be modified
  adaptor must construct a new IEvent
  which means adaptor must construct a provider.Event inside <- now adaptor is aware of impl-type of IEvent
  what is the forest from the treeees <- there is still a forest
 */

/**
 * Takes in an IEvent, spits out an Event!
 * <p>
 * Since only available IEvent impl from Provider is immutable, will need to construct
 * new IEvents everytime client.Event wants to be modified.
 * <p>
 * Only available IEvent impl is named Event, which will be mentioned as provider.Event to avoid
 * confusion between client.Event interface, and provider.Event implementation of IEvent.
 * <p>
 * Usage -> to translate IEvent sent into provider Features by View
 */
public class ProviderToClientEventAdaptor implements Event {

  // Event needs invitees organized in a specific way
  private final Event clientEvent;

  /**
   * Creates the adaptor that translates between an IEvent and Event.
   *
   * @param ievent the IEvent to convert to an event
   */
  public ProviderToClientEventAdaptor(IEvent ievent) {
    if (ievent == null) {
      throw new IllegalArgumentException("Invalid input into adaptor class... ");
    }

    this.clientEvent = translateIEventToEvent(ievent);
  }

  /**
   * Translates an IEvent into our version of an event.
   *
   * @param ievent the IEvent taken in from the provider
   */
  private Event translateIEventToEvent(IEvent ievent) {

    String name = ievent.getName();
    String location = ievent.getPlace();
    boolean isOnline = ievent.isOnline();

    // TIME CLASS
    int startTime = convertLocalTimeToEventMilitaryTime(ievent.getStartTime());
    int endTime = convertLocalTimeToEventMilitaryTime(ievent.getEndTime());
    DaysOfTheWeek startDay = convertDayToDaysOfTheWeek(ievent.getStartDay());
    DaysOfTheWeek endDay = convertDayToDaysOfTheWeek(ievent.getEndDay());

    // USERS
    ArrayList<String> listOfInvitees = new ArrayList<>();
    String host = ievent.getHostUser().getUid();

    // IMPORTANT
    // Ensures host of event is first in list of invitees
    listOfInvitees.add(host);
    for ( IUser user : ievent.getInvitedUsers()) {
      // their IEvent does not include host in invitee list, so below check is useless
      if (!user.getUid().equals(host)) {
        listOfInvitees.add(user.getUid());
      }
    }

    // can run a check here -> to see if invites does contain host

    return new NUEvent(listOfInvitees, name, location, isOnline,
            startDay, startTime, endDay, endTime);
  }

  /**
   * Converts the providers Day into our version of DaysOfTheWeek.
   *
   * @param day the day passed in from the provider
   * @return a matching DaysOfTheWeek
   */
  private DaysOfTheWeek convertDayToDaysOfTheWeek(Day day) {
    return DaysOfTheWeek.stringToDay(day.toString().toUpperCase());
  }

  /**
   * Converts the providers version of local time into our representation of military time.
   *
   * @param localtime the local time from the provider
   * @return the military time in our format
   */
  private int convertLocalTimeToEventMilitaryTime(LocalTime localtime) {
    // do not want to convert directly into military time
    // <- diff Event impl would reject such a conversion
    // <- bc Event holds representation of time in sys <- BAD
    // JANK
    // Means this adaptor will only work for one client.Event impl
    return (localtime.getHour() * 100) + localtime.getMinute();
  }

  @Override
  public void updateName(String name) {
    clientEvent.updateName(name);
  }

  @Override
  public void updateLocation(String location) {
    clientEvent.updateLocation(location);
  }

  @Override
  public void updateIsOnline(boolean isOnline) {
    clientEvent.updateIsOnline(isOnline);
  }

  @Override
  public void updateStartDay(DaysOfTheWeek startDay) {
    clientEvent.updateStartDay(startDay);
  }

  @Override
  public void updateEndDay(DaysOfTheWeek endDay) {
    clientEvent.updateEndDay(endDay);
  }

  @Override
  public void updateStartTime(int startTime) {
    clientEvent.updateStartTime(startTime);
  }

  @Override
  public void updateEndTime(int endTime) {
    clientEvent.updateEndTime(endTime);
  }

  @Override
  public void removeInvitee(String invitee) {
    clientEvent.removeInvitee(invitee);
  }

  @Override
  public void addInvitee(String invitee) {
    clientEvent.addInvitee(invitee);
  }

  @Override
  public String host() {
    return clientEvent.host();
  }

  @Override
  public String name() {
    return clientEvent.name();
  }

  @Override
  public String location() {
    return clientEvent.location();
  }

  @Override
  public boolean isOnline() {
    return clientEvent.isOnline();
  }

  @Override
  public DaysOfTheWeek startDay() {
    return clientEvent.startDay();
  }

  @Override
  public DaysOfTheWeek endDay() {
    return clientEvent.endDay();
  }

  @Override
  public int startTime() {
    return clientEvent.startTime();
  }

  @Override
  public int endTime() {
    return clientEvent.endTime();
  }

  @Override
  public List<String> eventInvitees() {
    return clientEvent.eventInvitees();
  }

  @Override
  public boolean containsTime(DaysOfTheWeek day, int time) {
    return clientEvent.containsTime(day, time);
  }

  @Override
  public List<Integer> extractObjectiveTimePair() {
    return clientEvent.extractObjectiveTimePair();
  }
}

