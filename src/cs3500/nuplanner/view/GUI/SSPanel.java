package cs3500.nuplanner.view.GUI;

import java.awt.*;

import javax.swing.*;

import cs3500.nuplanner.model.hw05.ReadableSchedulingSystem;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

public class SSPanel extends JPanel {

  private final ReadableSchedulingSystem model;

  public SSPanel(ReadableSchedulingSystem model) {
    super();
    this.model = model;
  }

  public void displayUserSchedule(String user) {



  }

  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    //drawing the lines for the scheduling system
    for (int i = 0; i < 7; i++) {
      g2d.drawLine(this.getWidth() / 7 * i, 0, this.getWidth() / 7 * i, this.getHeight());
    }
    for (int i = 0; i < 24; i++) {
      if (i % 4 != 0) {
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(0, this.getHeight() / 24 * i, this.getWidth(), this.getHeight() / 24 * i);
      } else {
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(0, this.getHeight() / 24 * i, this.getWidth(), this.getHeight() / 24 * i);
      }
    }

    int dayStart = (this.getWidth() / 7) - (this.getWidth() / 7);
    int hourStart = (this.getHeight() / 24) * 9 - (this.getHeight() / 24);
    int dayEnd = (this.getWidth() / 7);
    int lengthOfEvent = (int) ((this.getHeight() / 24) * 2.5);

    //setting the transparent color
    int alpha = 127; // 50% transparent
    Color transparentRed = new Color(255, 1, 1, alpha);
    g2d.setColor(transparentRed);

    //drawing the rectangle
    drawARectangle(g2d, dayStart, hourStart, dayEnd, lengthOfEvent);
  }

  private static void drawARectangle(Graphics2D g2d, int startDay, int startHour, int endDay,
                                     int endHour) {
    g2d.drawRect(startDay, startHour, endDay, endHour);
    g2d.fillRect(startDay, startHour, endDay, endHour);
  }


//  public void addClickListener(TicTacToeController controller) {
//    this.addMouseListener(new MouseListener() {
//      @Override
//      public void mouseClicked(MouseEvent e) {
//        System.out.println(e.getX() + ", " + e.getY());
//        TTTPanel panel = TTTPanel.this;
////        int row = e.getY() / (panel.getHeight() / 3) + 1;
////        int height = e.getX() / (panel.getWidth() / 3) + 1;
//
//        //figuring out what hours or minutes this click is at
//        int row = e.getY() / (panel.getHeight() / 24) + 1;
//        int height = e.getX() / (panel.getWidth() / 7) + 1;
//
//        controller.handleCellClick(row, height);
//      }
//
//      @Override
//      public void mousePressed(MouseEvent e) {
//
//      }
//
//      @Override
//      public void mouseReleased(MouseEvent e) {
//
//      }
//
//      @Override
//      public void mouseEntered(MouseEvent e) {
//
//      }
//
//      @Override
//      public void mouseExited(MouseEvent e) {
//
//      }
//    });
//  }

}
