package cs3500.nuplanner.model.hw05;

/**
 * Operations necessary for an Event in a Scheduling System. Observations
 * described within the ReadableEvent interface.
 */
public interface Event extends ReadableEvent {

  /**
   * Updates name of Event.
   *
   * @param name                          new name of Event
   * @throws IllegalArgumentException     if name provided is null
   */
  void updateName(String name);


  /**
   * Updates location of Event.
   *
   * @param location                      new location of Event
   * @throws IllegalArgumentException     if name provided is null
   */
  void updateLocation(String location);


  /**
   * Updates Event online or offline status.
   *
   * @param isOnline                      new online/offline status of Event
   */
  void updateIsOnline(boolean isOnline);


  /**
   * Updates day the Event starts.
   * The minimum and maximum time an Event can span is enforced by implementation.
   *
   * @param startDay                      new starting day of Event
   * @throws IllegalArgumentException     if time-span of updated Event is invalid
   * @throws IllegalArgumentException     if start day provided is null
   */
  void updateStartDay(DaysOfTheWeek startDay);


  /**
   * Updates day the Event ends.
   * The minimum and maximum time an Event can span is enforced by implementation.
   *
   * @param endDay                        new end day of Event
   * @throws IllegalArgumentException     if time-span of updated Event is invalid
   * @throws IllegalArgumentException     if end day provided is null
   */
  void updateEndDay(DaysOfTheWeek endDay);


  /**
   * Updates time the Event starts.
   * The minimum and maximum time an Event can span is enforced by implementation.
   * Desired format of time is enforced by implementation.
   *
   * @param startTime                     new start time of Event
   * @throws IllegalArgumentException     if time-span of updated Event is invalid
   * @throws IllegalArgumentException     if time provided isn't in implementation's desired format
   */
  void updateStartTime(int startTime);


  /**
   * Updates time the Event ends.
   * The minimum and maximum time an Event can span is enforced by implementation.
   * Desired format of time is enforced by implementation.
   *
   * @param endTime                       new end time of Event
   * @throws IllegalArgumentException     if time-span of updated Event is invalid
   * @throws IllegalArgumentException     if time provided isn't in implementation's desired format
   */
  void updateEndTime(int endTime);

  /**
   * Removes an invitee from the Event.
   * If an Event is removed from a Schedule, the invitee list of the Event should be updated
   * to reflect that change.
   *
   * @param invitee                       invitee to remove
   * @throws IllegalArgumentException     if no invitees left in Event
   * @throws IllegalArgumentException     if invitee to remove is not part of Event
   */
  void removeInvitee(String invitee); //remove user from invited user list

  /**
   * Adds an invitee to the Event.
   *
   * @param invitee                       invitee to add
   * @throws IllegalArgumentException     if invitee is already part of Event
   * @throws IllegalArgumentException     if first invitee is not host of Event
   */
  void addInvitee(String invitee);

}
