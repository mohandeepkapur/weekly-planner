package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.List;

/**
 * Event only works with military time.
 */
public class NUEvent implements Event {

  private static int count = 0;
  private final int ID;

  private String name;
  private String location;
  private boolean isOnline;
  private DaysOfTheWeek startDay;
  private int startTime;
  private DaysOfTheWeek endDay;
  private int endTime;
  // first user is OWNER
  private final List<String> invitees;

  /**
   * Constructs an Event.
   */
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

      this.invitees = invitees;
      this.name = eventName;
      this.location = location;
      this.isOnline = isOnline;
      this.startDay = startDay;
      this.startTime = startTime;
      this.endDay = endDay;
      this.endTime = endTime;

      // throw IAE if time-span invalid
      calculateTimeSpan(this.startTime, this.endTime, this.startDay, this.endDay);

      ID = count;
      count++;
  }

  // copy constructor
  public NUEvent(Event other) {
    this.invitees = new ArrayList<>(other.eventInvitees());
    this.name = other.name();
    this.location = other.location();
    this.isOnline = other.isOnline();
    this.startDay = other.startDay();
    this.startTime = other.startTime();
    this.endDay = other.endDay();
    this.endTime = other.endTime();

    calculateTimeSpan(this.startTime, this.endTime, this.startDay, this.endDay);

    ID = count;
    count++;
  }

  @Override
  public int ID() {
    return ID;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public void updateName(String name) {
    this.name = name;
  }

  @Override
  public String location() {
    return this.location;
  }

  @Override
  public void updateLocation(String location) {
    this.location = location;
  }

  @Override
  public boolean isOnline() {
    return this.isOnline;
  }

  @Override
  public void updateIsOnline(boolean isOnline) {
    this.isOnline = isOnline;
  }

  @Override
  public DaysOfTheWeek startDay() {
    return this.startDay;
  }

  @Override
  public void updateStartDay(DaysOfTheWeek startDay) {
    // throw IAE if time-span invalid
    calculateTimeSpan(this.startTime, this.endTime, startDay, this.endDay);
    this.startDay=startDay;
  }

  @Override
  public DaysOfTheWeek endDay() {
    return this.endDay;
  }

  @Override
  public void updateEndDay(DaysOfTheWeek endDay) {
    // throw IAE if time-span invalid
    calculateTimeSpan(this.startTime, this.endTime, this.startDay, endDay);
    this.endDay=endDay;
  }

  @Override
  public int startTime() {
    return this.startTime;
  }

  @Override
  public void updateStartTime(int startTime) {
    // throw IAE if time-span invalid
    calculateTimeSpan(startTime, this.endTime, this.startDay, this.endDay);
    this.startTime = startTime;
  }

  @Override
  public int endTime() {
    return this.endTime;
  }

  @Override
  public void updateEndTime(int endTime) {
    // throw IAE if time-span invalid
    calculateTimeSpan(this.startTime, endTime, this.startDay, this.endDay);
    this.endTime = endTime;
  }

  @Override
  public String eventHost() {
    // defensive copy?
    return this.invitees.get(0);
  }

  // reiterate, first user in list is OWNER
  public List<String> eventInvitees() {
    // defensive copy?
    return invitees;
  }


  @Override
  public void removeInvitee(String invitee) {

    for(int i = 0; i < invitees.size(); i++) {
      if(invitees.get(i).equals(invitee)) {
        invitees.remove(i);
        break;
      }
    }

  }

  // also check to see that events that exceed one week do not exceed timespan!!
  private void calculateTimeSpan(int startTime, int endTime,
                                 DaysOfTheWeek startDay, DaysOfTheWeek endDay)
          throws IllegalArgumentException {

    // Wed 1335 to Thur 1456 -> 24 hours, 1 hour, 21 min -> 1521
    // Wed 1456 to Thur 1335 -> 24 hours, -1 hour, -21 min -> 1359

    int dayDiff = endDay.val() - startDay.val();

    // if this is an event that extends into the next week
    if (dayDiff < 0 || (dayDiff == 0 && endTime <= startTime)) {
      dayDiff = dayDiff + 7;
    }

    int dayRangeMin = dayDiff * 24 * 60;

    int min1 = (startTime % 100) + (startTime/100) * 60;
    int min2 = (endTime % 100) + (endTime/100) * 60;

    int timeRangeMin = min2 - min1;

    if(dayRangeMin + timeRangeMin > (6*24*60) + (23*60) + 59) {
      throw new IllegalArgumentException("Event has a time span that is too long... ");
    }

    // unit: minutes
    int timespan = dayRangeMin + timeRangeMin;
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
