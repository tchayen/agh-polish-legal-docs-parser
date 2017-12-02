package agh.iisg.lab;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UokikParserTest {
  private static Parser uokik;

  @BeforeClass
  public static void setUp() {
    uokik = new Parser(FileLoader.load("assets/uokik.txt"));
  }

  @Test
  public void test() {
    assertTrue(true);
  }
}
