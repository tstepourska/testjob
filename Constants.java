package com.mycompany.myapp.util;

import java.text.SimpleDateFormat;

public class Constants {
	public static final String JOB_OUTBOUND 							= "Outbound";
	public static final String JOB_INBOUND 								= "Inbound";

	//mainframe naming convention
	public static final String FILE_OWNER_PREFIX						= "FX";
	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	/*
	 * Careful with the two fields below!
	 * Used setting OECDMessageType
	 */
	public static final String MSG_TYPE_MESSAGE_STATUS					= "MessageStatus";
	
	//used for creating status message file name
	public static final String MSG_TYPE_STATUS							= "Status";
	//used in the MessageSpec of the CRSStatusMessage as MessageTypeEnumType,
	//as per CRS Status Message User Guide - might be a typo!
	//public static final String __STATUS_MESSAGE									= "StatusMessage";
	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	
	public static final String SUFFIX_PAYLOAD							= "PL"; //used for outbound, starts from building XML/validating  and ends after its validation and saving it, before CASD
	//public static final String SUFFIX_PACKAGE							= "PK"; //used for outbound starts from digital signature and ends sending it off to CTS
	//public static final String SUFFIX_PRELIM							= "PRE"; // used for inbound, saves, uncompresses and validates metadata - before CASD
	//public static final String SUFFIX_UNPAK								= "UPK"; // used for inbound, decrypts, uncompreses payload, validates dig signature etc
	public static final String SUFFIX_EXTERNAL							= "EXT";
	public static final String SUFFIX_STATUS_MESSAGE					= "SM";
	
	public static final String JAVA_PKG_TASK							= "com.mycompany.myapp.step.";
	//public static final String JAVA_PKG_JAXB							= "ca.gc.cra.fxit.xmlt.generated.jaxb.";
	public static final String RESOURCE_BASE_PKG						= "com/mycompany/myapp/resources/";							
	
	public static final String CANADA								= "CA";
	
	public static final String OUTBOUND_UNPROCESSED_TOSEND_DIR 		= "outbound/unprocessed/";
	public static final String OUTBOUND_PROCESSED_TOSEND_DIR 		= "outbound/processed/";
	public static final String TEMP_DIR					 			= "temp/";
	public static final String ZIPPED_DIR					 		= "zipped/";
		
	//public static final String _TEST_BASEDIR						= "C:/git/repository2/CTS_XMLT/CTS_dataprep/test/testfiles/";
	//public static final String KEY_MAX_XML_FILE_SIZE				= "maxXmlFileSize";
	public static final String KEY_DEFAULT_MAX_PKG_SIZE				= "DefaultMaxPkgSize";
	public static final String KEY_SPECIFIC_MAX_FILE_SIZE			= "specificMaxFileSize";	
	public static final String KEY_SPECIFIC_MAX_SIZE_VALUE   		= "value";		//attribute name
	public static final String KEY_SPECIFIC_MAX_SIZE_DEST   		= "dest";		//attribute name
	public static final String KEY_SPECIFIC_MAX_SIZE_COMPRESSED   	= "compressed";		//attribute name
	public static final String KEY_TRK_USER							= "trk_user";
	
	/**
	 * Value in bytes corresponding to tested maximum size of digital signature,
	 * metadata file, key file
	 */
	public static final String KEY_PKG_SIZE_CONSTANT				= "PkgSizeConstant";
	/**
	 * Value in percents of package compression ratio
	 */
	//public static final String KEY_PKG_COMPRESSION_RATIO			= "PkgCompressionRatio";
	/**
	 *  Value in percents of XML payload compression ratio
	 */
	public static final String KEY_PAYLOAD_COMPRESSION_RATIO		= "PayloadCompressionRatio";
	public static final String KEY_TXT_TO_XML_FACTOR 				= "TxtToXmlFactor";
	public static final String KEY_FILE_SIGNATURE_SIZE_CONSTANT 	= "FileSignatureSizeConstant";
	public static final String KEY_FILE_SIZE_CONSTANT 				= "FileSizeConstant";
	
	public static final String KEY_JOB_CONFIG						= "JobConfig";
	public static final String KEY_DATA_PROVIDERS					= "DataProviders";
	//public static final String KEY_LOG_LEVEL						= "log_level";
	
	public static final int NO_SPLIT								= 1;
	
