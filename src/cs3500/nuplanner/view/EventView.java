package cs3500.nuplanner.view;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;

public interface EventView {

  // observations and operations of View
  // int + DaysOfTheWeek, or String?

  // model's job to verify data
  // but controller job can prevent clearly garbage data from being passed to model
  // controller will try and interpret Strings

  String nameInput();

  String locationInput();

  String isOnlineInput();

  String startDayInput();  //get selected item from JComboBox

  String startTimeInput();

  String endDayInput();

  String endTimeInput();

  void addFeatures();

  void exitFrame();

  //void toggleVisibility(boolean b);

}
