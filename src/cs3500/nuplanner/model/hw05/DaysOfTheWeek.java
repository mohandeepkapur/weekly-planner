package cs3500.nuplanner.model.hw05;

public enum DaysOfTheWeek {

  SUNDAY("SUNDAY"), MONDAY("MONDAY"), TUESDAY("TUESDAY"),
  WEDNESDAY("WEDNESDAY"), THURSDAY("THURSDAY"), FRIDAY("FRIDAY"),
  SATURDAY("SATURDAY");

  private final String day;

  private DaysOfTheWeek(String day) {
    this.day = day;
  }

  public String toString() {
    return day;
  }

}
