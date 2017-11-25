package agh.iisg.lab.legal;

import java.util.List;

/**
 * Paragraph
 * Corresponds to "Ustęp" written as "1.".
 */
public class Paragraph extends LegalPartition {
  public Paragraph(String rawContent) {
    super(rawContent);
  }

  public List<LegalPartition> getPoints() {
    return partitions;
  }
}
