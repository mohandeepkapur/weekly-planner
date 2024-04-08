package cs3500.nuplanner.view.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;

/**
 * Represents a schedule frame with all the associated text boxes and fields to collect data from
 * the user to be used in the model.
 */

public class ScheduleEventFrame extends JFrame implements ScheduleEventGUIView {
  private ScheduleEventPanel panel;
  private JTextField eventNameTextField;
  private JTextField locationTextField;
  private JComboBox<String> isOnline;
  private JTextField eventDurationTextField;
  private JButton scheduleEventButton;
  private JList<String> availableUsersList;

  private final ReadableSchedulingSystem model;
  private ReadableEvent event;
  private String scheduleFrameOpenerUser;

  /**
   * Creates an ScheduleFrame for a user with a default size and all the associated fields.
   *
   * @param model the model to be used
   * @param user  the user populating the EventFrame
   */
  public ScheduleEventFrame(ReadableSchedulingSystem model, String user) {
    super();

    this.model = model;
    this.scheduleFrameOpenerUser = user;

    setSize(500, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    panel = new ScheduleEventPanel(model);
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

    addEventNameBox();

    addIsOnlineAndLocationBoxSamePane();

    addEventDuration();

    addAvailableUsersBox(model);

    addScheduleButtonToPanel();

    // program won't end if frame closed
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.add(panel);
  }

  /**
   * Adds schedule event button to panel.
   */
  private void addScheduleButtonToPanel() {
    scheduleEventButton = new JButton("Schedule Event");

    JPanel scheduleButton = new JPanel(new FlowLayout());
    scheduleButton.add("removeEvent", scheduleEventButton);
    panel.add(scheduleButton, BorderLayout.SOUTH);
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
   * Adds event duration to the schedule panel.
   */
  private void addEventDuration() {
    JLabel eventDuration = new JLabel();
    eventDuration.setText("Duration In Minutes:");
    eventDuration.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(eventDuration, BorderLayout.CENTER);

    eventDurationTextField = new JTextField();
    eventDurationTextField.setPreferredSize(new Dimension(100, 20));
    JPanel schedulePane = new JPanel();
    schedulePane.setLayout(new BoxLayout(schedulePane, BoxLayout.LINE_AXIS));
    schedulePane.add(eventDurationTextField);
    panel.add(schedulePane, BorderLayout.CENTER);
  }

  /**
   * Add IsOnline and Location information to one panel.
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
   * Add Schedule name to Schedule panel.
   */
  private void addEventNameBox() {
    JLabel eventName = new JLabel();
    eventName.setText("Event Name:");
    eventName.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(eventName, BorderLayout.CENTER);

    eventNameTextField = new JTextField();
    eventNameTextField.setPreferredSize(new Dimension(100, 20));
    JPanel schedulePane = new JPanel();
    schedulePane.setLayout(new BoxLayout(schedulePane, BoxLayout.LINE_AXIS));
    schedulePane.add(eventNameTextField);
    panel.add(schedulePane, BorderLayout.CENTER);
  }

  @Override
  public String nameInput() {
    return eventNameTextField.getText();
  }

  @Override
  public void displayName(String name) {
    eventNameTextField.setText("");
    eventNameTextField.setText(name);
  }

  @Override
  public String locationInput() {
    return locationTextField.getText();
  }

  @Override
  public void displayLocation(String location) {
    locationTextField.setText("");
    locationTextField.setText(location);
  }

  @Override
  public String isOnlineInput() {
    return (String) isOnline.getSelectedItem();
  }

  @Override
  public void displayIsOnline(String isOnline) {
    this.isOnline.setSelectedItem(isOnline);
  }

  @Override
  public String startDayInput() {
    return null;
  }

  @Override
  public void displayStartDay(String startDay) {

  }

  @Override
  public String startTimeInput() {
    return null;
  }

  @Override
  public void displayStartTime(String startTime) {

  }

  @Override
  public String endDayInput() {
    return null;
  }

  @Override
  public void displayEndDay(String endDay) {

  }

  @Override
  public String endTimeInput() {
    return null;
  }

  @Override
  public void displayEndTime(String endTime) {

  }

  @Override
  public void addFeatures(Features features) {
    scheduleEventButton.addActionListener(actionEvent -> {
      // areInputsBlank(); <- view check before calling features method

      // print out create-event details
      if (areInputsBlank()) {
        printErrorMessage();
      } else {
        System.out.println("CREATING EVENT...");
        System.out.println("Creator/Host of event: " + scheduleFrameOpenerUser);
        printEventDetails();
        // print JList selections as invitees
        System.out.println(scheduleFrameOpenerUser + " " + availableUsersList.getSelectedValuesList());
      }
    });
  }

  private void printErrorMessage() {
    System.out.print("Cannot execute button based on user input... ");
  }

  /**
   * Checks for User if don't fill Event Frame completely.
   */
  private boolean areInputsBlank() {
    return this.nameInput().isEmpty() || this.locationInput().isEmpty() || this.isOnlineInput()
            .isEmpty()
            || this.startDayInput().isEmpty() || this.startTimeInput()
            .isEmpty() || this.endDayInput().isEmpty()
            || this.endTimeInput().isEmpty();
  }

  private void printEventDetails() {
    System.out.println(this.nameInput());
    System.out.println(this.locationInput());
    System.out.println(this.isOnlineInput());
    System.out.println(this.startDayInput());
    System.out.println(this.startTimeInput());
    System.out.println(this.endDayInput());
    System.out.println(this.endTimeInput());
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void displayInvitees(List<String> invitees) {
    List<String> inviteesToColor = new ArrayList<>();

    // for all users in scheduling system
    for (String user : model.allUsers()) {
      // if user is an invitee of event being displayed
      if (invitees.contains(user)) {
        inviteesToColor.add(user);
      }
    }
  }
}
