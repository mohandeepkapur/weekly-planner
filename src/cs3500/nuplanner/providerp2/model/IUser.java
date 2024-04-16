package cs3500.nuplanner.providerp2.model;

import org.w3c.dom.NodeList;

/**
 * This is the user
 */
public interface IUser {

  /**
   * This merges the schedule of a user with the given list of events.
   * (This method is being called inside the XMLParser class readScheduleFromFile method
   * whenever client uploads a file of existing user's schedule.) Thus, it is public.
   */
  ISchedule mergeSchedule(NodeList eventList);

  /**
   * This sets the given schedule as a user's schedule.
   * (This method is called inside XMLParser class inside readScheduleFromFil method.)
   * Thus, it is public.
   */
  void setSchedule(ISchedule s);

  /**
   * This returns the user ID for the purpose of other methods.
   */
  String getUid();

  /**
   * This returns the schedule of the user.
   */
  ISchedule getSchedule();


  /**
   * This removes a given event from the user's schedule.
   */
  void removeEventFromSchedule(Event event);
}
