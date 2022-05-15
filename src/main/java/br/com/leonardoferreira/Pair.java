package br.com.leonardoferreira;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static <F, S> Stream<Pair<F, S>> stream(final Map<F, S> map) {
        return map.entrySet()
                .stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue()));
    }

    @SafeVarargs
    public static <F, S> Map<F, S> mapOf(final Pair<F, S>... pairs) {
        return Arrays.stream(pairs).collect(Pair.toMap());
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}