package cs3500.nuplanner.providerp2.strategy;

import java.time.LocalTime;
import java.util.List;

import cs3500.nuplanner.providerp2.model.Day;
import cs3500.nuplanner.providerp2.model.Event;
import cs3500.nuplanner.providerp2.model.IUser;
import cs3500.nuplanner.providerp2.model.MutableSystems;

/**
 * Creates the class for scheduling an event with a duration at any time.
 */
public class AnyTime implements Strategy {
  private MutableSystems model;

  public AnyTime(MutableSystems model) {
    this.model = model;
  }

  @Override
  public Event createEventWithDuration(String eventName, boolean isOnline,
                                       String location, String duration,
                                       String selectedUser, List<IUser> users) {
    int duration1 = Integer.parseInt(duration);
    int hour = 0;
    int minute = 0;
    int endHour = duration1 / 60;
    Day startDay = Day.SUNDAY;
    Day endDay = Day.SUNDAY;
    while (endHour >= 24) {
      endHour -= 24;
    }
    LocalTime endTime = LocalTime.of(endHour, duration1 % 60);
    Event possible = new Event(eventName, startDay, LocalTime.of(hour, minute),
            endDay, endTime, isOnline, location, getHostUser(selectedUser), users);
    while (this.model.isEventConflicting(possible)) {
      if (duration1 >= 800 && duration1 <= 1600) {
        endDay = Day.MONDAY;
      }
      else if (duration1 >= 1600 && duration1 <= 2400) {
        endDay = Day.TUESDAY;
      }
      else if (duration1 >= 2400 && duration1 <= 3200) {
        endDay = Day.WEDNESDAY;
      }
      else if (duration1 >= 3200 && duration1 <= 4000) {
        endDay = Day.THURSDAY;
      }
      else if (duration1 >= 4000 && duration1 <= 4800) {
        endDay = Day.FRIDAY;
      }
      else if (duration1 >= 4800 && duration1 <= 5600) {
        endDay = Day.SATURDAY;
      }
      else {
        return null;
      }
      if (endTime.getHour() > 23) {
        endTime = LocalTime.of(0, endTime.getMinute());
      }
      while (minute < 60 && hour < 24) {
        possible = new Event(eventName, startDay, LocalTime.of(hour, minute),
                endDay, endTime, isOnline, location, getHostUser(selectedUser), users);
        minute++;
      }
      minute = 0;
      hour++;
      startDay = endDay;
    }
    return possible;
  }

  private IUser getHostUser(String uid) {
    return this.model.getUser(uid);
  }
}
