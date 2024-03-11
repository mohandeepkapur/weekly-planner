package cs3500.nuplanner.model.hw05;

// assumption:
// no scheduling conflicts in schedule
public interface Schedule {

  // assumption: assume added event does not conflict with any other events
  /**
   * Adds an Event into Schedule.
   *
   * @param  newEvent                       Event to be added
   * @throws IllegalArgumentException       if any Events in Schedule overlap
   *
   */
  // not constructing event within a schedule obj so same Event can be put into multiple Schedules
  void addEvent(Event newEvent);

  //void modifyEvent();

  /**
   * Removes an Event from Schedule. Event to be removed will update its invitee list.
   *
   * @param newEvent                       Event to be removed
   */
  void removeEvent(Event newEvent);

  /**
   * Observes number of Events contained within Schedule.
   */
  int numberOfEvents();

  /**
   * Provides a single Event within Schedule.
   *
   * @param eventIndex                    represents Event to be returned
   * @return                              Event
   *
   * @throws IllegalArgumentException     if eventIndex outofbounds
   */
  Event provideSingleEvent(int eventIndex);

  /**
   * Checks whether an Event would conflict with current Schedule. What counts as a conflict is
   * implementation detail.
   *
   * @param event                       Event that will be checked against Schedule's Events
   * @return                            boolean that signals whether Event can be added
   */
  boolean eventConflict(Event event);

}

