package cs3500.nuplanner.strategies;

import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.strategies.SchedulingStrategies;

/**
 * A strategy that lets the program create a scheduled event using only work hours which are 9AM
 * to 5PM from Monday to Friday.
 */

public class WorkHoursStrategy implements SchedulingStrategies {
  @Override
  public Event findTimeForScheduledEvent(SchedulingSystem model, String name, Boolean isOnline,
                                         String location, int duration, List<String> invitees) {

    for (int day = 1; day <= 5; day++) {
      DaysOfTheWeek startingDay = DaysOfTheWeek.valToDay(day);

      for (int hour = 9; hour <= 17; hour++) {
        for (int minutes = 0; minutes < 59; minutes++) {

          int startTime = (hour * 100) + minutes;
          int endTime = startTime + convertToMilitaryTime(duration);

          DaysOfTheWeek endingDay = determineEndingDay(day, startTime, duration);

          System.out.println(startTime);
          System.out.println(endTime);
          System.out.println(startingDay);
          System.out.println(endingDay);

          if (!model.eventConflict(invitees.get(0), invitees, name, location, isOnline, startingDay,
                  startTime, endingDay, endTime)) {

            return new NUEvent(invitees, name, location, isOnline, startingDay,
                    startTime, endingDay, endTime);
          }
        }


      } //do nothing and rerun the top For loop

    }
    throw new IllegalArgumentException("Can't create event with provided parameters");
  }

  private int convertToMilitaryTime(int duration) {
    int minutes = duration % 60;
    int hours = (duration / 60) * 100;
    return hours + minutes;
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
      return DaysOfTheWeek.valToDay(day + daysAfter);
    }
    return DaysOfTheWeek.valToDay(day);
  }

}
