package cs3500.nuplanner.providerp2.controller;

import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.providerp2.model.Event;
import cs3500.nuplanner.providerp2.strategy.Strategy;

/**
 * An adaptor to adapt the features interface created personally with the features interface that
 * the provider has to work with their view.
 */

public class FeaturesAdaptor implements Features {
  cs3500.nuplanner.controller.Features delegate;

  /**
   * Creates an adaptor to translate the view
   *
   * @param delegate our features interface
   */
  public FeaturesAdaptor(cs3500.nuplanner.controller.Features delegate) {
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
  public void onCreateEvent(String uid, Event event) {
    RawEventData rawData = new RawEventData(event.getInvitedUsers(), event.getName(),
            event.getPlace(), Boolean.toString(event.isOnline()), event.getStartDay().toString(),
            event.getStartTime(), event.getEndDay().toString(), event.getEndTime());
    delegate.requestCreateEvent(uid, rawData);
  }

  @Override
  public void onModifyEvent(String uid, Event originalEvent, Event newEvent) {
    RawEventData rawOriginalEventData = new RawEventData(originalEvent.getInvitedUsers(), originalEvent.getName(),
            originalEvent.getPlace(), Boolean.toString(originalEvent.isOnline()), originalEvent.getStartDay().toString(),
            originalEvent.getStartTime(), originalEvent.getEndDay().toString(), originalEvent.getEndTime());
    RawEventData rawNewEventData = new RawEventData(newEvent.getInvitedUsers(), newEvent.getName(),
            newEvent.getPlace(), Boolean.toString(newEvent.isOnline()), newEvent.getStartDay().toString(),
            newEvent.getStartTime(), newEvent.getEndDay().toString(), newEvent.getEndTime());
    delegate.requestModifyEvent(uid, rawOriginalEventData, rawNewEventData);
  }

  @Override
  public void onRemoveEvent(Event event) {
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
  public void handleClick(Event e) {
//TODO: idk we don't really have this huh?
  }
}
