package cs3500.nuplanner.model.hw05;

import java.util.List;

// assumption:
// no scheduling conflicts in schedule
public interface Schedule {

  String scheduleOwner();

  /**
   * Adds an Event into Schedule.
   *
   * @param  newEvent                       Event to be added
   * @throws IllegalArgumentException       if any Events in Schedule overlap
   *
   */
  // not constructing event within a schedule obj so same Event can be put into multiple Schedules
  void addEvent(Event newEvent);

  /**
   * Removes an Event from Schedule. Event to be removed will update its invitee list.
   *
   * @param newEvent                       Event to be removed
   */
  void removeEvent(Event event);

  /**
   * Observes number of Events contained within Schedule.
   */
  int numberOfEvents();

  /**
   * @param eventID           ID of an Event
   * @return                  Event object
   */
  Event eventAt(DaysOfTheWeek startDay, int startTime);

  /**
   * Checks whether an Event would conflict with current Schedule. What counts as a conflict is
   * implementation detail.
   *
   * @param event                       Event that will be checked against Schedule's Events
   * @return                            boolean that signals whether Event can be added
   */
  boolean eventConflict(Event event);

  List<Event> events();

}

