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
   * Match chapter's title.
   */
  public static final Pattern chapterTitle = Pattern.compile("Rozdział ");

  /**
   * Match title written in uppercase.
   */
  public static final Pattern uppercaseTitle = Pattern.compile("[A-ZĘÓĄŚŁŻŹĆŃ ]+\n");

  /**
   * Match article prefix.
   * NOTE: index is intentionally left to make parsing easier.
   */
  public static final Pattern articlePrefix = Pattern.compile("Art\\. ");

  /**
   * Match for number enumeration.
   */
  public static final Pattern paragraphPrefix = Pattern.compile("\n\\d+\\. ");

  /**
   * Match for number with enclosing parenthesis enumeration.
   */
  public static final Pattern pointPrefix = Pattern.compile("\n\\d+\\) ");
}
