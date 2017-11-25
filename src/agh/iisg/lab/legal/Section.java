package agh.iisg.lab.legal;

import java.util.List;

public class Section extends LegalPartition {
  public Section(String rawContent) {
    super(rawContent);
  }

  public List<LegalPartition> getParagraphs() {
    return partitions;
  }
}
