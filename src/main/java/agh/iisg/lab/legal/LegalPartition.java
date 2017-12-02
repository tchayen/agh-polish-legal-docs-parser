package agh.iisg.lab.legal;

import java.util.List;
import java.util.Optional;

public class LegalPartition implements Legal {
  protected String content;
  protected List<Legal> partitions;
  private Optional<String> number;
  private Optional<String> title;

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

  public String getNumber() {
    return number.orElse("");
  }

  public void setNumber(String number) {
    this.number = Optional.ofNullable(number);
  }

  public String getTitle() {
    return title.orElse("");
  }

  public void setTitle(String title) {
    this.title = Optional.ofNullable(title);
  }

  public String toString() {
    return content;
  }
}
