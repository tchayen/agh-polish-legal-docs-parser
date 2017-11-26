package agh.iisg.lab.legal;

import java.util.regex.Pattern;

/**
 * Indent
 * Corresponds to "Tiret" written as "-".
 */
public class Indent extends LegalPartition {
  public Indent(String rawContent) {
    super(rawContent);
  }

  @Override
  public Pattern regex() {
    return null;
  }
}
