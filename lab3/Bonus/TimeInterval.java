package org.example;

import java.time.LocalTime;

public class TimeInterval extends PairTimeIntervals<LocalTime, LocalTime>{
    public TimeInterval(LocalTime first, LocalTime second) {
        super(first, second);
    }

    public String getTimeInterval(){
      return getFirst() + " - " + getSecond();
    }
}
