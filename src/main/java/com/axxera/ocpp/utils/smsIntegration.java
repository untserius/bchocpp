package com.axxera.ocpp.utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.axxera.ocpp.config.EmailServiceImpl;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@Service
public class smsIntegration {
	static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
	@Value("${sms.account_sid}")
	String sms_account_sid;
	@Value("${sms.auth_token}")
	String sms_auth_token;
	@Value("${sms.from_number}")
	String sms_from_phone;
	@Autowired
	private LoggerUtil customeLogger;
	
	@Value("${symbol.currency}")
	private String currency;
    
    public String sendSMSToUser(final HashMap<String, String> reqObjMap) {
        String toPhoneNo = null;
        String sendTextmessage = null;
        String responseMesg = null;
        String stationId = null;
        try {
        	LOGGER.info("45>>>ACCOUNT_SID>>>" + sms_account_sid);
        	LOGGER.info("46>>>AUTH_TOKEN>>>" + sms_auth_token);
        	LOGGER.info("47>>>FROM_PHONE_NO>>>" + sms_from_phone);
            if (reqObjMap != null && !reqObjMap.isEmpty()) {
                if (reqObjMap.containsKey("toPhoneNo")) {
                    toPhoneNo = reqObjMap.get("toPhoneNo");
                }
                if (reqObjMap.containsKey("smsMessage")) {
                    sendTextmessage = reqObjMap.get("smsMessage");
                }
                if (reqObjMap.containsKey("stationId")) {
                    stationId = reqObjMap.get("stationId");
                }
                LOGGER.info("53>>>stationId>>>" + stationId + ">>>toPhoneNo>>>" + toPhoneNo + ">>>sendTextmessage>>>>" + sendTextmessage);
                if (toPhoneNo != null && sendTextmessage != null && stationId != null) {
                    Twilio.init(sms_account_sid, sms_auth_token);
                    try {
                        final Message message = (Message)Message.creator(new PhoneNumber(toPhoneNo), new PhoneNumber(sms_from_phone), sendTextmessage).create();
                        LOGGER.info("SMS Send Successfully....");
                        responseMesg = "success";
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        responseMesg = "fail";
                        LOGGER.error(String.valueOf(new StringBuilder().append(e)));
                    }
                }
                else {
                    responseMesg = "fail";
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            responseMesg = "fail";
            //LOGGER.error(String.valueOf(new StringBuilder().append(e)));
        }
        LOGGER.info("71>>>responseMesg>>>" + responseMesg);
        LOGGER.info("end sendSMSToUser");
        return responseMesg;
    }
    
    public void sendSMSUsingBootConfg(String stationId,String sessionId,String msgType,Double amount,String toNumber) {
    	int letterCnt = toNumber.length();
    	final String toNum = toNumber;
    	if(letterCnt > 8) {
			if(!toNumber.substring(0, 2).equalsIgnoreCase("+1")) {
				toNumber = "+1".concat(toNumber);
			}
    	}
    	try {
    		Thread th = new Thread() {
        		public void run() {
        			String messageFormat = "";
        	    	customeLogger.info(stationId, "smsSendingUsingBootConfg started.......");
        			try {
        				if(letterCnt > 8) {
        					String appName = "BC Hydro";
        					String revenueStr = "";
        					if(msgType != null && msgType.equals("PAYGAuth")){
        						messageFormat = ""+appName+" Charging Station ID "+stationId+" . Your charging session has started. We have placed an authorization hold of $40 on your card that will be released at the end of the transaction.";
        					}else if(msgType != null && msgType.equals("NrmlUserStop")){
        						DecimalFormat df1 = new DecimalFormat("##0.00");
        						revenueStr = df1.format(amount);
        						messageFormat = ""+appName+" Charging Station ID "+stationId+" . Your charging session has stopped. Total cost of session : "+currency+""+revenueStr;
        					}else if(msgType != null && msgType.equals("GuestStop")){
        						DecimalFormat df1 = new DecimalFormat("##0.00");
        						revenueStr = df1.format(amount);
        						messageFormat = ""+appName+" Charging Station ID "+stationId+" . Your charging session is over. Your Session Id is "+sessionId
        								+" and Session Cost is "+currency+""+revenueStr+" . Your initial pre-authorization amount will be released.";
        					}else if(msgType != null && msgType.equals("GuestStart")){
        						messageFormat = ""+appName+" Charging Station ID "+stationId+". We have authorised "+currency+"40.00 as pre-authorization.";
        					}
        					customeLogger.info(stationId, "SMS messageFormat : "+messageFormat);
        					HashMap<String,String> reqObjMap = new HashMap<String,String>();
        					reqObjMap.put("toPhoneNo", toNum);
        					reqObjMap.put("smsMessage", messageFormat);
        					reqObjMap.put("stationId", stationId);
        					sendSMSToUser(reqObjMap);
        					customeLogger.info(stationId,"smsSendingUsingBootConfg success .......");
        				}else {
        					customeLogger.info(stationId,"Invalid Phone Number to send SMS .......");
        				}
        			} catch (Exception e) {
        				e.printStackTrace();
        				customeLogger.info(stationId,"smsSendingUsingBootConfg failed .......");
        			}
        			customeLogger.info(stationId,"smsSendingUsingBootConfg ended .......");
        		}
        	};
        	th.start();
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	LOGGER.info("end sendSMSUsingBootConfg");
    }
}
