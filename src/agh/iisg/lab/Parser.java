package agh.iisg.lab;

import agh.iisg.lab.legal.Chapter;
import agh.iisg.lab.legal.LegalPartition;
import agh.iisg.lab.legal.Section;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class Parser {
  private List<Chapter> chapters = new ArrayList<>();

  public Parser(List<String> lines, List<Predicate<String>> filters) {

    Arrays.stream(lines.parallelStream()
                       .filter(l -> filters.stream().allMatch(p -> p.test(l)))
                       .map(line -> line + "\n")
                       .reduce("", String::concat)
                       .split("Rozdział "))
          .map(Chapter::new)
          .forEach(chapter -> {
            chapters.add(chapter);

            ArrayList<String> chapterLines = new ArrayList(Arrays.asList(chapter.getRawContent().split("\n")));

            if (chapterLines.get(0).equals("KONSTYTUCJA")) return;

            chapter.setNumber(chapterLines.remove(0));
            chapter.setTitle(chapterLines.remove(0));

            // TODO:
            // Extract section titles.
            // Move on to the next partitions.
            List<LegalPartition> sections = Arrays.stream(chapterLines.stream()
                                                                      .map(line -> line + "\n")
                                                                      .reduce("", String::concat)
                                                                      .split("[A-ZĘÓĄŚŁŻŹĆŃ ]+\n"))
                                                  .filter(sectionContent -> !"".equals(sectionContent))
                                                  .map(Section::new)
                                                  .collect(toList());
            chapter.setPartitions(sections);
          });

    int sth = 10;
  }

  public List<Chapter> getChapters() {
    return chapters;
  }
}
