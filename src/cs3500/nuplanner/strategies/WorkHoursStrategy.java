package cs3500.nuplanner.strategies;

import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.model.hw05.Time;
import cs3500.nuplanner.strategies.SchedulingStrategies;

/**
 * A strategy that lets the program create a scheduled event using only work hours which are 9AM
 * to 5PM from Monday to Friday.
 */

public class WorkHoursStrategy implements SchedulingStrategies {

  @Override
  public Event findTimeForScheduledEvent(SchedulingSystem model, String name, Boolean isOnline,
                                         String location, int duration, List<String> invitees) {

    List<Integer> maxEventTime = Time.convertTimesToObjectivePair(
            new Time(DaysOfTheWeek.SUNDAY, 900),
            new Time(DaysOfTheWeek.SUNDAY, 1700));

    if (duration > maxEventTime.get(0) - maxEventTime.get(1)) {
      throw new IllegalArgumentException("Can't create event with provided parameters... duration too long");
    }

    // start and end times in objective values here
    List<Integer> monWorkHours = Time.convertTimesToObjectivePair(
            new Time(DaysOfTheWeek.MONDAY, 900),
            new Time(DaysOfTheWeek.MONDAY, 1700));

    List<Integer> tuesWorkHours = Time.convertTimesToObjectivePair(
            new Time(DaysOfTheWeek.TUESDAY, 900),
            new Time(DaysOfTheWeek.TUESDAY, 1700));

    List<Integer> wedWorkHours = Time.convertTimesToObjectivePair(
            new Time(DaysOfTheWeek.WEDNESDAY, 900),
            new Time(DaysOfTheWeek.WEDNESDAY, 1700));

    List<Integer> thursWorkHours = Time.convertTimesToObjectivePair(
            new Time(DaysOfTheWeek.THURSDAY, 900),
            new Time(DaysOfTheWeek.THURSDAY, 1700));

    List<Integer> friWorkHours = Time.convertTimesToObjectivePair(
            new Time(DaysOfTheWeek.FRIDAY, 900),
            new Time(DaysOfTheWeek.FRIDAY, 1700));

    try {
      return findTimeThisDay(monWorkHours, model, name, isOnline, location, duration, invitees);
    } catch (IllegalArgumentException caught) {
      //do nothing
    }

    try {
      return findTimeThisDay(tuesWorkHours, model, name, isOnline, location, duration, invitees);
    } catch (IllegalArgumentException caught) {
      //do nothing
    }

    try {
      return findTimeThisDay(wedWorkHours, model, name, isOnline, location, duration, invitees);
    } catch (IllegalArgumentException caught) {
      //do nothing
    }

    try {
      return findTimeThisDay(thursWorkHours, model, name, isOnline, location, duration, invitees);
    } catch (IllegalArgumentException caught) {
      //do nothing
    }

    try {
      return findTimeThisDay(friWorkHours, model, name, isOnline, location, duration, invitees);
    } catch (IllegalArgumentException caught) {
      //do nothing
    }

    throw new IllegalArgumentException("Can't create event with provided parameters");

  }

  private Event findTimeThisDay(List<Integer> workhours, SchedulingSystem model, String name,
                               Boolean isOnline, String location,
                               int duration, List<String> invitees) {
    for (int objStartTime = workhours.get(0); objStartTime < workhours.get(1); objStartTime++) {

      int objEndTime = objStartTime + duration;

      List<Time> times = Time.convertObjectivePairToTimes(objStartTime, objEndTime);
      Time startPoint = times.get(0);
      Time endPoint = times.get(1);

      // convert objStartTime and objEndTime into Start day, Start time, End day, End time

      // then try and see if an Event with that range fits into all the invitees schedules
      if (!model.eventConflict(invitees.get(0), invitees, name, location, isOnline,
              startPoint.day(), startPoint.time(), endPoint.day(), endPoint.time())) {

        return new NUEvent(invitees, name, location, isOnline,
                startPoint.day(), startPoint.time(), endPoint.day(), endPoint.time());

      }

    }

    throw new IllegalArgumentException("Can't create event on this day");

  }

}
