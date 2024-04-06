package cs3500.nuplanner.view.gui;

import java.util.List;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableEvent;

/**
 * Represents the actions and observations desired from the EventGUI.
 */
public interface EventGUIView {

  /**
   * Returns the name of the event.
   *
   * @return the name of the event
   */
  String nameInput();

  /**
   * Sets the name of the event.
   *
   * @param name the name of the event
   */
  void displayName(String name);

  /**
   * Returns the location of an event.
   *
   * @return the location of an event
   */
  String locationInput();

  /**
   * Sets the location of an event.
   *
   * @param location the location of an event
   */
  void displayLocation(String location);

  /**
   * Returns whether an event is online or not.
   *
   * @return true if online, false if not
   */
  String isOnlineInput();

  /**
   * Sets whether an event is online or not.
   *
   * @param isOnline true if online, false if not
   */
  void displayIsOnline(String isOnline);

  /**
   * Returns the start day of an event.
   *
   * @return the start day of an event
   */
  String startDayInput();  //get selected item from JComboBox

  /**
   * Sets the start date of an event.
   *
   * @param startDay the start day of event
   */
  void displayStartDay(String startDay);

  /**
   * Returns the start time of an event.
   *
   * @return the start time of an event
   */
  String startTimeInput();

  /**
   * Sets the start time of an event.
   *
   * @param startTime the start time of an event
   */
  void displayStartTime(String startTime);

  /**
   * Returns the end day of an event.
   *
   * @return the end day of an event
   */
  String endDayInput();

  /**
   * Sets the end day of an event.
   *
   * @param endDay the end day of an event
   */
  void displayEndDay(String endDay);

  /**
   * Returns the end time of an event.
   *
   * @return the end time of an event
   */
  String endTimeInput();

  /**
   * Sets the end time of an event.
   *
   * @param endTime the end time of an event
   */
  void displayEndTime(String endTime);

  /**
   * Represents the different features that are applicable to an EventFrame.
   *
   * @param features the feature to use
   */
  void addFeatures(Features features);

  /**
   * Makes the frame visible to the user.
   */
  void makeVisible();

  /**
   * Displays Invitees of Event.
   */
  void displayInvitees(List<String> invitees);

  /**
   * Displays an Event that exists within Scheduling System to user.
   *
   * @param user
   * @param event event to be displayed
   */
  void displayExistingEvent(String user, ReadableEvent event);

}
