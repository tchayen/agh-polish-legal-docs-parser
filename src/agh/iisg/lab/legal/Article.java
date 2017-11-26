package agh.iisg.lab.legal;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Article
 * Corresponds to "Artykuł" written as "Art. {index as number}" where index
 * starts at 1 and is global.
 */
public class Article extends LegalPartition implements Enumerable {
  /**
   * Match article prefix.
   * NOTE: index is intentionally left to make parsing easier.
   */
  public static final Pattern regex = Pattern.compile("Art\\. ");

  private Optional<String> number;

  public Article(String number, String rawContent) {
    super(rawContent);
    this.number = Optional.ofNullable(number);
  }

  public String getNumber() {
    return number.orElse("");
  }

  public void setNumber(String number) {
    this.number = Optional.ofNullable(number);
  }

  @Override
  public Pattern regex() {
    return regex;
  }
}
