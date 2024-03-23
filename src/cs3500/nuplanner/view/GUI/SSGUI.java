package cs3500.nuplanner.view.GUI;

import cs3500.nuplanner.controller.Features;

public interface SSGUI {

  void addFeatures(Features features);

  /**
   * New Schedule will be shown
   * @param user
   */
  void displayNewSchedule(String user); // should be view operational method



  void makeVisible();

  //  will override from JFrame class
  //  void makeVisible();
  //  void refresh();
}