	/**
	 * Cobol mapping file name format <orig name>.X0000001  (X + 7 digits)
	 */
	public static final int FILE_SEQUENCE_NUM_SIZE					= 7;  	
	public static final int MAX_MAINFRAME_NAME_PERIOD				= 8;	
	public static final String MAIN_SCHEMA_NAME						= "main_schema.xsd";
	public static final String ISO_TYPES_NAME						= "isotypes.xsd";
	
	public static final int STATUS_CODE_SUCCESS 					= 0;
	public static final int STATUS_CODE_ERROR	 					= 9;
	//public static final int STATUS_CODE_INVALID_INPUT_FILE			= 90001;
	//public static final int STATUS_CODE_CREATE_JOB_LOOP	    		= -98;
	public static final int STATUS_CODE_INCOMPLETE					= -99;
	
	public static final int STATUS_CODE_FAILED_SCHEMA_VALIDATION	= 50007;
	public static final int STATUS_CODE_INVALID_MESSAGE_REF_ID		= 50008;
	public static final int STATUS_CODE_DUPLICATED_MESSAGE_REF_ID	= 50009;
	public static final int STATUS_CODE_TEST_DATA_FOR_PROD			= 50010;
	public static final int STATUS_CODE_PROD_DATA_FOR_TEST			= 50011;
	public static final int STATUS_CODE_RECORDS_FOR_WRONG_RECIPIENT	= 50012;
	
	public static final int STATUS_CODE_PERSON_CNTRY_NOMATCH        = 60011;
	public static final int STATUS_CODE_ENTITY_CNTRY_NOMATCH        = 60012;
	public static final int STATUS_CODE_REPORTING_FI_CNTRY_NOMATCH  = 60013;
	
	
	/**
	 * EEI Specific Status / Error Codes
	 10000		status not available
	Outbound:
	10001		Transformation Failed   	Something fatal happened during transformation/file split /messageRefId generation, file cannot be processed 
	10002		Ready For Review			Outbound XML message is waiting for CASD to review and approve
	10003		Release Rejected			CASD rejected message for sending, file cannot be packaged for transmission
	10004		DataPrep Initiated			CASD triggered data prep 
	10005		DataPrep Failed				Something fatal happened during data prep, file cannot be processed
	10006		Package Sent				Data prep successfully completed and package has been pushed to the drop zone for transmitting to CTS
	10007		Accepted By OJ				Package has been accepted by the OJ without error
	10008 		Accepted By OJ with Error	Package has been accepted by the OJ with error (Record level error)
	10009 		Rejected By OJ  (file error)	Package has been rejected by the OJ (File level error)
	10010 		Rejected By OJ (other reasons)	Package has been rejected by the OJ (unknown reason)


	Inbound:
	10011		Package Received			Zipped package came from CTS
	10012		Ready for Review			After metadata validated, ready for CASD access
	10013		Delivery Approved			CASD triggered delivery to the data provider
	10014		Delivery Completed			After all data is in the drop zone for data provider and any additional info is provided  to them (such as MessageRefID/DocRefID mapping for accepted file)
	10015		Accepted By Canada			Package has been accepted by the data provider without error
	10016 		Accepted By Canada with Error		Package has been accepted by the Canadian data provider with error (Record level error)
	10017 		Rejected By Canada (File Error)  	Package has been rejected by the Canada (File level error)
	10018 		Rejected By Canada (Other Reason)  	Package has been rejected by the Canada (other reason)
	
	10100		Country is not in the approved list
	*/
	public static final int STATUS_CODE_UNKNOWN								= 10000;
	public static final int STATUS_CODE_TRANSFORMATION_FAILED		 		= 10001;
	public static final int STATUS_CODE_TRANSFORMATION_SUCCESSFUL	 		= 10002; 
/*	public static final int STATUS_CODE_RELEASE_REJECTED		 			= 10003;
	public static final int STATUS_CODE_DATAPREP_INITIATED		 			= 10004;
	public static final int STATUS_CODE_DATAPREP_FAILED		 				= 10005;
	public static final int STATUS_CODE_PACKAGE_SENT		 				= 10006;
	public static final int STATUS_CODE_ACCEPTED_BY_OJ		 				= 10007;
	public static final int STATUS_CODE_ACCEPTED_BY_OJ_WITH_ERROR		 	= 10008;
	public static final int STATUS_CODE_REJECTED_BY_OJ		 				= 10009;
	public static final int STATUS_CODE_REJECTED_BY_OJ_OTHER		 		= 10010;
	public static final int STATUS_CODE_PACKAGE_RECEIVED		 			= 10011;
	public static final int STATUS_CODE_IN_READY_FOR_REVIEW		 			= 10012;
	public static final int STATUS_CODE_DELIVERY_APPROVED		 			= 10013;
	public static final int STATUS_CODE_DELIVERY_COMPLETED		 			= 10014;*/
	public static final int STATUS_CODE_ACCEPTED_BY_CANADA		 			= 10015;
	public static final int STATUS_CODE_ACCEPTED_BY_CANADA_WITH_ERROR		= 10016;
	public static final int STATUS_CODE_REJECTED_BY_CANADA_FILE_ERROR		= 10017;
	public static final int STATUS_CODE_REJECTED_BY_CANADA_OTHER		 	= 10018;
	public static final int STATUS_CODE_FILE_SPLIT							= 10019;
	public static final int STATUS_CODE_XML_FILE_TOO_LARGE					= 10020;
	
