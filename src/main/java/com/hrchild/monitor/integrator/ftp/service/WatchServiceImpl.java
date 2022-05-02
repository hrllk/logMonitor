package com.hrchild.monitor.integrator.ftp.service;

import com.hrchild.monitor.integrator.ftp.model.Instance;
import com.hrchild.monitor.integrator.ftp.model.MonitorConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WatchServiceImpl implements WatchService {

  @Autowired
  @Qualifier("monitorConf")
  MonitorConfig monitorConfig;

  @Override
  public void executeMonitoring() {
    log.info("executing watcher...");

    for (Instance instance : monitorConfig.getServers()) {
      WatchThread watchThread =
          new WatchThread(monitorConfig.getNotiKeywords(), monitorConfig.getMail(), instance);
      watchThread.start();
    }
  }
}
