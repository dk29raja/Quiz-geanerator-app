package sample;

import java.util.concurrent.atomic.AtomicInteger;

public class Randomize {

    private static final AtomicInteger counter = new AtomicInteger();

    public static int nextValue() {
        return counter.getAndIncrement();
    }
}


