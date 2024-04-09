package cs3500.nuplanner.view;

import java.io.IOException;

/**
 * Operations necessary for a Schedule to be viewed by users.
 */
public interface SchedulingSystemView {

  /**
   * Renders view of a user's schedule. Implementation may assume default path provided if none
   * given.
   *
   * @throws IOException                if unable to render view
   */
  void render(String user) throws IOException;

  /**
   * Renders view of a user's schedule. Saves rendering to provided path. Implementation may assume
   * default path even if one provided.
   *
   * @throws IOException                if unable to render view
   */
  void render(String user, String pathname) throws IOException;


}
