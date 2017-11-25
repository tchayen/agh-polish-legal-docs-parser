package agh.iisg.lab.legal;

import java.util.List;

/**
 * Chapter
 * Corresponds to "Rozdział" written as
 * "Rozdział {index as roman number}\n{title}" where index starts at 1.
 */
public class Chapter extends LegalPartition {
  private String number;
  private String title;

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
