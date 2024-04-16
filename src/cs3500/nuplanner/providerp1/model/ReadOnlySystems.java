package cs3500.nuplanner.providerp1.model;

import java.util.Map;

/**
 * Defines the contract for a central system within the planning application that doesn't allow
 * modification for the use of the views. This interface supports observation methods that returns
 * all the IUsers registered in the system, and other observation methods.
 */
public interface ReadOnlySystems {

  /**
   * Gets the map of all the IUsers to be used in for loops.
   * @return the map of all the IUsers
   */
  Map<String, IUser> getAllIUsers();

  /**
   * Retrieves a IUser by their UID.
   * @param uid the unique identifier of the IUser
   * @return the IUser object corresponding to the UID, or null if no such IUser exists
   */
  IUser getIUser(String uid);

  /**
   * Gets the passed in IUser's schedule and displays to the client.
   * @param uid the string that represents the IUser
   * @return the given IUser's schedule
   */
  Schedule getIUserSchedule(String uid);

  /**
   * Checks if an event conflicts with the schedules of all invited IUsers.
   *
   * @param event The event to check for conflicts.
   * @return true if the event conflicts with any invited IUser's schedule; false otherwise.
   */
  boolean isEventConflicting(Event event);
}
