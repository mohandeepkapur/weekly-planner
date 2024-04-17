package cs3500.nuplanner.providerp3.adaptors;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.providerp3.model.Day;
import cs3500.nuplanner.providerp3.model.IEvent;

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
  private final Event delegate;

  public ProviderToClientEventAdaptor(IEvent ievent) {
    if (ievent == null) {
      throw new IllegalArgumentException("Invalid input into adaptor class... ");
    }
    this.delegate = translateIEventToEvent(ievent);
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
    listOfInvitees.add(host);
    for (int users = 0; users < ievent.getInvitedUsers().size(); users++) {
      if (!ievent.getInvitedUsers().get(users).getUid().equals(host)) {
        listOfInvitees.add(ievent.getInvitedUsers().get(users).getUid());
      }
    }
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
    delegate.updateName(name);
  }

  @Override
  public void updateLocation(String location) {
    delegate.updateLocation(location);
  }

  @Override
  public void updateIsOnline(boolean isOnline) {
    delegate.updateIsOnline(isOnline);
  }

  @Override
  public void updateStartDay(DaysOfTheWeek startDay) {
    delegate.updateStartDay(startDay);
  }

  @Override
  public void updateEndDay(DaysOfTheWeek endDay) {
    delegate.updateEndDay(endDay);
  }

  @Override
  public void updateStartTime(int startTime) {
    delegate.updateStartTime(startTime);
  }

  @Override
  public void updateEndTime(int endTime) {
    delegate.updateEndTime(endTime);
  }

  @Override
  public void removeInvitee(String invitee) {
    delegate.removeInvitee(invitee);
  }

  @Override
  public void addInvitee(String invitee) {
    delegate.addInvitee(invitee);
  }

  @Override
  public String host() {
    return delegate.host();
  }

  @Override
  public String name() {
    return delegate.name();
  }

  @Override
  public String location() {
    return delegate.location();
  }

  @Override
  public boolean isOnline() {
    return delegate.isOnline();
  }

  @Override
  public DaysOfTheWeek startDay() {
    return delegate.startDay();
  }

  @Override
  public DaysOfTheWeek endDay() {
    return delegate.endDay();
  }

  @Override
  public int startTime() {
    return delegate.startTime();
  }

  @Override
  public int endTime() {
    return delegate.endTime();
  }

  @Override
  public List<String> eventInvitees() {
    return delegate.eventInvitees();
  }

  @Override
  public boolean containsTime(DaysOfTheWeek day, int time) {
    return delegate.containsTime(day, time);
  }

  @Override
  public List<Integer> extractObjectiveTimePair() {
    return delegate.extractObjectiveTimePair();
  }
}


