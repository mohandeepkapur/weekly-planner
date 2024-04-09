package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.SchedulingSystem;

/**
 * Controller for Scheduling System.
 */
public interface SchedulingSystemController {

  /**
   * Runs scheduling system using user input.
   */
  void useSchedulingSystem(SchedulingSystem model);

  /**
   * Runs scheduling system using XML file.
   *
   * @param pathname                     path to XML file
   * @throws IllegalStateException       if unable to open or parse XML file
   */
  void useSchedulingSystem(String pathname);

}
