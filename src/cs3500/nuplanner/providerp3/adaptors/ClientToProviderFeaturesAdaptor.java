package cs3500.nuplanner.providerp3.adaptors;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.providerp3.controller.Features;
import cs3500.nuplanner.providerp3.model.IEvent;
import cs3500.nuplanner.providerp3.model.IUser;
import cs3500.nuplanner.providerp3.strategy.AnyTime;
import cs3500.nuplanner.providerp3.strategy.Strategy;

/**
 * An adaptor to adapt the features interface created personally with the features interface that
 * the provider has to work with their view.
 */

// rawEventData assumes that provided format is
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

    RawEventData rawData = extractUsersAndTime(event);

    delegate.requestCreateEvent(uid, rawData);
  }

  @Override
  public void onModifyEvent(String uid, IEvent originalEvent, IEvent newEvent) {

    String host = originalEvent.getHostUser().getUid();
    RawEventData rawOriginalEventData = extractData(originalEvent);
    RawEventData rawNewEventData = extractData(newEvent);

    delegate.requestModifyEvent(host, rawOriginalEventData, rawNewEventData);
  }

  @Override
  public void onRemoveEvent(IEvent event) {

    String host = event.getHostUser().toString();
    RawEventData rawData = extractUsersAndTime(event);

    delegate.requestRemoveEvent(host, rawData);
  }

  /**
   * Extracts data from an IEvent and turns it into RawEventData.
   *
   * @param event the IEvent with the data passed in
   * @return the event now in RawEventData form
   */

  private RawEventData extractData(IEvent event) {
    List<String> newListOfUsers = new ArrayList<>();

    for (int user = 0; user < event.getInvitedUsers().size(); user++) {
      newListOfUsers.add(event.getInvitedUsers().get(user).getUid());
    }

    String newStartTime = Integer.toString(event.getStartTime().getHour() * 100
            + event.getStartTime().getMinute());
    String newEndTime = Integer.toString(event.getEndTime().getHour() * 100
            + event.getEndTime().getMinute());

    return new RawEventData(newListOfUsers, event.getName(),
            event.getPlace(), Boolean.toString(event.isOnline()), event.getStartDay().toString(),
            newStartTime, event.getEndDay().toString(), newEndTime);
  }

  /**
   * Extracts the list of users and time so that it is accessible to model.
   *
   * @param event the IEvent with the data passed in
   * @return the event now in RawEventData form
   */
  private RawEventData extractUsersAndTime(IEvent event) {
    String startTime = Integer.toString(event.getStartTime().getHour() * 100
            + event.getStartTime().getMinute());
    String endTime = Integer.toString(event.getEndTime().getHour() * 100
            + event.getEndTime().getMinute());

    List<String> listOfUsers = new ArrayList<>();

    for (int user = 0; user < event.getInvitedUsers().size(); user++) {
      listOfUsers.add(event.getInvitedUsers().get(user).getUid());
    }

    return new RawEventData(listOfUsers, event.getName(),
            event.getPlace(), Boolean.toString(event.isOnline()), event.getStartDay().toString(),
            startTime, event.getEndDay().toString(), endTime);
  }

  @Override
  public Strategy onCreateEventFrame() {
//    return delegate.displayBlankEvent(); //TODO: Why do they return a strategy bruh
    return null;
  }

  @Override
  public void onSwitchUser(String userId) {
    delegate.displayNewSchedule(userId);
  }

  @Override
  public void handleClick(IEvent e) {

    String host = e.getHostUser().getUid();

    int startTime = e.getStartTime().getHour() * 100
            + e.getStartTime().getMinute();
    int endTime = e.getEndTime().getHour() * 100
            + e.getEndTime().getMinute();

    List<String> listOfUsers = new ArrayList<>();

    for (int user = 0; user < e.getInvitedUsers().size(); user++) {
      listOfUsers.add(e.getInvitedUsers().get(user).getUid());
    }

    ReadableEvent readableEvent = new NUEvent(listOfUsers, e.getName(),
            e.getPlace(), e.isOnline(), DaysOfTheWeek.stringToDay(e.getStartDay().toString()),
            startTime, DaysOfTheWeek.stringToDay(e.getEndDay().toString()), endTime);

    delegate.displayExistingEvent(host, readableEvent);
  }
}
