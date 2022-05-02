package com.hrchild.monitor.integrator.ftp.model;

import java.util.List;

public class MonitorConfig {

  private List<String> notiKeywords;

  private Mail mail;

  private List<Instance> servers;

  public List<String> getNotiKeywords() {
    return notiKeywords;
  }

  public void setNotiKeywords(List<String> notiKeywords) {
    this.notiKeywords = notiKeywords;
  }

  public Mail getMail() {
    return mail;
  }

  public void setMail(Mail mail) {
    this.mail = mail;
  }

  public List<Instance> getServers() {
    return servers;
  }

  public void setServers(List<Instance> servers) {
    this.servers = servers;
  }

  @Override
  public String toString() {
    return "MonitorConfig{"
        + "notiKeywords="
        + notiKeywords
        + ", mail="
        + mail
        + ", servers="
        + servers
        + '}';
  }
}
