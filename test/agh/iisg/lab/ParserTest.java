package agh.iisg.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParserTest {
  Parser parser;

  @BeforeAll
  void setUp() {
    parser = new Parser(FileLoader.load("assets/konstytucja.txt"));
  }

  @Test
  void getPointTest() {
    assertEquals(
      "Zasady i tryb opracowania projektu budżetu państwa, stopień jego szczegółowości oraz wymagania, którym powinien odpowiadać projekt ustawy budżetowej, a także zasady i tryb wykonywania ustawy budżetowej określa ustawa.",
      parser.parse().get(10)
            .getPartitions().get(0)
            .getPartitions().get(3)
            .getPartitions().get(2)
            .getContent()
    );
  }

  @Test
  void getArticleTest() {
    assertEquals(
    "Art. 152.\n1. Przedstawicielem Rady Ministrów w województwie jest wojewoda.\n2. Tryb powoływania i odwoływania oraz zakres działania wojewodów określa ustawa.\n",
      parser.parse().get(6)
            .getPartitions().get(0)
            .getPartitions().get(6)
            .getContent()
    );
  }

  /**
   * Assert that there are no leftover "X. " prefixes in points.
   */
  @Test
  void extensivePointTest() {
    parser.parse().stream()
          .flatMap(chapter -> chapter.getPartitions().stream())
          .flatMap(section -> section.getPartitions().stream())
          .flatMap(article -> article.getPartitions().stream())
          .forEach(paragraph ->
            assertFalse(paragraph.getContent().matches("\\d+\\. .*"))
          );
  }
}