package agh.iisg.lab.legal;

import java.util.List;

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

  public List<LegalPartition> getIndents() {
    return partitions;
  }
}
