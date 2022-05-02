package com.hrchild.monitor.integrator.ftp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Instance {

  private String host;
  private String name;
  private int port;
  private String remoteAccnt;

  private String logPath;
  private String logFileName;

  private String keyName;
  private String keyPath;
  private String authKeyPath;
  private String password;
}
