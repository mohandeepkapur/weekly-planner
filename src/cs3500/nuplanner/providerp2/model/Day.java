package cs3500.nuplanner.providerp2.model;

/**
 * Creates the enumeration for the seven days of the week.
 */
public enum Day {
  SUNDAY, // ordinal 0
  MONDAY, // ordinal 1
  TUESDAY, // ordinal 2
  WEDNESDAY,// ordinal 3
  THURSDAY, // ordinal 4
  FRIDAY, // ordinal 5
  SATURDAY; // ordinal 6

  /**
   * Creates the to string method that returns the passed in day of the
   * week in string form.
   * @return a string of the current day
   */
  public String toString() {
    switch (this) {
      case SUNDAY:
        return "Sunday";
      case MONDAY:
        return "Monday";
      case TUESDAY:
        return "Tuesday";
      case WEDNESDAY:
        return "Wednesday";
      case THURSDAY:
        return "Thursday";
      case FRIDAY:
        return "Friday";
      case SATURDAY:
        return "Saturday";
      default:
        throw new IllegalStateException("Not a valid day:" + this);
    }
  }

  /**
   * This method converts the given string to the Day type.
   * It is used inside the view package, inside the eventFrame class.
   * @param s string that is given to this method
   * @return Day
   */
  public static Day toDay(String s) {
    switch (s) {
      case "Sunday":
        return SUNDAY;
      case "Monday":
        return MONDAY;
      case "Tuesday":
        return TUESDAY;
      case "Wednesday":
        return WEDNESDAY;
      case "Thursday":
        return THURSDAY;
      case "Friday":
        return FRIDAY;
      case "Saturday":
        return SATURDAY;
      default:
        throw new IllegalArgumentException("Not a valid string");
    }
  }
}
