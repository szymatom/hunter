package com.threehunter.config;

import com.threehunter.data.DbCreator;
import com.threehunter.data.InputProvider;
import com.threehunter.listener.ApplicationStarter;
import com.threehunter.repository.ThreeHunterRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.threehunter.repository")
public class HunterConfiguration {

  @Bean
  public DbCreator dbCreator(@Value("${lotto.drawing.file}") String resourcePath,
                             @Value("${lotto.range.closing}") Integer rangeClosing,
                             ThreeHunterRepository repository) {
    return new DbCreator(new InputProvider(resourcePath).provide(), rangeClosing, repository);
  }

  @Bean
  @ConditionalOnProperty(name = "ft.enable.starter")
  public ApplicationStarter getStarter(DbCreator dbCreator) {
    return new ApplicationStarter(dbCreator);
  }
}
