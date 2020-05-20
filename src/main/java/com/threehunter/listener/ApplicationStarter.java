package com.threehunter.listener;

import com.threehunter.data.StatsEngine;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApplicationStarter implements ApplicationListener<ApplicationStartedEvent> {

  private final StatsEngine statsEngine;

  @Override
  public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
    statsEngine.runNiner();
  }
}
