package cs3500.nuplanner.view.GUI;

public class GUIView implements IGUIView {

  private SSGUIView ssView;
  private EventGUIView eventView;

  public GUIView(SSGUIView ssView, EventGUIView eventGUIView) {
    this.ssView = ssView;
    this.eventView = eventGUIView;
  }

  @Override
  public SSGUIView accessSSPortion() {
    return this.ssView;
  }

  @Override
  public EventGUIView accessEventPortion() {
    return this.eventView;
  }
}
