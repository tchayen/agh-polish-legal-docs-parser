package agh.iisg.lab.legal;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Point
 * Corresponds to "Punkt" written as "{index as number})". Index is local to
 * the enclosing paragraph and starts at 1.
 */
public class Point extends LegalPartition implements Enumerable {

  /**
   * Match for number with enclosing parenthesis enumeration.
   */
  public static final Pattern regex = Pattern.compile("\n\\d+\\) ");

  private Optional<String> number;

  public Point() {
  }

  public String getNumber() {
    return number.orElse("");
  }

  public void setNumber(String number) {
    this.number = Optional.ofNullable(number);
  }

  @Override
  public Pattern regex() {
    return regex;
  }
}
