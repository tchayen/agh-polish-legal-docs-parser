package agh.iisg.lab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Printer {
  private Parser parser;
  private Map<Param, String> params;

  public Printer(List<String> lines, Map<Param, String> params) {
    this.parser = new Parser(lines);
    this.params = params;
  }

  public void checkMode() {
    String mode = params.get(Param.Mode);

    if (mode != null &&
      !mode.equals("table") &&
      !mode.equals("table_of_contents") &&
      !mode.equals("show")) {
      System.out.println("Incorrect mode: choose between 'table of contents'" +
                           " ('table' in short) or 'show' (empty default to it).");
      System.exit(1);
    }
  }

  public void printTableOfContents() {
    String mode = params.get(Param.Mode);
    String division = params.get(Param.Division);

    if (mode == null || (!mode.equals("table") && !mode.equals("table_of_contents"))) return;

    if (division == null) {
      parser.getLaw()
            .getPartitions()
            .forEach(div -> {
              if (div.getTitle() != null)
                System.out.println(div.getTitle());
              printDivisionsTableOfContents(div);
            });
    } else {
      Partition maybeDivision = parser.getLaw().getPartition(division);
      if (maybeDivision == null) {
        System.out.println("There is no division with given number (maybe you" +
                             "are using arabic instead of roman numerals?");
        System.exit(1);
      }
      printDivisionsTableOfContents(maybeDivision);
    }
  }

  public void printDivisionsTableOfContents(Partition division) {
    division.getPartitions().forEach(
      chapter -> {
        if (chapter.getTitle() != null) {
          if (division.getTitle() != null) System.out.print("    ");
          System.out.print(chapter.getTitle() + "\n");
        }
      }
    );
  }

  public void printChapter() {
    String mode = params.get(Param.Mode);
    String division = params.get(Param.Division);
    String chapter = params.get(Param.Chapter);

    if (division != null && chapter == null) {
      System.out.println(parser.getLaw().getPartition(division));
      System.exit(0);
    }

    if (chapter == null) {
      return;
    }

    if (parser.getLaw().getPartitions().size() != 1 &&
      division == null) {
      System.out.println("You must specify division in so program can " +
                           "recognize chapter.");
      System.exit(1);
    }

    Partition c = null;
    if (division == null) {
      c = parser.getLaw().getPartitions().get(0).getPartition(chapter);
    } else {
      Partition d = parser.getLaw().getPartition(division);
      if (d != null) c = d.getPartition(chapter);
    }
    if (c != null) print(c);
    else {
      System.out.println("Couldn't find chapter. Make you did not specify " +
                           "division in a law which does not have them (or otherwise");
    }
  }

  public void printDetails() {
    ArrayList<String> details = new ArrayList<>(Arrays.asList(
      params.get(Param.Paragraph),
      params.get(Param.Point),
      params.get(Param.Letter)
    ));

    if (params.get(Param.Article) == null) return;

    int i = details.size() - 1;
    while (i > 0 && details.get(i) == null) i--;
    details = new ArrayList<>(details.subList(0, i + 1));

    Partition parent = parser.getArticle(params.get(Param.Article));
    while (details.size() != 0) {
      String title = details.remove(0);
      try {
        Partition partition = parent.getPartition(title);
        if (partition == null) {
          if (title != null) throw new NullPointerException();
          break;
        }
        parent = partition;
      } catch (NullPointerException e) {
        System.out.println("Not found.");
        System.exit(1);
      }

    }
    if (parent != null) print(parent);
  }

  public void print(Partition p) {
    StringBuilder builder = new StringBuilder("");
    if (p.getTitle() != null) {
      builder.append(p.getTitle());
      if (p.getTitle().matches(Constraints.matchNoNewlineDelimetedTitles.pattern())) {
        builder.append(" ");
      } else {
        builder.append("\n");
      }
    }
    builder.append(p.getContent());
    builder.append("\n");
    System.out.print(builder.toString());
    System.exit(0);
  }

  public void printRange() {
    if (params.get(Param.ArticlesFrom) != null &&
      params.get(Param.ArticlesTo) != null) {
      if (parser.getArticle(params.get(Param.ArticlesFrom)) == null) {
        System.out.println("Couldn't find starting article.");
        System.exit(1);
      } else if (parser.getArticle(params.get(Param.ArticlesTo)) == null) {
        System.out.println("Couldn't find ending article.");
        System.exit(1);
      }

      parser.getArticleRange(
        params.get(Param.ArticlesFrom), params.get(Param.ArticlesTo)
      ).forEach(article -> {
        print(article);
      });
    }
  }
}
