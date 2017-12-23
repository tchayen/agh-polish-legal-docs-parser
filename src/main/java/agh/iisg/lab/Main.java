package agh.iisg.lab;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  @Option(
    name = "-m",
    aliases = {"--mode"},
    usage = "Specify mode (table[_of_contents]/show)."
  )
  private static String mode;

  @Option(
    name = "-d",
    aliases = {"--division"},
    usage = "Specify division to show.",
    forbids = {"-a", "-P", "-p", "-l"}
  )
  private static String division;

  @Option(
    name = "-c",
    aliases = {"--chapter"},
    usage = "Specify chapter to show."
  )
  private static String chapter;

  @Option(
    name = "-a",
    aliases = {"--article"},
    usage = "Specify article to show.",
    forbids = {"-d", "-c", "-f", "-t"}
  )
  private static String article;

  @Option(
    name = "-P",
    aliases = {"--paragraph"},
    usage = "Specify paragraph to show.",
    depends = {"-a"}
  )
  private static String paragraph;

  @Option(
    name = "-p",
    aliases = {"--point"},
    usage = "Specify point to show.",
    depends = {"-a", "-P"}
  )
  private static String point;

  @Option(
    name = "-letter",
    aliases = {"--letter"},
    usage = "Specify letter to show.",
    depends = {"-a", "-P", "-p"}
  )
  private static String letter;

  @Option(
    name = "-f",
    aliases = {"--articles-from"},
    usage = "Specify article range start to show."
  )
  private static String articlesFrom;

  @Option(
    name = "-t",
    aliases = {"--articles-to"},
    usage = "Specify end of the range (inclusive)."
  )
  private static String articlesTo;

  @Option(
    name = "-h",
    aliases = {"--help"},
    help = true
  )
  private static boolean help;

  @Argument
  private static String fileName = "";

  public static void main(String[] args) {
    new Main().read(args);
  }

  private void read(String[] args) {
    CmdLineParser parser = new CmdLineParser(this);
    try {
      parser.parseArgument(args);

      if (help) {
        parser.printUsage(System.out);
        System.exit(0);
      }

      List<String> lines = FileLoader.load(fileName);

      Map<Param, String> params = new HashMap<>();
      params.put(Param.Mode, mode);
      params.put(Param.Division, division);
      params.put(Param.Chapter, chapter);
      params.put(Param.Article, article);
      params.put(Param.Paragraph, paragraph);
      params.put(Param.Point, point);
      params.put(Param.Letter, letter);
      params.put(Param.ArticlesFrom, articlesFrom);
      params.put(Param.ArticlesTo, articlesTo);

      if (help) {
        parser.printUsage(System.err);
        System.exit(0);
      }

      Printer p = new Printer(lines, params);
      p.checkMode();
      p.printTableOfContents();
      p.printChapter();
      p.printDetails();
      p.printRange();
    } catch (CmdLineException e) {
      System.err.println(e.getMessage());
      parser.printUsage(System.err);
      System.exit(1);
    }
  }
}
