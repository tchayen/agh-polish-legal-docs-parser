package agh.iisg.lab.legal;

/**
 * Letter
 * Corresponds to "Litera" written as "{index as lowercase letter})" where
 * letter corresponds to roman alphabet letter at index (starting at 1).
 * Index is local to the enclosing point.
 */
public class Letter extends LegalPartition {
  public Letter(String rawContent) {
    super(rawContent);
  }
}
