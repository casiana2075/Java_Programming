package org.example;

import java.util.Objects;

public class PairTimeIntervals<T, U> {
    private final T first;
    private final U second;
    public PairTimeIntervals(T first, U second){
         this.first = first;
         this.second=second;
    }

    public T getFirst() {
        return this.first;
    }
    public U getSecond() {
        return this.second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairTimeIntervals<?, ?> that = (PairTimeIntervals<?, ?>) o;
        return Objects.equals(first, that.first) && Objects.equals(second, that.second);
    }

    @Override
    public String toString() {
        return "TimeIntervals{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
