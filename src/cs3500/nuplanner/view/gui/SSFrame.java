package cs3500.nuplanner.view.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.*;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.Event;
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
    createEventButton.addActionListener(evt -> features.displayBlankEvent());
    scheduleEventButton.addActionListener(evt -> features.displayBlankScheduleEvent());
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
        DaysOfTheWeek day = createDay(dayAsRow);

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
            features.requestExistingEventDetails(currentUserDisplayed, (Event) event);
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
   */
  @Override
  public void displayUserSchedule(String user) {
    System.out.println("Displaying new schedule... " + user);
    panel.displayUserSchedule(user);
    this.currentUserDisplayed = user;
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

  @Override
  public void displayBlankScheduleEvent() {
    if (currentUserDisplayed == null) {
      throw new IllegalArgumentException("Must select user first...");
    }

    ScheduleGUIView scheduleView = new ScheduleFrame(model, currentUserDisplayed);

    scheduleView.addFeatures(features);

    scheduleView.makeVisible();
  }

  /**
   * Displays the details of the displayed Event that current user has selected.
   * (Events available within their schedule.)
   *
   * @param user
   * @param event @throws IllegalArgumentException         if no user has been selected/ no schedule displayed
   */
  @Override
  public void displayExistingEvent(String user, Event event) {
    if (!user.equals(currentUserDisplayed)) {
      throw new IllegalArgumentException("Logically not possible to throw this exception...  ");
    }

    EventGUIView eventView = new EventFrame(model, currentUserDisplayed);

    eventView.displayExistingEvent(user, event);

    eventView.addFeatures(features);

    eventView.makeVisible();

  }


  /**
   * Converts provided an integer into a day of the week, if possible.
   *
   * @param day integer to convert into day
   * @return DaysOfTheWeek enum constant
   * @throws IllegalArgumentException if integer cannot be converted into a day
   */
  private DaysOfTheWeek createDay(int day) {
    if (day == SUNDAY.val()) {
      return SUNDAY;
    }
    if (day == MONDAY.val()) {
      return MONDAY;
    }
    if (day == TUESDAY.val()) {
      return TUESDAY;
    }
    if (day == WEDNESDAY.val()) {
      return WEDNESDAY;
    }
    if (day == THURSDAY.val()) {
      return THURSDAY;
    }
    if (day == FRIDAY.val()) {
      return FRIDAY;
    }
    if (day == SATURDAY.val()) {
      return SATURDAY;
    }
    throw new IllegalArgumentException("Not a day of the week!");
  }

}