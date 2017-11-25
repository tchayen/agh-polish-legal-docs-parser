package agh.iisg.lab.legal;

import java.util.Optional;

/**
 * Paragraph
 * Corresponds to "Ustęp" written as "{index as number}.". Index is local to
 * the enclosing section and starts at 1.
 */
public class Paragraph extends LegalPartition implements Enumerable {
  private Optional<String> number;

  public Paragraph() {
  }

  public Paragraph(String number, String rawContent) {
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
