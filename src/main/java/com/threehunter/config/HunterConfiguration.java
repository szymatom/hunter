package com.threehunter.config;

import com.threehunter.data.InputProvider;
import com.threehunter.listener.ApplicationStarter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HunterConfiguration {

  @Bean
  public InputProvider inputProvider(@Value("${lotto.drawing.file}") String resourcePath) {
    return new InputProvider(resourcePath);
  }

  @Bean
  @ConditionalOnProperty(name = "ft.enable.starter")
  public ApplicationStarter getStarter(InputProvider inputProvider) {
    return new ApplicationStarter(inputProvider);
  }
}
