package agh.iisg.lab;

import agh.iisg.lab.legal.Chapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Parser {
  private List<Chapter> chapters = new ArrayList<>();

  public Parser(List<String> lines, List<Predicate<String>> filters) {

    Arrays.asList(lines.parallelStream()
                       .filter(line -> filters.stream()
                                              .allMatch(predicate -> predicate.test(line)))
                       .map(line -> line + "\n")
                       .reduce("", String::concat)
                       .split("Rozdział "))
          .stream()
          .map(Chapter::new)
          .forEach(chapter -> {
            chapters.add(chapter);

            List<String> chapterLines = Arrays.asList(chapter.getRawContent()
                                                             .split("\n"));
//            chapter.setNumber(chapterLines.remove(0));
//            chapter.setTitle(chapterLines.remove(0));
//
//            chapter.setPartitions(articles);
          });
  }

  public List<Chapter> getChapters() {
    return chapters;
  }
}
