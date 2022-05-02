package com.hrchild.monitor.integrator.ftp.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class WatchServiceImplTest {

  @Autowired WatchService watchService;

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void startMonitoringTest() {
    log.debug("START MONITROING TEST METHOD");
    watchService.executeMonitoring();
  }
}
