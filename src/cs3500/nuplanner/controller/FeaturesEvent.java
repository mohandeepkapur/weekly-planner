package cs3500.nuplanner.controller;

// do not understand as well i would like, ugh... just need to read lecture notes seriously...
public interface FeaturesEvent {

  // existence of JFrame stuff (ActionEvents, KeyEvents)  no longer revealed to Controller
  // View is better encapsulated --> JFrame stuff leaking into controller --> bad encapsulation
  //    Controller should not be aware of View implementation details

  // Controller is no longer listener for JFrame component events --> instead, listener is a lambda
  // that executes a high-level callback -->



  // creates an Event given information currently contained on View
  void createEvent();

  // modifies an Event given information currently contained on View
  void modifyEvent();

  // removes an Event
  void removeEvent();

  void exitFrame();

}