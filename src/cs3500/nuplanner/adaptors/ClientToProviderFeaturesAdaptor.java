package cs3500.nuplanner.adaptors;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.provider.controller.Features;
import cs3500.nuplanner.provider.model.IEvent;
import cs3500.nuplanner.provider.strategy.Strategy;

/**
 * Adaptor class that converts client-Features into provider-Features. Provider's view under
 * the assumption that it's interacting with provider-Features, when in fact its client-Features
 * under the hood!
 */
public class ClientToProviderFeaturesAdaptor implements Features {

  private cs3500.nuplanner.controller.Features clientFeatures;

  /**
   * Constructs a client-Features to providerFeatures adaptor.
   *
   * @param clientFeatures client features interface
   */
  public ClientToProviderFeaturesAdaptor(cs3500.nuplanner.controller.Features clientFeatures) {
    if (clientFeatures == null) {
      throw new IllegalArgumentException("Adaptor cannot be constructed will null args... ");
    }

    this.clientFeatures = clientFeatures;
  }

  @Override
  public void onUploadXMLFile(String filePath) {
    clientFeatures.requestXMLScheduleUpload(filePath);
  }

  @Override
  public void onSaveSchedules(String directoryPath) {
    clientFeatures.requestAllSchedulesDownload(directoryPath);
  }

  @Override
  public void onCreateEvent(String uid, IEvent event) {
    // convert given IEvent to Event to RawEventData
    Event delOrigEvent = new ProviderToClientEventAdaptor(event);
    RawEventData delCreateEventRawData = extractEventData(delOrigEvent);

    // call relevant client-Features method to manipulate client-model state
    clientFeatures.requestCreateEvent(uid, delCreateEventRawData);

  }

  @Override
  public void onModifyEvent(String uid, IEvent originalEvent, IEvent newEvent) {
    // convert given IEvent to Event to RawEventData
    Event delOrigEvent = new ProviderToClientEventAdaptor(originalEvent);
    Event delNewEvent = new ProviderToClientEventAdaptor(newEvent);
    RawEventData rawOriginalEventData = extractEventData(delOrigEvent);
    RawEventData rawNewEventData = extractEventData(delNewEvent);

    clientFeatures.requestModifyEvent(uid, rawOriginalEventData, rawNewEventData);

  }

  @Override
  public void onRemoveEvent(IEvent event) {
    // user requesting event-removal not provided!
    // loss of important information, bc event-removal has different behavior depending on whether
    // host or non-host requests it

    // convert given IEvent to Event to RawEventData
    Event delRemoveEvent = new ProviderToClientEventAdaptor(event);
    RawEventData delRemoveEventRawData = extractEventData(delRemoveEvent);

    String host = event.getHostUser().toString();
    clientFeatures.requestRemoveEvent(host, delRemoveEventRawData);

  }

  /**
   * Extracts data from an Event and turns it into RawEventData.
   *
   * @param event the event passed in
   * @return the event in RawEventData form
   */
  private RawEventData extractEventData(Event event) {
    return new RawEventData(event.eventInvitees(), event.name(),
            event.location(), String.valueOf(event.isOnline()),
            event.startDay().toString(), String.valueOf(event.startTime()),
            event.endDay().toString(), String.valueOf(event.endTime()));

  }

  @Override
  public Strategy onCreateEventFrame() {
    return null;
  }

  @Override
  public void onSwitchUser(String userId) {
    clientFeatures.displayNewSchedule(userId);
  }

  @Override
  public void handleClick(IEvent e) {
    // convert IEvent to Event
    Event delEvent = new ProviderToClientEventAdaptor(e);

    // given IView operations, there's no way to display given Event-details
    clientFeatures.displayExistingEvent(delEvent.host(), delEvent);

  }

}
