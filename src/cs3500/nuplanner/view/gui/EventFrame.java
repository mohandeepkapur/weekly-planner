package cs3500.nuplanner.view.gui;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.DefaultListCellRenderer;

import java.util.ArrayList;
import java.util.List;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.RawEventData;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.FRIDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SATURDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SUNDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.THURSDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.WEDNESDAY;

/**
 * Represents an event frame with all the associated text boxes and fields to collect data from
 * the user to be used in the model.
 * Way View is currently set up, will only provide NUEvents to Features!!! Impl specific View!!!!
 * Cannot have this!!! If change impl-type of Events in Model, this View will need to be edited!!!
 * Bad coupling!!!
 */
public class EventFrame extends JFrame implements EventGUIView {

  private EventPanel panel;
  private JTextField eventNameTextField;
  private JTextField locationTextField;
  private JComboBox<String> isOnline;
  private JComboBox<String> startingDay;
  private JComboBox<String> endingDay;
  private JTextField startingTimeTextField;
  private JTextField endingTimeTextField;
  private JButton createEventButton;
  private JButton modifyEventButton;
  private JButton removeEventButton;
  private JList<String> availableUsersList;

  private final ReadableSchedulingSystem model;
  private ReadableEvent currEventDisp;
  private String userWhoOpenedEventFrame;

