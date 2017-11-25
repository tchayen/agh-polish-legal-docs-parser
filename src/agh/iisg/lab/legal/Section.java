package agh.iisg.lab.legal;

import java.util.List;

/**
 * Corresponds to "Oddział" written as "Oddział {index as Number}\n{title}"
 * where index starts at 1 and is local to the enclosing chapter. The first
 * line is optional.
 */
public class Section extends LegalPartition {
  private String title;

  public Section(String rawContent) {
    super(rawContent);
  }

  public List<LegalPartition> getParagraphs() {
    return partitions;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
