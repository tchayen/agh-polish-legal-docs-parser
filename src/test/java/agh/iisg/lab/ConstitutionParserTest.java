package agh.iisg.lab;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConstitutionParserTest {
  private static Parser constitution;

  @BeforeClass
  public static void setUp() {
    constitution = new Parser(FileLoader.load("assets/konstytucja.txt"));
  }

  @Test
  public void getPointTest() {
    assertEquals(
      "Trybunał Konstytucyjny rozstrzyga spory kompetencyjne pomiędzy centralnymi konstytucyjnymi organami państwa.",
      constitution.getLaw()
                  .getPartitions().get(0)
                  .getPartitions().get(7)
                  .getPartitions().get(2)
                  .getPartitions().get(1)
                  .getPartitions().get(0)
                  .getContent()
    );

    assertEquals(
      "Prezes Najwyższej Izby Kontroli nie może zajmować innego stanowiska, z wyjątkiem stanowiska profesora szkoły wyższej, ani wykonywać innych zajęć zawodowych.",
      constitution.getLaw()
                  .getPartitions().get(0)
                  .getPartitions().get(8)
                  .getPartitions().get(0)
                  .getPartitions().get(3)
                  .getPartitions().get(1)
                  .getContent()
    );
  }

  @Test
  public void enumerationTest() {
    assertTrue(true);
  }

  @Test
  public void titleStoringTest() {
    assertEquals(
      "Rozdział X FINANSE PUBLICZNE",
      constitution.getLaw()
                  .getPartitions().get(0)
                  .getPartitions().get(9)
                  .getTitle()
    );

    assertEquals(
      "RZECZNIK PRAW OBYWATELSKICH",
      constitution.getLaw()
                  .getPartitions().get(0)
                  .getPartitions().get(8)
                  .getPartitions().get(1)
                  .getTitle()
    );

  }

  @Test
  public void getArticleTest() {
    Partition p = constitution.getLaw()
                              .getPartitions().get(0)
                              .getPartitions().get(6)
                              .getPartitions().get(0)
                              .getPartitions().get(6);

    assertEquals(
      "Art. 169.\n" +
        "1. Jednostki samorządu terytorialnego wykonują swoje zadania za pośrednictwem organów stanowiących i wykonawczych.\n" +
        "2. Wybory do organów stanowiących są powszechne, równe, bezpośrednie i odbywają się w głosowaniu tajnym. Zasady i tryb zgłaszania kandydatów i przeprowadzania wyborów oraz warunki ważności wyborów określa ustawa.\n" +
        "3. Zasady i tryb wyborów oraz odwoływania organów wykonawczych jednostek samorządu terytorialnego określa ustawa.\n" +
        "4. Ustrój wewnętrzny jednostek samorządu terytorialnego określają, w granicach ustaw, ich organy stanowiące.",
      p.getTitle() + "\n" + p.getContent()
    );
  }

  /**
   * Assert that there are no leftover "X. " prefixes in points.
   */
  @Test
  public void extensivePointTest() {
    constitution.getLaw()
                .getPartitions().get(0)
                .getPartitions()
                .stream()
                .flatMap(chapter -> chapter.getPartitions().stream())
                .flatMap(section -> section.getPartitions().stream())
                .flatMap(article -> article.getPartitions().stream())
                .forEach(paragraph -> assertFalse(paragraph.getContent()
                                                           .matches("\\d+[a-z]*?\\. .*"))
                );
  }

  @Test
  public void articleCountTest() {
    assertEquals(
      243,
      constitution.getLaw()
                  .getPartitions().get(0)
                  .getPartitions()
                  .stream()
                  .flatMap(section -> section.getPartitions().stream())
                  .flatMap(article -> article.getPartitions().stream())
                  .count());
  }

  @Test
  public void chapterTitleTest() {
    assertEquals(
      "Rozdział VI RADA MINISTRÓW I ADMINISTRACJA RZĄDOWA",
      constitution.getLaw()
                  .getPartitions().get(0)
                  .getPartitions().get(5)
                  .getTitle());
  }

  @Test
  public void sectionTitleTest() {
    assertEquals(
      "RZECZNIK PRAW OBYWATELSKICH",
      constitution.getLaw()
                  .getPartitions().get(0)
                  .getPartitions().get(8)
                  .getPartitions().get(1)
                  .getTitle());
  }
}
