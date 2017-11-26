package agh.iisg.lab;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    List<String> lines = FileLoader.load("assets/konstytucja.txt");

    final Parser parser = new Parser(lines);

    int a = 1;

    System.out.println(parser.parse().get(10)
                             .getPartitions().get(0)
                             .getPartitions().get(3)
                             .getPartitions().get(2)
                             .getContent());
  }
}
