package com.threehunter.entity;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Math.max;
import static java.util.Objects.nonNull;

@Getter
@ToString
@Slf4j
public class Niner {

  private final List<Triplet> triplets;

  private int longestDrySpell;
  private List<Integer> successfulDraws;
  private int numberOfSuccesses;
  private final int numberOfDrawings;

  private Niner(List<Triplet> triplets) {
    this.triplets = triplets;
    numberOfDrawings = triplets.get(0).getDrawings().size();
  }

  public static Optional<Niner> of(List<Triplet> triplets) {
    if (isValid(triplets)) {
      Niner niner = new Niner(triplets);
      if (niner.successfulDraws()) {
        niner.prepareStats();
        return Optional.of(niner);
      }
    }
    return Optional.empty();
  }

  private boolean successfulDraws() {
    successfulDraws = tripletSuccessfulDraws(0).stream()
        .map(result -> {
          if (tripletSuccessfulDraws(1).contains(result) && tripletSuccessfulDraws(2).contains(result))
            return result;
          return null;
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    return !CollectionUtils.isEmpty(successfulDraws);
  }

  private List<Integer> tripletSuccessfulDraws(int index) {
    return triplets.get(index).getSuccessfulDraws();
  }

  private void prepareStats() {
    numberOfSuccesses = successfulDraws.size();
    if (numberOfSuccesses > 1)
      for (int i = 0; i < successfulDraws.size() - 1; i++)
        longestDrySpell = max(distanceBetweenTwo(i), longestDrySpell);

      updateLongestDrySpell();
  }

  private static boolean isValid(List<Triplet> triplets) {
    return triplets.stream()
        .flatMap(triplet -> triplet.getNumbers().stream())
        .collect(Collectors.toSet()).size() == 9;
  }

  private int distanceBetweenTwo(int beginning) {
    return successfulDraws.get(beginning + 1) - successfulDraws.get(beginning);
  }

  private void updateLongestDrySpell() {
    if(nonNull(successfulDraws) && !successfulDraws.isEmpty()) {
      longestDrySpell = max(successfulDraws.get(0), longestDrySpell);
      longestDrySpell = max(numberOfDrawings - successfulDraws.get(successfulDraws.size() - 1), longestDrySpell);
    } else {
      longestDrySpell = numberOfDrawings;
    }
  }

}
