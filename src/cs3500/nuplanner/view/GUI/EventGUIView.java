package cs3500.nuplanner.view.GUI;

import cs3500.nuplanner.controller.Features;

/**
 *
 */
public interface EventGUIView {

  /**
   *
   * @return
   */
  String nameInput();

  /**
   *
   * @param name
   */
  void displayName(String name);

  /**
   *
   * @return
   */
  String locationInput();

  /**
   *
   * @param location
   */
  void displayLocation(String location);

  /**
   *
   * @return
   */
  String isOnlineInput();

  /**
   *
   * @param isOnline
   */
  void displayIsOnline(String isOnline);

  /**
   *
   * @return
   */
  String startDayInput();  //get selected item from JComboBox

  /**
   *
   * @param startDay
   */
  void displayStartDay(String startDay);

  /**
   *
   * @return
   */
  String startTimeInput();

  /**
   *
   * @param startTime
   */
  void displayStartTime(String startTime);

  /**
   *
   * @return
   */
  String endDayInput();

  /**
   *
   * @param endDay
   */
  void displayEndDay(String endDay);

  /**
   *
   * @return
   */
  String endTimeInput();

  /**
   *
   * @param endTime
   */
  void displayEndTime(String endTime);

  /**
   *
   * @param features
   */
  void addFeatures(Features features);

  /**
   *
   */
  void makeVisible();

  // event frame needs to know which user invoked it (to create event)
  // so event-frame can auto-fill host of new event

  // model's job to verify data
  // but controller job can prevent clearly bad data from being passed to model

}
