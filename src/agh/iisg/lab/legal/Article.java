package agh.iisg.lab.legal;

import java.util.List;

/**
 * Article
 * Corresponds to "Article" written as "Art. {index as number}" where index
 * starts at 1 and is global.
 */
public class Article extends LegalPartition {
  public Article(String rawContent) {
    super(rawContent);
  }

  public List<LegalPartition> getSections() {
    return partitions;
  }
}
