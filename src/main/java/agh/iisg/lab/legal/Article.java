package agh.iisg.lab.legal;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Article
 * Corresponds to "Artykuł" written as "Art. {index as number}" where index
 * starts at 1 and is global.
 */
public class Article extends LegalPartition {
  public static final Pattern split = Pattern.compile("Art\\. \\d+\\.\n");
  public static final Pattern matchTitle = Pattern.compile("Art\\. \\d+\\.\n");

  private Optional<String> number;

  public Article() {
  }

  public String getNumber() {
    return number.orElse("");
  }

  public void setNumber(String number) {
    this.number = Optional.ofNullable(number);
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public void setTitle(String Title) {

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
  public String getContent() {
    return String.format("Art. %s.\n%s", number.get(), content);
  }
}
