package com.mycompany.myapp.util;

import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mycompany.myapp.main.Main;
import com.mycompany.myapp.step.IStep;

/**
 * Domain properties used by the transformation process.
 * @author Txs285
 */
public class Globals {

	public static Logger log = Logger.getLogger(Globals.class);
/*
	public static String databaseEnvironment = "";
	public static String docTypeIndicEnv = "";
	public static String mailFromAddress = "";
	public static String mailToAddressList = "";
	
	public static String trkUser	= "";
	public static Level loglevel    = null;
	public static String _validationTools = "XMLT 1.0";//"javax.xml.validation 1.7";
	
	//for metadata
	public static String metaContactAddressList = "casd@cra.gc.ca";
	public static boolean sendMailFlag = false;
	*/
	public static String baseFileDir;// 			= null; //"C:/git/repository2/CTS_XMLT/CTS_dataprep/test/testfiles/";
	//public static String baseFileDir 			= "/disk/data/weblogic/domains/wls-domain/applications/data/fxit/xmlt/outbound/unprocessed/";	
	public static String FILE_WORKING_DIR; //	= baseFileDir+ Constants.OUTBOUND_UNPROCESSED_TOSEND_DIR + Constants.TEMP_DIR;	
			
	public static long defaultMaxPkgSize				= 1000000;
//	public static boolean defaultPkgCompressed 			= false; //true;
	public static long fileSizeConstant 				= 2000;
	//public static double PkgCompressionRatio 			= 0.01;
	public static double payloadCompressionRatio 		= 0.2;
	public static double txtToXmlFactor 				= 3;
	public static long fileSignatureSizeConstant 		= 0;//900;

//	public static HashMap<String, FileSize> specificFileSizes = null;
	
	public static String[] DATA_PROVIDERS;
	
	private static Properties map = new Properties();
	private static Map<String, LinkedList<IStep>> jobs = new HashMap<>();

	public static String getProperty(String key){
		return map.getProperty(key);
	}
	
