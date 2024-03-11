package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NUSchedule implements Schedule {

  // event obj can be contained within multiple schedules
  // access a specific event in user schedule using "event index" --> sorting events
  // map data rep for scheduling system
  // delegating bunch of checks to schedule and event <-- keeping model as light as possible

  // XML parsing
  // modifying events
  // view relying on schedule/event interfaces

  private final List<Event> events;

  private final String user;

  public NUSchedule(String user) {
    this.events = new ArrayList<>();
    this.user = user;
  }

  // runs event conflict check automatically, j
  @Override
  public void addEvent(Event newEvent) {
    Objects.requireNonNull(newEvent);

    // THROW ILLEGALARG if new event would overlap with existing events in schedule
    this.events.add(newEvent);

    checkForTimingConflicts();

    sortListOfEvents();

  }

  @Override
  public void modifyEvent() {

  }

  @Override
  public void removeEvent(Event eventToRemove) {
    //simply removes an event <-- higher-level checks done above

    Objects.requireNonNull(eventToRemove);

    eventToRemove.removeInvitee(this.user);

    for(int i = 0; i < this.numberOfEvents(); i++) {
      if(this.events.get(i) == eventToRemove) {
        this.events.remove(i);
        break;
      }
    }

    // if invitee of event --> fuck
    // if host --> fuck
  }

  @Override
  public int numberOfEvents() {
    return this.events.size();
  }

  @Override
  public Event provideSingleEvent(int eventIndex) {
    return this.events.get(eventIndex);
  }

  /**
   * @implNote                              two Events not considered to overlap/conflict if the
   *                                        end day+time for one event == start day+time for other
   */
  @Override
  public boolean eventConflict(Event event) {

   



    return false;
  }

  // CRUCIAL: must be performed everytime an Event added
  private void sortListOfEvents() {


    
  }

}
