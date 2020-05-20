package com.threehunter.entity;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.ToString;

import static java.lang.Math.max;

@Getter
@ToString
public class Niner {

  private final List<Triplet> triplets;

  private int longestDrySpell;
  private List<Integer> successfulDraws;
  private int numberOfSuccesses;

  private Niner(List<Triplet> triplets) {
    this.triplets = triplets;
  }

  public static Optional<Niner> of(List<Triplet> triplets) {
    if (isValid(triplets)) {
      Niner niner = new Niner(triplets);
      if (niner.getSuccessfulDraws()) {
        niner.prepareStats();
        return Optional.of(niner);
      }
    }
    return Optional.empty();
  }

  private boolean getSuccessfulDraws() {
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
  }

  private static boolean isValid(List<Triplet> triplets) {
    return triplets.stream()
        .map(Triplet::getNumbers)
        .collect(Collectors.toSet()).size() == 9;
  }

  private int distanceBetweenTwo(int beginning) {
    return successfulDraws.get(beginning + 1) - successfulDraws.get(beginning);
  }
}
