package agh.iisg.lab;

import agh.iisg.lab.legal.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class Parser {
  private List<Chapter> chapters = new ArrayList<>();

  public Parser(List<String> lines, List<Predicate<String>> filters) {
    Arrays.stream(lines.parallelStream()
                       .filter(l -> filters.stream().allMatch(p -> p.test(l)))
                       .map(line -> line + "\n")
                       .reduce("", String::concat)
                       .replaceAll(Regex.dashedNewline.pattern(), "")
                       .replaceAll(Regex.skipNewlines.pattern(), " ")
                       .replaceAll(Regex.replaceSpaces.pattern(), "\n")
                       .split(Regex.chapterTitle.pattern()))
          .map(Chapter::new)
          .forEach(this::processChapter);
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
      Arrays.stream(chapterLines.stream()
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
      section.setTitle(sectionTitles.size() > 0
                         ? sectionTitles.remove(0)
                         : "");

      List<Article> articles =
        Arrays.stream(section.getContent().split(Regex.articlePrefix.pattern()))
              .dropWhile(String::isEmpty)
              .map(raw -> {
                ArrayList<String> content = new ArrayList(Arrays.asList(raw.split("\n")));
                String number = content.remove(0);
                return new Article(
                  number.substring(0, number.length() - 1),
                  content.stream()
                         .map(line -> line + "\n")
                         .reduce("", String::concat));
              })
              .collect(toList());
      section.setPartitions(new ArrayList<>(articles));

      articles.parallelStream()
              .forEach(article -> this.getParagraphs(
                article,
                Paragraph::new,
                Regex.paragraphPrefix));
    });
  }

  private void getParagraphs(Legal parent, Supplier<Enumerable> supplier, Pattern regex) {
    AtomicInteger i = new AtomicInteger(0);
    List<Legal> partitions =
      Arrays.stream(parent.getContent().split(regex.pattern()))
            .dropWhile(String::isEmpty)
            .map(raw -> new Paragraph(Integer.toString(i.incrementAndGet()), raw))
            .collect(toList());
    parent.setPartitions(new ArrayList<>(partitions));

    partitions.parallelStream()
              .forEach(partition -> this.getPartitions(
                partition,
                Point::new,
                Regex.pointPrefix));
  }

  private void getPartitions(Legal parent, Supplier<Enumerable> supplier, Pattern regex) {
    AtomicInteger i = new AtomicInteger(0);
    List<Legal> partitions =
      Arrays.stream(parent.getContent().split(regex.pattern()))
            .dropWhile(String::isEmpty)
            .map(raw -> {
              Enumerable partition = supplier.get();
              partition.setNumber(Integer.toString(i.incrementAndGet()));
              partition.setContent(raw);
              return partition;
            })
            .collect(toList());
    parent.setPartitions(partitions);
  }

  public List<Chapter> getChapters() {
    return chapters;
  }
}
