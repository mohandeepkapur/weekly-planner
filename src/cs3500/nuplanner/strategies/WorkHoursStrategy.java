package cs3500.nuplanner.strategies;

import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

public class WorkHoursStrategy implements SchedulingStrategies {
  @Override
  public Event findTimeForScheduledEvent(SchedulingSystem model, String name, Boolean isOnline, String location, int duration, List<String> invitees) {

    for (int day = 1; day <= 5; day++) {
      DaysOfTheWeek startingDay = DaysOfTheWeek.valToDay(day);
      for (int startTime = 900; startTime < 1700; startTime++) {
        int endTime = startTime + duration;
        int endingDay = determineEndingDay(day, startTime, duration);

        if (endTime <= 1700
                && endingDay <= 5
                && model.eventConflict(invitees.get(0), invitees, name, location, isOnline,
                startingDay,
                startTime, DaysOfTheWeek.valToDay(endingDay), endTime)) {
          return new NUEvent(invitees, name, location, isOnline, startingDay,
                  startTime, DaysOfTheWeek.valToDay(endingDay), endTime);
        } //do nothing and rerun the top For loop
      }
    }
    throw new IllegalArgumentException("Can't create event with provided parameters");
  }

  /**
   * Returns the correct ending day, which may not be the current day.
   *
   * @param day       starting day of the event
   * @param startTime starting time of the event
   * @param duration  duration of the event
   * @return ending day of the event
   */
  private int determineEndingDay(int day, int startTime, int duration) {
    int daysAfter = (startTime + duration) / 2400;
    if (startTime + duration > 0) {
      return day + daysAfter;
    }
    return day;
  }

}
