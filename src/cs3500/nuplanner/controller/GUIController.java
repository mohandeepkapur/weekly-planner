package cs3500.nuplanner.controller;

import java.io.IOException;
import java.util.List;

import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.NUEvent;
import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.SchedulingSystem;
import cs3500.nuplanner.strategies.SchedulingStrategies;
import cs3500.nuplanner.view.SchedulingSystemView;
import cs3500.nuplanner.view.SchedulingSystemXMLView;
import cs3500.nuplanner.view.gui.SSGUIView;

/**
 * Controller for GUI-led Scheduling System. Implements Features because goal of controller
 * and Features align very well (the purpose of both is to manipulate view and model state).
 */
public class GUIController implements SchedulingSystemController, Features {

  private SchedulingSystem model;
  private SSGUIView view;
  private SchedulingStrategies strategy;

  /**
   * Constructs a controller.
   *
   * @param ssView main GUI controller will manipulate
   */
  public GUIController(SSGUIView ssView, SchedulingStrategies strategy) {
    this.view = ssView;
    this.strategy = strategy;
  }

  /**
   * Request for a new user's schedule to be shown.
   *
   * @param user                        user whose schedule to be shown
   */
  @Override
  public void displayNewSchedule(String user) {
    try {
      view.displayUserSchedule(user);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage("This user does not exist in scheduling system... schedule cannot be displayed");
    }
  }

  /**
   * Request to create a new Event.
   */
  @Override
  public void displayBlankEvent(String user) {
    try {
      view.displayBlankEvent();
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage("Must select user first... ");
    }
  }

  /**
   * Request for an Event's details to be shown. Event must belong in displayed user's schedule.
   *
   * @param user      user whose schedule to be shown
   * @param eventData the event data of the event to be shown
   */
  @Override
  public void displayExistingEvent(String user, ReadableEvent eventData) {
    // check if given event does exist in user's schedule
    try {
      view.displayExistingEvent(user, eventData);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage(caught.getMessage());
    }
  }

  @Override
  public void displayBlankScheduleEvent(String user) {
    try {
      view.displayBlankScheduleEvent();
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage(caught.getMessage());
    }
  }

  /**
   * Request for an XML file to be uploaded.
   *
   * @param pathname path of XML file
   */
  @Override
  public void requestXMLScheduleUpload(String pathname) {
    try {
      SchedulingSystemController xmlcontroller = new XMLController(this.model);
      xmlcontroller.useSchedulingSystem(pathname);
      this.view.refresh();
      this.view.displayUserSchedule(this.model.allUsers().get(0));
    } catch (IllegalStateException caught) {
      this.view.displayErrorMessage("Unable to process XML file..");
    }
  }

  /**
   * Request for model state to be saved into multiple XML schedules.
   *
   * @param pathname path of XML directory
   */
  @Override
  public void requestAllSchedulesDownload(String pathname) {
    System.out.println(pathname);
    try {
      SchedulingSystemView xmlview = new SchedulingSystemXMLView(this.model);
      for (String user : this.model.allUsers()) {
        xmlview.render(user, pathname + user + ".xml");
      }
    } catch (IOException e) {
      this.view.displayErrorMessage("Cannot save XML version of schedules to given directory...");
    }
  }

  /**
   * Request to add an Event into requester's schedule.
   */
  @Override
  public void requestCreateEvent(String user, RawEventData event) {

    // check if given event does exist in user's schedule

    // controller ensuring valid inputs in limited manner:
    // check that user has filled all necessary event fields
    // check that certain inputs can be parsed as desired types

    Event validEvent;
    try {
      validEvent = parseRawEventData(event);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage("Cannot create event with provided input... ");
      return;
    }

    try {
      model.addEvent(validEvent.eventInvitees().get(0), validEvent.eventInvitees(),
              validEvent.name(),
              validEvent.location(), validEvent.isOnline(), validEvent.startDay(),
              validEvent.startTime(), validEvent.endDay(), validEvent.endTime());
      this.view.displayUserSchedule(user);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage(caught.getMessage());
    }

  }


  /**
   * User request to remove an event they've selected from scheduling system.
   */
  @Override
  public void requestRemoveEvent(String user, RawEventData event) {

    // current View impl logically will never trigger this try-catch
    // but future impls may do so
    Event validEvent;
    try {
      validEvent = parseRawEventData(event);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage("Cannot remove event with provided input... "
              + caught.getMessage());
      return;
    }

    // should also never get triggered
    try {
      checkIfEventInUserSchedule(user, validEvent);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage(caught.getMessage());
    }

    try {
      this.model.removeEvent(user, validEvent.startDay(), validEvent.startTime());
      this.view.displayUserSchedule(user);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage(caught.getMessage());
    }
  }

