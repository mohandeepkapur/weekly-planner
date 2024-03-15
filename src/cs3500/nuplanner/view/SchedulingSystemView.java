package cs3500.nuplanner.view;

import java.io.IOException;

/**
 * Operations necessary for a Schedule to be viewed by users.
 */
public interface SchedulingSystemView {

  /**
   * Renders view of a user's schedule.
   */
  void render(String user) throws IOException;


}
