package cs3500.nuplanner.view.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.nuplanner.controller.Features;
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
  private ReadableEvent event;
  private String eventFrameOpenerUser;

  /**
   * Creates an EventFrame for a user with a default size and all the associated fields.
   *
   * @param model the model to be used
   * @param user  the user populating the EventFrame
   */
  public EventFrame(ReadableSchedulingSystem model, String user) {
    super();

    this.model = model;
    this.eventFrameOpenerUser = user;

    setSize(500, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    panel = new EventPanel(model);
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

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
    availableUsersList = new JList<>(users);
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

  /**
   * Will be removed once Features properly linked to controls in this View.
   */
  private void printEventDetails() {
    System.out.println(this.nameInput());
    System.out.println(this.locationInput());
    System.out.println(this.isOnlineInput());
    System.out.println(this.startDayInput());
    System.out.println(this.startTimeInput());
    System.out.println(this.endDayInput());
    System.out.println(this.endTimeInput());
    System.out.println(this.event.eventInvitees());
    System.out.print("\n");
  }

  // not connecting this frame to Features yet properly (not necessary for current assignment)
  @Override
  public void addFeatures(Features features) {
    // instead of linking existing class to control,
    // creating anon. class whose callback will be executed once relevant event happens

    createEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        //areInputsBlank(); //view check before calling features method
        // print out create-event details
        if (areInputsBlank()) {

        } else {
          System.out.println("CREATING EVENT...");
          System.out.println("Creator of event: " + eventFrameOpenerUser);
          printEventDetails();
        }
      }
    });

    removeEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        //areInputsBlank(); //view check before calling features method
        // print out remove-event details
        if (areInputsBlank()) {

        } else {
          System.out.println("REMOVING EVENT...");
          System.out.println("Remover of event: " + eventFrameOpenerUser);
          printEventDetails();
        }
        //features.requestRemoveEvent(); //provide start day, start time, user of event-to-remove
                                         //event-frame needs to be aware of who opened frame?
      }
    });

    modifyEventButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        // print out modify-event details
        // System.out.print(availableUsersList.getSelectedValue()); //null if nothing selected
        if (areInputsBlank()) {

        } else {
          System.out.println("MODIFYING EVENT...");
          System.out.println("Modifier of event: " + eventFrameOpenerUser);
          printEventDetails();
        }

      }
    });
  }

  private boolean areInputsBlank() {
    // if any inputs on GUI blank return true
    return false;
  }

  @Override
  public void displayEvent(ReadableEvent event) {
    this.event = event;

    this.displayName(event.name());
    this.displayLocation(event.location());
    this.displayIsOnline(String.valueOf(event.isOnline()));
    this.displayStartDay(event.startDay().toString());
    this.displayStartTime(String.valueOf(event.startTime()));
    this.displayEndDay(event.endDay().toString());
    this.displayEndTime(String.valueOf(event.endTime()));
    this.displayInvitees(event.eventInvitees());
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void displayInvitees(List<String> invitees) {
    // event extracted from within model, thus invitees provided are subset of available users
    List<String> inviteesToColor = new ArrayList<>();
    String hostToColor = invitees.get(0); // again, guaranteed to exist in available users

    // for all users in scheduling system
    for(String user: model.allUsers()) {
      // if user is an invitee of event being displayed
      if (invitees.contains(user)) {
        inviteesToColor.add(user);
      }
    }

    availableUsersList.setCellRenderer(new ListCellRenderer(inviteesToColor, hostToColor));

  }

  private class ListCellRenderer extends DefaultListCellRenderer {
    private List<String> inviteesToColor;
    private String hostToColor;

    public ListCellRenderer(List<String> inviteesToColor, String hostToColor) {
      this.inviteesToColor = inviteesToColor;
      this.hostToColor = hostToColor;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
      Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

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