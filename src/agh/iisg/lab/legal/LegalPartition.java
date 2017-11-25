package agh.iisg.lab.legal;

import java.util.List;

public abstract class LegalPartition {
  protected final String rawContent;
  protected List<? extends LegalPartition> partitions;

  public LegalPartition(String rawContent) {
    this.rawContent = rawContent;
  }

  public String getRawContent() {
    return rawContent;
  }

  public List<? extends LegalPartition> getPartitions() {
    return partitions;
  }

  public void setPartitions(List<? extends LegalPartition> partitions) {
    this.partitions = partitions;
  }
}
