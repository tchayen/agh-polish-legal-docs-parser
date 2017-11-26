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
    List<Predicate<String>> filters = Arrays.asList(
      line -> !Objects.equals(line, "©Kancelaria Sejmu"),
      Pattern.compile("\\d{4}-\\d{2}-\\d{2}")
             .asPredicate()
             .negate(),
      line -> line.length() > 1
    );

    parser = new Parser(FileLoader.load("assets/konstytucja.txt"), filters);
  }

  @Test
  void getPointTest() {
    assertEquals(
      "Zasady i tryb opracowania projektu budżetu państwa, stopień jego szczegółowości oraz wymagania, którym powinien odpowiadać projekt ustawy budżetowej, a także zasady i tryb wykonywania ustawy budżetowej określa ustawa.",
      parser.getPartitions().get(10)
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
      parser.getPartitions().get(6)
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
    parser.getPartitions().stream()
          .flatMap(chapter -> chapter.getPartitions().stream())
          .flatMap(section -> section.getPartitions().stream())
          .flatMap(article -> article.getPartitions().stream())
          .forEach(paragraph ->
            assertFalse(paragraph.getContent().matches("\\d+\\. .*"))
          );
  }
}