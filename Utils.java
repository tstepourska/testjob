package com.mycompany.myapp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;


import javax.naming.InitialContext;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConstants;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import com.mycompany.myapp.model.*;

/**
 * 
 * @author Txs285
 *
 */
public class Utils {
	private static Logger log = Logger.getLogger(Utils.class);
	
    public static String unicodeEscaped(char ch) {
        if (ch < 0x10) {
            return "\\u000" + Integer.toHexString(ch);
        } else if (ch < 0x100) {
            return "\\u00" + Integer.toHexString(ch);
        } else if (ch < 0x1000) {
            return "\\u0" + Integer.toHexString(ch);
        }
        return "\\u" + Integer.toHexString(ch);
    }
    
	public static boolean isInCarryOnList(int status){
		for(int s: Constants.ERRORS_TO_CARRY_OVER){
			if(status==s)
				return true;
		}
		
		return false;
	}


	public static String getDataProvider(String filename) throws Exception {
		String dp = null;
		String tmp = filename.toLowerCase();
		for(int i=0;i<Globals.DATA_PROVIDERS.length;i++){
			if(tmp.indexOf(Globals.DATA_PROVIDERS[i])>=0){
				dp = Globals.DATA_PROVIDERS[i];
				break;
			}
		}
			
		return dp;
	}
	
	/**
	 * Handles up to 9 digits
	 * @param num
	 * @return
	 */
	public static String padPackageId(String s, int paddedLength) {
		String fp = "padPackageId: ";
		String result = "";
		//int MAXLEN = 9;
		
		if(s==null || s.isEmpty()){			//empty
			result = null;
		}
		
		//validate number
		try {
			long num = Long.parseLong(s);
		}
		catch(Exception e){
			log.error(fp + "Error parsing package id out of string, returning null");
			return null;
		}
		
		int len = s.length();
		if(len>paddedLength){
			log.error(fp + "String length is greater than allowed 9 digit, returning null");
			return null;
		}
		int loops = paddedLength - len;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<loops;i++){
			sb.append("0");
		}
		sb.append(s);
		result = sb.toString();
		//log.info(fp + "result: " + result);

