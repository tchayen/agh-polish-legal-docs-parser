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
  public static final Pattern split = Pattern.compile("\n(?=[A-zĘęÓóĄąŚśŁłŻżŹźĆćŃń, ]+\n)");
  public static final Pattern matchTitle = Pattern.compile("^[A-zĘęÓóĄąŚśŁłŻżŹźĆćŃń, ]+\n");

  private Optional<String> title;

  public Section() {
  }

  public Section(String rawContent) {
    super(rawContent);
  }

  public String getTitle() {
    return title.orElse("");
  }

  public void setTitle(String title) {
    this.title = Optional.ofNullable(title);
  }

  @Override
  public Pattern split() {
    return split;
  }

  @Override
  public Pattern matchTitle() {
    return matchTitle;
  }

  @Override
  public String getNumber() {
    return null;
  }

  @Override
  public void setNumber(String number) {
  }
}
