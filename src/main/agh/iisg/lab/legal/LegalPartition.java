package agh.iisg.lab.legal;

import java.util.List;

public abstract class LegalPartition implements Legal {
  protected String content;
  protected List<Legal> partitions;

  public LegalPartition() {
  }

  public LegalPartition(String rawContent) {
    this.content = rawContent;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<Legal> getPartitions() {
    return partitions;
  }

  public void setPartitions(List<Legal> partitions) {
    this.partitions = partitions;
  }
}
