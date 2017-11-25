package agh.iisg.lab.legal;

import java.util.List;

/**
 * Chapter
 * Corresponds to "Rozdział" written as "Rozdział I".
 */
public class Chapter extends LegalPartition {
  private String number;

  public Chapter(String rawContent) {
    super(rawContent);
  }

  public List<LegalPartition> getArticles() {
    return partitions;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}
