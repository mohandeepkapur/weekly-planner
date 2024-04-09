package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps user input data necessary to create an Event. Event may or may not be able
 * be initializeable from held data.
 *
 * Point of this class is to get data. Not observe.
 */
public class RawEventData {

  private List<String> invitees;
  private final String eventName, location, isOnline, startDay, startTime, endDay, endTime;

  public RawEventData(List<String> invitees,
                      String eventName, String location, String isOnline,
                      String startDay, String startTime,
                      String endDay, String endTime) {
    this.invitees = new ArrayList<>(invitees);
    this.eventName = eventName;
    this.location = location;
    this.isOnline = isOnline;
    this.startDay = startDay;
    this.startTime = startTime;
    this.endDay = endDay;
    this.endTime = endTime;
  }

  public String nameInput() {
    return eventName;
  }

  public String locationInput() {
    return location;
  }

  public String isOnlineInput() {
    return isOnline;
  }

  public String startDayInput() {
    return startDay;
  }

  public String startTimeInput() {
    return startTime;
  }

  public String endDayInput() {
    return endDay;
  }

  public String endTimeInput() {
    return endTime;
  }

  public List<String> invitees() {
    return invitees;
  }

}
