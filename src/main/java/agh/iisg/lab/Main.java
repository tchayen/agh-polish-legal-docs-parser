package agh.iisg.lab;

import agh.iisg.lab.legal.Legal;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Main {
  @Option(name = "-a", aliases = {"--article"}, usage = "Specify article to show.")
  private static Integer article;

  @Option(name = "-f", aliases = {"--articles-from"}, usage = "Specify article range start to show.")
  private static Integer articlesFrom;

  @Option(name = "-t", aliases = {"--articles-to"}, usage = "Specify end of the range (inclusive).")
  private static Integer articlesTo;

  @Option(name = "-c", aliases = {"--chapter"}, usage = "Specify chapter to show.")
  private static Integer chapter;

  public static void main(String[] args) {
    new Main().read(args);

    List<String> lines = FileLoader.load("assets/konstytucja.txt");

    final Parser parser = new Parser(lines);

    int a = 1;

//    System.out.println(parser.getPartitions().get(10)
//                             .getPartitions().get(0)
//                             .getPartitions().get(3)
//                             .getPartitions().get(2)
//                             .getContent());

    List<Legal> articles = new ArrayList<>(
        parser.getPartitions()
              .stream()
              .flatMap(chapter -> chapter.getPartitions().stream())
              .flatMap(section -> section.getPartitions().stream())
              .collect(toList()));

    articles.remove(0);

    if (article != null) {
      System.out.println(articles.get(article - 1).getContent());
    }

    if (articlesFrom != null && articlesTo != null) {
      articles.subList(articlesFrom, articlesTo)
              .forEach(System.out::println);
    }

    if (chapter != null) {
      System.out.println(parser.getPartitions().get(chapter).getContent());
    }
  }

  private void read(String[] args) {
    CmdLineParser parser = new CmdLineParser(this);
    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      System.err.println(e.getMessage());
      return;
    }

    System.out.println(article);
  }
}
