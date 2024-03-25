package cs3500.nuplanner.view.GUI;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableEvent;

// how to design GUI interface
// how to think of Features properly

public interface SSGUIView {

  // ask features for permission to display a new schedule
  void displayNewSchedule(String user);

  // creates instance of Event GUI
  void displayBlankEvent();

  void displayFilledEvent(ReadableEvent event);

  // how user is able to change model/view state through components in GUI
  void addFeatures(Features features);

  // makes GUI visible
  void makeVisible();

}
