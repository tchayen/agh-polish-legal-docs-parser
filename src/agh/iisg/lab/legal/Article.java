package agh.iisg.lab.legal;

import java.util.Optional;

/**
 * Article
 * Corresponds to "Article" written as "Art. {index as number}" where index
 * starts at 1 and is global.
 */
public class Article extends LegalPartition {
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

}
