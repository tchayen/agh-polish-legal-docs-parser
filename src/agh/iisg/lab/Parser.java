package agh.iisg.lab;

import agh.iisg.lab.legal.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class Parser {
  private final Pattern chapterTitle = Pattern.compile("Rozdział ");
  private final Pattern uppercaseTitle = Pattern.compile("[A-ZĘÓĄŚŁŻŹĆŃ ]+\n");
  private final Pattern articlePrefix = Pattern.compile("Art\\. ");
  private final Pattern paragraphPrefix = Pattern.compile("\n\\d+\\. ");
  private final Pattern pointPrefix = Pattern.compile("\n\\d+\\) ");

  private List<Chapter> chapters = new ArrayList<>();

  public Parser(List<String> lines, List<Predicate<String>> filters) {
    Arrays.stream(lines.parallelStream()
                       .filter(l -> filters.stream().allMatch(p -> p.test(l)))
                       .map(line -> line + "\n")
                       .reduce("", String::concat)
                       // Join lines with words separated by "-".
                       .replaceAll("-\n", "")
                       // Replace new line with space where it is not followed by one of the non-breaking line beginnings.
                       .replaceAll("\n(?![A-ZĘÓĄŚŁŻŹĆŃ ]+\n|Rozdział [IVX]+|Art\\. \\d+\\.|\\d+\\. |\\d+\\) )", " ")
                       // Replace spaces with new lines in cases where article is followed directly by plain text.
                       .replaceAll("(?<=Art\\. \\d{0,3}\\.) ", "\n")
                       .split(chapterTitle.pattern()))
          .map(Chapter::new)
          .forEach(this::processChapter);
  }

  private void processChapter(Chapter chapter) {
    chapters.add(chapter);

    ArrayList<String> chapterLines =
      new ArrayList<>(Arrays.asList(chapter.getRawContent()
                                           .split("\n")));

    if (!chapterLines.get(0).equals("KONSTYTUCJA"))
      chapter.setNumber(chapterLines.remove(0));

    chapter.setTitle(chapterLines.remove(0));

    List<String> sectionTitles = new ArrayList<>();
    List<Section> sections =
      Arrays.stream(chapterLines.stream()
                                .map(line -> {
                                  if (uppercaseTitle.matcher((line + "\n")).find())
                                    sectionTitles.add(line);

                                  return line + "\n";
                                })
                                .reduce("", String::concat)
                                .split(uppercaseTitle.pattern()))
            .dropWhile(String::isEmpty)
            .map(Section::new)
            .collect(toList());
    chapter.setPartitions(sections);

    sections.forEach(section -> {
      section.setTitle(sectionTitles.size() > 0 ? sectionTitles.remove(0) : "");

      List<Article> articles =
        Arrays.stream(section.getRawContent().split(articlePrefix.pattern()))
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
      section.setPartitions(articles);

      articles.forEach(article -> {
        AtomicInteger i = new AtomicInteger(0);
        List<Paragraph> paragraphs =
          Arrays.stream(article.getRawContent().split(paragraphPrefix.pattern()))
                .dropWhile(String::isEmpty)
                .map(raw -> new Paragraph(Integer.toString(i.incrementAndGet()), raw))
                .collect(toList());
        article.setPartitions(paragraphs);

        paragraphs.forEach(paragraph -> {
          AtomicInteger j = new AtomicInteger(0);
          List<Point> points =
            Arrays.stream(paragraph.getRawContent().split(pointPrefix.pattern()))
                  .dropWhile(String::isEmpty)
                  .map(raw -> new Point(Integer.toString(j.incrementAndGet()), raw))
                  .collect(toList());
          paragraph.setPartitions(points);
        });
      });
    });
  }

  public List<Chapter> getChapters() {
    return chapters;
  }
}
