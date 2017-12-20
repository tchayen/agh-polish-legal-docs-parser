package agh.iisg.lab;

import agh.iisg.lab.legal.Legal;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConstitutionParserTest {
  private static Parser constitution;

  @BeforeClass
  public static void setUp() {
    constitution = new Parser(FileLoader.load("assets/konstytucja.txt"));
  }

  @Test
  public void getPointTest() {
    assertEquals(
      "Krajowa Rada Radiofonii i Telewizji stoi na straży wolności słowa, prawa do informacji oraz interesu publicznego w radiofonii i telewizji.",
      constitution.getLaw()
                  .getPartitions().get(0)
                  .getPartitions().get(7)
                  .getPartitions().get(2)
                  .getPartitions().get(0)
                  .getPartitions().get(0)
                  .getContent()
    );

    assertEquals(
      "Zasady i tryb opracowania projektu budżetu państwa, stopień jego szczegółowości oraz wymagania, którym powinien odpowiadać projekt ustawy budżetowej, a także zasady i tryb wykonywania ustawy budżetowej określa ustawa.",
      constitution.getLaw()
                  .getPartitions().get(0)
                  .getPartitions().get(9)
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
    Legal p = constitution.getLaw()
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

  /**
   * -1 because preamble should be skipped.
   */
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
                  .count() - 1);
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
