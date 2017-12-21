package agh.iisg.lab;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constraints {
  public static final String UPPERCASE_WORD_REGEX = "[A-ZĘÓĄŚŁŻŹĆŃ, ]+";
  public static final String WORD_REGEX = "[A-zĘęÓóĄąŚśŁłŻżŹźĆćŃń, ]+";

  public static final List<Pattern> splitters = Stream.of(
    "\n(?=DZIAŁ [IVX]+ " + WORD_REGEX + "\n)",
    "\n(?=Rozdział ([IVX]+|\\d+[a-z]*) " + WORD_REGEX + "\n)",
    "\n(?=" + UPPERCASE_WORD_REGEX + "+\n)",
    "\n(?=Art\\. \\d+[a-z]*?\\.\n)",
    "\n\\d+[a-z]*?\\. ",
    "\n\\d+[a-z]*?\\) ",
    "\n[a-z]+\\) ",
    "\n- "
  ).map(Pattern::compile).collect(Collectors.toList());

  public static final List<Pattern> titleMatchers = Stream.of(
    "^DZIAŁ [IVX]+ " + WORD_REGEX + "\n",
    "^Rozdział ([IVX]+|\\d+[a-z]*) " + WORD_REGEX + "\n",
    "^" + WORD_REGEX + "\n",
    "^Art\\. \\d+[a-z]*?\\.\n",
    "^\\d+[a-z]*?\\. ",
    "^\\d+[a-z]*?\\) ",
    "^[a-z]+\\) ",
    "^\n- "
  ).map(Pattern::compile).collect(Collectors.toList());

  public static final List<Pattern> numberExtractors = Stream.of(
    "(?<=DZIAŁ )[IVX]+",
    "(?<=Rozdział )([IVX]+|\\d{1,4}[a-z]{0,4})",
    "",
    "(?<=Art\\. )\\d{1,4}[a-z]{0,4}?(?=\\.)",
    "\\d{1,4}[a-z]{0,4}(?=\\. )",
    "\\d{1,4}[a-z]{0,4}(?=\\) )",
    "[a-z]{1,4}(?=\\) )",
    "^\n- "
  ).map(Pattern::compile).collect(Collectors.toList());

  /**
   * Join lines with words separated by "-".
   */
  public static final Pattern dashedNewline = Pattern.compile("-\n");

  /**
   * Replace spaces with new lines in cases where article is followed directly by plain text.
   */
  public static final Pattern replaceSpaces = Pattern.compile("(?<=Art\\. \\d{1,3}[a-z]{0,2}\\.) ");
  public static final List<Predicate<String>> filters = Arrays.asList(
    Pattern.compile("©Kancelaria Sejmu( s. \\d+/\\d+)?")
           .asPredicate()
           .negate(),
    Pattern.compile("\\d{4}-\\d{2}-\\d{2}")
           .asPredicate()
           .negate(),
    line -> line.length() > 1
  );

  public static final List<String> newLiners = Arrays.asList(
    "(?<!Rozdział [IVX]{1,9}\n)" + UPPERCASE_WORD_REGEX,
    "\\d{1,4}[a-z]{0,4}?\\. ",
    "\\d{1,4}[a-z]{0,4}?\\) ",
    "[a-z]{1,4}\\)"
  );

  /**
   * Replace new line with space where it is not followed by one of the non-breaking line beginnings.
   */
  public static final Pattern skipNewlines = Pattern.compile(
    "\n(?!" + newLiners.stream().reduce((a, b) -> a + "|" + b).orElse("") + ")"
  );

  public static final Pattern joinTitles = Pattern.compile(
    "(?<=DZIAŁ [IVX]{1,9}|Rozdział \\d{1,4}[a-z]{0,4})\n"
  );
}