	public static void setProperty(String key, String value){
		map.put(key, value);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static LinkedList<IStep> getJob(String key){
		return jobs.get(key);
	}
	
	public static Map<String, LinkedList<IStep>> getJobs(){
		return jobs;
	}
	
	/**
	 * Load properties:
	 * 		- directory names for transform input/output
	 * 		- mainframe login parameters to use for file transfers from eBCI to mainframe
	 * 
	 */	
	public static void loadDomainProperties() throws NamingException, Exception {
		log.debug("loading Domain Properties..");
		
		try {
			InitialContext context = new InitialContext();		
			baseFileDir = context.lookup(Constants.JNDI_BATCH_DATA_DIRECTORY).toString();
			//set the working directory
			FILE_WORKING_DIR = baseFileDir+ Constants.OUTBOUND_UNPROCESSED_TOSEND_DIR + Constants.TEMP_DIR;

	/*		databaseEnvironment 	= context.lookup(Constants.JNDI_FXMT_ENV).toString();
			docTypeIndicEnv 		= context.lookup(Constants.JNDI_DOCTYPEINDIC_ENV).toString();
			mailFromAddress 		= context.lookup(Constants.JNDI_FXIT_MAIL_FROM_ADDRESS).toString();
			mailToAddressList 		= context.lookup(Constants.JNDI_FXIT_MAIL_TO_ADDRESS).toString();
			//for metadata
			metaContactAddressList 	= context.lookup(Constants.JNDI_FXIT_MAIL_TO_ADDRESS).toString();
			sendMailFlag 			= Boolean.parseBoolean((String)context.lookup(Constants.JNDI_FXIT_MAIL_SEND_FLAG));
		
			try {
				//throw new Exception("loglevel is null");
				loglevel = Level.toLevel((String)context.lookup(Constants.JNDI_LOG_LEVEL));			
			}
			catch(Exception e){
				log.error("Exception loading log level: " + e.getMessage());
				loglevel = Level.DEBUG;
			}
			
		//Logger.getRootLogger().addAppender(Utils.setupLogger(Globals.loglevel));
			//log = Logger.getRootLogger();
			
			//Logger.getLogger(clazz)
			
			log.debug("loaded loglevel: " + loglevel);
			trkUser					= context.lookup(Constants.JNDI_TRK_USER).toString();
			log.info("Domain properties loaded");
			*/
		}
		catch(Exception e){
			log.error("Error loading domain properties: " + e.getMessage());
		}
	}
	
/*	public static void testLoadDomainProperties() throws NamingException, Exception {
		log.debug("testLoadDomainProperties");
		baseFileDir = "C:/run/xmlt/";
		//set the working directory
		FILE_WORKING_DIR = baseFileDir+ Constants.OUTBOUND_UNPROCESSED_TOSEND_DIR + Constants.TEMP_DIR;
		databaseEnvironment 	= "U";
		docTypeIndicEnv 		= "T";
		mailFromAddress 		= "";
		mailToAddressList 		= "";
		metaContactAddressList 	= "";
		sendMailFlag 			= true;	
		trkUser					= "xmltd";
		log.info("Domain properties loaded");
	}*/
	
/*	public static void testLoadBatchProperties() throws Exception {
		//String fp = "testLoadBatchProperties: ";
		DATA_PROVIDERS = new String("crs,cbc,etr").split(",");
		defaultMaxPkgSize = 262144000;
		defaultPkgCompressed  = true;

		//jobs.put();
	}*/
	
	/**
	 * Config file is in the resources directory
	 * @param batchCfgFile
	 * @throws Exception
	 */
	public static void loadJobs() throws Exception {
		String fp = "loadJobs: ";
		String jobCfgFile = Constants.RESOURCE_BASE_PKG + "myapp.cfg.xml";
		if(log.isDebugEnabled())
		log.debug(fp + "job cfg file: " + jobCfgFile);
		InputStream is = null;
 
		//if(specificFileSizes==null)
		//	specificFileSizes = new HashMap<>();
			
        try {
        	//get instance of DocumentBuilderFactory
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        dbf.setNamespaceAware(true);
	        //get instance of XML DocumentBuilder
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        //parse the document using DOM; at this point all doc is already sitting in memory!
	      //  Document doc = db.parse(new File(batchCfgFile));
	        is = Globals.class.getClassLoader().getResourceAsStream(jobCfgFile);
	        Document doc = db.parse(is);
           // log.debug(fp + "Document parsed");
           
            //getting jndi lookup name
           // NodeList nodes = doc.getElementsByTagName("jndi-name");
           // nodes = nodes.item(0).getChildNodes();
          //  batchJNDI = nodes.item(0).getNodeValue();
           // log.debug(fp + "batch JNDI name extracted: " + batchJNDI);
            
            //get properties
            NodeList ppNodes = doc.getElementsByTagName("myapp");
            if (ppNodes==null || ppNodes.getLength() <= 0 || !ppNodes.item(0).hasChildNodes()){
            	;//log.info(fp + "ctsagent batch configuration has no properties");
            }
            
            NodeList agentNodeList = ppNodes.item(0).getChildNodes();
            if (agentNodeList==null || agentNodeList.getLength() <= 0 || !agentNodeList.item(0).hasChildNodes())
            	//log.info(fp + "ctsagent batch configuration has no properties");

            for (int i = 0; i < agentNodeList.getLength(); ++i) {
                Node node = agentNodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)   {
                		Element custom = (Element) node;
                    	String propName = custom.getNodeName();
                    	String propValue = custom.getFirstChild().getNodeValue();
                    //	log.debug(fp + "name: " + propName + ", value: " + propValue);
                    	Attr compressed = null;
                    	Attr dest = null;
                    	String compressedValue = null;
                    	String destValue = null;
                    	long sizeValue = -1;
                    	
                    	if(propName.equalsIgnoreCase(Constants.KEY_DATA_PROVIDERS)&& propValue!=null &&propValue.trim().length()>0){
                    		DATA_PROVIDERS = propValue.split(",");
                    	}                    	
                    	else if(propName.equalsIgnoreCase(Constants.KEY_DEFAULT_MAX_PKG_SIZE)&& propValue!=null &&propValue.trim().length()>0){
                    		defaultMaxPkgSize = Long.parseLong(propValue);
                    		compressed = custom.getAttributeNode("compressed");
                    		//defaultPkgCompressed = (new Boolean(compressed.getNodeValue())).booleanValue();
                    	}
                    	else if(propName.equalsIgnoreCase(Constants.KEY_SPECIFIC_MAX_FILE_SIZE)){
                    		
                    			
                    		try {
                    			sizeValue = Long.parseLong(propValue);
                    			
                    			compressed = custom.getAttributeNode("compressed");
                    			compressedValue = compressed.getNodeValue();
                    			dest = custom.getAttributeNode("dest");
                    			destValue = dest.getNodeValue();
                    			
                    			if(sizeValue<=0 ||destValue==null)
                    				throw new IllegalArgumentException();
                    			
                    			//specificFileSizes.put(destValue, new FileSize(sizeValue,destValue,(new Boolean(compressedValue)).booleanValue()));
                    		}
                    		catch(Exception e){
                    			//Utils.logError(log, e);
                    			log.error("Exception: " + e.getMessage());
                    		}                	                 		
                    	}
                    	else if(propName.equalsIgnoreCase(Constants.KEY_FILE_SIGNATURE_SIZE_CONSTANT)&& propValue!=null &&propValue.trim().length()>0){
                    		fileSignatureSizeConstant = Long.parseLong(propValue);
                    	}
                    	else if(propName.equalsIgnoreCase(Constants.KEY_FILE_SIZE_CONSTANT)&& propValue!=null &&propValue.trim().length()>0){
                    		fileSizeConstant = Long.parseLong(propValue);
                    	}
                    	else if(propName.equalsIgnoreCase(Constants.KEY_TXT_TO_XML_FACTOR)&& propValue!=null &&propValue.trim().length()>0){
                    		txtToXmlFactor = Double.parseDouble(propValue);
                    	}
                    	else {
                    	map.put(propName, propValue);
                    	}
                }
            }                   

            //get list of job configurations
            NodeList jNodes = doc.getElementsByTagName("job");
           
            if (jNodes==null || jNodes.getLength() <= 0 || !jNodes.item(0).hasChildNodes()){
            	//log.info(fp + "configuration has no jobs");
            	return;
            }
          //  log.debug(fp + "got list of " + jNodes.getLength() + " jobs");
            
            //for each tag with name job
            for(int i=0;i<jNodes.getLength();i++){
            	Node jobNode = jNodes.item(i);
            	//get job id to use as a key
            	NamedNodeMap attrMap = jobNode.getAttributes();
            	String jobId = (attrMap.getNamedItem("id")).getTextContent();
            	//log.info(fp + "jobId: " + jobId);
            	
            	//get step nodes, convert them to POJO and compile step list (job)
            	NodeList stepNodes = jobNode.getChildNodes();
            	
            	if(stepNodes==null || stepNodes.getLength()<=0){
            		//log.info(fp + "jobId " + jobId + " has no steps, continue..");
            		continue;
            	}
            	int numOfStepsForJob = stepNodes.getLength();
            	//log.debug(fp + "got list of " + numOfStepsForJob + " child nodes for job");
            	
            	//prepare class loader for loading tasks
            	ClassLoader classLoader = Main.class.getClassLoader();
            	LinkedList<IStep> job = new LinkedList<>();
            	
            	//for each tag with name task
            	for(int j=0;j<numOfStepsForJob;j++){
            		Node stepNode = stepNodes.item(j);
            		//log.info(fp + "got stepNode: " + stepNode);
            		if(stepNode==null || !stepNode.getNodeName().equalsIgnoreCase("step")){
            			continue;
            		}
            		//log.debug(fp + "child node name: " + stepNode.getNodeName());
            		NamedNodeMap stepPropMap = stepNode.getAttributes();
            		//log.info(fp + "got step attributes: " + stepPropMap);
            		String stepId 			= (stepPropMap.getNamedItem("id")).getTextContent();
            		//log.info(fp + "stepId: " + stepId);
            		String stepSequence 	= (stepPropMap.getNamedItem("sequence")).getTextContent();
            		//log.info(fp + "stepSequence: " + stepSequence);
            		String stepClass 		= (stepPropMap.getNamedItem("class")).getTextContent();
            		//log.info(fp + "stepClass: " + stepClass);

            		//load task based on class from the configuration 
            		Class<?> myObjectClass = classLoader.loadClass(stepClass);
        			IStep step = (IStep) myObjectClass.newInstance();
        			step.setId(stepId);
        			step.setJobId(jobId);
        			step.setSequence(Integer.parseInt(stepSequence));
        			
        			job.add(step);       			
            	}      	//end of for each task         	
            	Collections.sort(job);
            	Globals.jobs.put(jobId, job);
            }		//end of for each job            	
        } catch(Exception ex) {
           // Utils.logError(log, ex);
        	log.error("Exception: " + ex.getMessage());
            throw ex;
        }
        finally {
        	try {
        		is.close();
        	}catch(Exception e){}
        }
	}
	
