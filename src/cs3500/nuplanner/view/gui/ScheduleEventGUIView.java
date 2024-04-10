package cs3500.nuplanner.view.gui;

import java.util.List;

import cs3500.nuplanner.controller.Features;

/**
 * Represents the actions and observations desired from the ScheduleEventGUI.
 * The purpose of the ScheduleEventGUIView is to AUTOMATICALLY schedule an Event
 * through a strategy
 */

public interface ScheduleEventGUIView {

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

}

