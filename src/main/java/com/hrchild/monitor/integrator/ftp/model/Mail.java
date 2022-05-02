package com.hrchild.monitor.integrator.ftp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class Mail {

  private String from;
  private List<String> to;
  private String replyTo;
  private String bcc;
  private String subject;
  private String text;

  private MultipartFile attach;
  private String host;
  private int port;
  private String account;
  private String password;

  public Mail() {}
}
