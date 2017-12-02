package agh.iisg.lab;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Constraints {
  public static final String UPPERCASE_WORD_REGEX = "[A-ZĘÓĄŚŁŻŹĆŃ, ]+";
  public static final String WORD_REGEX = "[A-zĘęÓóĄąŚśŁłŻżŹźĆćŃń, ]+";

  public static final List<Pattern> splitters = Arrays.asList(
    "\n(?=DZIAŁ [IVX]+\n"+ Constraints.WORD_REGEX + "\n)",
    "\n(?=Rozdział ([IVX]|\\d)+\n" + Constraints.UPPERCASE_WORD_REGEX + "\n)",
    "\n(?=[A-zĘęÓóĄąŚśŁłŻżŹźĆćŃń, ]+\n)",
    "\n(?=Art\\. \\d+\\.\n)",
    "\n\\d+\\. ",
    "\n\\d+\\) ",
    "\n[a-z]+\\) ",
    "\n- "
  ).stream().map(Pattern::compile).collect(Collectors.toList());

  public static final List<Pattern> titleMatchers = Arrays.asList(
    "^DZIAŁ [IVX]+\n" + Constraints.WORD_REGEX + "\n",
    "Rozdział ([IVX]|\\d)+\n" + Constraints.UPPERCASE_WORD_REGEX + "\n",
    "^[A-zĘęÓóĄąŚśŁłŻżŹźĆćŃń, ]+\n",
    "Art\\. \\d+\\.\n",
    "^\\d+\\. ",
    "^\\d+\\) ",
    "^[a-z]+\\) ",
    "\n- "
  ).stream().map(Pattern::compile).collect(Collectors.toList());

  private static final List<String> newLiners = Arrays.asList(
    UPPERCASE_WORD_REGEX + "\n",
    "DZIAŁ [IVX]+",
    "Rozdział ([IVX]|\\d)+",
    "^[A-zĘęÓóĄąŚśŁłŻżŹźĆćŃń, ]+\n",
    "Art\\. \\d+\\.",
    "\\d+\\. ",
    "\\d+\\) ",
    "[a-z]+\\)"
    );

  /**
   * Join lines with words separated by "-".
   */
  public static final Pattern dashedNewline = Pattern.compile("-\n");

  /**
   * Replace new line with space where it is not followed by one of the non-breaking line beginnings.
   */
  public static final Pattern skipNewlines = Pattern.compile(
    "\n(?!" + newLiners.stream().reduce((a, b) -> a + "|" + b).orElse("") + ")");

  /**
   * Replace spaces with new lines in cases where article is followed directly by plain text.
   */
  public static final Pattern replaceSpaces = Pattern.compile("(?<=Art\\. \\d{1,3}\\.) ");

  public static final List<Predicate<String>> filters = Arrays.asList(
    Pattern.compile("©Kancelaria Sejmu( s. \\d+/\\d+)?")
           .asPredicate()
           .negate(),
    Pattern.compile("\\d{4}-\\d{2}-\\d{2}")
           .asPredicate()
           .negate(),
    line -> line.length() > 1
  );
}
