package agh.iisg.lab.legal;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Corresponds to "Oddział" written as "Oddział {index as Number}\n{title}"
 * where index starts at 1 and is local to the enclosing chapter. The first
 * line is optional.
 */
public class Section extends LegalPartition {
  /**
   * Match title written in uppercase.
   */
  public static final Pattern regex = Pattern.compile("[A-ZĘÓĄŚŁŻŹĆŃ ]+\n");

  private Optional<String> title;

  public Section() {
  }

  public Section(String rawContent) {
    super(rawContent);
  }

  @Override
  public Pattern regex() {
    return regex;
  }

  public String getTitle() {
    return title.orElse("");
  }

  public void setTitle(String title) {
    this.title = Optional.ofNullable(title);
  }

  @Override
  public String getNumber() {
    return null;
  }

  @Override
  public void setNumber(String number) {
  }
}
