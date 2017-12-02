package agh.iisg.lab;

import agh.iisg.lab.legal.Legal;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class PartitionGenerator {
  private AtomicInteger counter;
  private int index;

  public PartitionGenerator(int index, AtomicInteger counter) {
    this.index = index;
    this.counter = counter == null ? new AtomicInteger(0) : counter;
  }

  public int getIndex() {
    return index;
  }

  public AtomicInteger getCounter() {
    return counter;
  }
}
