package cs3500.nuplanner.model.hw05;

import java.util.List;

/**
 * Readable-only Scheduling System. All observations on a Scheduling System have been
 * isolated in this interface.
 */
public interface ReadableSchedulingSystem {

  /**
   * Observes all users existing within the scheduling system.
   *
   * @return all users in scheduling system
   */
  List<String> allUsers();

  /**
   * Observes all the Events contained within a user's Schedule.
   *
   * @param user                        name of user whose Schedule to return
   * @return                            Schedule belonging to that user
   * @throws IllegalArgumentException   if user does not exist in scheduling system
   */
  List<ReadableEvent> eventsInSchedule(String user);

  /**
   * Observes a unique Event contained within a user's schedule.
   *
   * @param user                        name of user whose Event to return
   * @return                            Event belonging to that user
   * @throws IllegalArgumentException   if user does not exist in scheduling system
   */
  ReadableEvent eventAt(String user, DaysOfTheWeek startDay, int startTime);

  //ReadableEvent eventAt(String user, Event eventToExtract)

}
