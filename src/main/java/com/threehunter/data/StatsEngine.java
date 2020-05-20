package com.threehunter.data;

import com.threehunter.entity.Niner;
import com.threehunter.entity.Triplet;

import org.paukov.combinatorics3.Generator;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import static java.util.Objects.isNull;

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

    public void runNiner() {
      log.info("Total number of draws: {}", drawings.size());
      final Niner[] mostSuccesses = new Niner[1];
      final Niner[] shortestDrySpell = new Niner[1];
      Flux.fromStream(Generator
          .combination(getTriplets())
          .simple(3).stream())
          .map(this::getNiner)
          .filter(Optional::isPresent)
          .map(Optional::get)
          .subscribe(niner -> {
            if(isNull(mostSuccesses[0]) || mostSuccesses[0].getNumberOfSuccesses() < niner.getNumberOfSuccesses()) {
              mostSuccesses[0] = niner;
            }
            if(isNull(shortestDrySpell[0]) || shortestDrySpell[0].getLongestDrySpell() > niner.getLongestDrySpell()) {
              shortestDrySpell[0] = niner;
            }
          });
          log.info("Most success: {}", mostSuccesses[0]);
          log.info("Shortest dry spell: {}", shortestDrySpell[0]);
    }

    private List<Triplet> getTriplets() {
      return Generator
          .combination(generateRange())
          .simple(3).stream()
          .map(this::getTriplet)
          .collect(Collectors.toList());
    }

    private Optional<Niner> getNiner(List<Triplet> triplets) {
      return Niner.of(triplets);
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
