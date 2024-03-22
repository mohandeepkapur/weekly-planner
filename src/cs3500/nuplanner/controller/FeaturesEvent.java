package cs3500.nuplanner.controller;

public interface FeaturesEvent {

  // existence of JSwing stuff (ActionEvents, KeyEvents)  no longer revealed to Controller
  // View is better encapsulated --> JFrame stuff leaking into controller --> bad encapsulation
  //    Controller should not be aware of View implementation details

  // Controller is no longer listener for JFrame component events --> instead, listener is a lambda
  // that executes a high-level "feature"

  // instead of con. implementing generic listener method, and then deciding within that method what actions to perform
  // now, controller given a specific action to perform

  // command design pattern --> explicit flow to data comment --> popping up everywhere, reason


  /**
   * User request to create an event given information they provide.
   */
  void createEvent();

  /**
   * User request to remove an event they've selected from scheduling system.
   */
  void removeEvent();

  /**
   * User request to modify an existing event based on how its manipulated event in GUI.
   */
  void modifyEvent();

  /**
   * User request to exit event frame. (Impl: just make Event frame invisible and clear its content)
   */
  void exitProgram();

}

// view --> operations and observations would want on GUI
// for any response to user interacting w/ component --> that should be delegated to features