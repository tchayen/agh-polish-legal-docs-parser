package agh.iisg.lab;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class ConstitutionParserTest {
  private static Parser constitution;

  @BeforeClass
  public static void setUp() {
    constitution = new Parser(FileLoader.load("assets/konstytucja.txt"));
  }

  @Test
  public void getPointTest() {
    assertEquals(
      "Zasady i tryb opracowania projektu budżetu państwa, stopień jego szczegółowości oraz wymagania, którym powinien odpowiadać projekt ustawy budżetowej, a także zasady i tryb wykonywania ustawy budżetowej określa ustawa.",
      constitution.getLaw()
                  .getPartitions().get(0)
                  .getPartitions().get(10)
                  .getPartitions().get(0)
                  .getPartitions().get(3)
                  .getPartitions().get(2)
                  .getContent()
    );
  }

  @Test
  public void getArticleTest() {
    assertEquals("Art. 152.\n" +
                   "1. Przedstawicielem Rady Ministrów w województwie jest wojewoda.\n" +
                   "2. Tryb powoływania i odwoływania oraz zakres działania wojewodów określa ustawa.\n",
                 constitution.getLaw()
                             .getPartitions().get(0)
                             .getPartitions().get(6)
                             .getPartitions().get(0)
                             .getPartitions().get(6)
                             .getContent()
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
                .forEach(paragraph ->
                           assertFalse(paragraph.getContent().matches("\\d+\\. .*"))
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
}
