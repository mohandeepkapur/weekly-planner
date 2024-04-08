package cs3500.nuplanner.model.hw05;

/**
 * Represents a day of the week.
 *
 * @implNote integers assigned to each day are used for temporal comparison
 */
public enum DaysOfTheWeek {

  SUNDAY(0, "SUNDAY"), MONDAY(1, "MONDAY"),
  TUESDAY(2, "TUESDAY"), WEDNESDAY(3, "WEDNESDAY"),
  THURSDAY(4, "THURSDAY"), FRIDAY(5, "FRIDAY"),
  SATURDAY(6, "SATURDAY");

  private final int dayVal;
  private final String dayString;

  DaysOfTheWeek(int dayVal, String dayString) {
    this.dayVal = dayVal;
    this.dayString = dayString;
  }

  public int val() {
    return dayVal;
  }

  @Override
  public String toString() {
    return dayString;
  }

  /**
   * Integer to DaysOfTheWeek Factory.
   *
   * @param dayRep      int representing day in DaysOfTheWeek
   * @return            DaysOfTheWeek day
   */
  public static DaysOfTheWeek valToDay(int dayRep) {
    if (DaysOfTheWeek.SUNDAY.val() == dayRep) {
      return DaysOfTheWeek.SUNDAY;
    }
    else if (DaysOfTheWeek.MONDAY.val() == dayRep) {
      return DaysOfTheWeek.MONDAY;
    }
    else if (DaysOfTheWeek.TUESDAY.val() == dayRep) {
      return DaysOfTheWeek.TUESDAY;
    }
    else if (DaysOfTheWeek.WEDNESDAY.val() == dayRep) {
      return DaysOfTheWeek.WEDNESDAY;
    }
    else if (DaysOfTheWeek.THURSDAY.val() == dayRep) {
      return DaysOfTheWeek.THURSDAY;
    }
    else if (DaysOfTheWeek.FRIDAY.val() == dayRep) {
      return DaysOfTheWeek.FRIDAY;
    }
    else if (DaysOfTheWeek.SATURDAY.val() == dayRep) {
      return DaysOfTheWeek.SATURDAY;
    } else {
      throw new IllegalArgumentException("No DaysOfTheWeek day is represented by given value... ");
    }
  }

  /**
   * String to DaysOfTheWeek Factory.
   *
   * @param dayRep      string representing day in DaysOfTheWeek
   * @return            DaysOfTheWeek day
   */
  public static DaysOfTheWeek stringToDay(String dayRep) {
    if (DaysOfTheWeek.SUNDAY.toString().equals(dayRep)) {
      return DaysOfTheWeek.SUNDAY;
    }
    else if (DaysOfTheWeek.MONDAY.toString().equals(dayRep)) {
      return DaysOfTheWeek.MONDAY;
    }
    else if (DaysOfTheWeek.TUESDAY.toString().equals(dayRep)) {
      return DaysOfTheWeek.TUESDAY;
    }
    else if (DaysOfTheWeek.WEDNESDAY.toString().equals(dayRep)) {
      return DaysOfTheWeek.WEDNESDAY;
    }
    else if (DaysOfTheWeek.THURSDAY.toString().equals(dayRep)) {
      return DaysOfTheWeek.THURSDAY;
    }
    else if (DaysOfTheWeek.FRIDAY.toString().equals(dayRep)) {
      return DaysOfTheWeek.FRIDAY;
    }
    else if (DaysOfTheWeek.SATURDAY.toString().equals(dayRep)) {
      return DaysOfTheWeek.SATURDAY;
    } else {
      throw new IllegalArgumentException("No DaysOfTheWeek day is represented by given value... ");
    }
  }

}