package com.threehunter.data;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

public class InputProvider {

  private final String resourceClasspath;

  public InputProvider(String resourceClasspath) {
    this.resourceClasspath = resourceClasspath;
  }

  @SneakyThrows
  public List<Set<Integer>> provide() {
    InputStream inputStream = new ClassPathResource(resourceClasspath).getInputStream();
    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
      return bufferedReader.lines()
          .map(line -> convert(line.split(" ")))
          .collect(Collectors.toList());
    }
  }

  private Set<Integer> convert(String... numbers) {
    return Arrays.stream(numbers)
        .map(Integer::parseInt)
        .collect(Collectors.toSet());
  }
}
