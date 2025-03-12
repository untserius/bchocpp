package com.axxera.ocpp.utils;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.axxera.ocpp.dao.GeneralDao;
import com.axxera.ocpp.message.TransactionForm;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Utils {
	private final static Logger logger = LoggerFactory.getLogger(Utils.class);

	@Autowired
	private LoggerUtil customLogger;
	
	private final RestTemplate restTemplate;
	private final RestTemplate transactionrestTemplate1;
	public Utils(@Qualifier("transactionrestTemplate1") RestTemplate restTemplate1,@Qualifier("restTemplate") RestTemplate restTemplate) {
	        this.transactionrestTemplate1 = restTemplate1;
	        this.restTemplate = restTemplate;
	}
	
	@Autowired
	private GeneralDao<?, ?> generalDao;
	
	@Value("${transaction.url}")
	private String transactionURL;
	
	public Integer randomIntNumber() {
		Integer randomInt = new Integer(0);
		try{
			Random random = new Random();
			randomInt = random.nextInt(1000000000);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return randomInt;
	}
	public String userTimeZone(String desiredTimeZone) {
		 ZonedDateTime currentTime = ZonedDateTime.now(java.time.ZoneId.of("UTC"));
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy hh:mm:ss:SSSa z");
		try {
			currentTime = ZonedDateTime.now(java.time.ZoneId.of(desiredTimeZone));
			return currentTime.format(formatter);
		}catch (Exception e) {
			e.printStackTrace();
		}
		currentTime = ZonedDateTime.now(java.time.ZoneId.of(desiredTimeZone));
		return currentTime.format(formatter);
	}
	public static String getTimeFormate(Double timecon){
		String afterConverTion=null;
		try {
			int minsinsec = (int) (timecon*60);
			int p1 = minsinsec % 60;
			int p2 = minsinsec / 60;
			int p3 = p2 % 60;
			p2 = p2 / 60;
			String str1 = String.format("%02d", p1);
			String str2 = String.format("%02d", p2);
			String str3 = String.format("%02d", p3);
			afterConverTion=str2 +":"+str3+":"+str1;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return afterConverTion;
	}
	
	
	public static String getOneYearUTC()  {
		String time = "";
		try {
			LocalDateTime actualDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
			actualDateTime=actualDateTime.plusYears(1);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			//formatter.withZone(ZoneId.of("UTC"));
			time = actualDateTime.format(formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return time;
	}
	public static String getYearUTC()  {
		String time = "";
		try {
			LocalDateTime actualDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			time = actualDateTime.format(formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return time;
	}
	
	public static String getUTC()  {
		String finalUtctime = null;
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			String utctime = dateFormat.format(date);
			finalUtctime = utctime + "Z";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return finalUtctime;
	}
	
	
	
	public static Date getUtcDateFormate(Date date)  {
		Date parse = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//2020-09-02T07:41:28Z  yyyy-MM-dd'T'HH:mm:ssZ
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			String utctime = dateFormat.format(date);
			parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(utctime);
		}catch (Exception e) {
			e.printStackTrace();
		}
	   return parse;
	}

	public void chargerMessage(WebSocketSession session, String msg,String stationRefNum) {
		try {
			if (session != null && session.isOpen()) {
				customLogger.info(stationRefNum, "message sent to the charger " + msg + "");
				session.sendMessage(new TextMessage(msg));
			} else {
				customLogger.info(stationRefNum, "Session already closed - Response : " + msg + "");
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public static Date getUTCDate()  {
		Date parse = null;
		try {
			Date date = new Date();
			SimpleDateFormat DateFormat = new SimpleDateFormat("MMMM d, yyyy, HH:mm:ss.SSS z");
			DateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			String sysDate = DateFormat.format(date);
			parse = new SimpleDateFormat("MMMM d, yyyy, HH:mm:ss.SSS z").parse(sysDate);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return parse;
	}
	
	public static String getUTCDateWithTimeZone()  {
		String sysDate = null;
		try {
			Date date = new Date();
			SimpleDateFormat DateFormat = new SimpleDateFormat("MMMM d, yyyy, HH:mm:ss z");
			DateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			sysDate = DateFormat.format(date);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sysDate;
	}
	
	public static String getUTCDateString()  {
		String sysDate = null;
		try {
			Date date = new Date();
			SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			sysDate = DateFormat.format(date);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sysDate;
	}
	public static String getDate(Date date) {
		String sysDate = null;
		try {
			SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			sysDate = DateFormat.format(date);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sysDate;
	}
	
	public static String getUtcTime() {
		String sysDate = null;
		try {
			Date date = new Date();
			SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			sysDate = DateFormat.format(date);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sysDate;
	}
	
	public static Date getDateFormate(Date date)  {	
		Date parse = null;
		try {
			parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return parse;
	}
	public static Date getDateFrmt(Date date)  {
		Date parse = null;
		try {
			parse = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return parse;
	}
	public static String getTimeFrmt(Date date)  {
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}
	
	public static Date browserDateFormateToUtcDateFormat(String date)  {
		Date parse = null;
		try {
			Date formatteris = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss").parse(date.replace("GMT", ""));
			
			String parsingdDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(formatteris);
			
			parse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(parsingdDate);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return parse;
	}
	
	public static String browserDateFormate(String date)  {
		String parsingdDate = null;
		try {
			Date formatteris = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss").parse(date.replace("GMT", ""));
			
			parsingdDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(formatteris);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return parsingdDate;
	}
	
	public static String getIntRandomNumber() {
		Random rand1 = new Random();
		String randomValues = String.valueOf(Math.abs(rand1.nextInt()));
		return randomValues;
	}
	
	public boolean getOfflineFlag(Date startTransTime)  {
		try {
			Date currentUtcTime=getUTCDate();
			//Date currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentUtcTime);
			//Date startTransactionTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTransTime);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(currentUtcTime.getTime()-startTransTime.getTime());
			logger.info("minutes : "+minutes);
			if(minutes>10)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static Map<String,Double> getTimeDifferenceInMiliSec(Date startTimeStamp,Date endTimeStamp)  {
		
		Map<String, Double> timeConvertMap=new HashMap<String, Double>();
		
		try {
			//Time difference in Miliseconds
			Long timeDifference=endTimeStamp.getTime()-startTimeStamp.getTime();
			
			//converting to miliseconds to seconds
			double seconds = Math.abs(TimeUnit.MILLISECONDS.toSeconds(timeDifference));
			//converting to miliseconds to Hours
			double hours=TimeUnit.MILLISECONDS.toHours(timeDifference);
			double totalDurationHours= (seconds/3600);
			double totalDurationInminutes=(seconds/60);
			
			timeConvertMap.put("Seconds", seconds);
			timeConvertMap.put("Minutes", totalDurationInminutes);
			timeConvertMap.put("Hours", hours);
			timeConvertMap.put("durationInHours", totalDurationHours);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return timeConvertMap;
	}
	
	
	public Map<String, Double> getTimeInHours_duration(String startTime,String EndTime) {
		Map<String, Double> timeConvertMap=new HashMap<String, Double>();
		try {
			Date startTimeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(startTime);
			Date endTimeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(EndTime);
			
			double MinutesForSessionElapsed=0.0;
			
			long diff = endTimeStamp.getTime()-startTimeStamp.getTime();
			
			long diffSeconds = diff / 1000 % 60;
			//int seconds = (int) diffSeconds;
			long diffMinutes = diff / (60 * 1000) % 60;//(diff/(6000)%60)
			
			long diffHours = diff / (60 * 60 * 1000);
			
				double Mins = (double) (diffHours + ((double) diffMinutes / 60) + ((double) diffSeconds / (60 * 60)));
				double MinutesForSessionElapsedForMaxSession = (double) (diffHours * 60 + ((double) diffMinutes) + ((double) diffSeconds / (60)));
			if (diffSeconds >= 30.0) {
				Mins = (double) (diffHours + ((double) diffMinutes / 60) + ((double) diffSeconds / (60 * 60)));
				MinutesForSessionElapsed = (double) (diffHours * 60 + ((double) diffMinutes) + ((double) diffSeconds / (60)));
			} else {
				Mins = (double) (diffHours + ((double) diffMinutes / 60));
				MinutesForSessionElapsed = (double) (diffHours * 60+ ((double) diffMinutes));
			}
			
			timeConvertMap.put("durationInHours", Mins);
			timeConvertMap.put("MinutesForSessionElapsed", MinutesForSessionElapsed);
			timeConvertMap.put("MinutesForSessionElapsedForMaxSession", MinutesForSessionElapsedForMaxSession);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return timeConvertMap;
	}
	
	public BigDecimal decimalwithtwodecimals(BigDecimal final_Cost) {
				
		try {
			String finalcostString = String.valueOf(final_Cost);
			String finalcostbeforedecimals = finalcostString.split("\\.")[0];			
			String finalcostafterdecimals = finalcostString.substring(finalcostString.indexOf(".")).substring(1, 3);
			String finalcoststringcombined = finalcostbeforedecimals + "." + finalcostafterdecimals;			
			final_Cost = new BigDecimal(finalcoststringcombined);
			//final_Cost = Double.valueOf(new DecimalFormat("##.##").format(final_Cost));
			
		}catch(Exception e) {
			//e.printStackTrace();
		}
		return final_Cost;
	}
	
	public BigDecimal decimalwithFourdecimals(BigDecimal final_Cost) {
		try {
			String finalcostString = String.valueOf(final_Cost);
			String finalcostbeforedecimals = finalcostString.split("\\.")[0];			
			String finalcostafterdecimals = finalcostString.substring(finalcostString.indexOf(".")).substring(1, 5);
			String finalcoststringcombined = finalcostbeforedecimals + "." + finalcostafterdecimals;			
			final_Cost = new BigDecimal(finalcoststringcombined);
			//final_Cost = Double.valueOf(new DecimalFormat("##.##").format(final_Cost));
			
		}catch(Exception e) {
			//e.printStackTrace();
		}
		return final_Cost;
	}
	public String decimalwithtwoZeros(BigDecimal final_Cost) {
		
		String finalcostString = String.valueOf(final_Cost);
		try {
			if(finalcostString.substring(finalcostString.indexOf(".")).length()==2) {
				finalcostString=finalcostString+"0";
			}
		}catch(Exception e) {
			//e.printStackTrace();
		}
		
		return finalcostString;		
	}
	
	public static boolean isPathValid(String path) {
		boolean flag = false;
        try {
        	File file = new File(path);
			if(file.exists()){
				flag = true;
			}
        } catch (Exception ex) {
        	flag=false;
        }
        return flag;
    }
	public String strConverter(String val) {
		try {
			val = val.replaceAll(" ", "");
			val = (val == null ? "0" : val.equalsIgnoreCase("null") ? "0" : val.equalsIgnoreCase("") ? "0" : val);
		}catch(Exception e) {
			e.printStackTrace();
			 val = "0";
		}
		return val;
	}
	
	public String strValid(String str) {
		try {
			str =  str==null ? "0" 
				: str.equalsIgnoreCase("false") ? "0" 
					: str.equalsIgnoreCase("true") ? "0" 
							:  str.equalsIgnoreCase("null") ? "0" 
									: str.equalsIgnoreCase("") ? "0" : str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	public String strValidBit(String str) {
		try {
			str =  str==null ? "false" 
				: str.equalsIgnoreCase("false") ? "false" 
					: str.equalsIgnoreCase("true") ? "true" 
							:  str.equalsIgnoreCase("null") ? "false" 
									: str.equalsIgnoreCase("") ? "false"
											: str.equalsIgnoreCase("0") ? "false"
													: str.equalsIgnoreCase("1") ? "true": str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	public Date stringToDate(String val) {
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = dateFormat.parse(val);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	public String stringToDate(Date date) {
		String val = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			val = dateFormat.format(date);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public static String addHour(int  n)  {
		String time = "";
		try {
			LocalDateTime actualDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
			actualDateTime=actualDateTime.plusHours(n);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			//formatter.withZone(ZoneId.of("UTC"));
			time = actualDateTime.format(formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return time;
	}
	public Date addSec(int n,Date date)  { 
		Date time = null;
		try {
			//LocalDateTime actualDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
			//actualDateTime=actualDateTime.plusSeconds(n);
			
			//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			//formatter.withZone(ZoneId.of("UTC"));
			//time = stringToDate(actualDateTime.format(formatter));
			
			
		    Calendar gcal = new GregorianCalendar();
		    gcal.setTime(date);
		    gcal.add(Calendar.SECOND, n);
		    time = gcal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return time;
	}
	public Date addMin(int n,Date date)  { 
		Date time = null;
		try {
			//LocalDateTime actualDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
			//actualDateTime=actualDateTime.plusSeconds(n);
			
			//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			//formatter.withZone(ZoneId.of("UTC"));
			//time = stringToDate(actualDateTime.format(formatter));
			
			
		    Calendar gcal = new GregorianCalendar();
		    gcal.setTime(date);
		    gcal.add(Calendar.MINUTE, n);
		    time = gcal.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return time;
	}
	public Date addSec(int n)  { 
		Date time = null;
		try {
			LocalDateTime actualDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
			actualDateTime=actualDateTime.plusSeconds(n);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			//formatter.withZone(ZoneId.of("UTC"));
			time = stringToDate(actualDateTime.format(formatter));
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return time;
	}
	public static String addMin(int  n)  { 
		String time = "";
		try {
			LocalDateTime actualDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
			actualDateTime=actualDateTime.plusMinutes(n);
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			//formatter.withZone(ZoneId.of("UTC"));
			time = actualDateTime.format(formatter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return time;
	}
	public String getIntRandomNumber(int digit) {
		String randomValues = "";
		try {
			Random rand1 = new Random();
			randomValues = String.valueOf(Math.abs(rand1.nextInt(digit)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return randomValues;
	}
	public String getRandomNumber(String type) {
		StringBuilder val = new StringBuilder();
		try {
			if(type.equalsIgnoreCase("transactionId")) {
				val.append(System.currentTimeMillis()).append("01");//TransactionId
			}else if(type.equalsIgnoreCase("txnSessionId")) {
				val.append(System.currentTimeMillis()).append("02");//SessionId
			}else if(type.equalsIgnoreCase("RSTP")) {
				val.append(System.currentTimeMillis()).append("03");//RemoteStopTransaction RequestId
			}else if(type.equalsIgnoreCase("UC")) {
				val.append(System.currentTimeMillis()).append("04");//UnlockConnector RequestId
			}else {
				val.append(System.currentTimeMillis());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(val);
	}
	
	public static String getUTCByOne(int days) {
		String finalUtctime = null;
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			String utctime = dateFormat.format(date);
			finalUtctime = utctime + "Z";
			Calendar c = Calendar.getInstance();
			c.setTime(dateFormat.parse(finalUtctime));
			c.add(Calendar.DATE, days); // number of days to add
			String utcTime = dateFormat.format(c.getTime());
			finalUtctime = utcTime + "Z";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalUtctime;
	}
	
	public String getAuthRefNum(String idTag,long stnId,long portId) {
		String recordBySql="";
		try {
			String str = "select portalStation from ocpp_remoteStartTransaction where stationId = '"+stnId+"' and connectorId = '"+portId+"' and idTag = '"+idTag+"' order by id desc;";
			logger.info("str : "+str);
			recordBySql = generalDao.getRecordBySql(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recordBySql;
	}
	
	public static Date getPDTDate() {
		 Date date = null;
		try {
			Timestamp ts=new Timestamp(System.currentTimeMillis()-25200000);  
	        date=ts; 
	        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat.setTimeZone(TimeZone.getTimeZone("PDT"));
			String sysDate = DateFormat.format(date);
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sysDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public double round(double value) {
		try {
	        DecimalFormat df_obj = new DecimalFormat("#.###");
	        String format = df_obj.format(value);
	        value = Double.valueOf(format);
		}catch (Exception e) {
			//e.printStackTrace();
		}
		return value;
	}
	public void apicallingPOST(String url, HttpEntity<Map<String, Object>> requestBody) {
		try {
			Thread newThread = new Thread() {
				public void run() {
					ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);
					logger.info("response : "+response.getHeaders());
					logger.info("response : "+response);
				}
			};
			newThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getuuidRandomId() {
		return String.valueOf(UUID.randomUUID());
	}
	
	public String getStationRandomNumber(long stationId) {
		String number=String.valueOf(UUID.randomUUID()).replace("-", "");
		try {
			number=String.valueOf(stationId)+System.currentTimeMillis();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}	
	public static String uuid() {
		String id = "";
		try {
			id = String.valueOf(UUID.randomUUID());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	public void transactionCalling(String message,String clientId,String type,long stnId) {
		logger.info("start transactionCalling : "+clientId+" , type : "+type);
		try {
			Thread newThread = new Thread() {
				public void run() {
					try {
						String url= transactionURL + "/transaction/request";
						TransactionForm transactionForm=new TransactionForm();
						transactionForm.setClientId(clientId);
						transactionForm.setMessage(message);
						transactionForm.setMessageType(type);
						transactionForm.setStnId(stnId);
						transactionrestTemplate1.postForEntity(url, transactionForm, TransactionForm.class);
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			};
			newThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static boolean isJSONValid(String jsonInString ) {
	    try {
	       if(jsonInString != null && !jsonInString.equalsIgnoreCase("null") && !jsonInString.equalsIgnoreCase("")) {
	    	   final ObjectMapper mapper = new ObjectMapper();
		       mapper.readTree(jsonInString);
		       return true;
	       }else {
	    	   return false;
	       }
	    } catch (Exception e) {
	       return false;
	    }
	    
	}

	public ResponseEntity<String> apicallingPOSTResponse(String url, HttpEntity<Map<String, Object>> requestBody) {
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.postForEntity(url, requestBody, String.class);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return response;
	}
	public static String getReferenceNumber() {
		String value="0";
		try {
			long smallest = 1000000000000000L;
			long biggest = 9999999999999999L;
			long random = ThreadLocalRandom.current().nextLong(smallest, biggest + 1);
			value = String.valueOf(random);
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return value;
	}
	public String getuuidRandomId(long stationId) {
		String number=String.valueOf(UUID.randomUUID());
		try {
			number=String.valueOf(stationId)+number.replace("-", "");
		}catch (Exception e) {
			logger.error("",e);
		}
		return number;
	}
}
