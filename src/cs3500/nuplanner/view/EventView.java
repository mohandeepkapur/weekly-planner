package cs3500.nuplanner.view;

import java.awt.event.KeyListener;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;

public interface EventView {

  // observations and operations of View

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

  // first user provided by model is unmodifiable - modify input bruv
  List<String> eventInviteesInput();

  void displayEventInvitees();

  void clearInputStrings();

  void addFeatures();

  void exitFrame();


  //  will override from JFrame class
  //  void makeVisible();
  //  void refresh();

  // schedule frame would need to pass knowledge of current user selected to event frame
  // so event frame can display first invitee as user

}
