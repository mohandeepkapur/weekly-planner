package cs3500.nuplanner.strategies;

import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

public class AnyTimeStrategy implements SchedulingStrategies {


  @Override
  public Event findTimeForScheduledEvent(SchedulingSystem model, String name, Boolean isOnline,
                                         String location, int duration, List<String> invitees) {

    for (int day = 0; day < 7; day++) {
      DaysOfTheWeek startingDay = convertIntToDay(day);
      for (int startTime = 0; startTime < 2400; startTime++) {
        int endTime = startTime + duration;
        DaysOfTheWeek endingDay = determineEndingDay(day, startTime, duration);

        if (model.eventConflict(invitees.get(0), invitees, name, location, isOnline, startingDay,
                startTime, endingDay, endTime)) {
          return new NUEvent(invitees, name, location, isOnline, startingDay,
                  startTime, endingDay, endTime);
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
  private DaysOfTheWeek determineEndingDay(int day, int startTime, int duration) {
    int daysAfter = (startTime + duration) / 2400;
    if (startTime + duration > 0) {
      return convertIntToDay(day + daysAfter);
    }
    return convertIntToDay(day);
  }

  /**
   * Converts provided int into a day of the week, if possible.
   *
   * @param day int to convert into day
   * @return DaysOfTheWeek enum constant
   * @throws IllegalArgumentException if string cannot be converted into a day
   */
  private DaysOfTheWeek convertIntToDay(int day) {

    if (day == 0) {
      return DaysOfTheWeek.SUNDAY;
    }
    if (day == 1) {
      return DaysOfTheWeek.MONDAY;
    }
    if (day == 2) {
      return DaysOfTheWeek.TUESDAY;
    }
    if (day == 3) {
      return DaysOfTheWeek.WEDNESDAY;
    }
    if (day == 4) {
      return DaysOfTheWeek.THURSDAY;
    }
    if (day == 5) {
      return DaysOfTheWeek.FRIDAY;
    }
    if (day == 6) {
      return DaysOfTheWeek.SATURDAY;
    }

    throw new IllegalArgumentException("Invalid modification request... ");

  }
}
