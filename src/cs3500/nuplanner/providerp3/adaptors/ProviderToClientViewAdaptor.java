package cs3500.nuplanner.providerp3.adaptors;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.providerp3.view.IView;
import cs3500.nuplanner.view.gui.SSGUIView;

public class ProviderToClientViewAdaptor implements SSGUIView {

  // providers don't have a MAIN view interface
  // assuming dealing w/ MAIN impl
  private IView delegate;

  public ProviderToClientViewAdaptor(IView view) {
    this.delegate = view;
  }

  @Override
  public void displayUserSchedule(String user) {
    
  }

  @Override
  public void displayBlankEvent() {

  }

  @Override
  public void displayBlankScheduleEvent() {

  }

  @Override
  public void displayExistingEvent(String user, ReadableEvent event) {

  }

  @Override
  public void addFeatures(Features features) {

  }

  @Override
  public void makeVisible() {

  }

  @Override
  public void displayErrorMessage(String errorMessage) {

  }

  @Override
  public void refresh() {

  }
}
