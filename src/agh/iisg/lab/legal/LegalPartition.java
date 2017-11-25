package agh.iisg.lab.legal;

import java.util.List;

public abstract class LegalPartition {
  protected final String rawContent;
  protected List<LegalPartition> partitions;

  public LegalPartition(String rawContent) {
    this.rawContent = rawContent;
  }

  public String getRawContent() {
    return rawContent;
  }

  public List<LegalPartition> getPartitions() {
    return partitions;
  }

  public void setPartitions(List<LegalPartition> partitions) {
    this.partitions = partitions;
  }
}
