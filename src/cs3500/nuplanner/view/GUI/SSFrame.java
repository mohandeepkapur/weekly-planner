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

  // all components that exist within frame
  private JButton createEventButton;
  private JButton scheduleEventButton;
  private JComboBox<String> userDropdown;
  private JMenuItem uploadXML;
  private JMenuItem downloadXMLs;
  private JButton fileChooserButton;

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
    // callbacks for components are program-relevant commands (no JFrame dependence)
    // rather than callback being a class that needs to interpret JFrame specific code
    createEventButton.addActionListener(evt -> features.requestCreateEvent());
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
        if (fileChooser.showOpenDialog(SSFrame.this) == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          features.requestScheduleDownload(selectedFile.getAbsolutePath());
        }
      }
    });


  }

  @Override
  public void displayNewSchedule(String user) {
    System.out.println("Displaying new schedule... " + user);
    panel.displayNewSchedule(user);
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void displayEventGUIView() {
    EventGUIView eventView = new EventFrame(model);
    eventView.makeVisible();
  }

}
