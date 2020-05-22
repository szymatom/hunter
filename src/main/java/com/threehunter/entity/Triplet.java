package com.threehunter.entity;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;

import static java.lang.Math.max;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Getter
public class Triplet {
  private final List<Integer> numbers;
  private final List<Set<Integer>> drawings;

  private int longestDrySpell;
  private List<Integer> successfulDraws;
  private int numberOfSuccesses;
  private double successPercentage;

  private Triplet(List<Integer> numbers, List<Set<Integer>> drawings) {
    this.numbers = numbers;
    this.drawings = drawings;
  }

  @Override
  public String toString() {
    return String.format("Triplet: %d, %d, %d; ", numbers.get(0), numbers.get(1), numbers.get(2));
  }

  public static Triplet of(List<Integer> numbers, List<Set<Integer>> drawings) {
    Triplet triplet = new Triplet(numbers, drawings);
    triplet.prepareStats();
    return triplet;
  }

  private void prepareStats() {
    successfulDraws = new ArrayList<>();
    for (int i = 0; i < drawings.size(); i++) {
      if (!isEmpty(Sets.intersection(drawings.get(i), new HashSet<>(numbers)))) {
        successfulDraws.add(i);
        longestDrySpell = max(distanceBetweenTwoLast(), longestDrySpell);
        numberOfSuccesses++;
      }
    }
    updateLongestDrySpell();
    successPercentage = (double) numberOfSuccesses / (double) drawings.size();
  }

  private void updateLongestDrySpell() {
    if(nonNull(successfulDraws) && !successfulDraws.isEmpty()) {
      longestDrySpell = max(successfulDraws.get(0), longestDrySpell);
      longestDrySpell = max(drawings.size() - successfulDraws.get(successfulDraws.size() - 1), longestDrySpell);
    } else {
      longestDrySpell = drawings.size();
    }
  }

  private int distanceBetweenTwoLast() {
    return successfulDraws.size() > 1 ?
        successfulDraws.get(successfulDraws.size() - 1) - successfulDraws.get(successfulDraws.size() - 2) :
        0;
  }
}
