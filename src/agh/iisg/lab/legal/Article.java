package agh.iisg.lab.legal;

import java.util.List;

/**
 * Article
 * Corresponds to "Article" written as "Art. X".
 */
public class Article extends LegalPartition {
  public Article(String rawContent) {
    super(rawContent);
  }

  public List<LegalPartition> getSections() {
    return partitions;
  }
}
