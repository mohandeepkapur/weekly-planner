package cs3500.nuplanner.controller;

public interface FeaturesEvent {

  // existence of JFrame events (through ActionEvents and stuff) no longer revealed to Controller
  // instead of controller being listener for view --> now features is listener, and then requests
  // high-level methods from controller

  // application specific events <-- not swing-specific events, like ActionEvent, BlahBlah
  // higher level callbacks for Controller --> low-level stuff kept in View < jank

  // creates an Event given information currently contained on View
  void createEvent();

  // modifies an Event given information currently contained on View
  void modifyEvent();

  // removes an Event
  void removeEvent();

  void exitFrame();

}