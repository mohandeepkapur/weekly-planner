package cs3500.nuplanner.view.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.FRIDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SATURDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SUNDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.THURSDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.WEDNESDAY;

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

  public EventFrame(ReadableSchedulingSystem model, String user) {
    super();

    setSize(500, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    panel = new EventPanel(model);
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

    JLabel eventName = new JLabel();
    eventName.setText("Event Name:");
    eventName.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(eventName,BorderLayout.CENTER);

    eventNameTextField = new JTextField();
    eventNameTextField.setPreferredSize(new Dimension(100, 20));
    JPanel eventPane = new JPanel();
    eventPane.setLayout(new BoxLayout(eventPane, BoxLayout.LINE_AXIS));
    eventPane.add(eventNameTextField);
    panel.add(eventPane, BorderLayout.CENTER);

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

    JLabel availableUsers = new JLabel();
    availableUsers.setText("Available Users:");
    panel.add(availableUsers, BorderLayout.CENTER);

    String[] users = model.allUsers().toArray(new String[0]);
    JList<String> availableUsersList = new JList<>(users);
    availableUsersList.setPreferredSize(new Dimension(400, 100));
    panel.add(availableUsersList, BorderLayout.CENTER);

    createEventButton = new JButton("Create Event");
    modifyEventButton = new JButton("Modify Event");
    removeEventButton = new JButton("Remove Event");

    JPanel eventButtons = new JPanel(new FlowLayout());
    eventButtons.add("createEvent", createEventButton);
    eventButtons.add("modifyEvent", modifyEventButton);
    eventButtons.add("removeEvent", removeEventButton);
    panel.add(eventButtons, BorderLayout.SOUTH);

    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // program won't end if frame closed
    this.add(panel);
  }

  @Override
  public String nameInput() {
    return eventNameTextField.getText();
  }

  @Override
  public void displayName(String name) {
    eventNameTextField.setText(""); // redundant?
    eventNameTextField.setText(name);
  }

  @Override
  public String locationInput() {
    return locationTextField.getText();
  }

  @Override
  public void displayLocation(String location) {
    locationTextField.setText(""); // redundant?
    locationTextField.setText(location);
  }

  @Override
  public String isOnlineInput() {
    return (String) isOnline.getSelectedItem();
  }

  @Override
  public void displayIsOnline(String isOnline) {
    if (isOnline.equals(String.valueOf(true)) || isOnline.equals(String.valueOf(false))) {
      throw new IllegalArgumentException("from event frame, invalid online-state given");
    }
    this.isOnline.setSelectedItem(isOnline);
  }

  @Override
  public String startDayInput() {
    return (String) startingDay.getSelectedItem();
  }

  @Override
  public void displayStartDay(String startDay) {
    // check if string provided is actually a day
    this.startingDay.setSelectedItem(startDay);
  }

  @Override
  public String startTimeInput() {
    return startingTimeTextField.getText();
  }

  @Override
  public void displayStartTime(String startTime) {
    startingTimeTextField.setText(""); // redundant?
    startingTimeTextField.setText(startTime);
  }

  @Override
  public String endDayInput() {
    return (String) endingDay.getSelectedItem();
  }

  @Override
  public void displayEndDay(String endDay) {
    // check if string provided is actually a day
    this.endingDay.setSelectedItem(endDay);
  }

  @Override
  public String endTimeInput() {
    return endingTimeTextField.getText();
  }

  @Override
  public void displayEndTime(String endTime) {
    endingTimeTextField.setText(""); // redundant?
    endingTimeTextField.setText(endTime);
  }

  @Override
  public void clearTextFields() { // maybe, setDefault? <- reset JComboBox stuff too
    endingTimeTextField.setText("");
    startingTimeTextField.setText("");
    locationTextField.setText("");
    eventNameTextField.setText("");
  }

  @Override
  public void addFeatures(Features features) {
    // not connecting this frame to Features yet (not necessary for current assignment)

    createEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {

      }
    });

  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

}

