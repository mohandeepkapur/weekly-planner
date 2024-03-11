package cs3500.nuplanner.model.hw05;

import java.util.List;

// not getters and setters <- intention not to return field, but state of obj
public class NUEvent implements Event {

  // all an event can check for:
  // whether time-span is valid when constructing or updating event

  // unit: minutes
  private int timespan;

  private String name;
  private String location;
  private boolean isOnline;
  private DaysOfTheWeek startDay;
  private int startTime;
  private DaysOfTheWeek endDay;
  private int endTime;
  // first user is OWNER
  private List<String> invitees;
  //private String owner;

  /**
   * Constructs an Event.
   */
  // assuming client will pass in proper military time format
  public NUEvent(List<String> invitees,
               String eventName, String location, boolean isOnline,
               DaysOfTheWeek startDay, int startTime,
               DaysOfTheWeek endDay, int endTime) {

      if (eventName == null || location == null || invitees == null) {
        throw new IllegalArgumentException("Invalid... no Event parameters can be null");
      }

      if(startDay == null || endDay == null) {
        throw new IllegalArgumentException("Invalid start or end day... cannot be null");
      }

      // throw IAE if startTime or endTime invalid
      // ensures only proper military time accepted by Event
      if ((startTime/100) < 0 || (startTime/100) > 23 || (endTime/100) < 0
              || (endTime/100) > 23 || (startTime%100) > 59 || (endTime%100) > 59) {
        throw new IllegalArgumentException("Invalid start or end time given... ");
      }

      //this.owner = user;
      this.invitees = invitees;
      this.name = eventName;
      this.location = location;
      this.isOnline = isOnline;
      this.startDay = startDay;
      this.startTime = startTime;
      this.endDay = endDay;
      this.endTime = endTime;

      // throw IAE if time-span invalid
      initializeTimeSpan(this.startTime, this.endTime, this.startDay, this.endDay);
  }

  // copy constructor
  public NUEvent(Event other) {
    this.invitees = other.eventInvitees();
    this.name = other.name();
    this.location = other.location();
    this.isOnline = other.isOnline();
    this.startDay = other.startDay();
    this.startTime = other.startTime();
    this.endDay = other.endDay();
    this.endTime = other.endTime();
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public void changeName(String name) {
    this.name = name;
  }

  @Override
  public String location() {
    return this.location;
  }

  @Override
  public void changeLocation(String location) {
    this.location = location;
  }

  @Override
  public boolean isOnline() {
    return this.isOnline;
  }

  @Override
  public void changeIsOnline(boolean isOnline) {
    this.isOnline = isOnline;
  }

  @Override
  public DaysOfTheWeek startDay() {
    return this.startDay;
  }

  @Override
  public void changeStartDay(DaysOfTheWeek startDay) {
    // throw IAE if time-span invalid
    initializeTimeSpan(this.startTime, this.endTime, startDay, this.endDay);
    this.startDay=startDay;
  }

  @Override
  public DaysOfTheWeek endDay() {
    return this.endDay;
  }

  @Override
  public void changeEndDay(DaysOfTheWeek endDay) {
    // throw IAE if time-span invalid
    initializeTimeSpan(this.startTime, this.endTime, this.startDay, endDay);
    this.endDay=endDay;
  }

  @Override
  public int startTime() {
    return this.startTime;
  }

  @Override
  public void changeStartTime(int startTime) {
    // throw IAE if time-span invalid
    initializeTimeSpan(startTime, this.endTime, this.startDay, this.endDay);
    this.startTime = startTime;
  }

  @Override
  public int endTime() {
    return this.endTime;
  }

  @Override
  public void changeEndTime(int endTime) {
    // throw IAE if time-span invalid
    initializeTimeSpan(this.startTime, endTime, this.startDay, this.endDay);
    this.endTime = endTime;
  }

  @Override
  public String eventHost() {
    // defensive copy?
    return this.invitees.get(0);
  }

  // assumes that first user in list is OWNER
  public List<String> eventInvitees() {
    // defensive copy?
    return invitees;
  }

  // can event remove its owner? sure, but that is an irrelevant step
  // SS persp: asked to remove event --> gets event obj --> calls every SC and passes in event obj
  // SC persp: finds event obj, removes it from list
  @Override
  public void removeInvitee(String invitee) {

    for(int i = 0; i < invitees.size(); i++) {
      if(invitees.get(i).equals(invitee)) {
        invitees.remove(i);
        break;
      }
    }

  }

  private void initializeTimeSpan(int startTime, int endTime, DaysOfTheWeek startDay, DaysOfTheWeek endDay) {

    // Wed 1335 to Thur 1456 -> 24 hours, 1 hour, 21 min -> 1521
    // Wed 1456 to Thur 1335 -> 24 hours, -1 hour, -21 min -> 1359

    int dayDiff = convertDayToNum(endDay) - convertDayToNum(startDay);

    if (dayDiff < 0 || (dayDiff == 0 && endTime < startTime)) {
      dayDiff = dayDiff + 7;
    }

    int dayRangeMin = dayDiff * 24 * 60;

    int timeDiff = endTime - startTime;

    int timeRangeMin = (timeDiff % 100) + (timeDiff/100) * 60;

    if(dayRangeMin + timeRangeMin > (6*24*60) + (23*60) + 59) {
      throw new IllegalArgumentException("Time span of event is too long... ");
    }

    timespan = dayRangeMin + timeRangeMin;
  }

  private int convertDayToNum(DaysOfTheWeek day) {

    switch(day.toString()) {
      case "SUNDAY":
        return 0;
      case "MONDAY":
        return 1;
      case "TUESDAY":
        return 2;
      case "WEDNESDAY":
        return 3;
      case "THURSDAY":
        return 4;
      case "FRIDAY":
        return 5;
      case "SATURDAY":
        return 6;
      default:
        throw new IllegalArgumentException("Shouldn't be able to be reached");
    }

  }

}
