package agh.iisg.lab.legal;

import agh.iisg.lab.Constraints;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Chapter
 * Corresponds to "Rozdział" written as
 * "Rozdział {index as roman number}\n{title}" where index starts at 1.
 */
public class Law extends LegalPartition {
  public static final Pattern split = Pattern.compile("\n(?=Rozdział [IVX]+\n" + Constraints.UPPERCASE_WORD_REGEX + "\n)");
  public static final Pattern matchTitle = Pattern.compile("Rozdział [IVX]+\n" + Constraints.UPPERCASE_WORD_REGEX + "\n");

  private Optional<String> number;
  private Optional<String> title;

  public Law(String rawContent) {
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
