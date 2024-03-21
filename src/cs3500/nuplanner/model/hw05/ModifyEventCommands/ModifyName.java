package cs3500.nuplanner.model.hw05.ModifyEventCommands;

import cs3500.nuplanner.model.hw05.Event;
import cs3500.nuplanner.model.hw05.SchedulingSystem;

public class ModifyName implements ModifyEventCommand {

  private final String name;

  public ModifyName(String name) {
    this.name = name;
  }

  @Override
  public void modify(Event event, SchedulingSystem model) {
    event.updateName(this.name);
  }

}
