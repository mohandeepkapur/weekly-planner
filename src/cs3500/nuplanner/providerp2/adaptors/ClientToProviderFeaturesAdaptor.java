package cs3500.nuplanner.providerp2.adaptors;

import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.providerp2.controller.Features;
import cs3500.nuplanner.providerp2.model.IEvent;
import cs3500.nuplanner.providerp2.strategy.Strategy;

/**
 * An adaptor to adapt the features interface created personally with the features interface that
 * the provider has to work with their view.
 */

public class ClientToProviderFeaturesAdaptor implements Features {

  private cs3500.nuplanner.controller.Features delegate;


  /**
   * Creates an adaptor to translate the view
   *
   * @param delegate our features interface
   */
  public ClientToProviderFeaturesAdaptor(cs3500.nuplanner.controller.Features delegate) {
    this.delegate = delegate;
  }

  @Override
  public void onUploadXMLFile(String filePath) {
    delegate.requestXMLScheduleUpload(filePath);
  }

  @Override
  public void onSaveSchedules(String directoryPath) {
    delegate.requestAllSchedulesDownload(directoryPath);
  }

  @Override
  public void onCreateEvent(String uid, IEvent event) {

    // need to convert their event into our event first though
    // convert from IEvent to Event


    RawEventData rawData = new RawEventData(event.getInvitedUsers(), event.getName(),
            event.getPlace(), Boolean.toString(event.isOnline()), event.getStartDay().toString(),
            event.getStartTime(), event.getEndDay().toString(), event.getEndTime());
    delegate.requestCreateEvent(uid, rawData);
  }

  @Override
  public void onModifyEvent(String uid, IEvent originalEvent, IEvent newEvent) {
    RawEventData rawOriginalEventData = new RawEventData(originalEvent.getInvitedUsers(), originalEvent.getName(),
            originalEvent.getPlace(), Boolean.toString(originalEvent.isOnline()), originalEvent.getStartDay().toString(),
            originalEvent.getStartTime(), originalEvent.getEndDay().toString(), originalEvent.getEndTime());
    RawEventData rawNewEventData = new RawEventData(newEvent.getInvitedUsers(), newEvent.getName(),
            newEvent.getPlace(), Boolean.toString(newEvent.isOnline()), newEvent.getStartDay().toString(),
            newEvent.getStartTime(), newEvent.getEndDay().toString(), newEvent.getEndTime());
    delegate.requestModifyEvent(uid, rawOriginalEventData, rawNewEventData);
  }

  @Override
  public void onRemoveEvent(IEvent event) {
    String host = event.getHostUser().toString();
    RawEventData rawData = new RawEventData(event.getInvitedUsers(), event.getName(),
            event.getPlace(), Boolean.toString(event.isOnline()), event.getStartDay().toString(),
            event.getStartTime(), event.getEndDay().toString(), event.getEndTime());
    delegate.requestRemoveEvent(host, rawData);
  }

  @Override
  public Strategy onCreateEventFrame() {
    return null; //TODO: idk we don't really have this huh?
  }

  @Override
  public void onSwitchUser(String userId) {
//TODO: idk we don't really have this huh?
  }

  @Override
  public void handleClick(IEvent e) {
//TODO: idk we don't really have this huh?
  }
}
