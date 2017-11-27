package agh.iisg.lab.legal;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Letter
 * Corresponds to "Litera" written as "{index as lowercase letter})" where
 * letter corresponds to roman alphabet letter at index (starting at 1).
 * Index is local to the enclosing point.
 */
public class Letter extends LegalPartition {
  public static final Pattern split = Pattern.compile("\n[a-z]\\) ");
  public static final Pattern matchTitle = Pattern.compile("\n[a-z]\\) ");

  private Optional<String> number;

  public Letter() {
  }

  @Override
  public Pattern split() {
    return split;
  }

  @Override
  public Pattern matchTitle() {
    return matchTitle;
  }

  @Override
  public String getNumber() {
    return number.orElse("");
  }

  @Override
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
