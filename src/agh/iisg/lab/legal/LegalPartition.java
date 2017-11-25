package agh.iisg.lab.legal;

import java.util.List;

public abstract class LegalPartition implements Legal {
  protected String rawContent;
  protected List<Legal> partitions;
  private String content;

  public LegalPartition() {
  }

  public LegalPartition(String rawContent) {
    this.rawContent = rawContent;
  }

  public String getRawContent() {
    return rawContent;
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
