package com.hrchild.monitor.integrator.ftp.config;

import com.hrchild.monitor.integrator.ftp.model.MonitorConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@Slf4j
public class ServerInfoConf {

  private static final String confPath = System.getProperty("CONF_HOME");

  @Bean("monitorConf")
  public MonitorConfig getProperties() throws IOException {
    log.debug("created Bean ServerInfoConf.getProperties() method");

    File confFile = new File(confPath);
    if (!confFile.exists()) {
      throw new IllegalStateException("CONF FILE DOES NOT EXIST");
    }

    InputStream is = new FileInputStream(new File(confPath));
    Yaml yml = new Yaml();
    MonitorConfig monitorConfig = yml.loadAs(is, MonitorConfig.class);
    log.debug("monitorConfig:[{}]", monitorConfig);
    return monitorConfig;
  }
}
