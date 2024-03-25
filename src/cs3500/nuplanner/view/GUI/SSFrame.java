package cs3500.nuplanner.view.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.*;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.DaysOfTheWeek;
import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;

import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.FRIDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.MONDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SATURDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.SUNDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.THURSDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.TUESDAY;
import static cs3500.nuplanner.model.hw05.DaysOfTheWeek.WEDNESDAY;

public class SSFrame extends JFrame implements SSGUIView {

  private ReadableSchedulingSystem model;
  private SSPanel panel;
  private Features features; // means both frames must share same Features obj

  // all components that exist within frame
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JComboBox<String> userDropdown;
  private JMenuItem uploadXML;
  private JMenuItem downloadXMLs;
  private JButton fileChooserButton;

  private String currentUserDisplayed;

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

    this.fileChooserButton = new JButton("Open File");

    JPanel buttonLayout = new JPanel(new FlowLayout());
    buttonLayout.add("createEvent", createEventButton);
    buttonLayout.add("scheduleEvent", scheduleEventButton);
    buttonLayout.add("userDropdown", userDropdown);
    panel.add(buttonLayout, BorderLayout.SOUTH);

    this.add(menuBar, BorderLayout.PAGE_START);

    // as soon as View obj is "set-up", display a user's schedule before making View visible
    this.displayUserSchedule(model.allUsers().get(0));
  }

  @Override
  public void addFeatures(Features features) {
    this.features = features;
    // callback details handled internally within View
    // callbacks are now program-relevant commands (no JFrame dependence externally)
    // rather than callback being a class that needs to interpret JFrame specific code
    createEventButton.addActionListener(evt -> features.displayBlankEvent());
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
      public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX() + ", " + e.getY());

        //figuring out what hours or minutes this click is at
        int row = e.getX() / (panel.getWidth() / 7);
        DaysOfTheWeek day = createDay(row);

        int col = e.getY() / (panel.getHeight() / 24 / 6); // 10 minute increments

        int hours = col / 6;
        int minutes = col % 6 * 10;
        int militarytime;
        if (minutes == 0) militarytime = hours*100;
        else militarytime = (hours*100)+minutes;

        System.out.println("current user displayed " + currentUserDisplayed + " col " + col);
        features.requestExistingEventDetails(day, militarytime);
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
   * Converts provided an integer into a day of the week, if possible.
   *
   * @param day integer to convert into day
   * @return DaysOfTheWeek enum constant
   * @throws IllegalArgumentException if string cannot be converted into a day
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

  @Override
  public void displayUserSchedule(String user) {
    System.out.println("Displaying new schedule... " + user);
    panel.displayNewSchedule(user);
    this.currentUserDisplayed = user;
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void displayEmptyEventWindow() {
    if (currentUserDisplayed == null) throw new IllegalArgumentException("must select user first");
    EventGUIView eventView = new EventFrame(model, currentUserDisplayed);
    eventView.addFeatures(features);
    eventView.makeVisible();
  }

  @Override
  public void displayFilledEventWindow(DaysOfTheWeek day, int time) { // event exist within scheduling system <- no need to check if null <- why?
    // extract relevant user schedule
    List<ReadableEvent> userEvents = model.eventsInSchedule(currentUserDisplayed);

    // if clicked day/time did land on a user's event, display those event details
    for (ReadableEvent event : userEvents) {
      System.out.println("ran");
      if (event.containsTime(day, time)) {
        System.out.println("success");
        openEventWindowWithFilledDetails(event); // think about this...
      }
    }

  }

  private void openEventWindowWithFilledDetails(ReadableEvent event) {
    EventGUIView eventView = new EventFrame(model, currentUserDisplayed);
    eventView.addFeatures(features);
    eventView.displayName(event.name());
    eventView.displayLocation(event.location());
    eventView.displayIsOnline(String.valueOf(event.isOnline()));
    eventView.displayStartDay(event.startDay().toString());
    eventView.displayStartTime(String.valueOf(event.startTime()));
    eventView.displayEndDay(event.endDay().toString());
    eventView.displayEndTime(String.valueOf(event.endTime()));
    eventView.makeVisible();
  }

}