package cs3500.nuplanner.model.hw05;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent time in military time format. time representation can change.
 * Used for Strategies only as proof of concept to encapsulate an Event's (day, time)!
 *
 * Will add for fun after OOD. Need to think wisely about Time class.
 */
// advantages of having a time interface/impl
// in theory, if I swap out this time-impl and enforce a new kind of time, codebase should still run

public class Time {

  private DaysOfTheWeek day;
  private int militaryTime;

  /**
   * Creates the time with a specific military format.
   *
   * @param time the time passed in
   */
  public Time(DaysOfTheWeek day, int time) {
    // perform checks to make sure time is valid
    ensureProperMilitaryTime(time);
    this.militaryTime = time;
    this.day = day;
  }

  /**
   * Creates a time with proper military format.
   *
   * @param time the time passed in
   */
  public void updateTime(int time) {
    // perform checks to make sure time is valid
    ensureProperMilitaryTime(time);
    this.militaryTime = time;
  }

  /**
   *
   * @return
   */
  public int time() {
    return this.militaryTime;
  }

  /**
   *
   * @param day
   */
  public void updateDay(DaysOfTheWeek day) {
    this.day = day;
  }

  /**
   *
   * @return
   */
  public DaysOfTheWeek day() {
    return this.day;
  }

  // very useful
  /**
   * Given the start-time and end-time (say, from some Event), will produce objective values
   * for start time and end time.
   *
   * Objective values defined as how many minutes from Sunday 0000 is the given Time.
   *
   * startTime and endTime both important, to determine if the endTime exceeds 1 week (not obvious
   * if only endTime given).
   */
  public static List<Integer> convertTimesToObjectivePair(Time startTime, Time endTime) {

    int sDv = startTime.day().val();
    int sT = startTime.time();

    int eDv = endTime.day().val();
    int eT = endTime.time();

    int startVal;
    int endVal;

    // event that extends into next week
    if (eDv - sDv < 0 || (eDv - sDv == 0 && eT <= sT)) {
      endVal = ((eDv + 7) * 60 * 24) + (eT / 100 * 60) + (eT % 100);
    } else {
      // event contained within first week
      endVal = (eDv * 60 * 24) + (eT / 100 * 60) + (eT % 100);
    }
    // start day always within first week
    startVal = (sDv * 60 * 24) + (sT / 100 * 60) + (sT % 100);

    return new ArrayList<Integer>(List.of(startVal, endVal));

  }

  /**
   * Inverse of {@code convertTimesToObjectivePair()}
   */
  public static List<Time> convertObjectivePairToTimes(int startObjTime, int endObjTime) {

    if (endObjTime > 7 * 24 * 60) {
      endObjTime -= 7 * 24 * 60;
    }

    // extract start day and time
    int sDv = startObjTime / (60 * 24);
    int sT = (((startObjTime % (60 * 24)) / 60) * 100) + (startObjTime % 60);

    // Extract end day and time
    int eDv = endObjTime / (60 * 24);
    int eT = (endObjTime % (60 * 24)) / 60 * 100 + (endObjTime % 60);

    // Create Time objects
    Time startTime = new Time(DaysOfTheWeek.valToDay(sDv), sT);
    Time endTime = new Time(DaysOfTheWeek.valToDay(eDv), eT);

    return new ArrayList<>(List.of(startTime, endTime));
  }

  //  /**
  //   * Tries to convert random input into time representation, if possible
  //   */
  //  public int convertInputToTime() {
  //    return 0;
  //  }

  private void ensureProperMilitaryTime(int time) {
    if (time / 100 < 0 || time / 100 > 23 || time % 100 > 59) {
      throw new IllegalArgumentException("Time cannot be init. with invalid military time...  ");
    }
  }


}
