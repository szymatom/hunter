package com.threehunter.listener;

import com.threehunter.data.InputProvider;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApplicationStarter implements ApplicationListener<ApplicationStartedEvent> {

  private final InputProvider inputProvider;

  @Override
  public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
    List<Set<Integer>> input = inputProvider.provide();
    System.out.println("hello world");
  }
}
