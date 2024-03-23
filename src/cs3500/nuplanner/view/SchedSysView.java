package cs3500.nuplanner.view;

import cs3500.nuplanner.model.hw05.Schedule;

public interface SchedSysView {

  // operations and observations of View
  String observeCurrentUser();

  void paintUserSchedule(Schedule userSchedule);

  void addFeatures();

  //  will override from JFrame class
  //  void makeVisible();
  //  void refresh();

}
