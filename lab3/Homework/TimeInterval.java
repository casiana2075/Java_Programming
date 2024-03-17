package org.example;

import java.time.LocalTime;

public class TimeInterval extends PairTimeIntervals<LocalTime, LocalTime>{
    public TimeInterval(LocalTime first, LocalTime second) {
        super(first, second);
    }

    public PairTimeIntervals<LocalTime, LocalTime> getTimeInterval(){
      return new PairTimeIntervals<>(getFirst(), getSecond());
    }
}