	/**
	 * Compiles a string to print with all properties
	 * 
	 * @return String
	 */
	public static String toStaticString(){
		StringBuilder sb = new StringBuilder();
		 String newLine = System.getProperty("line.separator");	//CR + LF
		
		Enumeration<Object> keys = map.keys();
		String key;
		while(keys.hasMoreElements()){
			key = (String)keys.nextElement();
			sb.append("\n").append(key).append("=").append(map.get(key));
		}
		
	//	sb
	/*	.append(newLine).append("databaseEnvironment").append("=").append(databaseEnvironment).append(";")
		.append(newLine).append("docTypeIndicEnv").append("=").append(docTypeIndicEnv).append(";")
		.append(newLine).append("mailFromAddress").append("=").append(mailFromAddress).append(";")
		.append(newLine).append("mailToAddressList").append("=").append(mailToAddressList).append(";")
		//for metadata
		//.append("\n").append("mailSenderAddressList").append("=").append(metaContactAddressList).append(";")
		.append(newLine).append("sendMailFlag").append("=").append(sendMailFlag).append(";")
		.append(newLine).append("log_level").append("=").append(loglevel).append(";")
		*/
	/*	.append(newLine).append("baseFileDir").append("=").append(baseFileDir).append(";")
		.append(newLine).append("FILE_WORKING_DIR")						.append("=").append(FILE_WORKING_DIR).append(";")			
		.append(newLine).append(Constants.KEY_DEFAULT_MAX_PKG_SIZE)		.append("=").append(defaultMaxPkgSize).append(";")
	//	.append(newLine).append("defaultPkgCompressed")					.append("=").append(defaultPkgCompressed).append(";")
		.append(newLine).append(Constants.KEY_FILE_SIZE_CONSTANT)			.append("=").append(fileSizeConstant).append(";")
		//.append("\n").append(Constants.KEY_PKG_COMPRESSION_RATIO)		.append("=").append(PkgCompressionRatio).append(";")
		.append(newLine).append(Constants.KEY_PAYLOAD_COMPRESSION_RATIO)	.append("=").append(payloadCompressionRatio).append(";")
		.append(newLine).append(Constants.KEY_TXT_TO_XML_FACTOR)			.append("=").append(txtToXmlFactor).append(";")
		.append(newLine).append(Constants.KEY_FILE_SIGNATURE_SIZE_CONSTANT).append("=").append(fileSignatureSizeConstant).append(";")*/
	//	;
		
	/*	if(specificFileSizes!= null && specificFileSizes.size()>0){
			Iterator<String> it = specificFileSizes.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				sb.append(newLine).append(key).append("=").append(specificFileSizes.get(key)).append(";");
			}
		}*/
		
	/*	if(DATA_PROVIDERS!=null && DATA_PROVIDERS.length>0){
			for(String s : DATA_PROVIDERS){
				sb.append(newLine).append("Data provider: ").append(s).append(";");
			}
		}
		*/
		sb.append(newLine).append(Globals.getJobs());
		
		return sb.toString();
	}

	//no instantiating allowed
	private Globals(){}
	
	//no cloning
	@Override
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException("Cloning is not supported!");
	}
	
	public static class FileSize {
		private long size;
		private String countryCode;
		private boolean compressed;
		
		public FileSize(long s, String cc, boolean c){
			this.size = s;
			this.countryCode = cc;
			this.compressed = c;
		}
		
		public long getSize(){
			return this.size;
		}
		
		public boolean isCompressed(){
			return this.compressed;
		}
		
		public String getCountryCode(){
			return this.countryCode;
		}
		
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb
			.append("\ncountry=").append(countryCode)
			.append(",size=").append(size)
			.append(",compressed=").append(compressed);
			return sb.toString();
		}
	}
}
