package agh.iisg.lab;

import agh.iisg.lab.legal.Legal;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class PartitionGenerator {
  private AtomicInteger counter;
  private Supplier<Legal> supplier;

  public PartitionGenerator(Supplier<Legal> supplier, AtomicInteger counter) {
    this.supplier = supplier;
    this.counter = counter == null ? new AtomicInteger(0) : counter;
  }

  public Supplier<Legal> getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier<Legal> supplier) {
    this.supplier = supplier;
  }

  public AtomicInteger getCounter() {
    return counter;
  }
}
