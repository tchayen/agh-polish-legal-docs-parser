package agh.iisg.lab;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
  static List<String> load(String path) {
    List<String> lines = new ArrayList<String>();
    try {
      lines = Files.readAllLines(Paths.get(path));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }
}
