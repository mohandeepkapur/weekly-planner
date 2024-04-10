//package cs3500.nuplanner.view.gui;
//
//import java.awt.*;
//
//import javax.swing.*;
//
//import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;
//
//public abstract class AbstractScheduleEventFrame extends JFrame {
//
//  private JTextField eventNameTextField;
//  private JTextField locationTextField;
//  private JComboBox<String> isOnline;
//  private JList<String> availableUsersList;
//
//  protected AbstractScheduleEventFrame(ReadableSchedulingSystem model, String user) {
//
//    super();
//
//    this.model = model;
//    this.userOpenedSchedFrame = user;
//
//    setSize(500, 400);
//    setDefaultCloseOperation(EXIT_ON_CLOSE);
//    panel = new ScheduleEventPanel(model);
//    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
//
//    addEventNameBox();
//
//    addIsOnlineAndLocationBoxSamePane();
//
//    addEventDuration();
//
//    addAvailableUsersBox(model);
//
//    addScheduleButtonToPanel();
//
//    // program won't end if frame closed
//    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//    this.add(panel);
//
//  }
//
//  /**
//   * Add Event name to Event panel.
//   */
//  private void addEventNameBox() {
//    JLabel eventName = new JLabel();
//    eventName.setText("Event Name:");
//    eventName.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//    panel.add(eventName, BorderLayout.CENTER);
//
//    eventNameTextField = new JTextField();
//    eventNameTextField.setPreferredSize(new Dimension(100, 20));
//    JPanel eventPane = new JPanel();
//    eventPane.setLayout(new BoxLayout(eventPane, BoxLayout.LINE_AXIS));
//    eventPane.add(eventNameTextField);
//    panel.add(eventPane, BorderLayout.CENTER);
//  }
//
//
//
//
//
//}