  private void checkIfEventInUserSchedule(String user, Event validEvent) {

    ReadableEvent userEvent = this.model.eventAt(user,
            validEvent.startDay(), validEvent.startTime());

    if (!userEvent.equals(validEvent)) {
      throw new IllegalArgumentException("Given Event not in given user schedule... ");
    }

  }

  /**
   * Request to "schedule an event".
   */
  @Override
  public void requestScheduleEvent(String user, String name, String location, String isOnline,
                                   String duration, List<String> invitees) {
    Event scheduledEvent;
    try {
      scheduledEvent = strategy.findTimeForScheduledEvent(model, name,
              Boolean.parseBoolean(isOnline),
              location,
              Integer.parseInt(duration), invitees);

    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage("Cannot create event with provided input... " + caught.getMessage());
      return; //TODO: the error message pane doesn't seem to pop up <- Mohan: message pops up for me
    }

    try {
      model.addEvent(user, scheduledEvent.eventInvitees(),
              scheduledEvent.name(),
              scheduledEvent.location(), scheduledEvent.isOnline(), scheduledEvent.startDay(),
              scheduledEvent.startTime(), scheduledEvent.endDay(), scheduledEvent.endTime());
      this.view.displayUserSchedule(user);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage(caught.getMessage());
    }
  }

  /**
   * User request to modify an existing event based on how its manipulated event in GUI.
   */
  @Override
  public void requestModifyEvent(String user, RawEventData currEvent, RawEventData modEvent) {
    // check if currEvent not in requester's schedule (View itself will never this error)
    // but any other component stupidly could, and actually harm model internals
    // thanks to model signature chosen (could collide model w/ other event w/ same start-point)

    Event validCurrEvent;
    try {
      validCurrEvent = parseRawEventData(currEvent);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage(caught.getMessage());
      return;
    }

    Event validModEvent;
    try {
      validModEvent = parseRawEventData(modEvent);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage(caught.getMessage());
      return;
    }

    checkIfEventInUserSchedule(user, validCurrEvent);

    try {
      this.model.modifyEvent(user, validCurrEvent.startDay(),
              validCurrEvent.startTime(), validModEvent);
      this.view.displayUserSchedule(user);
    } catch (IllegalArgumentException caught) {
      this.view.displayErrorMessage("ERROR: " + caught.getMessage());
    }
  }

  /**
   * User request to exit program.
   */
  @Override
  public void requestExitProgram() {
    // empty for now
  }

  /**
   * Runs scheduling system using user input.
   */
  @Override
  public void useSchedulingSystem(SchedulingSystem model, SchedulingStrategies strategy) {
    this.model = model;
    view.addFeatures(this);
    view.makeVisible();
  }

  /**
   * Runs scheduling system using XML file.
   *
   * @param pathname path to XML file
   * @throws IllegalStateException if unable to open or parse XML file
   */
  @Override
  public void useSchedulingSystem(String pathname) {
    throw new IllegalArgumentException("Cannot use this signature for this impl of Controller...");
  }

  /**
   * Uses the event wrapper to get raw data to create an event.
   *
   * @param event the event that the data is pulled from
   * @return returns an event
   * @throws IllegalArgumentException if the input isn't correct or cannot be parsed
   */
  private Event parseRawEventData(RawEventData event) throws IllegalArgumentException {

    if (isAnyDataWithheldBlank(event)) {
      throw new IllegalArgumentException(
              "Input withheld necessary details needed to create an Event... ");
    }

    try {
      int startTime = Integer.parseInt(event.startTimeInput());
      int endTime = Integer.parseInt(event.endTimeInput());
      boolean isOnline = Boolean.parseBoolean(event.isOnlineInput());
      DaysOfTheWeek startDay = DaysOfTheWeek.stringToDay(event.startDayInput());
      DaysOfTheWeek endDay = DaysOfTheWeek.stringToDay(event.endDayInput());

      // hard-coded NUEvent. Wouldn't matter if time-rep was decoupled from an Event impl!
      return new NUEvent(event.invitees(), event.nameInput(), event.locationInput(),
              isOnline, startDay, startTime, endDay, endTime);

    } catch (IllegalArgumentException caught) {
      throw new IllegalArgumentException("Input cannot be parsed into a valid Event... ");
    }

  }

  private boolean isAnyDataWithheldBlank(RawEventData event) {
    return event.nameInput().isEmpty() || event.locationInput().isEmpty()
            || event.isOnlineInput().isEmpty() || event.startDayInput().isEmpty()
            || event.startTimeInput().isEmpty() || event.endDayInput().isEmpty()
            || event.endTimeInput().isEmpty();// || event.invitees().isEmpty();
  }
}