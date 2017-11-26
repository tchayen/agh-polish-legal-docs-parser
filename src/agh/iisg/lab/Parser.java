package agh.iisg.lab;

import agh.iisg.lab.legal.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

public class Parser {
  /**
   * Globally counts articles. Starts with -2 to offset preamble and first skipped section.
   */
  private AtomicInteger articleCounter = new AtomicInteger(-2);

  private List<Chapter> chapters = new ArrayList<>();

  public Parser(List<String> lines) {
    Arrays.stream(
      lines.parallelStream()
           .filter(l -> ConstitutionConstraints.filters.stream().allMatch(p -> p.test(l)))
           .map(line -> line + "\n")
           .reduce("", String::concat)
           .replaceAll(ConstitutionConstraints.dashedNewline.pattern(), "")
           .replaceAll(ConstitutionConstraints.skipNewlines.pattern(), " ")
           .replaceAll(ConstitutionConstraints.replaceSpaces.pattern(), "\n")
           .split(Chapter.regex.pattern()))
          .map(Chapter::new)
          .forEach(chapter -> {
            this.chapters.add(chapter);
            this.parse(
              chapter,
              new ArrayList<>(Arrays.asList(
                new PartitionGenerator(Section::new, null),
                new PartitionGenerator(Article::new, articleCounter),
                new PartitionGenerator(Paragraph::new, null),
                new PartitionGenerator(Point::new, null),
                new PartitionGenerator(Letter::new, null),
                new PartitionGenerator(Indent::new, null)
              )));
          });
  }

  public List<Chapter> parse() {
    return chapters;
  }

  /**
   * Recursively parse legal partitions using given list of generators.
   *
   * @param parent     parent object.
   * @param generators List<PartitionGenerators> providing constructor-lambdas, regex patterns and instance counters.
   */
  private void parse(Legal parent, ArrayList<PartitionGenerator> generators) {
    if (generators.size() == 0) return;

    PartitionGenerator generator = generators.remove(0);
    String pattern = generator.getSupplier().get().regex().pattern();

    List<Legal> partitions =
      Arrays.stream(parent.getContent().split(pattern))
            .dropWhile(String::isEmpty)
            .map(raw -> {
              Legal partition = generator.getSupplier().get();
              partition.setNumber(Integer.toString(generator.getCounter().incrementAndGet()));
              partition.setTitle(raw.replaceAll(pattern, ""));
              partition.setContent(raw);
              return partition;
            })
            .collect(toList());
    parent.setPartitions(partitions);

    partitions.parallelStream()
              .forEach(partition -> this.parse(partition, new ArrayList<>(generators)));
  }
}
