package cs3500.nuplanner.strategies;

import java.util.List;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

/**
 * Represents the scheduling strategies available to a user to determine when individuals in the
 * system can meet and is chosen through a command line argument.
 */
public interface SchedulingStrategies {

  /**
   * Strategy will then decide when to schedule the event based on certain criteria.
   *
   * @param model    the model passed in
   * @param duration the duration of the event
   */
  Event findTimeForScheduledEvent(SchedulingSystem model, String name,
                                  Boolean isOnline, String location,
                                  int duration, List<String> invitees);
}

