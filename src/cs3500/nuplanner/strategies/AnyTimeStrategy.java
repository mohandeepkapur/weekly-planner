package cs3500.nuplanner.strategies;

import java.util.List;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.model.hw05.Time;

/**
 * A strategy that lets the program create a scheduled event using any time available.
 */
public class AnyTimeStrategy implements SchedulingStrategies {


  @Override
  public Event findTimeForScheduledEvent(SchedulingSystem model, String name, Boolean isOnline,
                                         String location, int duration, List<String> invitees) {

    // start and end times in objective values here
    for (int objStartTime = 0; objStartTime < 7 * 24 * 60; objStartTime++) {

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

    throw new IllegalArgumentException("Can't create event with provided parameters");

  }

}
