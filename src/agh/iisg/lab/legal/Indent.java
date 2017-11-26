package agh.iisg.lab.legal;

import java.util.regex.Pattern;

/**
 * Indent
 * Corresponds to "Tiret" written as "-".
 */
public class Indent extends LegalPartition {
  /**
   * Match for dash enumeration.
   */
  public static final Pattern regex = Pattern.compile("\n- ");

  public Indent() {
  }

  @Override
  public Pattern regex() {
    return regex;
  }

  @Override
  public String getNumber() {
    return null;
  }

  @Override
  public void setNumber(String number) {
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public void setTitle(String Title) {
  }
}
