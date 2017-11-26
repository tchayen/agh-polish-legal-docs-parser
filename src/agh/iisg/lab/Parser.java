package agh.iisg.lab;

import agh.iisg.lab.legal.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class Parser {
  private List<Chapter> chapters = new ArrayList<>();
  private AtomicInteger articleCounter = new AtomicInteger(-1);

  public Parser(List<String> lines, List<Predicate<String>> filters) {
    Arrays.stream(
      lines.parallelStream()
           .filter(l -> filters.stream().allMatch(p -> p.test(l)))
           .map(line -> line + "\n")
           .reduce("", String::concat)
           .replaceAll(Regex.dashedNewline.pattern(), "")
           .replaceAll(Regex.skipNewlines.pattern(), " ")
           .replaceAll(Regex.replaceSpaces.pattern(), "\n")
           .split(Chapter.regex.pattern()))
          .map(Chapter::new)
          .forEach(this::processChapter);
  }

  public List<Chapter> getPartitions() {
    return chapters;
  }

  private void processChapter(Chapter chapter) {
    chapters.add(chapter);

    ArrayList<String> chapterLines =
      new ArrayList<>(Arrays.asList(chapter.getContent()
                                           .split("\n")));

    if (!chapterLines.get(0).equals("KONSTYTUCJA"))
      chapter.setNumber(chapterLines.remove(0));

    chapter.setTitle(chapterLines.remove(0));

    List<String> sectionTitles = new ArrayList<>();
    List<Section> sections =
      Arrays.stream(
        chapterLines.stream()
                    .map(line -> {
                      if (Regex.uppercaseTitle.matcher((line + "\n")).find())
                        sectionTitles.add(line);

                      return line + "\n";
                    })
                    .reduce("", String::concat)
                    .split(Regex.uppercaseTitle.pattern()))
            .dropWhile(String::isEmpty)
            .map(Section::new)
            .collect(toList());
    chapter.setPartitions(new ArrayList<>(sections));

    sections.forEach(section -> {
      section.setTitle(sectionTitles.size() > 0 ? sectionTitles.remove(0) : "");

      this.getPartitions(
        section,
        new ArrayList<>(Arrays.asList(
          new PartitionGenerator(Article::new, articleCounter),
          new PartitionGenerator(Paragraph::new, null),
          new PartitionGenerator(Point::new, null),
          new PartitionGenerator(Letter::new, null)
        )));
    });
  }

  private void getPartitions(Legal parent, ArrayList<PartitionGenerator> generators) {
    if (generators.size() == 0) return;

    PartitionGenerator generator = generators.remove(0);
    String pattern = generator.getSupplier().get().regex().pattern();

    List<Legal> partitions =
      Arrays.stream(parent.getContent().split(pattern))
            .dropWhile(String::isEmpty)
            .dropWhile(line -> line.matches(pattern))
            .map(raw -> {
              Enumerable partition = (Enumerable) generator.getSupplier().get();
              partition.setNumber(Integer.toString(generator.getCounter().incrementAndGet()));
              partition.setContent(raw);
              return partition;
            })
            .collect(toList());
    parent.setPartitions(partitions);

    partitions.parallelStream()
              .forEach(partition -> this.getPartitions(partition, new ArrayList<>(generators)));
  }
}
