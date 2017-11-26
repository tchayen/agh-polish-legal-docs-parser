package agh.iisg.lab.legal;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Paragraph
 * Corresponds to "Ustęp" written as "{index as number}.". Index is local to
 * the enclosing section and starts at 1.
 */
public class Paragraph extends LegalPartition implements Enumerable {
  /**
   * Match for number enumeration.
   */
  public static final Pattern regex = Pattern.compile("\n\\d+\\. ");

  private Optional<String> number;

  public Paragraph() {
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