	public static final int STATUS_CODE_COUNTRY_NOT_APPROVED				= 10100; 
	
	public static final int[] ERRORS_TO_CARRY_OVER = {
		STATUS_CODE_COUNTRY_NOT_APPROVED,		//10100,
		STATUS_CODE_XML_FILE_TOO_LARGE,			// 10020	
		
		STATUS_CODE_FAILED_SCHEMA_VALIDATION,	// 50007;
		STATUS_CODE_INVALID_MESSAGE_REF_ID,		// 50008;
		STATUS_CODE_DUPLICATED_MESSAGE_REF_ID,	// 50009;
		STATUS_CODE_TEST_DATA_FOR_PROD,			// 50010;
		STATUS_CODE_PROD_DATA_FOR_TEST,			// 50011;
		STATUS_CODE_RECORDS_FOR_WRONG_RECIPIENT,// 50012;	
		
		STATUS_CODE_PERSON_CNTRY_NOMATCH ,      // 60011;
		STATUS_CODE_ENTITY_CNTRY_NOMATCH ,      // 60012;
		STATUS_CODE_REPORTING_FI_CNTRY_NOMATCH	// 60013
		};
	
	//public static final String DB_TRACKING_UID						= "xmlt";
	public static final String META_BINARY_ENCODING_SCHEME			= "NONE";
	public static final String META_FILE_FORMAT_CD					= "XML";
	
	public static final String DB_TIME_ZONE							= "EST";
	
	public static final String STATUS_MESSAGE_SUCCESS 				= "success";
	public static final String STATUS_MESSAGE_ERROR					= "error";

	public static final String DEFAULT_ENCODING 					= "ISO-8859-1";  
	
	public static final String FILE_EXT_XML							= ".xml";
	//public static final String METADATA								= "Metadata";
	public static final String PREFIX_MAPPING						= "Mapping_";
	public static final String INVALID									= "INV";
	public static final String DUPLICATE								= "DUP";
	//public static final String __MSG_REF_ID_PLACEHOLDER				= "MESSAGEREFID";
	public static final int MIX_TAX_YEAR							= 2000;
	
	public static final String STATS_CFG							= "stats.cfg";

	////////////////////////////////////////////
	// out bound create package constants

	public static final int maxAttempts 							= 5;

	public static int bufSize 										= 8 * 1024;
	
	public static final String UNDERSCORE		 					= "_";
	public static final String DASH									= "-";
	public static final String ENV_TEST								= "T";
	public static final String ENV_PROD			 					= "P";
	public static final String COMMA								= ",";

	public static SimpleDateFormat sdfMessageTs 			= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");		
	public static SimpleDateFormat sdfMessageTsUTC 			= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	public static SimpleDateFormat sdfFileNameUTC			= new SimpleDateFormat("YYYYMMdd'T'hhmmssSSS'Z'");
	public static SimpleDateFormat sdfSweepTimeTs 			= new SimpleDateFormat("yyyyMMdd'T'HHmmss");
	
	//////////////////////////////////////////////////////////////////////////////
	// end of outbound create package constants
	///////////////////////////////
	
