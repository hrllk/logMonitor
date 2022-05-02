package com.hrchild.monitor.integrator.ftp.model;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ServerInfoConfTest {

  @Autowired
  @Qualifier("monitorConf")
  MonitorConfig config;

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}
}
