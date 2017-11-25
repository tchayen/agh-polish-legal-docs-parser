package agh.iisg.lab.legal;

import java.util.List;

public interface Legal {
  String getContent();

  void setContent(String content);

  List<Legal> getPartitions();

  void setPartitions(List<Legal> partitions);
}
