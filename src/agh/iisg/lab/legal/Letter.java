package agh.iisg.lab.legal;

import java.util.List;

/**
 * Letter
 * Corresponds to "Litera" written as "a)".
 */
public class Letter extends LegalPartition {

  public Letter(String rawContent) {
    super(rawContent);
  }

  public List<LegalPartition> getIndents() {
    return partitions;
  }
}
