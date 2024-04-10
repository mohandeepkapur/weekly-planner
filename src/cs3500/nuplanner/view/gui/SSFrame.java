package cs3500.nuplanner.view.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;

/**
 * A frame that shows the schedule of a user and implements all the features to function.
 */
public class SSFrame extends JFrame implements SSGUIView {

  private ReadableSchedulingSystem model;
  private SSPanel panel;
  private Features features; //allows both Event and SS Views to be linked to the same Features

  // all components that exist within frame
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JComboBox<String> userDropdown;
  private JMenuItem uploadXML;
  private JMenuItem downloadXMLs;
  private String currentUserDisplayed;

  /**
   * Creates the schedule with the proper days, time blocks, buttons, and menus.
   *
   * @param model the model used in the GUI
   */
  public SSFrame(ReadableSchedulingSystem model) {
    super();

    this.model = model;

    setSize(800, 800);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    panel = new SSPanel(model);
    panel.setLayout(new BorderLayout());
    this.add(panel, BorderLayout.CENTER);

    this.createEventButton = new JButton("Create Event"); // do not need setActionCommand
    panel.add(createEventButton);

    this.scheduleEventButton = new JButton("Schedule Event");
    panel.add(scheduleEventButton, BorderLayout.SOUTH);

    String[] users = model.allUsers().toArray(new String[0]);
    userDropdown = new JComboBox<>(users);
    panel.add(userDropdown, BorderLayout.SOUTH);

    JMenuBar menuBar = new JMenuBar();
    this.uploadXML = new JMenuItem("Upload XML");
    this.downloadXMLs = new JMenuItem("Download XMLs");
    JMenu menu = new JMenu("Menu");
    menu.add(this.uploadXML);
    menu.add(this.downloadXMLs);
    menuBar.add(menu);

    JButton fileChooserButton = new JButton("Open File");

    JPanel buttonLayout = new JPanel(new FlowLayout());
    buttonLayout.add("createEvent", createEventButton);
    buttonLayout.add("scheduleEvent", scheduleEventButton);
    buttonLayout.add("userDropdown", userDropdown);
    panel.add(buttonLayout, BorderLayout.SOUTH);

    this.add(menuBar, BorderLayout.PAGE_START);

    // as soon as View obj is "set-up", display a user's schedule before making View visible
    try {
      this.displayUserSchedule(model.allUsers().get(0));
      // if no users exist in scheduling system
    } catch (IndexOutOfBoundsException ignore) {
      // do nothing
    }
  }

