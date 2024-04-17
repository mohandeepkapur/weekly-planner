package cs3500.nuplanner.providerp3.adaptors;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.providerp3.view.IView;
import cs3500.nuplanner.view.gui.SSGUIView;

// provView manipulates its own state
// all GUIController can do when trying to manipulate provView, is hit refresh
// will need to

/**
 * Adaptor class that converts an IView into an SSGUIView.
 */
public class ProviderToClientViewAdaptor implements SSGUIView {

  // providers don't have a MAIN view interface
  // assuming dealing w/ MAIN impl
  private IView delegate;

  /**
   * Creates the adaptor that bridges the gap between the two views.
   *
   * @param view the view from the provider
   */

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
    cs3500.nuplanner.providerp3.controller.Features features2 =
            new ClientToProviderFeaturesAdaptor(features);
    this.delegate.setListener(features2);
  }

  @Override
  public void makeVisible() {
    this.delegate.makeVisible();
  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    this.displayErrorMessage(errorMessage);
  }

  @Override
  public void refresh() {
    this.delegate.refresh();
  }

}
