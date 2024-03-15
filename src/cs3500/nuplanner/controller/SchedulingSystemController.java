package cs3500.nuplanner.controller;

/**
 * Controller for Scheduling System.
 */
public interface SchedulingSystemController {

  /**
   * Runs scheduling system using user input.
   */
  void useSchedulingSystem();

  /**
   * Runs scheduling system using XML file.
   *
   * @param pathname                     path to XML file
   * @throws IllegalStateException       if unable to open or parse XML file
   */
  void useSchedulingSystem(String pathname);

}
