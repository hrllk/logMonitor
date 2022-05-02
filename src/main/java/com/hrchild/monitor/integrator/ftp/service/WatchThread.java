package com.hrchild.monitor.integrator.ftp.service;

import com.hrchild.monitor.integrator.ftp.model.EmailUtil;
import com.hrchild.monitor.integrator.ftp.model.Instance;
import com.hrchild.monitor.integrator.ftp.model.Mail;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Slf4j
public class WatchThread extends Thread {

  JSch jsch = null;
  Session session = null;
  //  ChannelSftp channelSftp = null;
  ChannelExec channelExec = null;
  int preSP = 0;
  int preEP = 0;
  int sp = 0;
  int ep = 0;

  private List<String> notiKeywords;
  private Mail mail;
  private Instance instance;

  public WatchThread(List<String> notiKeywords, Mail mail, Instance instance) {
    this.notiKeywords = notiKeywords;
    this.mail = mail;
    this.instance = instance;
  }

  @Override
  public void run() {

    log.debug("EXECUTE MONITORING SERVER :[{}]", instance.getName());
    connect();
    log.debug("SUCCESS TO CONNECT TO SERVER");
    executeMonitoring();
    log.debug("DISCONNECT FROM SERVER");
    disConnect();
  }

  /** * 1. Duplication Prevention(pointer) then.. Skip.. 2. Rolling Append */
  public void executeMonitoring() {
    try {

      while (true) {

        Thread.sleep(2000);
        /* check Line Cnt */
        int lineCnt = getLineCnt();
        log.debug("lineCnt: [{}]", lineCnt);
        /* 최초 sp는 linCnt - 1 */
        sp = sp == 0 ? lineCnt - 1 : sp;
        ep = lineCnt;

        if (sp > ep) {
          /* Case When The Log Is Not Printed */
          if (sp - 1 == ep) {
            sp--;
            /** * Case Rolling Append Then init */
            /* Case Rolling Append Log */
          } else {
            sp = 0;
          }
        }

        /* Case When The Log Is Not Printed */
        if (preSP == sp && preEP == ep) {
          log.debug("SKIP");
          continue;
        }

        /* Read Log */
        checkLine();

        /* Prevent Duplicate Line */
        sp = ep + 1;
      }

    } catch (InterruptedException e) {
      log.error("ERROR", e);
    }
  }

  public void connect() {

    try {
      jsch = new JSch();

      session = jsch.getSession(instance.getRemoteAccnt(), instance.getHost(), instance.getPort());
      if (instance.getPassword() != null && !(instance.getPassword().isEmpty())) {
        log.debug("password:[{}]", instance.getPassword());
        session.setPassword(instance.getPassword());
      } else {
        jsch.addIdentity(instance.getAuthKeyPath());
      }

      Properties prop = new Properties();
      prop.put("StrictHostKeyChecking", "no"); // ??
      session.setConfig(prop);
      session.connect();

    } catch (JSchException e) {
      log.error(
          "FAIL TO CONNECT TO INSTANCE([{}]), HOST:[{}] ", instance.getName(), instance.getHost());
    }
  }

  private void disConnect() {
    log.debug("THE SYSTEM HAS BEEN SHUTDOWN");
    channelExec.disconnect();
  }
  //
  private int getLineCnt() {
    try {
      channelExec = (ChannelExec) session.openChannel("exec");
      channelExec.setCommand("cat " + instance.getLogPath() + " | wc -l");
      InputStream is = channelExec.getInputStream();
      channelExec.connect();

      byte[] buffer = new byte[8192];
      int decodedLengthLN;
      StringBuilder resLN = new StringBuilder();
      while ((decodedLengthLN = is.read(buffer, 0, buffer.length)) > 0) {
        resLN.append(new String(buffer, 0, decodedLengthLN));
      }
      channelExec.disconnect();

      return Integer.parseInt(resLN.toString().replaceAll("[^0-9]", ""));
    } catch (JSchException | IOException e) {
      log.error("ERROR", e);
    }
    return 0;
  }

  private void checkLine() {

    try {
      channelExec = (ChannelExec) session.openChannel("exec");
      log.debug("INSTANCE : [{}], CHECKING LINE BETWEEN :[{}] ~ [{}]", instance.getName(), sp, ep);
      preSP = sp;
      preEP = ep;
      channelExec.setCommand("sed -n \'" + sp + "," + ep + "p" + "\' " + instance.getLogPath());
      InputStream is = channelExec.getInputStream();
      channelExec.connect();

      byte[] buffer = new byte[8192];
      int decodedLength;
      StringBuilder response = new StringBuilder();
      while ((decodedLength = is.read(buffer, 0, buffer.length)) > 0) {
        response.append(new String(buffer, 0, decodedLength));
      }

      for (String notiKeyword : notiKeywords) {
        if (response.toString().contains(notiKeyword)) {
          log.debug("[{}] AN {} HAS OCCURRED PLEASE CHECK EMAIL", instance.getName(), notiKeyword);
          mail.setSubject(instance.getName() + "AN " + notiKeyword + " HAS OCCURRED");
          mail.setText(response.toString());

          EmailUtil.send(mail);
        }
      }

    } catch (JSchException e) {
      log.error("ERROR", e);
    } catch (IOException e) {
      log.error("ERROR", e);
    }
  }
}
