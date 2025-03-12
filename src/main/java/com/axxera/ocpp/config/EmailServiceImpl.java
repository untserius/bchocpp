package com.axxera.ocpp.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.axxera.ocpp.message.MailForm;
import com.axxera.ocpp.utils.LoggerUtil;
import com.axxera.ocpp.utils.Utils;
import com.axxera.ocpp.webScoket.serviceImpl.OCPPUserService;
import com.axxera.ocpp.webScoket.serviceImpl.propertiesServiceImpl;;

@Component
public class EmailServiceImpl {

	static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	private LoggerUtil customeLogger;

	@Autowired
	private propertiesServiceImpl propertiesServiceImpl;

	@Autowired
	private OCPPUserService userService;

	@Value("${customer.Instance}")
	String instance;

	@Value("${file.sessionlogsLocation}")
	private String sessionlogsLocation;

	public void sendEmail(MailForm mail, Map<String, Object> tamplateData, long orgId, String stationRefNum,long stnId) {
		try {
			FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
			bean.setTemplateLoaderPath("/templates/");
			freemarker.template.Template template = null;
			Map<String, Object> whiteLabelOrg = userService.getWhiteLabelOrg(Long.valueOf(stnId));
			Map<String, Object> logoData = userService.logoDeatils(Long.valueOf(whiteLabelOrg.get("orgId").toString()), stationRefNum);
			mail.setImgPath(String.valueOf(logoData.get("url")));
			tamplateData.put("imagePath", String.valueOf(logoData.get("url")));
			Map<String, Object> orgData = userService.getOrgData(orgId, stationRefNum);
			if (tamplateData.get("mailType").toString().equalsIgnoreCase("onClose")) {
				template = bean.createConfiguration().getTemplate("oncloseAlert.ftl");
				mail.setMailSubject(mail.getMailSubject() + " from " + instance + " network");
				mail.setMailFrom(String.valueOf(orgData.get("email")));
				mail.setHost(String.valueOf(orgData.get("host")));
				mail.setPort(String.valueOf(orgData.get("port")));
				mail.setPassword(String.valueOf(orgData.get("password")));
				tamplateData.put("contactUsNo", String.valueOf(orgData.get("phoneNumber")));
				tamplateData.put("portalUrl", String.valueOf(orgData.get("portalLink")));
				tamplateData.put("orgAddress", String.valueOf(orgData.get("address")));
				tamplateData.put("whiteLabelName", String.valueOf(orgData.get("orgName")));
			} else if (tamplateData.get("mailType").toString().equalsIgnoreCase("unsuccessAlert")) {
				template = bean.createConfiguration().getTemplate("unsuccessAlert.ftl");
				mail.setMailSubject(mail.getMailSubject() + " from " + instance + " network");
				mail.setMailFrom(String.valueOf(orgData.get("email")));
				mail.setHost(String.valueOf(orgData.get("host")));
				mail.setPort(String.valueOf(orgData.get("port")));
				mail.setPassword(String.valueOf(orgData.get("password")));
				tamplateData.put("contactUsNo", String.valueOf(orgData.get("phoneNumber")));
				tamplateData.put("portalUrl", String.valueOf(orgData.get("portalLink")));
				tamplateData.put("orgAddress", String.valueOf(orgData.get("address")));
				tamplateData.put("whiteLabelName", String.valueOf(orgData.get("orgName")));
			} else if (tamplateData.get("mailType").toString().equalsIgnoreCase("portStatus")) {
				template = bean.createConfiguration().getTemplate("portStatus.ftl");
				mail.setMailSubject(mail.getMailSubject() + " " + instance + " Network");
				mail.setMailFrom(String.valueOf(orgData.get("email")));
				mail.setHost(String.valueOf(orgData.get("host")));
				mail.setPort(String.valueOf(orgData.get("port")));
				mail.setPassword(String.valueOf(orgData.get("password")));
				tamplateData.put("contactUsNo", String.valueOf(orgData.get("phoneNumber")));
				tamplateData.put("portalUrl", String.valueOf(orgData.get("portalLink")));
				tamplateData.put("orgAddress", String.valueOf(orgData.get("address")));
				tamplateData.put("orgName", String.valueOf(orgData.get("orgName")));
				tamplateData.put("support_mail", String.valueOf(orgData.get("supportEmail")));
				tamplateData.put("support_phone", String.valueOf(orgData.get("phoneNumber")));
				tamplateData.put("to_mail", mail.getMailTo());
			} else if (tamplateData.get("mailType").toString().equalsIgnoreCase("provisionStation")) {
				template = bean.createConfiguration().getTemplate("provisionStations.ftl");
				mail.setMailSubject(mail.getMailSubject() + " " + instance + " Network");
				mail.setMailFrom(String.valueOf(orgData.get("email")));
				mail.setHost(String.valueOf(orgData.get("host")));
				mail.setPort(String.valueOf(orgData.get("port")));
				mail.setPassword(String.valueOf(orgData.get("password")));
				tamplateData.put("contactUsNo", String.valueOf(orgData.get("phoneNumber")));
				tamplateData.put("portalUrl", String.valueOf(orgData.get("portalLink")));
				tamplateData.put("orgAddress", String.valueOf(orgData.get("address")));
				tamplateData.put("whiteLabelName", String.valueOf(orgData.get("orgName")));
			} else if (tamplateData.get("mailType").toString().equalsIgnoreCase("reservation")) {
				template = bean.createConfiguration().getTemplate("reservation.ftl");
				mail.setMailSubject(mail.getMailSubject());
				mail.setMailFrom(String.valueOf(orgData.get("email")));
				mail.setHost(String.valueOf(orgData.get("host")));
				mail.setPort(String.valueOf(orgData.get("port")));
				mail.setPassword(String.valueOf(orgData.get("password")));
				tamplateData.put("contactUsNo", String.valueOf(orgData.get("phoneNumber")));
				tamplateData.put("portalUrl", String.valueOf(orgData.get("portalLink")));
				tamplateData.put("orgAddress", String.valueOf(orgData.get("address")));
				tamplateData.put("whiteLabelName", String.valueOf(orgData.get("orgName")));
			} else if (tamplateData.get("mailType").toString().equalsIgnoreCase("cancelReservation")) {
				template = bean.createConfiguration().getTemplate("cancelReservation.ftl");
				mail.setMailSubject(mail.getMailSubject());
				mail.setMailFrom(String.valueOf(orgData.get("email")));
				mail.setHost(String.valueOf(orgData.get("host")));
				mail.setPort(String.valueOf(orgData.get("port")));
				mail.setPassword(String.valueOf(orgData.get("password")));
				tamplateData.put("contactUsNo", String.valueOf(orgData.get("phoneNumber")));
				tamplateData.put("portalUrl", String.valueOf(orgData.get("portalLink")));
				tamplateData.put("orgAddress", String.valueOf(orgData.get("address")));
				tamplateData.put("whiteLabelName", String.valueOf(orgData.get("orgName")));
			} else if (tamplateData.get("mailType").toString().equalsIgnoreCase("reservationRefund")) {
				template = bean.createConfiguration().getTemplate("reservationRefund.ftl");
				mail.setMailSubject(mail.getMailSubject());
				mail.setMailFrom(String.valueOf(orgData.get("email")));
				mail.setHost(String.valueOf(orgData.get("host")));
				mail.setPort(String.valueOf(orgData.get("port")));
				mail.setPassword(String.valueOf(orgData.get("password")));
				tamplateData.put("contactUsNo", String.valueOf(orgData.get("phoneNumber")));
				tamplateData.put("portalUrl", String.valueOf(orgData.get("portalLink")));
				tamplateData.put("orgAddress", String.valueOf(orgData.get("address")));
				tamplateData.put("whiteLabelName", String.valueOf(orgData.get("orgName")));
			}
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, tamplateData);
			String protocol = String.valueOf(orgData.get("protocol"));
			Thread emailThread = new Thread() {
				public void run() {
					JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
					mailSender.setHost(mail.getHost());
					mailSender.setPort(Integer.valueOf(mail.getPort()));
					mailSender.setUsername(mail.getMailFrom());
					mailSender.setPassword(mail.getPassword());
					Properties javaMailProperties = new Properties();
//					javaMailProperties.put("mail.smtp.starttls.enable", "true");
					javaMailProperties.put("mail.smtp.ssl.enable", "true");
					javaMailProperties.put("mail.smtp.auth", "true");
					javaMailProperties.put("mail.transport.protocol", protocol);
					javaMailProperties.put("mail.debug", "false");
					mailSender.setJavaMailProperties(javaMailProperties);

					MimeMessage mimeMessage = mailSender.createMimeMessage();

					try {
						MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
								MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
						helper.addInline("logo.png", new ClassPathResource("evg.png"));
						helper.setTo(mail.getMailTo());
						helper.setText(html, true);
						helper.setSubject(mail.getMailSubject());
						helper.setFrom(new InternetAddress(String.valueOf(orgData.get("fromEmail"))));
						if (!String.valueOf(tamplateData.get("MailCc")).equalsIgnoreCase("")
								&& !String.valueOf(tamplateData.get("MailCc")).equalsIgnoreCase("null")) {
							helper.setCc(String.valueOf(tamplateData.get("MailCc")).split(","));
						}
						boolean pathValid = Utils.isPathValid(mail.getImgPath());
						MimeMultipart multipart = new MimeMultipart("related");
						BodyPart messageBodyPart = new MimeBodyPart();
						messageBodyPart.setContent(html, "text/html");
						multipart.addBodyPart(messageBodyPart);
						if (pathValid) {
							messageBodyPart = new MimeBodyPart();
							DataSource fds = new FileDataSource(mail.getImgPath());
							messageBodyPart.setDataHandler(new DataHandler(fds));
							messageBodyPart.setHeader("Content-ID", "<logo>");
							multipart.addBodyPart(messageBodyPart);

							mimeMessage.setContent(multipart);
						}
						if (tamplateData.get("mailType").toString().equalsIgnoreCase("stopTxn")) {
							String filename = "session_" + String.valueOf(tamplateData.get("sessionId")) + ".pdf";
							String pdfPath = sessionlogsLocation + stationRefNum + "/"
									+ String.valueOf(tamplateData.get("randomSession")) + "/" + filename;
							boolean pdfPathValid = Utils.isPathValid(pdfPath);
							if (pdfPathValid) {
								messageBodyPart = new MimeBodyPart();
								DataSource source = new FileDataSource(pdfPath);
								messageBodyPart.setDataHandler(new DataHandler(source));
								messageBodyPart.setFileName(filename);
								multipart.addBodyPart(messageBodyPart);
								mimeMessage.setContent(multipart);
							}
						}
						mailSender.send(mimeMessage);
						LOGGER.info("EmailServiceImpl.sendEmail() -mailTo [" + mail.getMailTo()
								+ "] - Successfully Sent !.");
						helper = null;
					} catch (MessagingException e3) {
						e3.printStackTrace();
					}
				}
			};
			emailThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("end sendEmail");
	}

	public void customerSupportMailService(MailForm mail) {
		LOGGER.info("start customerSupportMailService");
		Map<String, Object> orgData = userService.getOrgData(1, "");
		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();

			mail.setMailFrom(String.valueOf(orgData.get("email")));
			mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
			mimeMessage.addHeader("format", "flowed");
			mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");

			mimeMessage.setFrom(new InternetAddress(String.valueOf(orgData.get("email"))));

			mimeMessage.setReplyTo(InternetAddress.parse(String.valueOf(orgData.get("email"))));

			mimeMessage.setSubject(mail.getMailSubject(), "UTF-8");

			mimeMessage.setContent(mail.getMailContent(), "text/html");
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getMailTo(), false));// env.getProperty("customer.To")
			mimeMessage.setSentDate(new Date());
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			customeLogger.info("Error", "Mail Not Send Due to Below Exception : " + e.getMessage() + " , from mailId : "
					+ String.valueOf(orgData.get("email")));
			e.printStackTrace();
		}
		LOGGER.info("end customerSupportMailService");
	}
}