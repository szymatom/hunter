package com.threehunter.listener;

import com.threehunter.data.DbCreator;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApplicationStarter implements ApplicationListener<ApplicationStartedEvent> {

  private final DbCreator dbCreator;

  @Override
  public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
    dbCreator.create();
  }
}
