package cs3500.nuplanner.view.GUI;

import java.awt.*;

import javax.swing.*;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;

public class SSFrame extends JFrame implements SSGUI {

  private ReadableSchedulingSystem model;

  private SSPanel panel;
  // all components that exist within frame
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JComboBox<String> userDropdown;

  public SSFrame(ReadableSchedulingSystem model) {
    super();

    this.model = model;

    setSize(800, 800);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    panel = new SSPanel(model);
    panel.setLayout(new BorderLayout());
    this.add(panel, BorderLayout.CENTER);

    this.createEventButton = new JButton("Create Event");
    this.createEventButton.setActionCommand("bro...");
    panel.add(createEventButton);

    this.scheduleEventButton = new JButton("Schedule Event");
    panel.add(scheduleEventButton, BorderLayout.SOUTH);

    String[] users = model.allUsers().toArray(new String[0]);
    userDropdown = new JComboBox<>(users);
    panel.add(userDropdown, BorderLayout.SOUTH);

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    menu.add(new JMenuItem("Add Calendar"));
    menu.add(new JMenuItem("Save All Calendars"));
    menuBar.add(menu);

    JPanel buttonLayout = new JPanel(new FlowLayout());
    buttonLayout.add("createEvent", createEventButton);
    buttonLayout.add("scheduleEvent", scheduleEventButton);
    buttonLayout.add("userDropdown", userDropdown);
    panel.add(buttonLayout, BorderLayout.SOUTH);

    this.add(menuBar, BorderLayout.PAGE_START);

    this.makeVisible();
  }

  @Override
  public void addFeatures(Features features) {

    createEventButton.addActionListener(evt -> features.requestCreateEvent());
    userDropdown.addActionListener(evt -> features.displayNewSchedule((String) userDropdown.getSelectedItem()));

  }

  @Override
  public void displayNewSchedule(String user) {
    System.out.println("Displaying new schedule... " + user);
    panel.displayUserSchedule(user);
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

}
