package cs3500.nuplanner.model.hw05.ModifyEventCommands;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

public interface ModifyEventCommand {
  void modify(Event event, SchedulingSystem model);
}