//public class ProviderToClientEventAdaptor implements Event {
//
//  private IEvent ievent;
//
//  /**
//   * Data necessary to create an event, extracted from IEvent.
//   */
//  private DaysOfTheWeek startDay;
//  private DaysOfTheWeek endDay;
//  // understand why this is bad
//  private int startTime;
//  private int endTime;
//
//  public ProviderToClientEventAdaptor(IEvent ievent) {
//
//    if (ievent == null) {
//      throw new IllegalArgumentException("Invalid input into adaptor class... ");
//    }
//
//    this.ievent = ievent;
//    setIEventToEventFields();
//  }
//
//  private void setIEventToEventFields() {
//    this.startDay = convertDayToDaysOfTheWeek(this.ievent.getStartDay());
//
//    this.endDay = convertDayToDaysOfTheWeek(this.ievent.getEndDay());
//
//    // impl specific <- more like IEvent to NUEvent converter
//    this.startTime = convertBetweenLocalTimeToEventMilitaryTime(this.ievent.getStartTime());
//
//    this.endTime = convertBetweenLocalTimeToEventMilitaryTime(this.ievent.getEndTime());
//  }
//
//  /**
//   * Necessary when trying to modify this impl of client.Event. Because IEvent is apparently
//   * immutable.
//   */
//  private IEvent buildNewIEvent(String name, Day startDay, LocalTime startTime, Day endDay, LocalTime endTime, boolean online, String place, IUser hostUser, List<IUser> invitedUsers) {
//
//    //something icky has happened, and i def have the brainpower to see it
//    return new cs3500.nuplanner.providerp3.model.Event(name, startDay, startTime, endDay, endTime, online, place, hostUser, invitedUsers);
//
//  }
//
//  private DaysOfTheWeek convertDayToDaysOfTheWeek(Day day) {
//    return DaysOfTheWeek.stringToDay(day.toString().toUpperCase());
//  }
//
//  private Day convertDaysOfTheWeekToDay(DaysOfTheWeek day) {
//    String dayRep = day.toString().toLowerCase();
//    return Day.toDay(Character.toUpperCase(dayRep.charAt(0)) + dayRep.substring(1));
//  }
//
//
//  private int convertBetweenLocalTimeToEventMilitaryTime(LocalTime localtime) {
//    // do not want to convert directly into military time
//    // <- diff Event impl would reject such a conversion
//    // <- bc Event holds representation of time in sys <- BAD
//    // JANK
//    // Means this adaptor will only work for one client.Event impl
//    return (localtime.getHour() * 100) + localtime.getMinute();
//  }
//
//  private LocalTime convertBetweenEventMilitaryTimeToLocalTime(int militaryTime) {
//    int hour = militaryTime / 100;
//    int minute = militaryTime % 100;
//    return LocalTime.of(hour, minute);
//  }
//
//  @Override
//  public void updateName(String name) {
//
//    this.ievent = buildNewIEvent(name, this.ievent.getStartDay(),
//            this.ievent.getStartTime(), this.ievent.getEndDay(),
//            this.ievent.getEndTime(), this.ievent.isOnline(),
//            this.ievent.getPlace(), this.ievent.getHostUser(),
//            this.ievent.getInvitedUsers());
//
//    setIEventToEventFields();
//
//  }
//
//  @Override
//  public void updateLocation(String location) {
//    this.ievent = buildNewIEvent(this.ievent.getName(), this.ievent.getStartDay(),
//            this.ievent.getStartTime(), this.ievent.getEndDay(),
//            this.ievent.getEndTime(), this.ievent.isOnline(),
//            location, this.ievent.getHostUser(),
//            this.ievent.getInvitedUsers());
//
//    setIEventToEventFields();
//
//  }
//
//  @Override
//  public void updateIsOnline(boolean isOnline) {
//    this.ievent = buildNewIEvent(this.ievent.getName(), this.ievent.getStartDay(),
//            this.ievent.getStartTime(), this.ievent.getEndDay(),
//            this.ievent.getEndTime(), isOnline,
//            this.ievent.getPlace(), this.ievent.getHostUser(),
//            this.ievent.getInvitedUsers());
//
//    setIEventToEventFields();
//
//  }
//
//  @Override
//  public void updateStartDay(DaysOfTheWeek startDay) {
//    Day newStartDay = convertDaysOfTheWeekToDay(startDay);
//
//    this.ievent = buildNewIEvent(this.ievent.getName(), newStartDay,
//            this.ievent.getStartTime(), this.ievent.getEndDay(),
//            this.ievent.getEndTime(), this.ievent.isOnline(),
//            this.ievent.getPlace(), this.ievent.getHostUser(),
//            this.ievent.getInvitedUsers());
//
//    setIEventToEventFields();
//
//  }
//
//  @Override
//  public void updateEndDay(DaysOfTheWeek endDay) {
//    Day newEndDay = convertDaysOfTheWeekToDay(startDay);
//
//    this.ievent = buildNewIEvent(this.ievent.getName(), this.ievent.getStartDay(),
//            this.ievent.getStartTime(), newEndDay,
//            this.ievent.getEndTime(), this.ievent.isOnline(),
//            this.ievent.getPlace(), this.ievent.getHostUser(),
//            this.ievent.getInvitedUsers());
//
//    setIEventToEventFields();
//
//  }
//
//  @Override
//  public void updateStartTime(int startTime) {
//    LocalTime newStartTime = convertBetweenEventMilitaryTimeToLocalTime(startTime);
//
//    this.ievent = buildNewIEvent(this.ievent.getName(), this.ievent.getStartDay(),
//            newStartTime, this.ievent.getEndDay(),
//            this.ievent.getEndTime(), this.ievent.isOnline(),
//            this.ievent.getPlace(), this.ievent.getHostUser(),
//            this.ievent.getInvitedUsers());
//
//    setIEventToEventFields();
//
//  }
//
//  @Override
//  public void updateEndTime(int endTime) {
//    LocalTime newEndTime = convertBetweenEventMilitaryTimeToLocalTime(endTime);
//
//    this.ievent = buildNewIEvent(this.ievent.getName(), this.ievent.getStartDay(),
//            this.ievent.getStartTime(), this.ievent.getEndDay(),
//            newEndTime, this.ievent.isOnline(),
//            this.ievent.getPlace(), this.ievent.getHostUser(),
//            this.ievent.getInvitedUsers());
//
//    setIEventToEventFields();
//
//  }
//
//  /**
//   * Removes an invitee from the Event. If host of Event is removed, all other invitees of Event
//   * are removed as consequence, creating invitee-less Event.
//   *
//   * @param invitee                     invitee to remove
//   * @throws IllegalArgumentException   if no invitees left in Event
//   * @throws IllegalArgumentException   if invitee to remove is not part of Event
//   */
//  @Override
//  public void removeInvitee(String invitee) {
//
//    List<IUser> invitees = this.ievent.getInvitedUsers();
//
//    if (invitees.isEmpty()) {
//      throw new IllegalArgumentException("Cannot remove invitee from empty invitee list... ");
//    }
//
//    // if invitee mentioned is host of iEvent
//    if (invitee.equals(this.ievent.getHostUser().getUid())) {
//      this.ievent = buildNewIEvent(this.ievent.getName(), this.ievent.getStartDay(),
//              this.ievent.getStartTime(), this.ievent.getEndDay(),
//              this.ievent.getEndTime(), this.ievent.isOnline(),
//              this.ievent.getPlace(), this.ievent.getHostUser(),
//              new ArrayList<IUser>());
//    }
//
//    // if invitee mentioned is non-host
//    for (IUser user : invitees) {
//      if (user.getUid().equals(invitee)) {
//        invitees.remove(user);
//        this.ievent = buildNewIEvent(this.ievent.getName(), this.ievent.getStartDay(),
//                this.ievent.getStartTime(), this.ievent.getEndDay(),
//                this.ievent.getEndTime(), this.ievent.isOnline(),
//                this.ievent.getPlace(), this.ievent.getHostUser(),
//                invitees);
//        break;
//      }
//    }
//
//    // if invitee not contained within iEvent
//    throw new IllegalArgumentException("Event does not contain user to remove... ");
//
//  }
//
//  /**
//   * Adds an invitee to the Event. First invitee added to invitee-less Event becomes host.
//   *
//   * @param invitee                       invitee to add
//   * @throws IllegalArgumentException     if invitee is already invited in Event
//   */
//  @Override
//  public void addInvitee(String invitee) {
//
//    List<IUser> invitees = this.ievent.getInvitedUsers();
//
//    for (IUser user : invitees) {
//      if (user.getUid().equals(invitee)) {
//        throw new IllegalArgumentException("Invitee already exists in schedule... ");
//      }
//    }
//
//    invitees.add()
//
//        invitees.remove(user);
//        this.ievent = buildNewIEvent(this.ievent.getName(), this.ievent.getStartDay(),
//                this.ievent.getStartTime(), this.ievent.getEndDay(),
//                this.ievent.getEndTime(), this.ievent.isOnline(),
//                this.ievent.getPlace(), this.ievent.getHostUser(),
//                invitees);
//
//
//
//    // if invitee not contained within iEvent
//    throw new IllegalArgumentException("Event does not contain user to remove... ");
//
//  }
//
//  @Override
//  public String host() {
//    return this.ievent.getHostUser().getUid();
//  }
//
//  @Override
//  public String name() {
//    return this.ievent.getName();
//  }
//
//  @Override
//  public String location() {
//    return this.ievent.getPlace();
//  }
//
//  @Override
//  public boolean isOnline() {
//    return this.ievent.isOnline();
//  }
//
//  @Override
//  public DaysOfTheWeek startDay() {
//    return this.startDay;
//  }
//
//  @Override
//  public DaysOfTheWeek endDay() {
//    return this.endDay;
//  }
//
//  @Override
//  public int startTime() {
//    return this.startTime;
//  }
//
//  @Override
//  public int endTime() {
//    return this.endTime;
//  }
//
//  @Override
//  public List<String> eventInvitees() {
//    List<String> ieventusers = new ArrayList<>();
//    for (IUser user : this.ievent.getInvitedUsers()) {
//      ieventusers.add(user.getUid());
//    }
//    //make sure host is first in list
//    String eventHost = this.ievent.getHostUser().getUid();
//    if (!ieventusers.get(0).equals(eventHost)) {
//      ieventusers.remove(eventHost);
//      ieventusers.add(0, eventHost);
//    }
//    return ieventusers;
//  }
//
//  /**
//   * Checks whether provided time is contained within Event.
//   *
//   * @param day     day event supposedly contains
//   * @param time    time event supposedly contains
//   * @return        whether provided time is contained within Event
//   */
//  @Override
//  public boolean containsTime(DaysOfTheWeek day, int time) {
//    List<Integer> objValues = extractObjectiveTimePair();
//    int providedDayTime = (day.val() * 60 * 24) + (time / 100 * 60) + (time % 100);
//
//    return providedDayTime <= objValues.get(1) && providedDayTime >= objValues.get(0);
//  }
//
//  @Override
//  public List<Integer> extractObjectiveTimePair() {
//    int sDv = this.startDay().val();
//    int sT = this.startTime();
//
//    int eDv = this.endDay().val();
//    int eT = this.endTime();
//
//    int startVal;
//    int endVal;
//
//    // event that extends into next week
//    if (eDv - sDv < 0 || (eDv - sDv == 0 && eT <= sT)) {
//      endVal = ((eDv + 7) * 60 * 24) + (eT / 100 * 60) + (eT % 100);
//    } else {
//      // event contained within first week
//      endVal = (eDv * 60 * 24) + (eT / 100 * 60) + (eT % 100);
//    }
//    // start day always within first week
//    startVal = (sDv * 60 * 24) + (sT / 100 * 60) + (sT % 100);
//
//    return new ArrayList<Integer>(List.of(startVal, endVal));
//  }
//
//}
