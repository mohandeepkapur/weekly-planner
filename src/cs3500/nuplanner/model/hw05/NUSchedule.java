package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of a Schedule in a Scheduling System. Adheres to following INVARIANT: all Events
 * contained within a Schedule will never conflict.
 *
 *
 */
public class NUSchedule implements Schedule {

  // sorted from earliest to latest event
  private final List<Event> events;

  private final String user;

  /**
   * Constructs a schedule.
   * @param user scheduleOwner
   */
  public NUSchedule(String user) {
    this.events = new ArrayList<>();
    this.user = user;
  }

  /**
   * Observes the owner of the schedule.
   *
   * @return schedule owner
   */
  @Override
  public String scheduleOwner() {
    return user;
  }

  /**
   * Adds an Event into Schedule.
   *
   * @param newEvent                        Event to be added
   * @throws IllegalArgumentException       if Event is null
   * @throws IllegalArgumentException       if Event to be added conflicts with at least one
   *                                        Event within schedule
   * @throws IllegalArgumentException       if Event already exists in Schedule
   */
  @Override
  public void addEvent(Event newEvent) {
    // if event is null
    checkIfEventNull(newEvent);

    // if event-to-add already exists in schedule
    if (events.contains(newEvent)) {
      throw new IllegalArgumentException("Provided event already exists in schedule... ");
    } // could remove?

    // if event-to-add conflicts with existing events in schedule
    if (eventConflict(newEvent)) {
      throw new IllegalArgumentException("Provided event conflicts with " + user + "'s schedule");
    }

    // add event to schedule
    this.events.add(newEvent);

    // sort events
    sortEvents();

  }

  /**
   * Removes an Event from Schedule.
   *
   * If an Event is removed from a Schedule, the invitee list of the Event should be updated
   * to reflect that change.
   *
   * @param eventToRemove                   Event to be removed
   * @throws IllegalArgumentException       if Event is null
   * @throws IllegalArgumentException       if the given Event does not exist in Schedule
   */
  @Override
  public void removeEvent(Event eventToRemove) {
    // if event is null
    checkIfEventNull(eventToRemove);

    if (events.isEmpty()) {
      throw new IllegalArgumentException("Schedule is empty... no more events to remove... ");
    }

    // remove event from schedule
    for (int i = 0; i < this.numberOfEvents(); i++) {
      if (this.events.get(i).equals(eventToRemove)) {
        // update event's invitee list to notify other invitees of attendance change
        this.events.get(i).removeInvitee(this.user);
        this.events.remove(i);
        return;
      }
    }

    throw new IllegalArgumentException("Event given does not exist in schedule... ");
  }

  /**
   * Observes number of Events contained within Schedule.
   *
   * @return number of Events within Schedule
   */
  @Override
  public int numberOfEvents() {
    return this.events.size();
  }

  /**
   * Observes an Event contained within the Schedule.
   *
   * @throws IllegalArgumentException     if no Event at given start day and time exists
   *
   * @return                              an Event within Schedule
   */
  @Override
  public Event eventAt(DaysOfTheWeek startDay, int startTime) {
    for (Event event : events) {
      if (event.startDay() == startDay && event.startTime() == startTime) {
        // returns defensive copy of the event
        return new NUEvent(event);
      }
    }
    throw new IllegalArgumentException("Event with specified start day and time "
            + "does not exist in " + user + "'s schedule");

  }

  /**
   * Observes all the Events contained within the Schedule.
   *
   * @return                             all Events within Schedule
   */
  @Override
  public List<Event> events() {
    // returns defensive copy of the events
    return new ArrayList<>(events);
  }

  /**
   * Checks whether given Event would conflict with Events currently within schedule.
   *
   * @param outerEvent                     Event that will be checked against Schedule's Events
   * @throws IllegalArgumentException      if given Event is null
   *
   * @return                               boolean that signals whether Event can be added
   *
   * @implNote                             two Events not considered to overlap/conflict if the
   *                                       end day+time for one event == start day+time for other
   */
  @Override
  public boolean eventConflict(Event outerEvent) {
    checkIfEventNull(outerEvent);

    // 1: assign objective values to every event start day, time and end day, time
    List<Integer> outEventObjValues = extractObjectiveValues(outerEvent);
    int oStartVal = outEventObjValues.get(0);
    int oEndVal = outEventObjValues.get(1);

    for (Event insideEvent : events) {

      List<Integer> insideEventObjValues = extractObjectiveValues(insideEvent);
      int iStartVal = insideEventObjValues.get(0);
      int iEndVal = insideEventObjValues.get(1);

      // 2: compare overlaps using those objective values (super easy)
      // only four ways two events can conflict
      if (oStartVal >= iStartVal && oEndVal <= iEndVal) {
        return true;
      }

      if (oStartVal <= iStartVal && oEndVal >= iEndVal) {
        return true;
      }

      if (oStartVal < iStartVal && oEndVal > iStartVal) {
        return true;
      }

      if (oStartVal < iEndVal && oEndVal > iEndVal) {
        return true;
      }

    }

    return false;

  }

  /**
   * Extracts an objective value for an Event's start day/time and end day/time
   * using 1st week Sunday 0:00 as reference.
   *
   * Objective value is difference in minutes from reference point to current date!
   *
   * @param event           Event
   * @return                minutes away from Sunday 0:00 for both start and end day/time of Event
   */
  private List<Integer> extractObjectiveValues(Event event) {
    int sDv = event.startDay().val();
    int sT = event.startTime();

    int eDv = event.endDay().val();
    int eT = event.endTime();

    int startVal;
    int endVal;

    // event that extends into next week
    if (eDv - sDv < 0 || (eDv - sDv == 0 && eT <= sT)) {
      endVal = ((eDv + 7) * 60 * 24) + (eT / 100 * 60) + (eT % 100);
    } else {
      // event contained within first week
      endVal = (eDv * 60 * 24) + (eT / 100 * 60) + (eT % 100);
    }
    // start day always within first week
    startVal = (sDv * 60 * 24) + (sT / 100 * 60) + (sT % 100);

    return new ArrayList<Integer>(List.of(startVal, endVal));

  }

  /**
   * Sorts the Events within Schedule from earliest to least time. Performed everytime an Event
   * is added.
   */
  private void sortEvents() {
    // all need to do is order start times, since every event within schedule does not conflict
    events.sort(Comparator.comparingInt((Event event) -> event.startDay().val())
            .thenComparingInt(Event::startTime));

  }

  /**
   * Checks whether given Event is null.
   * @param event                        Event
   * @throws IllegalArgumentException    if Event is null
   */
  private void checkIfEventNull(Event event) {
    if (event == null) {
      throw new IllegalArgumentException("Provided event cannot be null... ");
    }
  }

}
