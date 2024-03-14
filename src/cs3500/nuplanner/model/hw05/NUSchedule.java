package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// assumptions made when dealing with schedule:
// 1) internal events never conflict,
// 2) internal events always sorted
public class NUSchedule implements Schedule {

  // an event obj can be contained within multiple schedules
  // can access a specific event in user schedule using event ID
  // delegating relevant checks to schedule and event <-- keeping model as light as possible

  // sorted from earliest to latest event
  private final List<Event> events;

  private final String user;

  public NUSchedule(String user) {
    this.events = new ArrayList<>();
    this.user = user;
  }

  @Override
  public String scheduleOwner() {
    return user;
  }

  @Override
  public void addEvent(Event newEvent) {
    // if event is null
    checkIfEventNull(newEvent);

    // if event-to-add already exists in schedule
    if (events.contains(newEvent)) {
      throw new IllegalArgumentException("Provided event already exists in schedule... ");
    }

    // if event-to-add conflicts with existing events in schedule
    if (eventConflict(newEvent)) {
      throw new IllegalArgumentException("Provided event conflicts with " + user + "'s schedule");
    }

    // add event to schedule
    this.events.add(newEvent);

    // sort events
    sortEvents();

  }

  @Override
  public void removeEvent(Event eventToRemove) {
    // if event is null
    checkIfEventNull(eventToRemove);

    // if event-to-remove does not exist in schedule
    if (!events.contains(eventToRemove)) {
      throw new IllegalArgumentException("Provided event does not exist in schedule... ");
    }

    // remove event from schedule
    for (int i = 0; i < this.numberOfEvents(); i++) {
      if (this.events.get(i) == eventToRemove) {
        this.events.remove(i);
        break;
      }
    }

    // update event's invitee list to notify other invitees of attendance change
    eventToRemove.removeInvitee(this.user);

    // no constraint of (if host is removed, then event removed from all invitees) here
    // ^ model specific
  }

  @Override
  public int numberOfEvents() {
    return this.events.size();
  }

  //  @Override
  //  public Event eventAt(int eventID) {
  //    for (Event event : events) {
  //      if (event.ID() == eventID) {
  //        return event;
  //      }
  //    }
  //    throw new IllegalArgumentException("Event of this ID does not exist... ");
  //  }

  @Override
  public Event eventAt(DaysOfTheWeek startDay, int startTime) {

    for (Event event : events) {
      if (event.startDay() == startDay && event.startTime() == startTime) {
        return event; // Can be a direct getter <-- only downcast to readable event in model?
      }
    }

    throw new IllegalArgumentException("Event with specified start day and time "
            + "does not exist in " + user + "'s schedule");

  }

  //  @Override
  //  public List<Integer> eventIDs() {
  //    List<Integer> listOfIDs = new ArrayList<>();
  //    for (Event event : events) {
  //      listOfIDs.add(event.ID());
  //    }
  //    return listOfIDs;
  //  }

  @Override
  public List<Event> events() {

    return events; // Can be a direct getter <-- only downcast to readable event in model?

  }

  /**
   * @implNote two Events not considered to overlap/conflict if the
   * end day+time for one event == start day+time for other
   */
  @Override
  public boolean eventConflict(Event outEvent) {
    // 1: assign objective values to every event start time and end time <- EXPLAIN WHAT THEY ARE
    List<Integer> outEventObjValues = extractObjectiveValues(outEvent);
    int oStartVal = outEventObjValues.get(0);
    int oEndVal = outEventObjValues.get(1);

    for (Event insideEvent : events) {

      List<Integer> insideEventObjValues = extractObjectiveValues(insideEvent);
      int iStartVal = insideEventObjValues.get(0);
      int iEndVal = insideEventObjValues.get(1);

      // 2: compare overlaps using those objective values (super easy)

      // only ways two events can conflict (using objective time good strategy)
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
   * starting from 1st week Sunday 0:00.
   *
   * @param event
   * @return
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

  // CRUCIAL: must be performed everytime an Event added
  private void sortEvents() {
    // all need to do is order start times, since every event within schedule does not conflict
    events.sort(Comparator.comparingInt((Event event) -> event.startDay().val())
            .thenComparingInt(Event::startTime));

  }

  private void checkIfEventNull(Event event) {
    if (event == null) {
      throw new IllegalArgumentException("Provided event cannot be null... ");
    }
  }

}
