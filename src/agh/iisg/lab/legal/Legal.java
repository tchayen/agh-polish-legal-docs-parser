package agh.iisg.lab.legal;

import java.util.List;

public interface Legal {
  String getContent();

  List<Legal> getPartitions();

  void setPartitions(List<Legal> partitions);

  void setContent(String content);
}
