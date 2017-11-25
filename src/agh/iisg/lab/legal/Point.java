package agh.iisg.lab.legal;

/**
 * Point
 * Corresponds to "Punkt" written as "{index as number})". Index is local to
 * the enclosing paragraph and starts at 1.
 */
public class Point extends LegalPartition {
  public Point(String rawContent) {
    super(rawContent);
  }
}
