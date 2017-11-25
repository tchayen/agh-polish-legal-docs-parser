package agh.iisg.lab.legal;

import java.util.Optional;

/**
 * Chapter
 * Corresponds to "Rozdział" written as
 * "Rozdział {index as roman number}\n{title}" where index starts at 1.
 */
public class Chapter extends LegalPartition {
  private Optional<String> number;
  private Optional<String> title;

  public Chapter(String rawContent) {
    super(rawContent);
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
}
