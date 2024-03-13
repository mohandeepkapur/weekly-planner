package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// assumptions made when dealing with schedule: internal events never conflict, events always sorted, events
// every event in schedule does not conflict -> add events one at a time, and check for conflicts when adding each one
public class NUSchedule implements Schedule {

  // event obj can be contained within multiple schedules
  // access a specific event in user schedule using event ID
  // delegating bunch of checks to schedule and event <-- keeping model as light as possible

  // sorted all times
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

  // runs event conflict check automatically
  @Override
  public void addEvent(Event newEvent) {

    if(newEvent == null) throw new IllegalArgumentException();

    // redundant, event conflict would've been run in model before this method called
    if (eventConflict(newEvent)) throw new IllegalArgumentException("Event conflict... ");

    this.events.add(newEvent);

    sortListOfEvents();
  }

  @Override
  public void removeEvent(Event eventToRemove) {

    //simply removes an event <-- higher-level checks done above
    if(eventToRemove == null) throw new IllegalArgumentException();

    eventToRemove.removeInvitee(this.user);

    for(int i = 0; i < this.numberOfEvents(); i++) {
      if(this.events.get(i) == eventToRemove) {
        this.events.remove(i);
        break;
      }
    }
  }

  @Override
  public int numberOfEvents() {
    return this.events.size();
  }

  @Override
  public Event eventAt(int eventID) {

    for (Event event : events) {
      if (event.ID() == eventID) {
        return event;
      }
    }

    throw new IllegalArgumentException("Event of this ID does not exist... ");
  }

  @Override
  public List<Integer> eventIDs() {
    List<Integer> listOfIDs = new ArrayList<>();
    for (Event event : events) {
      listOfIDs.add(event.ID());
    }
    return listOfIDs;
  }

  //  @Override
  //  public Event provideSingleEvent(int eventIndex) {
  //    return this.events.get(eventIndex);
  //  }

  /**
   * @implNote                              two Events not considered to overlap/conflict if the
   *                                        end day+time for one event == start day+time for other
   */
  @Override
  public boolean eventConflict(Event outEvent) {


    // 1: assign objective values to every event start time and end time
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
    if (eDv - sDv < 0 || (eDv - sDv == 0 && eT <= sT) ) {
      endVal = ((eDv + 7) * 60 * 24) + (eT/100 * 60) + (eT%100);
    } else {
      // event contained within first week
      endVal = (eDv * 60 * 24) + (eT/100 * 60) + (eT%100);
    }
    // start day always within first week
    startVal = (sDv * 60 * 24) + (sT/100 * 60) + (sT%100);

    return new ArrayList<Integer>(List.of(startVal, endVal));

  }

  // CRUCIAL: must be performed everytime an Event added
  private void sortListOfEvents() {

    // after event accepted, all need to do is order start times
    events.sort(Comparator.comparingInt((Event event) -> event.startDay().val())
            .thenComparingInt(Event::startTime));
    
  }

}
