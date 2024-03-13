package cs3500.nuplanner.model.hw05;

public enum DaysOfTheWeek {

  SUNDAY(0, "SUNDAY"), MONDAY(1, "MONDAY"), TUESDAY(2, "TUESDAY"),
  WEDNESDAY(3, "WEDNESDAY"), THURSDAY(4, "THURSDAY"), FRIDAY(5, "FRIDAY"),
  SATURDAY(6, "SATURDAY");

  private final int dayVal;
  private final String dayString;

  private DaysOfTheWeek(int dayVal, String dayString) {
    this.dayVal = dayVal;
    this.dayString = dayString;
  }

  public int val() {
    return dayVal;
  }

  @Override
  public String toString() {return dayString;}

}