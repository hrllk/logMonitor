package com.hrchild.monitor.integrator.ftp;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
// @RunWith(SpringRunner.class)
@Slf4j
public class ThreadTest {

  //  @Qualifier("executor")
  @Autowired ThreadPoolTaskExecutor executor;

  @Test
  public void createThreadTest() {

    log.info("i'm in createThreadTest method");
    Runnable r =
        () -> {
          try {
            log.info(Thread.currentThread().getName() + ", Now sleeping 10 seconds..");
            Thread.sleep(10000);
          } catch (InterruptedException e) {
            //            e.printStackTrace();
            log.error("ERROR", e);
          }
        };
    for (int i = 0; i < 10; i++) {
      executor.execute(r);
    }
  }
}
