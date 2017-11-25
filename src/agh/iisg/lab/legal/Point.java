package agh.iisg.lab.legal;

import java.util.List;

/**
 * Point
 * Corresponds to "Punkt" written as "1)".
 */
public class Point extends LegalPartition {
  public Point(String rawContent) {
    super(rawContent);
  }

  public List<LegalPartition> getLetters() {
    return partitions;
  }
}
