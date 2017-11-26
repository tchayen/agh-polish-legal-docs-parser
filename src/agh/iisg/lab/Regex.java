package agh.iisg.lab;

import java.util.regex.Pattern;

public class Regex {
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

  /**
   * Match title written in uppercase.
   */
  public static final Pattern uppercaseTitle = Pattern.compile("[A-ZĘÓĄŚŁŻŹĆŃ ]+\n");
}