		return result;
	}
	
	public static XMLGregorianCalendar generateReportingPeriod(String year, String month, String day) throws Exception {
		//year must be valid
		int iYear = Integer.parseInt(year);
		//month and day might be null, then set defaults
		int iMon = 12;
		int iDay = 31;
		try { 
			iMon = Integer.parseInt(month); 
			try{ 
				iDay = Integer.parseInt(day); 
			}
			catch(Exception e){ 
				iDay = -1; 
			}
		}
		catch(Exception e){ 
			iMon = 12; 
			iDay = 31;
		}
	
		XMLGregorianCalendar taxyear = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
			
			taxyear.setTime(DatatypeConstants.FIELD_UNDEFINED,
							DatatypeConstants.FIELD_UNDEFINED,
							DatatypeConstants.FIELD_UNDEFINED);
			taxyear.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			
			if(iDay>0)
				taxyear.setDay  (iDay);
			else
				taxyear.setDay(DatatypeConstants.FIELD_UNDEFINED);
			taxyear.setMonth(iMon);
			taxyear.setYear (iYear);

		return taxyear;
	}
	
	/**
	 * Creates XMLGregorianCalendar object which produces timestamp in a format 
	 * YYYY-MM-DD'T'hh:mm:ss
	 * 
     * This data element identifies the date and time when the message was compiled. 
     * It is anticipated this element will be automatically populated by the host system. 
     * The format for use is YYYY-MM-DD’T’hh:mm:ss. Fractions of seconds may be used. 
     * Example: 2018-02-15T14:37:40
	 * 
	 * @param ts
	 * @return
	 * @throws Exception
	 */
	public static XMLGregorianCalendar generateStatusMessageXMLTimestamp(long ts) throws Exception {
		String strdate = Constants.sdfMessageTsUTC.format(new Date(ts));
		//log.debug("generateStatusMessageXMLTimestamp: stringdate: " + strdate);
		XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(strdate);
		//log.debug("generateStatusMessageXMLTimestamp: returning cal: " + cal);
		return cal;
	}
	
	public static Timestamp generateSweepTimestamp(String sweepTimestampString) throws Exception {
		//Date dt = new Date(ts);
		if(log.isDebugEnabled())
		log.debug("generateSweepTimestamp: sweepTimestampString: " + sweepTimestampString);
		//String strdate = runDate + "T" + runTime;
		Date dt = Constants.sdfMessageTs.parse(sweepTimestampString);
		long t = dt.getTime();
		
		return new Timestamp(t);
	}
	
	public static XMLGregorianCalendar generateSweepXMLGregorianCalendar(String sweepTime) throws Exception {
		
		int year = Integer.parseInt(sweepTime.substring(0,4)); 
		int month = Integer.parseInt(sweepTime.substring(4,6)); 
		int day = Integer.parseInt(sweepTime.substring(6,8)); 
		int hour = Integer.parseInt(sweepTime.substring(9,11)); 
		int minute = Integer.parseInt(sweepTime.substring(11,13)); 
		int second = Integer.parseInt(sweepTime.substring(13,15)); 
		//log.debug("year="+year+", month=" +month+", day="+day+", hour="+hour+", minute="+minute+", second=" + second);
		int millisecond = DatatypeConstants.FIELD_UNDEFINED;
		int timezone = DatatypeConstants.FIELD_UNDEFINED;
		XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar( year,  month,  day,  hour, minute, second, millisecond,timezone);
		//log.debug("generateSweepXMLGregorianCalendar: returning cal: " + cal);

		return cal;
	}
	
	public static String generateSweepTimestampFromETRString(String t) throws Exception {
		StringBuilder sb = new StringBuilder();
		char c;
		for(int i=0;i<t.length();i++){
			c = t.charAt(i);
			if(c!=':' && c!='-')
				sb.append(c);
		}

		return sb.toString();
	}
		


	public static String generateMessageRefID(String sendingCountry, String receivingCountry, XMLGregorianCalendar cal, int packageId, boolean isStatus) throws Exception {
		//String fp = "generateMessageRefID:";
		String taxYear = "0000";
		if(cal!=null)
			taxYear  = ""+cal.getYear();
		
		StringBuilder sb = new StringBuilder();
		
		if(isStatus)
			sb.append("Status");
		//create message
		sb.append(sendingCountry).append(taxYear).append(receivingCountry).append(padPackageId(""+packageId,Constants.MAX_PACKAGE_LENGTH));
		//if(log.isDebugEnabled())
		//	log.debug(fp + "messageRefID: " + sb.toString());

		return sb.toString();
	}
	
	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean isFileXML(String filename){
		//XML file
		String pattern = "(.*)(\\.[xX][mM][lL])";

	    // Create a Pattern object
	    Pattern r = Pattern.compile(pattern);
	    // Now create matcher object.
	    Matcher m = r.matcher(filename);
	      
		if(m.find())
			return true;
		
		return false;
	}

	/**
	 * @param filename
	 */
	public static void deleteFile(String filename) {
		if (filename!=null && filename.trim().length()>0) {
			File file = new File(filename);
			int attempts = Constants.maxAttempts;
			while (file.exists() && !file.delete() && attempts-- > 0)
				Thread.yield();
		}
	}
	
	/**
	 * Delete file.
	 * Called by BatchProcessBean for removing processed input files
	 * 
	 * @param path
	 * @return
	 */
	public static boolean removeFile(File inputFile) {

		boolean isDeleted = false; 

		try {
			isDeleted = inputFile.isFile() && inputFile.delete();
			if (isDeleted) {
				log.info("File deleted: '" + inputFile.getPath() + "'");
			}
			else {
				log.error("File not deleted: '" + inputFile.getPath() + "'");
			}
		}
		catch (SecurityException ex) {
			log.error("File not deleted. Insufficient permissions to delete the file. Exception: " + ex.toString());
		}
		catch (Throwable ex) {
			log.error("File not deleted. An exception occurred: " + ex.toString());
		}
		
		return isDeleted;
	}
	
	/**
	 * Delete files.
	 * Called by BatchProcessBean for removing temporary and interim output files
	 * 
	 * @param filesToDelete
	 * @return
	 */
	public static void removeFiles(List<String> filesToDelete) {
	 
		for (String pathName : filesToDelete) {
			File file = new File(pathName);
			if (file != null) {
				removeFile(file);
			}
		}
	}
	
	
	
	public static void cleanXmlFile(String path, String origFileName){
	
		String line = null;
		File file = null;
		File newfile = null;
		
		try {
			file = new File(path+origFileName);
			newfile = new File(path+origFileName +".OUTPUT");
		}
		catch(Exception e){
			Utils.logError(log, e);
		}
		
		try (BufferedReader bReader= new BufferedReader(new FileReader(file));
			FileWriter writer = new FileWriter(newfile);
		){
			while((line = bReader.readLine())!=null){
				line = line.replaceAll("[^\\x20-\\x7e]", "");
				writer.write(line);
				writer.flush();
			}
		//String XString = writer.toString();
		//XString = XString.replaceAll("[^\\x20-\\x7e]", "");
		}
		catch(Exception e){
			Utils.logError(log, e);
		}
	}
	
	public static String xmlToString(String path, String filename){
		
		String line = null;
		File file = null;
		StringBuilder sb = new StringBuilder();
		sb.append("|");
		
		try {
			file = new File(path+filename);
		}
		catch(Exception e){
			Utils.logError(log, e);
		}
		
		try (BufferedReader bReader= new BufferedReader(new FileReader(file));
		){
			while((line = bReader.readLine())!=null){
				sb.append(line);			
			}
			sb.append("|");
		}
		catch(Exception e){
			Utils.logError(log, e);
		}
		return sb.toString();
	}
	
	
	public static void logError(Logger lg, Exception e){
		lg.error("Exception: " + e.getMessage());
  	  /*	if(lg.isDebugEnabled()){
  		  StackTraceElement[] trace = e.getStackTrace();
  		  if(trace!=null){
  			  for(StackTraceElement tLine : trace){
  				  lg.error(tLine);
  			  }
  		  }
  	  	}*/
	}
	
	public static void logError(Logger lg, String s){
		lg.error("Exception: " + s);
	}
	
	public static void logErrorDetails(Logger lg, Exception e){
		lg.error("Exception: " + e.getMessage());
  	  	if(lg.isDebugEnabled()){
  		  StackTraceElement[] trace = e.getStackTrace();
  		  if(trace!=null){
  			  for(StackTraceElement tLine : trace){
  				  lg.error(tLine);
  			  }
  		  }
  	  	}
	}
	
	/**
	 * Checks and adjusts docTypeIndic string value according to 
	 * the environment (for the case where Infodec does not have 
	 * test environment)
	 * 
	 * @param docTypeIndic
	 * @param isTest
	 * @return
	 */
	public static String adjustDocTypeIndic(String docTypeIndic, boolean isTest){
		String fp = "adjustDocTypeIndic: ";
		
		StringBuilder sb = new StringBuilder();
		sb.append("OECD");
		
		try{
			String s = docTypeIndic.substring(4);
			int n = Integer.parseInt(s);
			
			if(isTest){
				if(n<10)
					n = n + 10;
			}
			sb.append(n);
		}
		catch(Exception e){
			log.error(fp + "Error: " + e.getMessage());
		}
		return sb.toString();
	}
	
	
	public static XMLGregorianCalendar generateMetadataTaxYear(String year) throws Exception {
		String fp = "generateMetadataTaxYear: ";
		
		log.debug(fp + "year: " + year);
		
		//year must be valid
		int iYear = 0;
		
		try{
			iYear = Integer.parseInt(year);
		}
		catch(Exception e){
			log.error(fp + "error parsing year");
		}
		XMLGregorianCalendar taxyear = null;
			taxyear = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
			
			taxyear.setTime(DatatypeConstants.FIELD_UNDEFINED,
							DatatypeConstants.FIELD_UNDEFINED,
							DatatypeConstants.FIELD_UNDEFINED);
			taxyear.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			
			taxyear.setDay  (DatatypeConstants.FIELD_UNDEFINED);
			taxyear.setMonth(DatatypeConstants.FIELD_UNDEFINED);
			taxyear.setYear (iYear);

		return taxyear;
	}
	
	public static void printClasspath(){
		try {
		String clazzpath = System.getProperty("java.class.path"); 
		System.out.println("\nfxit.XMLT: clazzpath: " + clazzpath);
		
		ClassLoader loader = Utils.class.getClassLoader();
		URL loaderUrl = loader.getResource("ca/gc/cra/fxit/xmlt/util/Utils.class");
		System.out.println("\nfxit.XMLT: loader Url path: " + loaderUrl);
		//System.out.println("\nfxit.XMLT: loader Url host: " + loaderUrl.getHost());

		String executionDir = (new File(".")).getAbsolutePath();
		System.out.println("fxit.XMLT: execution directory: " +executionDir);
		}
		catch(Exception e){
			
		}
	}
	
	public static RollingFileAppender setupLogger(Level level){
		RollingFileAppender fa = null;
		try {
			fa = new RollingFileAppender();
			fa.setName("DailyFile");
		 		  
			// fa.setFile("mylog.log");
			// fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
			fa.setFile("./logs/application/fxit.xmlt.${weblogic.Name}.log");		 
			//fa.setFile(arg0, arg1, arg2, arg3) do not use this method directly, instead  set its properties one by one and then call activateOptions
			// fa.setLayout(new PatternLayout("%d{ISO8601}-%-5p-%c-%m%n"));
			fa.setLayout(new PatternLayout("%d{ISO8601}-%-5p-%c{2}-%m%n"));
			fa.setThreshold(level);
			fa.setAppend(true);
			fa.activateOptions();
		}
		catch(Exception e){
			System.out.println(new Timestamp(System.currentTimeMillis()) + ": fxit.XMLT.Utils: error setting up logger: " + e.getMessage());
		}
		  //add appender to any Logger (here is root)
		  //Logger.getRootLogger().addAppender(fa);
		  return fa;
	}
	
	/**
	 * Converts Timestamp in local time to Timestamp in UTC
	 * TODO: BEFORE CALLING THIS METHOD FROM PackageInfoFactory, 
	 * 		 REMOVE THE toUTC() CALL FROM @see Utils.generateXMLFileName !!!
	 * 
	 * @param time
	 * @return
	 */
	public static Timestamp localToUTC(Timestamp time){
		String fp = "localToUTC: ";
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS zzz");
		//SimpleDateFormat sdfUtc = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS 'UTC'");
		TimeZone dtz = TimeZone.getDefault();
		log.debug(fp + "dtz: " + dtz.getID());
		
		long localTimeZoneoffset = dtz.getOffset(0L);
		//log.debug("localTimeZoneoffset: " + localTimeZoneoffset);
		
		long dstOffset = dtz.getDSTSavings();
		//log.debug("dstOffset: " + dstOffset);
		
		long offsetOftime = dtz.getOffset(time.getTime());
		//log.debug("offsetOftime: " + offsetOftime);
		
		long timeinmilli = 0L; 
		if(offsetOftime != localTimeZoneoffset){
			log.debug(fp + "applying DST");
			timeinmilli = time.getTime()-localTimeZoneoffset-dstOffset;			
		}
		else{
			timeinmilli = time.getTime()-localTimeZoneoffset;
		}
		Timestamp ts =  new Timestamp(timeinmilli);
		//log.debug(fp + "UTC: " + ts);
		
		return ts;
	}
	
	public static void main(String[] args){
		//String filename = "fxit.ctsagent.batch.xml";	
		//String path = "C:/git/repository/CTS_dataprep/implementation/cfg/";
		//Utils.cleanXmlFile(path, filename);
		//String ts = Utils.toUTC(new Date(System.currentTimeMillis()));
		String year = "2016";
		String month = null;
		String day = null;
		
		try {
			Timestamp est = new Timestamp(System.currentTimeMillis());
			Timestamp utc = localToUTC(est);
			System.out.println("Current EST: " + est + ", UTC: " + utc);
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		
		/*try {
		XMLGregorianCalendar cal = generateReportingPeriod(year,  month,  day);
		int yr = cal.getYear();
		log.info("year: " + yr);
		}
		catch(Exception e){
			
		}*/
	}
}
