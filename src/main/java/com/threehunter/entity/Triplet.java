package com.threehunter.entity;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;

import static java.lang.Math.max;
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
    successPercentage = (double) numberOfSuccesses / (double) drawings.size();
  }

  private int distanceBetweenTwoLast() {
    return successfulDraws.size() > 1 ?
        successfulDraws.get(successfulDraws.size() - 1) - successfulDraws.get(successfulDraws.size() - 2) :
        0;
  }
}
