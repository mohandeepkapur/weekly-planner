package cs3500.nuplanner.view.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import cs3500.nuplanner.controller.Features;
import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;

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

    this.makeVisible();
  }

  @Override
  public void addFeatures(Features features) {
    this.features = features;

    // callbacks are now program-relevant commands (no JFrame dependence externally)
    // rather than callback being a class that needs to interpret JFrame specific code
    createEventButton.addActionListener(evt -> features.displayBlankEvent());
    userDropdown.addActionListener(evt -> features
            .displayNewSchedule((String) userDropdown.getSelectedItem()));
    uploadXML.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(SSFrame.this) == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          features.requestScheduleUpload(selectedFile.getAbsolutePath());
        }
      }
    });
    downloadXMLs.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showOpenDialog(SSFrame.this) == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          features.requestScheduleDownload(selectedFile.getAbsolutePath());
        }
      }
    });
    // callback to display specific event details --> request from controller --> controller calls displayFilledEvent
    /*
    if panel touched
    read touch, send day and time and user to features
    if event valid, features will open up filled event window
     */
  }

  @Override
  public void displayNewSchedule(String user) {
    System.out.println("Displaying new schedule... " + user);
    panel.displayNewSchedule(user);
    this.currentUserDisplayed = user;
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override // oh mannnn, adding public methods to suit my implementation.... bad. never let the how influence the what
  public void displayBlankEvent() {
    if (currentUserDisplayed == null) throw new IllegalArgumentException("must select user first");
    EventGUIView eventView = new EventFrame(model, currentUserDisplayed);
    eventView.addFeatures(features);
    eventView.makeVisible();
  }

  @Override
  public void displayFilledEventWindow() { //event details
    // make event frame
    // set all event details
    // addFeatures
    // make event frame visible
  }

}