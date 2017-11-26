package agh.iisg.lab;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ConstitutionConstraints {
  /**
   * Join lines with words separated by "-".
   */
  public static final Pattern dashedNewline = Pattern.compile("-\n");

  /**
   * Replace new line with space where it is not followed by one of the non-breaking line beginnings.
   */
  public static final Pattern skipNewlines = Pattern.compile(
    "\n(?![A-ZĘÓĄŚŁŻŹĆŃ ]+\n|Rozdział [IVX]+|Art\\. \\d+\\.|\\d+\\. |\\d+\\) )");

  /**
   * Replace spaces with new lines in cases where article is followed directly by plain text.
   */
  public static final Pattern replaceSpaces = Pattern.compile("(?<=Art\\. \\d{0,3}\\.) ");

  public static final List<Predicate<String>> filters = Arrays.asList(
    line -> !Objects.equals(line, "©Kancelaria Sejmu"),
    Pattern.compile("\\d{4}-\\d{2}-\\d{2}")
           .asPredicate()
           .negate(),
    line -> line.length() > 1
  );
}
