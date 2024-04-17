package cs3500.nuplanner.provider.model;

import java.time.LocalTime;
import java.util.List;

public interface IEvent {
  /**
   * This returns the name of the Event.
   */
  String getName();

  /**
   * This returns the place of the Event.
   */
  String getPlace();

  /**
   * This returns whether the Event is online or not.
   */
  boolean isOnline();
  /**
   * This returns the start time of the Event.
   */
  LocalTime getStartTime();

  /**
   * This returns the end time of the Event.
   */
  LocalTime getEndTime();

  /**
   * This returns the start day of the Event.
   */
  Day getStartDay();

  /**
   * This returns the end day of the Event.
   */
  Day getEndDay();

  /**
   * This returns the host user of the Event.
   */
  IUser getHostUser();

  /**
   * This returns the invited users of the Event.
   */
  List<IUser> getInvitedUsers();

  /**
   * The list of the users invited to the event.
   * @return the string of the users invited to the event
   */
  String invites();
}
