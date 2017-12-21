package agh.iisg.lab;

import java.util.List;
import java.util.Optional;

public class LegalPartition {
  protected String content;
  protected List<LegalPartition> partitions;
  private Optional<String> number;
  private Optional<String> title;
//  Map<String, Legal>

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

  public List<LegalPartition> getPartitions() {
    return partitions;
  }

  public void setPartitions(List<LegalPartition> partitions) {
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
