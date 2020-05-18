package com.threehunter.config;

import com.threehunter.data.InputProvider;
import com.threehunter.data.StatsEngine;
import com.threehunter.listener.ApplicationStarter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HunterConfiguration {
  @Bean
  public StatsEngine statsEngine(@Value("${lotto.drawing.file}") String resourcePath,
                                 @Value("${lotto.range.closing}") Integer rangeClosing) {
    return new StatsEngine(new InputProvider(resourcePath).provide(), rangeClosing);
  }

  @Bean
  @ConditionalOnProperty(name = "ft.enable.starter")
  public ApplicationStarter getStarter(StatsEngine statsEngine) {
    return new ApplicationStarter(statsEngine);
  }
}
