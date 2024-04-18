package cs3500.nuplanner.provider.adaptors;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.provider.view.IView;
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
  private IView providerMainView;

  /**
   * Creates the adaptor that bridges the gap between the two views.
   *
   * @param view the view from the provider
   */
  public ProviderToClientViewAdaptor(IView view) {
    if (view == null) {
      throw new IllegalArgumentException("Adaptor cannot be constructed will null args... ");
    }

    this.providerMainView = view;
  }

  @Override
  public void displayUserSchedule(String user) {
    this.providerMainView.refresh();
  }

  @Override
  public void displayBlankEvent() {
    this.providerMainView.refresh();
  }

  @Override
  public void displayBlankScheduleEvent() {
    this.providerMainView.refresh();
  }

  @Override
  public void displayExistingEvent(String user, ReadableEvent event) {
    this.providerMainView.refresh();
  }

  @Override
  public void addFeatures(Features features) {
    cs3500.nuplanner.provider.controller.Features features2 =
            new ClientToProviderFeaturesAdaptor(features);
    this.providerMainView.setListener(features2);
  }

  @Override
  public void makeVisible() {
    this.providerMainView.makeVisible();
  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    this.providerMainView.showErrorMessage(errorMessage);
  }

  @Override
  public void refresh() {
    this.providerMainView.refresh();
  }

}