	///////////////////////////////////
	//batch processing
	/**
     * Warning text for outgoing FATCA XML files. 
     * The exact wording of the warning text is provided by Canada's Competent Authority.
     */
	public static final String PACKAGE_WARNING_TEXT = "THIS INFORMATION IS FURNISHED UNDER THE PROVISIONS OF AN INCOME TAX TREATY WITH A FOREIGN GOVERNMENT. ITS USE AND DISCLOSURE MUST BE GOVERNED BY THE PROVISIONS OF THAT TREATY.";
													  
	// end of batch processing
	//////////////////////////////////////
    /**
     * Value of TRANS-CD for PART XVIII Master header record: IP6PRTHD. Value {@value}.
     */
	public static final int LINE_CODE_MESSAGE_HEADER = 1001;
	public static final int LINE_CODE_STATUS_HEADER = 1001;
    /**
     * Value of TRANS-CD for PART XVIII Summary record: IP6PRTSM. Value {@value}.
     */
	public static final int LINE_CODE_FI = 1002;
    /**
     * Value of TRANS-CD for PART XVIII Sponsor record: IP6PRTSP. Value {@value}.
     */
	public static final int LINE_CODE_SPONSOR = 1003;
    /**
     * Value of TRANS-CD for PART XVIII Slip record: IP6PRTSL. Value {@value}.
     */
	public static final int LINE_CODE_SLIP = 1004;
    /**
     * Value of TRANS-CD for PART XVIII Controlling Person record: IP6PRTCP. Value {@value}.
     */
	public static final int LINE_CODE_PERSON = 1005;
    /**
     * Value of TRANS-CD for PART XVIII Account Holder record: IP6PRTAC. Value {@value}.
     */
	public static final int LINE_CODE_ACCOUNT_HOLDER = 1006;
    /**
     * Value of TRANS-CD for PART XVIII Master trailer record: IP6PRTTR. Value {@value}.
     */
	public static final int LINE_CODE_TRAILER = 1007;
	public static final int LINE_CODE_STATUS_TRAILER = 1004;

	public static final int LINE_CODE_CRS_RECORD_ERROR = 1002;
	public static final int LINE_CODE_CRS_RECORD_ERROR_2 = 1003;	
	
    /**
     * Minimum length of a cobol record. Use length of TRANS-CD field.
     */
	//public static final int MIN_REC_LEN = new IP6PRTHD().getField("TransCd").getLength(); // 4 chars - digits
	//public static final int MIN_REC_LEN = new IP7PRTHD().getField("TransCd").getLength(); // 4 chars - digits
 
    /**
     * Factor to apply to cobol record length to determine approximate length of XML. 
     */
	public static final long TXT2XML_FACTOR 			= 3L;
	
    /**
     * Maximum number of occurrences of Residence Country Code. Value {@value}.
     * This is the maximum number of occurrences in the input file. The output is unbounded. 
     */
	public static final int MAX_RESIDENCE_COUNTRY_CODES = 5;

    /**
     * Maximum number of occurrences of Controlling Person per Account Report. Value {@value}.
     * This is the maximum number of occurrences of Controlling Person per Account Report in the input file. The output is unbounded. 
     */
	public static final int MAX_CONTROLLING_PERSONS 	= 16;

	/**
     * Number of digits after the decimal place in account balance and payment amounts. Value {@value}. 
     */
	public static final int MAX_DECIMAL_PLACES 			= 2;

	/**
	 * If true, use test DocTypeIndic codes. If false, use production codes.
	 * Test codes should ONLY be used to create FATCA XML files to be sent to IRS during system testing periods, 
	 * and not for PRODUCTION files to be sent to IRS. 
	 */
	public boolean useTestDocTypeIndicCodes 			= false;
	
	public static final int MAX_PACKAGE_LENGTH			= 9;
	public static final int MAX_FATCA_PACKAGE_LENGTH	= 7;
	public static final String DATA_PROVIDER_FATCA	 							= "ftc";
	public static final String DATA_PROVIDER_CRS	 							= "crs";
	
	/////////////////////////////////////////////////////////////////////////////
	// JNDI NAMES
	/////////////////////////////////////////////////////////////////////////////
	/**
	 * JNDI name to lookup data directory.
	 */
	public static final String JNDI_BATCH_DATA_DIRECTORY 		= "ca/gc/cra/fxit/xmlt/env/basedir";  

