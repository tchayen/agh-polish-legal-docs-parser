package agh.iisg.lab;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.Arrays;
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
  private static String articlesFrom;

  @Option(name = "-t", aliases = {"--articles-to"}, usage = "Specify end of the range (inclusive).")
  private static String articlesTo;

  @Option(name = "-c", aliases = {"--chapter"}, usage = "Specify chapter to show.")
  private static String chapter;

  @Argument
  private static String fileName = "";

  private static Parser parser;

  public static void main(String[] args) {
    new Main().read(args);
    List<String> lines = FileLoader.load(fileName);
    parser = new Parser(lines);

    checkMode();
    printChapter();
    printDetails();
    printRange();
  }

  private static void checkMode() {
    if (mode != null &&
      !mode.equals("table") &&
      !mode.equals("table_of_contents") &&
      !mode.equals("show")) {
      System.out.println("Incorrect mode: choose between 'table of contents'" +
                           " ('table' in short) or 'show' (empty default to it).");
      System.exit(1);
    }
  }

  private static void printChapter() {
    if (chapter == null) return;
    Partition c = parser.getChapter(chapter);
    if (c != null) print(c);
  }

  private static void printDetails() {
    ArrayList<String> details = new ArrayList<>(Arrays.asList(paragraph, point, letter));

    if (article == null) return;

    Partition parent = parser.getArticle(article);
    while (details.size() != 0) {
      String title = details.remove(0);
      Partition partition = parent.getPartition(title);
      if (partition == null) break;
      parent = partition;
    }
    if (parent != null) print(parent);
  }

  private static void print(Partition p) {
    System.out.println(p.getTitle());
    System.out.println(p.getContent());
    System.exit(0);
  }

  private static void printRange() {
    boolean areDetailsEmpty = chapter == null &&
      article == null &&
      paragraph == null &&
      point == null &&
      letter == null;

    if (areDetailsEmpty && articlesFrom != null && articlesTo != null) {
      // FIX: why does it print only first line?
      parser.getArticleRange(articlesFrom, articlesTo).forEach(Main::print);
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
