package agh.iisg.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Parser {
  private LegalPartition law;
  private List<LegalPartition> chapters;
  private List<LegalPartition> articles;

  public Parser(List<String> lines) {
    Stream.of(
      lines.parallelStream()
           .filter(l -> Constraints.filters.stream().allMatch(p -> p.test(l)))
           .map(line -> line + "\n")
           .reduce("", String::concat)
           .replaceAll(Constraints.dashedNewline.pattern(), "")
           .replaceAll(Constraints.skipNewlines.pattern(), " ")
           .replaceAll(Constraints.replaceSpaces.pattern(), "\n")
           .replaceAll(Constraints.joinTitles.pattern(), " ")
    )
          .map(LegalPartition::new)
          .forEach(law -> {
            this.law = law;

            // Skip different amount of lines based on document.
            if (law.getContent().startsWith("KONSTYTUCJA")) {
              law.setContent(law.getContent().split("\n", 5)[4]);
            } else {
              law.setContent(law.getContent().split("\n", 3)[2]);
            }

            this.parse(law, IntStream.range(0, 8).collect(
              ArrayList::new, ArrayList::add, ArrayList::addAll));

            chapters = this.law.getPartitions().stream()
                               .flatMap(division -> division.getPartitions().stream())
                               .collect(toList());

            articles = this.law
              .getPartitions()
              .stream()
              .flatMap(division -> division.getPartitions().stream())
              .flatMap(chapter -> chapter.getPartitions().stream())
              .flatMap(section -> section.getPartitions().stream())
              .collect(toList());
          });
  }

  public LegalPartition getLaw() {
    return law;
  }

  public List<LegalPartition> getChapters() {
    return chapters;
  }

  public List<LegalPartition> getArticles() {
    return articles;
  }

  /**
   * Recursively parse legal partitions using given list of generators.
   *
   * @param parent  parent object.
   * @param indices List<Integer> providing indexes of partitions.
   */
  private void parse(LegalPartition parent, ArrayList<Integer> indices) {
    if (indices.size() == 0) return;

    Integer index = indices.remove(0);
    List<LegalPartition> partitions =
      Arrays.stream(parent.getContent().split(Constraints.splitters.get(index).pattern()))
            .filter(line -> !line.isEmpty())
            .map(raw -> {
              LegalPartition partition = new LegalPartition();
              Matcher title = Constraints.titleMatchers.get(index).matcher(raw);
              if (title.find()) {
                String foundTitle = title.group(0);
                raw = raw.replaceFirst(foundTitle, "");
                partition.setTitle(foundTitle.substring(0, foundTitle.length() - 1));
                Matcher number = Constraints.numberExtractors.get(index).matcher(title.group(0));
                if (number.find()) partition.setNumber(number.group(0));
              }
              partition.setContent(raw);
              return partition;
            })
            .collect(toList());
    parent.setPartitions(partitions);

    partitions.forEach(partition -> this.parse(partition, new ArrayList<>(indices)));
  }
}
