package agh.iisg.lab.legal;

import java.util.regex.Pattern;

/**
 * Indent
 * Corresponds to "Tiret" written as "-".
 */
public class Indent extends LegalPartition {
  public static final Pattern split = Pattern.compile("\n- ");
  public static final Pattern matchTitle = Pattern.compile("\n- ");

  public Indent() {
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
