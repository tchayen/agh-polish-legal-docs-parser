package agh.iisg.lab.legal;

import java.util.Optional;

/**
 * Corresponds to "Oddział" written as "Oddział {index as Number}\n{title}"
 * where index starts at 1 and is local to the enclosing chapter. The first
 * line is optional.
 */
public class Section extends LegalPartition {
  private Optional<String> title;

  public Section(String rawContent) {
    super(rawContent);
  }

  public String getTitle() {
    return title.orElse("");
  }

  public void setTitle(String title) {
    this.title = Optional.ofNullable(title);
  }
}
