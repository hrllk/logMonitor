package com.hrchild.monitor.integrator.ftp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@Slf4j
public class MonitorApp {
  private static ApplicationContext applicationContext;

  public static void main(String[] args) {
    SpringApplication.run(MonitorApp.class, args);
  }
}
