package br.com.leonardoferreira;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class Pair<F, S> {

    private final F first;

    private final S second;

    private Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }

    public static <F, S> Pair<F, S> of(final F first, final S second) {
        return new Pair<>(first, second);
    }

    public static <F, S> Collector<Pair<F, S>, ?, Map<F, S>> toMap() {
        return Collectors.toMap(Pair::getFirst, Pair::getSecond);
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}