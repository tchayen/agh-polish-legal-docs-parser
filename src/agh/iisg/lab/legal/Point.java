package agh.iisg.lab.legal;

import java.util.Optional;

/**
 * Point
 * Corresponds to "Punkt" written as "{index as number})". Index is local to
 * the enclosing paragraph and starts at 1.
 */
public class Point extends LegalPartition {
  private Optional<String> number;

  public Point(String number, String rawContent) {
    super(rawContent);
    this.number = Optional.ofNullable(number);
  }

  public String getNumber() {
    return number.orElse("");
  }

  public void setNumber(String number) {
    this.number = Optional.ofNullable(number);
  }
}