  /**
   * Connects low-level events created by controls in GUI to high-level actions that affect rest
   * of codebase.
   *
   * @param features program-specific events in response to low-level events
   */
  @Override
  public void addFeatures(Features features) {
    this.features = features;
    // callback details handled internally within View
    // callbacks are now program-relevant commands (no JFrame dependence externally)
    // rather than callback being a class that needs to interpret JFrame specific code
    createEventButton.addActionListener(evt ->
            features.displayBlankEvent(currentUserDisplayed));
    scheduleEventButton.addActionListener(evt ->
            features.displayBlankScheduleEvent(currentUserDisplayed));
    userDropdown.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        currentUserDisplayed = (String) userDropdown.getSelectedItem();
        features.displayNewSchedule(currentUserDisplayed);
      }
    });
    uploadXML.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(SSFrame.this) == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          features.requestXMLScheduleUpload(selectedFile.getAbsolutePath());
        }
      }
    });
    downloadXMLs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showOpenDialog(SSFrame.this) == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          features.requestAllSchedulesDownload(selectedFile.getAbsolutePath());
        }
      }
    });
    panel.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent mouse) { // System.out.println(e.getX() + ", " + e.getY
        // ());
        //figuring out what day this click is at
        int dayAsRow = mouse.getX() / (panel.getWidth() / 7);
        DaysOfTheWeek day = DaysOfTheWeek.valToDay(dayAsRow);

        //figuring out what hours + minutes this click is at + translating into total minutes
        double minAsCol = ((double) mouse.getY() / (double) (panel.getHeight() / 24)) * 60;

        int hours = (int) minAsCol / 60;
        double minutes = minAsCol % 60;

        double militaryTime = 0;
        if (minutes == 0) {
          militaryTime = hours * 100;
        } else {
          militaryTime = (hours * 100) + minutes;
        }
        int finalTime = (int) militaryTime;

        System.out.println(militaryTime); //prints correct military time

        for (ReadableEvent event : SSFrame.this.model.eventsInSchedule(currentUserDisplayed)) {
          if (event.containsTime(day, finalTime)) {
            features.displayExistingEvent(currentUserDisplayed, event);
          }
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
        // not considered as relevant event
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        // not considered as relevant event
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        // not considered as relevant event
      }

      @Override
      public void mouseExited(MouseEvent e) {
        // not considered as relevant event
      }
    });

  }

  /**
   * Displays the Schedule of an existing user in the Scheduling System.
   * Schedule displayed is considered current user of system.
   *
   * @param user user in Scheduling System
   * @throws IllegalArgumentException   if user does not exist in scheduling system
   */
  @Override
  public void displayUserSchedule(String user) {
    System.out.println("Displaying new schedule... " + user);
    panel.displayUserSchedule(user);
    this.currentUserDisplayed = user;

    // one method two purpose?
  }

  /**
   * Makes GUI visible.
   */
  @Override
  public void makeVisible() {
    setVisible(true);
  }

  /**
   * Displays an empty Event-creation window for a user to interact with.
   *
   * @throws IllegalArgumentException      if no user schedule currently displayed by view
   */
  @Override
  public void displayBlankEvent() {
    if (currentUserDisplayed == null) {
      throw new IllegalArgumentException("Must select user first...");
    }

    EventGUIView eventView = new EventFrame(model, currentUserDisplayed);

    eventView.addFeatures(features);

    eventView.makeVisible();

  }

  /**
   * Displays an empty Event-scheduling window for a user to interact with.
   *
   * @throws IllegalArgumentException if a user's schedule is not currently displayed
   */
  @Override
  public void displayBlankScheduleEvent() {
    if (currentUserDisplayed == null) {
      throw new IllegalArgumentException("Must select user first...");
    }

    ScheduleEventGUIView scheduleView = new ScheduleEventFrame(model, currentUserDisplayed);

    scheduleView.addFeatures(features);

    scheduleView.makeVisible();
  }

  /**
   * Displays the details of the displayed Event that current user has selected.
   * (Events available within their schedule.)
   *
   * @param user  the current user
   * @param event @throws IllegalArgumentException if no user has been selected/ no schedule
   *              displayed
   */
  @Override
  public void displayExistingEvent(String user, ReadableEvent event) {
    if (!user.equals(currentUserDisplayed)) {
      throw new IllegalArgumentException("Logically not possible to throw this exception...  ");
    }

    EventGUIView eventView = new EventFrame(model, currentUserDisplayed);

    eventView.displayExistingEvent(user, event);

    eventView.addFeatures(features);

    eventView.makeVisible();

  }

  @Override
  public void displayErrorMessage(String errorMessage) {
    JOptionPane.showMessageDialog(null, errorMessage, "Error",
            JOptionPane.ERROR_MESSAGE);
    System.out.println("Cannot execute request based on user input... ");
  }

  /**
   * Refreshes relevant controls of View according to updated Scheduling System state.
   */
  @Override
  public void refresh() {
    List<String> jcomboCurrEntries = new ArrayList<>();
    for (int i = 0; i < userDropdown.getItemCount(); i++) {
      jcomboCurrEntries.add(userDropdown.getItemAt(i));
    }
    for (String user : this.model.allUsers()) {
      if (!jcomboCurrEntries.contains(user)) {
        this.userDropdown.addItem(user);
      }
    }
  }


}