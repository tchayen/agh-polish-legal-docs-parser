package agh.iisg.lab;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Parser {
  private Partition law;

  private List<Partition> articles;
  private Map<String, Partition> resolveArticles = new HashMap<>();

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
    ).map(Partition::new).forEach(law -> {
      this.law = law;

      // Skip different amount of lines based on document.
      if (law.getContent().startsWith("KONSTYTUCJA")) {
        law.setContent(law.getContent().split("\n", 5)[4]);
      } else {
        law.setContent(law.getContent().split("\n", 3)[2]);
      }

      this.parse(law, IntStream.range(0, 8).collect(
        ArrayList::new, ArrayList::add, ArrayList::addAll));

      articles = this.law
        .getPartitions()
        .stream()
        .flatMap(division -> division.getPartitions().stream())
        .flatMap(chapter -> chapter.getPartitions().stream())
        .flatMap(section -> section.getPartitions().stream())
        .collect(toList());
      articles.forEach(a -> resolveArticles.put(a.getNumber(), a));
    });
  }

  /**
   * Recursively parse legal partitions using given list of generators.
   *
   * @param parent  parent object.
   * @param indices List<Integer> providing indexes of partitions.
   */
  private void parse(Partition parent, ArrayList<Integer> indices) {
    if (indices.size() == 0) return;

    Integer index = indices.remove(0);
    List<Partition> partitions =
      Arrays.stream(parent.getContent().split(Constraints.splitters.get(index).pattern()))
            .filter(line -> !line.isEmpty())
            .map(raw -> {
              Partition partition = new Partition();
              Matcher title = Constraints.titleMatchers.get(index).matcher(raw);
              if (title.find()) {
                String foundTitle = title.group(0);
                raw = raw.replaceFirst(Pattern.quote(foundTitle), "");
                partition.setTitle(foundTitle.substring(0, foundTitle.length() - 1));
                Matcher number = Constraints.numberExtractors.get(index).matcher(title.group(0));
                if (number.find()) partition.setNumber(number.group(0));
              }
              partition.setContent(raw);
              return partition;
            })
            .collect(toList());
    // NOTE: 4 is an arbitrary number allowing us to assume that structure
    // can get inconsistent deeper in the hierarchy from now on.
//    if (indices.size() < 4 && !partitions.stream().allMatch(p -> p.getTitle() != null)) return;

    parent.setPartitions(partitions);
    partitions.forEach(partition -> this.parse(partition, new ArrayList<>(indices)));
  }

  public Partition getLaw() {
    return law;
  }

  public List<Partition> getArticles() {
    return articles;
  }

  public List<Partition> getArticleRange(String from, String to) {
    int i = 0;
    while ((articles.get(i).getNumber() == null ||
      !articles.get(i).getNumber().equals(from)) &&
      i < articles.size()) i++;
    int j = 0;
    while ((articles.get(i).getNumber() == null ||
      !articles.get(j).getNumber().equals(to))
      && j < articles.size()) j++;
    return articles.subList(i, j + 1);
  }

  public Partition getArticle(String title) {
    return resolveArticles.get(title);
  }
}
