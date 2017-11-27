package agh.iisg.lab.legal;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Point
 * Corresponds to "Punkt" written as "{index as number})". Index is local to
 * the enclosing paragraph and starts at 1.
 */
public class Point extends LegalPartition {
  public static final Pattern split = Pattern.compile("\n\\d+\\) ");
  public static final Pattern matchTitle = Pattern.compile("\n\\d+\\) ");

  private Optional<String> number;

  public Point() {
  }

  @Override
  public Pattern split() {
    return split;
  }

  @Override
  public Pattern matchTitle() {
    return matchTitle;
  }

  public String getNumber() {
    return number.orElse("");
  }

  public void setNumber(String number) {
    this.number = Optional.ofNullable(number);
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public void setTitle(String Title) {

  }
}