  /**
   * Creates an EventFrame for a user with a default size and all the associated fields.
   *
   * @param model the model to be used
   * @param user  the user populating the EventFrame
   */
  public EventFrame(ReadableSchedulingSystem model, String user) {
    super();

    this.model = model;
    this.userWhoOpenedEventFrame = user;

    setSize(500, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    panel = new EventPanel(model);
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

    addEventNameBox();

    // not very one method one purpose-y
    addIsOnlineAndLocationBoxSamePane();

    addEventStartAndEndInformation();

    addAvailableUsersBox(model);

    addButtonsToPanel();

    // program won't end if frame closed
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.add(panel);
  }

  /**
   * Adds create, modify, remove event buttons to panel.
   */
  private void addButtonsToPanel() {
    createEventButton = new JButton("Create Event");
    modifyEventButton = new JButton("Modify Event");
    removeEventButton = new JButton("Remove Event");

    JPanel eventButtons = new JPanel(new FlowLayout());
    eventButtons.add("createEvent", createEventButton);
    eventButtons.add("modifyEvent", modifyEventButton);
    eventButtons.add("removeEvent", removeEventButton);
    panel.add(eventButtons, BorderLayout.SOUTH);
  }

  /**
   * Adds available users to panel.
   *
   * @param model used to extract available users in sched sys
   */
  private void addAvailableUsersBox(ReadableSchedulingSystem model) {
    JLabel availableUsers = new JLabel();
    availableUsers.setText("Available Users:");
    panel.add(availableUsers, BorderLayout.CENTER);

    String[] users = model.allUsers().toArray(new String[0]);
    availableUsersList = new JList<>(users);
    //availableUsersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    availableUsersList.setPreferredSize(new Dimension(400, 100));
    panel.add(availableUsersList, BorderLayout.CENTER);
  }

  /**
   * Adds start and end day and time to panel.
   */
  private void addEventStartAndEndInformation() {
    JLabel startingDay = new JLabel();
    startingDay.setText("Starting Day: ");
    String[] daysOfTheWeek = {SUNDAY.toString(), MONDAY.toString(), TUESDAY.toString(),
            WEDNESDAY.toString(), THURSDAY.toString(), FRIDAY.toString(), SATURDAY.toString()};

    this.startingDay = new JComboBox<>(daysOfTheWeek);

    JPanel startDayPane = new JPanel();
    startDayPane.setLayout(new BoxLayout(startDayPane, BoxLayout.LINE_AXIS));
    startDayPane.add(startingDay);
    startDayPane.add(this.startingDay);
    panel.add(startDayPane, BorderLayout.CENTER);

    JLabel startingTime = new JLabel();
    startingTime.setText("Starting Time: ");

    startingTimeTextField = new JTextField();
    startingTimeTextField.setPreferredSize(new Dimension(100, 20));

    JPanel startTimePane = new JPanel();
    startTimePane.setLayout(new BoxLayout(startTimePane, BoxLayout.LINE_AXIS));
    startTimePane.add(startingTime);
    startTimePane.add(startingTimeTextField);
    panel.add(startTimePane, BorderLayout.CENTER);


    JLabel endingDay = new JLabel();
    endingDay.setText("Ending Day: ");
    this.endingDay = new JComboBox<>(daysOfTheWeek);

    JPanel endDayPane = new JPanel();
    endDayPane.setLayout(new BoxLayout(endDayPane, BoxLayout.LINE_AXIS));
    endDayPane.add(endingDay);
    endDayPane.add(this.endingDay);
    panel.add(endDayPane, BorderLayout.CENTER);

    JLabel endingTime = new JLabel();
    endingTime.setText("Ending Time: ");
    endingTimeTextField = new JTextField();
    endingTimeTextField.setPreferredSize(new Dimension(100, 20));

    JPanel endTimePane = new JPanel();
    endTimePane.setLayout(new BoxLayout(endTimePane, BoxLayout.LINE_AXIS));
    endTimePane.add(endingTime);
    endTimePane.add(endingTimeTextField);
    panel.add(endTimePane, BorderLayout.CENTER);
  }

  /**
   * Add IsOnline and Location information to one pane, bl.
   */
  private void addIsOnlineAndLocationBoxSamePane() {
    JLabel location = new JLabel();
    location.setText("Location:");
    panel.add(location, BorderLayout.CENTER);

    String[] trueOrFalse = {String.valueOf(true), String.valueOf(false)};
    isOnline = new JComboBox<>(trueOrFalse);

    locationTextField = new JTextField();
    locationTextField.setPreferredSize(new Dimension(100, 20));

    JPanel onlinePane = new JPanel();
    onlinePane.setLayout(new BoxLayout(onlinePane, BoxLayout.LINE_AXIS));
    onlinePane.add(isOnline);
    onlinePane.add(locationTextField);
    panel.add(onlinePane, BorderLayout.CENTER);
  }

  /**
   * Add Event name to Event panel.
   */
  private void addEventNameBox() {
    JLabel eventName = new JLabel();
    eventName.setText("Event Name:");
    eventName.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(eventName, BorderLayout.CENTER);

    eventNameTextField = new JTextField();
    eventNameTextField.setPreferredSize(new Dimension(100, 20));
    JPanel eventPane = new JPanel();
    eventPane.setLayout(new BoxLayout(eventPane, BoxLayout.LINE_AXIS));
    eventPane.add(eventNameTextField);
    panel.add(eventPane, BorderLayout.CENTER);
  }

  /**
   * Returns the name of the event.
   *
   * @return the name of the event
   */
  @Override
  public String nameInput() {
    return eventNameTextField.getText();
  }

  /**
   * Sets the name of the event.
   *
   * @param name the name of the event
   */
  @Override
  public void displayName(String name) {
    eventNameTextField.setText(""); // redundant?
    eventNameTextField.setText(name);
  }

  /**
   * Returns the location of an event.
   *
   * @return the location of an event
   */
  @Override
  public String locationInput() {
    return locationTextField.getText();
  }

  /**
   * Sets the location of an event.
   *
   * @param location the location of an event
   */
  @Override
  public void displayLocation(String location) {
    locationTextField.setText(""); // redundant?
    locationTextField.setText(location);
  }

  /**
   * Returns whether an event is online or not.
   *
   * @return true if online, false if not
   */
  @Override
  public String isOnlineInput() {
    return (String) isOnline.getSelectedItem();
  }

  /**
   * Sets whether an event is online or not.
   *
   * @param isOnline true if online, false if not
   */
  @Override
  public void displayIsOnline(String isOnline) {
    this.isOnline.setSelectedItem(isOnline);
  }

  /**
   * Returns the start day of an event.
   *
   * @return the start day of an event
   */
  @Override
  public String startDayInput() {
    return (String) startingDay.getSelectedItem();
  }

  /**
   * Sets the start date of an event.
   *
   * @param startDay the start day of event
   */
  @Override
  public void displayStartDay(String startDay) {
    // check if string provided is actually a day
    this.startingDay.setSelectedItem(startDay);
  }

  /**
   * Returns the start time of an event.
   *
   * @return the start time of an event
   */
  @Override
  public String startTimeInput() {
    return startingTimeTextField.getText();
  }

  /**
   * Sets the start time of an event.
   *
   * @param startTime the start time of an event
   */
  @Override
  public void displayStartTime(String startTime) {
    startingTimeTextField.setText(""); // redundant?
    startingTimeTextField.setText(startTime);
  }

  /**
   * Returns the end day of an event.
   *
   * @return the end day of an event
   */
  @Override
  public String endDayInput() {
    return (String) endingDay.getSelectedItem();
  }

  /**
   * Sets the end day of an event.
   *
   * @param endDay the end day of an event
   */
  @Override
  public void displayEndDay(String endDay) {
    // check if string provided is actually a day
    this.endingDay.setSelectedItem(endDay);
  }

  /**
   * Returns the end time of an event.
   *
   * @return the end time of an event
   */
  @Override
  public String endTimeInput() {
    return endingTimeTextField.getText();
  }

  /**
   * Sets the end time of an event.
   *
   * @param endTime the end time of an event
   */
  @Override
  public void displayEndTime(String endTime) {
    endingTimeTextField.setText(""); // redundant?
    endingTimeTextField.setText(endTime);
  }

  /**
   * Will be removed once Features properly linked to controls in this View.
   * Really jank scaffolding around printing stuff out/not how I would organize permanent code.
   */
  private void printEventDetails() {
    System.out.println(this.nameInput());
    System.out.println(this.locationInput());
    System.out.println(this.isOnlineInput());
    System.out.println(this.startDayInput());
    System.out.println(this.startTimeInput());
    System.out.println(this.endDayInput());
    System.out.println(this.endTimeInput());
  }

  /**
   * Represents the different features that are applicable to an EventFrame.
   *
   * @param features the feature to use
   */
  // not connecting this frame to Features yet properly (not necessary for current assignment)
  @Override
  public void addFeatures(Features features) {
    // instead of linking existing class to control,
    // creating anon. class whose callback will be executed once relevant event happens

    createEventButtonCallback(features);
    removeEventButtonCallback(features);
    modifyEventButtonCallback(features);

  }

  private void modifyEventButtonCallback(Features features) {
    modifyEventButton.addActionListener(actionEvent -> {
      // if user selects a non-invitee on the screen, add that into mod event's invitee list

      // update Event's invitee list based on currently selected users
      List<String> modInviteeList = this.currEventDisp.eventInvitees();
      for (String user : availableUsersList.getSelectedValuesList()) {
        // if event invitee list contains selected user
        if (!this.currEventDisp.eventInvitees().contains(user)) {
          // if user selects a non-invitee on the screen, add that into mod event's invitee list
          modInviteeList.add(user);
        } else {
          // if user selects a invitee on the screen, remove from mod event's invitee list
          modInviteeList.remove(user);
        }
      }

      // if host of event was removed by user
      if (!modInviteeList.isEmpty()
              && !modInviteeList.get(0).equals(currEventDisp.eventInvitees().get(0))) {
        // modified event should be removed from all schedules
        modInviteeList.clear();
      }


      // print out modified event details <-- new event
      System.out.println("MODIFYING EVENT...");
      System.out.println("Modifier of event: " + userWhoOpenedEventFrame);
      printEventDetails();
      System.out.println("Modified Event invitees" + modInviteeList);

      features.requestModifyEvent(userWhoOpenedEventFrame, createRawEventFromEvent(currEventDisp),
              createRawEventFromUserInput(modInviteeList));
      this.dispose();

    });
  }

  private void removeEventButtonCallback(Features features) {
    removeEventButton.addActionListener(actionEvent -> {
      // button doesn't care if any data removed from event-frame popup by user
      // will still remove correct event thanks to field that tracks curr Event obj displayed
      features.requestRemoveEvent(this.userWhoOpenedEventFrame,
              createRawEventFromEvent(currEventDisp));
      this.dispose();
    });
  }

  private RawEventData createRawEventFromEvent(ReadableEvent event) {
    return new RawEventData(event.eventInvitees(), event.name(), event.location(),
            String.valueOf(event.isOnline()), String.valueOf(event.startDay()),
            String.valueOf(event.startTime()), String.valueOf(event.endDay()),
            String.valueOf(event.endTime()));
  }

  private void createEventButtonCallback(Features features) {
    createEventButton.addActionListener(actionEvent -> {
      // print out create-event details
      System.out.println("CREATING EVENT...");
      System.out.println("Creator/Host of event: " + userWhoOpenedEventFrame);
      printEventDetails();
      System.out.println(userWhoOpenedEventFrame + " "
              + availableUsersList.getSelectedValuesList());

      List<String> eventInvitees = new ArrayList<>(availableUsersList.getSelectedValuesList());

      // interpret getSelectedValues List properly to send
      // correct create-event data
      if (eventInvitees.contains(userWhoOpenedEventFrame)) {
        eventInvitees.remove(userWhoOpenedEventFrame);
        eventInvitees.add(0, userWhoOpenedEventFrame);
      } else {
        eventInvitees.add(0, userWhoOpenedEventFrame);
      }

      //conversion of low-level data into high-level signature
      // but focus is on <<request>> evolution, not signature evolution
      features.requestCreateEvent(userWhoOpenedEventFrame,
              createRawEventFromUserInput(eventInvitees));
      this.dispose();
    });
  }


  private RawEventData createRawEventFromUserInput(List<String> invitees) {
    return new RawEventData(invitees, this.nameInput(), this.locationInput(),
            this.isOnlineInput(), this.startDayInput(),
            this.startTimeInput(), this.endDayInput(), this.endTimeInput());

  }

  /**
   * Displays all Event details to user.
   *
   * @param user  the current user
   * @param event event to be displayed
   */
  @Override
  public void displayExistingEvent(String user, ReadableEvent event) {
    this.currEventDisp = event;

    this.displayName(event.name());
    this.displayLocation(event.location());
    this.displayIsOnline(String.valueOf(event.isOnline()));
    this.displayStartDay(event.startDay().toString());
    this.displayStartTime(String.valueOf(event.startTime()));
    this.displayEndDay(event.endDay().toString());
    this.displayEndTime(String.valueOf(event.endTime()));
    this.displayInvitees(event.eventInvitees());
  }

  /**
   * Makes the frame visible to the user.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }

  /**
   * Displays Invitees of Event.
   */
  @Override
  public void displayInvitees(List<String> invitees) {
    // event extracted from within model, thus invitees provided are subset of available users
    List<String> inviteesToColor = new ArrayList<>();
    String hostToColor = invitees.get(0); // again, guaranteed to exist in available users

    // for all users in scheduling system
    for (String user : model.allUsers()) {
      // if user is an invitee of event being displayed
      if (invitees.contains(user)) {
        inviteesToColor.add(user);
      }
    }

    availableUsersList.setCellRenderer(new ListCellRenderer(inviteesToColor, hostToColor));

  }

  /**
   * Private class that highlights relevant elements in JList given criteria.
   */
  private class ListCellRenderer extends DefaultListCellRenderer {
    private List<String> inviteesToColor;
    private String hostToColor;

    /**
     * Constructs ListCellRenderer object.
     */
    private ListCellRenderer(List<String> inviteesToColor, String hostToColor) {
      this.inviteesToColor = inviteesToColor;
      this.hostToColor = hostToColor;
    }

    /**
     * Highlights relevant elements in JList given criteria defined in method.
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
      Component renderer = super.getListCellRendererComponent(list, value,
              index, isSelected, cellHasFocus);

      // check if value in JList is contained in usersToHighlight
      if (value != null && inviteesToColor.contains(value.toString())) {
        renderer.setForeground(Color.BLUE);
      } else {
        renderer.setForeground(list.getForeground());
      }

      if (value != null && hostToColor.equals(value.toString())) {
        renderer.setForeground(Color.GREEN);
      }

      return renderer;

    }

  }

}