package agh.iisg.lab;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Main {
  public static void main(String[] args) {
    List<String> lines = FileLoader.load("assets/konstytucja.txt");

    List<Predicate<String>> filters = Arrays.asList(
      line -> !Objects.equals(line, "©Kancelaria Sejmu"),
      Pattern.compile("\\d{4}-\\d{2}-\\d{2}")
             .asPredicate()
             .negate(),
      line -> line.length() > 1
    );

    final Parser parser = new Parser(lines, filters);

    int a = 1;

    System.out.println(parser.getPartitions().get(10)
                             .getPartitions().get(0)
                             .getPartitions().get(2)
                             .getContent());
  }
}
