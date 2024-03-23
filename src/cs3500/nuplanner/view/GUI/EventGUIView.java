package cs3500.nuplanner.view.GUI;

import cs3500.nuplanner.controller.Features;

// observations and operations of View
public interface EventGUIView {

  // model's job to verify data
  // but controller job can prevent clearly bad data from being passed to model

  String nameInput();

  void displayName(String name);

  String locationInput();

  void displayLocation(String location);

  String isOnlineInput();

  void displayIsOnline(String isOnline);

  String startDayInput();  //get selected item from JComboBox

  void displayStartDay(String startDay);

  String startTimeInput();

  void displayStartTime(String startTime);

  String endDayInput();

  void displayEndDay(String endDay);

  String endTimeInput();

  void displayEndTime(String endTime);

  void clearTextFields();

  void addFeatures(Features features);

  void makeVisible();

  //  void refresh();

  // schedule frame would need to pass knowledge of current user selected to event frame
  // so event frame can display first invitee as user

}
