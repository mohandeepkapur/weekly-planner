package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;

public interface FeaturesSchedulingSystem {

  /**
   * Requests Event if it exists: controller essentially given grid position of view
   */
  void requestEventIfExists(int day, int time);

  /**
   * Controller will need to display a new schedule onto the view
   */
  void requestNewSchedule(String user);

  void requestCreateEvent(); // sequence: features to controller to eventView

  void requestScheduleUpload(String pathname);

  void requestScheduleDownload(String pathname);

  void requestScheduleEvent(); // do nothing
}

