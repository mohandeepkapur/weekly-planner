package cs3500.nuplanner.providerp3.adaptors;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.providerp3.view.IView;
import cs3500.nuplanner.view.gui.SSGUIView;

// provView manipulates its own state
// all GUIController can do when trying to manipulate provView, is hit refresh
// will need to
public class ProviderToClientViewAdaptor implements SSGUIView {

  // providers don't have a MAIN view interface
  // assuming dealing w/ MAIN impl
  private IView delegate;

  public ProviderToClientViewAdaptor(IView view) {
    this.delegate = view;
  }

  @Override
  public void displayUserSchedule(String user) {
    this.delegate.refresh();
  }

  @Override
  public void displayBlankEvent() {
    this.delegate.refresh();
  }

  @Override
  public void displayBlankScheduleEvent() {
    this.delegate.refresh();
  }

  @Override
  public void displayExistingEvent(String user, ReadableEvent event) {
    this.delegate.refresh();
  }

  @Override
  public void addFeatures(Features features) {
    this.delegate.setListener();
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
