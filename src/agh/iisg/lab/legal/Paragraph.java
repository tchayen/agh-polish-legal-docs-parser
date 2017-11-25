package agh.iisg.lab.legal;

import java.util.List;

/**
 * Paragraph
 * Corresponds to "Ustęp" written as "{index as number}.". Index is local to
 * the enclosing section and starts at 1.
 */
public class Paragraph extends LegalPartition {
  public Paragraph(String rawContent) {
    super(rawContent);
  }

  public List<LegalPartition> getPoints() {
    return partitions;
  }
}
