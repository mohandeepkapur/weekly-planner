package cs3500.nuplanner.model.hw05;

import java.util.ArrayList;
import java.util.List;

/**
 * Wraps user-input data used to create an Event. Event may not be able
 * be initializeable from held data.
 * Point of this class is to get data. Not observe.
 */
public class RawEventData {

  private List<String> invitees;
  private final String eventName;
  private final String location;
  private final String isOnline;
  private final String startDay;
  private final String startTime;
  private final String endDay;
  private final String endTime;

  /**
   * Creates the raw event data with the strings passed in.
   *
   * @param invitees  the invitees to an event
   * @param eventName the event name
   * @param location  the location of the event
   * @param isOnline  if the event is online or not
   * @param startDay  the start day of the event
   * @param startTime the start time of the event
   * @param endDay    the end day of the event
   * @param endTime   the end time of the event
   */
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
