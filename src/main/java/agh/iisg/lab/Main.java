package agh.iisg.lab;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.List;

public class Main {
  @Option(name = "-m", aliases = {"--mode"}, usage = "Specify mode (table[_of_contents]/show)")
  private static String mode;

  @Option(name = "-a", aliases = {"--article"}, usage = "Specify article to show.")
  private static String article;

  @Option(name = "-P", aliases = {"--paragraph"}, usage = "Specify paragraph to show.")
  private static String paragraph;

  @Option(name = "-p", aliases = {"--point"}, usage = "Specify point to show.")
  private static String point;

  @Option(name = "-letter", aliases = {"--letter"}, usage = "Specify letter to show.")
  private static String letter;

  @Option(name = "-f", aliases = {"--articles-from"}, usage = "Specify article range start to show.")
  private static Integer articlesFrom;

  @Option(name = "-t", aliases = {"--articles-to"}, usage = "Specify end of the range (inclusive).")
  private static Integer articlesTo;

  @Option(name = "-c", aliases = {"--chapter"}, usage = "Specify chapter to show.")
  private static String chapter;

  @Argument
  private static String fileName = "";

  public static void main(String[] args) {
    new Main().read(args);

    List<String> lines = FileLoader.load(fileName);

    final Parser parser = new Parser(lines);

    if (mode != null &&
      !mode.equals("table") &&
      !mode.equals("table_of_contents") &&
      !mode.equals("show")) {
      System.out.println("Incorrect mode: choose between 'table of contents'" +
                           " ('table' in short) or 'show' (empty default to it).");
      System.exit(1);
    }

    if (article != null) {
      if (letter != null) {
        if (point != null && paragraph != null) {

        }
      } else if (point != null) {

      } else if (paragraph != null) {

      } else {
        parser.getArticles().forEach(a -> {
          if (a.getNumber().equals(article)) {
            System.out.println(a.getTitle());
            System.out.println(a.getContent());
          }
        });
      }
    }

    if (articlesFrom != null && articlesTo != null) {
      parser.getArticles().subList(articlesFrom, articlesTo)
            .forEach(System.out::println);
    }

    if (chapter != null) {
      parser.getChapters().forEach(c -> {
        if (c.getNumber().equals(chapter)) {
          System.out.println(c.getContent());
        }
      });
    }
  }

  private void read(String[] args) {
    CmdLineParser parser = new CmdLineParser(this);
    try {
      parser.parseArgument(args);
    } catch (CmdLineException e) {
      System.err.println(e.getMessage());
    }
  }
}
