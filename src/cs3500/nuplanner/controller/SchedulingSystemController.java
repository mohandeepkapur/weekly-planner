package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.SchedulingSystem;

/**
 * An interface that describes the overarching methods for the controller that run the entire
 * program and handle user input for the Scheduling System.
 */
public interface SchedulingSystemController {

  /**
   * Launches a Scheduling System that can handle user input and is connected to the model.
   */
  void launchSchedulingSystem(SchedulingSystem model);

  /**
   * Launches a Scheduling System by loading in an XML file of already created user schedules.
   *
   * @param pathname                     path to XML file
   * @throws IllegalStateException       if unable to open or parse XML file
   */
  void launchSchedulingSystem(String pathname);

}