	/**
	 * JNDI name to lookup log level.
	 */
	public static final String JNDI_LOG_LEVEL 					= "ca/gc/cra/fxit/xmlt/env/log_level";

	/**
	 * JNDI name to lookup DocTypeIndic codes to use. Valid Values: T P
	 */
	public static final String JNDI_DOCTYPEINDIC_ENV 			= "ca/gc/cra/fxit/xmlt/env/docTypeIndicEnv";
	public static final String DOCTYPEINDIC_ENV_TEST			= "T";
	public static final String DOCTYPEINDIC_ENV_U			= "U";
	public static final String DOCTYPEINDIC_ENV_A			= "A";
	public static final String DOCTYPEINDIC_ENV_O			= "O";
	public static final String DOCTYPEINDIC_ENV_F			= "F";
	public static final String DOCTYPEINDIC_ENV_PROD			= "P";
	
	/**
	 * JNDI name to lookup user name for tracking purposes
	 */
	public static final String JNDI_TRK_USER 			= "ca/gc/cra/fxit/xmlt/env/trk_user";

	/**
	 * JNDI name to lookup FXMT database environment: U (UT), S (SI), A (UA), O (OT), or P (PR)
	 */
	public final static String JNDI_FXMT_ENV   					= "ca/gc/cra/db/fxmt/environment";
	public final static String JNDI_FXMT_ENV_2   					= "ca/gc/cra/db/fx/environment";
	
	/**
	 * JNDI name to lookup EJB for FXMT database access.
	 */
	//public final static String JNDI_INIT_CODES_HOME   				= "ca/gc/cra/fxit/xmlt/dao/ejb/InitCodesHome";
	//public final static String JNDI_INSERT_PACKAGE_HOME   			= "ca/gc/cra/fxit/xmlt/dao/ejb/InsertPackageHome";
	//public final static String JNDI_INSERT_REMAINING_RECORD_HOME   	= "ca/gc/cra/fxit/xmlt/dao/ejb/InsertRemainingRecordHome";
	//public final static String JNDI_INSERT_MAPPING_HOME   			= "ca/gc/cra/fxit/xmlt/dao/ejb/InsertMappingHome";
	//public final static String JNDI_INSERT_STATS_HOME   			= "ca/gc/cra/fxit/xmlt/dao/ejb/InsertStatsHome";
	//public final static String JNDI_UPDATE_STATUS_HOME   			= "ca/gc/cra/fxit/xmlt/dao/ejb/UpdateStatusHome";
	//public final static String JNDI_SELECT_COUNTRY_HOME				= "ca/gc/cra/fxit/xmlt/dao/ejb/SelectCountryHome";
	//public final static String JNDI_SELECT_MSG_REF_ID_HOME			= "ca/gc/cra/fxit/xmlt/dao/ejb/SelectMsgRefIdHome";
	//public final static String JNDI_INSERT_STATUS_INFO_HOME			= "ca/gc/cra/fxit/xmlt/dao/ejb/InsertStatusInfoHome";

	/**
	 * JNDI name to lookup FXMT From address used for sending email.
	 */
	public final static String JNDI_FXIT_MAIL_FROM_ADDRESS   	= "ca/gc/cra/fxit/mail/FromAddress";
	
	/**
	 * JNDI name to lookup IRMS email address that will receive FTP confirmation from FXIT.
	 */
	public final static String JNDI_FXIT_MAIL_TO_ADDRESS   		= "ca/gc/cra/fxit/mail/ToAddressList";
	
	public final static String JNDI_FXIT_MAIL_SENDER_ADDRESS   	= "ca/gc/cra/fxit/mail/SenderAddressList";

	/**
	 * JNDI name to lookup flag that controls whether or not FXIT sends email.
	 */
	public final static String JNDI_FXIT_MAIL_SEND_FLAG   = "ca/gc/cra/fxit/mail/sendFlag";
	
	public static final String JNDI_MAIL = "ca.gc.cra.fxit.xmlt.mail.Session";
	// END OF JNDI NAMES
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static final long SLEEP_BETWEEN_JOBS_SEC				= 1000 * 2;
	//public static final long SLEEP_BETWEEN_BATCHES_SEC			= 1000 * 5;
}
