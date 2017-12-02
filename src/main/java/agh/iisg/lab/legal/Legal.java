package agh.iisg.lab.legal;

import java.util.List;
import java.util.regex.Pattern;

public interface Legal {
  String getContent();

  void setContent(String content);

  List<Legal> getPartitions();

  void setPartitions(List<Legal> partitions);

  String getNumber();

  void setNumber(String number);

  String getTitle();

  void setTitle(String Title);
}
