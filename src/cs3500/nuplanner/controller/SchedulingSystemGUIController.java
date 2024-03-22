package cs3500.nuplanner.controller;

import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.view.EventView;
import cs3500.nuplanner.view.SchedulingSystemView;

public class SchedulingSystemGUIController implements SchedulingSystemController, FeaturesEvent, FeaturesSchedulingSystem {

  private SchedulingSystem model;
  private EventView eventView;
  private SchedulingSystemView sysView;

  public SchedulingSystemGUIController() {

  }

  @Override
  public void modifyEvent() {

  }

  @Override
  public void removeEvent() {

  }

  @Override
  public void exitFrame() {

  }


  @Override
  public void useSchedulingSystem() {

  }

  @Override
  public void useSchedulingSystem(String pathname) {

  }
}

/*
SchedSysView:
void refresh()? <- whenever model-state changed
void makeVisible()
 */

