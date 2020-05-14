package com.threehunter.data;

import com.google.common.collect.Sets;

import com.threehunter.entity.Matched;
import com.threehunter.entity.ThreeEntity;
import com.threehunter.repository.ThreeHunterRepository;

import org.paukov.combinatorics3.Generator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DbCreator {

  private final List<Set<Integer>> drawings;
  private final Integer rangeClose;
  private final ThreeHunterRepository repository;

  @Transactional
  public void create() {
    Generator.combination(generateRange())
        .simple(3)
        .stream()
        .map(this::getEntity)
        .forEach(repository::save);

    repository.flush();
  }

  private ThreeEntity getEntity(List<Integer> triplet) {
    Set<Integer> three = new HashSet<>(triplet);
    return new ThreeEntity()
        .setTriplet(three)
        .setMatch(getMatched(three));
  }

  private List<Matched> getMatched(Set<Integer> triplet) {
    return drawings
        .stream()
        .map(draw -> Sets.intersection(draw, triplet).immutableCopy())
        .map(intersect -> new Matched().setMatches(intersect))
        .collect(Collectors.toList());
  }

  private Integer[] generateRange() {
    return IntStream
        .rangeClosed(1, rangeClose)
        .boxed()
        .toArray(Integer[]::new);
  }
}
