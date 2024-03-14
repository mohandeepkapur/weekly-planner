package cs3500.nuplanner.model.hw05;

/**
 * Operations and observations necessary for an Event in a Scheduling System.
 */
public interface Event extends ReadableEvent {

  /**
   * Updates name of Event.
   */
  void updateName(String name);


  /**
   * Updates location of Event.
   */
  void updateLocation(String location);


  /**
   * Updates Event online or offline status.
   */
  void updateIsOnline(boolean isOnline);


  /**
   * Updates day the Event starts.
   *
   * @param day new starting day of Event
   * @throws IllegalArgumentException if the time the Event spans is too large
   */
  void updateStartDay(DaysOfTheWeek day);


  /**
   * Updates day the Event ends.
   *
   * @param day new end day of Event
   * @throws IllegalArgumentException if time-span of modified Event exceeds 6-days 23-hrs 59-min
   */
  void updateEndDay(DaysOfTheWeek day);


  /**
   * Updates time the Event starts.
   *
   * @param startTime new start time of Event
   * @throws IllegalArgumentException if time-span of modified Event exceeds 6-days 23-hrs 59-min
   */
  void updateStartTime(int startTime);


  /**
   * Updates time the Event ends.
   *
   * @param endTime new end time of Event
   * @throws IllegalArgumentException if time-span of modified Event exceeds 6-days 23-hrs 59-min
   */
  void updateEndTime(int endTime);

  /**
   * Removes an invitee from the Event.
   *
   * @implNote will only be used on an invitee <--
   */
  void removeInvitee(String invitee); //remove user from invited user list

  /**
   * Add an invitee from the Event.
   *
   * @param invitee
   */
  void addInvitee(String invitee);

}
