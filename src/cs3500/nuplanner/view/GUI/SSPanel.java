package cs3500.nuplanner.view.GUI;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import javax.swing.*;

import cs3500.nuplanner.model.hw05.ReadableEvent;
import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;

public class SSPanel extends JPanel {

  private final ReadableSchedulingSystem model;
  private List<ReadableEvent> currUserSched; //what if extracted entire schedule instead

  public SSPanel(ReadableSchedulingSystem model) {
    super();
    this.model = model;
    this.currUserSched = new ArrayList<>();
  }

  public void displayNewSchedule(String user) {
    this.currUserSched = this.model.eventsInSchedule(user);
    this.repaint();
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    int alpha = 127; // 50% transparent
    Color transparentRed = new Color(217, 110, 110, alpha);
    g2d.setColor(transparentRed);

    // painting every event in user's schedule
    for (ReadableEvent event : currUserSched) {
      // objective values important
      List<Integer> objValues = extractObjectiveValues(event);
      drawEventBlock(g2d, objValues.get(0), objValues.get(1));
    }

    int alpha2 = 127; // 50% transparent
    Color transparentRed2 = new Color(0, 0, 0, alpha2);
    g2d.setColor(transparentRed2);

    // painting scheduling system grid (partitioned 7 days horizontally, 24 hours vertically)
    // vertical lines: each day in week
    for (int i = 0; i < 7; i++) {
      g2d.drawLine(this.getWidth() / 7 * i, 0,
              this.getWidth() / 7 * i, this.getHeight());
    }

    // horizontal lines: each hour in day
    for (int i = 0; i < 24; i++) {
      if (i % 4 != 0) {
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(0, this.getHeight() / 24 * i,
                this.getWidth(), this.getHeight() / 24 * i);
      } else {
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(0, this.getHeight() / 24 * i,
                this.getWidth(), this.getHeight() / 24 * i);
      }
    }

  }

  private void drawEventBlock(Graphics2D g2d, int startVal, int endVal) {

    // for every smallest time-block, ask whether it belongs within event
    // if so, paint it!
    // do for every smallest-even partition in grid

    for(int i = 0; i < 7*24*60; i++) {
      if ( i >= startVal && i+1 <= endVal ) {
        drawSmallestEventUnit(g2d, i, i+1);
      }
    }
  }

  private void drawSmallestEventUnit(Graphics2D g2d, int startVal, int endVal) {

    //convert two objective values back into days

    int startDay = (startVal / (24 * 60));
    int startHours = (startVal % (24 * 60)) / 60;
    int startMin = (startVal % (24 * 60) % 60);
    int minFromDayStart = (startHours * 60) + startMin;
    int startTime = (startHours * 100) + startMin; // military time

    //System.out.println("schedule has event that includes time: "+startTime+" to " + startTime+1);

    Rectangle2D rect = new Rectangle2D.Double(
            (int) ((double) this.getWidth() / 7) * startDay,
            ((double) this.getHeight() / (24 * 60)) * minFromDayStart,
            (int) ((double) this.getWidth() / 7),
            (double) this.getHeight() / (24 * 60)); // change

    g2d.fill(rect);

  }

  // duplicate code
  private List<Integer> extractObjectiveValues(ReadableEvent event) {
    int sDv = event.startDay().val();
    int sT = event.startTime();

    int eDv = event.endDay().val();
    int eT = event.endTime();

    int startVal;
    int endVal;

    // event that extends into next week
    if (eDv - sDv < 0 || (eDv - sDv == 0 && eT <= sT)) {
      endVal = ((eDv + 7) * 60 * 24) + (eT / 100 * 60) + (eT % 100);
    } else {
      // event contained within first week
      endVal = (eDv * 60 * 24) + (eT / 100 * 60) + (eT % 100);
    }
    // start day always within first week
    startVal = (sDv * 60 * 24) + (sT / 100 * 60) + (sT % 100);

    return new ArrayList<Integer>(List.of(startVal, endVal));

  }

}
