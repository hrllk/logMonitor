package com.hrchild.monitor.integrator.ftp;

import com.hrchild.monitor.integrator.ftp.service.WatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Runner implements CommandLineRunner {

  @Autowired WatchService watchService;

  @Override
  public void run(String... args) throws Exception {
    /* MONITORING */
    log.debug("Runner.run() method");
    watchService.executeMonitoring();
  }
}
