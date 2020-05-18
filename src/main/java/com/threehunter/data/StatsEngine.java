package com.threehunter.data;

import com.google.common.collect.Sets;

import com.threehunter.entity.Triplet;

import org.paukov.combinatorics3.Generator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
public class StatsEngine {

    private final List<Set<Integer>> drawings;
    private final Integer rangeClose;

    public void run() {
      Flux.fromStream(Generator
          .combination(generateRange())
          .simple(3).stream())
          .map(this::getTriplet)
          .subscribe(this::logTriplet);
    }

    private Triplet getTriplet(List<Integer> triplet) {
      return Triplet.of(triplet, drawings);
    }

    private void logTriplet(Triplet triplet) {
      log.info("Triplet: {}, successes: {}, success percentage: {}, longestDrySpell: {}",
          triplet.getNumbers(),
          triplet.getNumberOfSuccesses(),
          triplet.getSuccessPercentage(),
          triplet.getLongestDrySpell());
    }

    private Integer[] generateRange() {
      return IntStream
          .rangeClosed(1, rangeClose)
          .boxed()
          .toArray(Integer[]::new);
    }
}
