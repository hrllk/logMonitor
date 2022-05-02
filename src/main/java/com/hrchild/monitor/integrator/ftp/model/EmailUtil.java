package com.hrchild.monitor.integrator.ftp.model;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtil {

  private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

  public static void send(final Mail mail) {
    try {

      Properties prop = new Properties();
      prop.put("mail.smtp.host", mail.getHost());
      prop.put("mail.smtp.port", mail.getPort());
      prop.put("mail.smtp.auth", "true");
      prop.put("mail.smtp.starttls.enable", "true");

      Session session =
          Session.getInstance(
              prop,
              new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(mail.getAccount(), mail.getPassword());
                }
              });

      MimeMessage mimeMsg = new MimeMessage(session);
      mimeMsg.setFrom(new InternetAddress(mail.getFrom()));

      String toList = StringUtils.join(mail.getTo(), ",");
      mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toList));

      mimeMsg.setSubject(mail.getSubject(), "utf-8");
      mimeMsg.setText(mail.getText());

      if (mail.getAttach() != null) {
        mimeMsg.setContent((Multipart) mail.getAttach());
      }
      if (mail.getReplyTo() != null) {
        mimeMsg.setReplyTo(InternetAddress.parse(mail.getReplyTo()));
      }

      Transport.send(mimeMsg);
    } catch (MessagingException e) {
      logger.error("FAIL TO SEND MAIL", e);
    }
  }
}
