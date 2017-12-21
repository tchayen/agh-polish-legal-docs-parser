package agh.iisg.lab;

import agh.iisg.lab.legal.LegalPartition;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UokikParserTest {
  private static Parser uokik;

  @BeforeClass
  public static void setUp() {
    uokik = new Parser(FileLoader.load("assets/uokik.txt"));
  }

  @Test
  public void articleCountTest() {
    List<LegalPartition> articles = uokik.getLaw()
                                .getPartitions()
                                .stream()
                                .flatMap(division -> division.getPartitions().stream())
                                .flatMap(chapter -> chapter.getPartitions().stream())
                                .flatMap(article -> article.getPartitions().stream()).collect(toList());

    assertEquals(
      177,
      articles.size()
    );

    assertEquals(
      "Art. 138.",
      articles.get(articles.size() - 1).getTitle()
    );
  }
}
