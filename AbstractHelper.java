package com.mycompany.myapp.step.xml;

import java.io.InputStream;

import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.util.Constants;
import com.mycompany.myapp.util.Utils;

/**
 * This is an abstract class which contains methods common for 
 * all data providers
 * 
 * @author Tatiana Stepourska
 */
public abstract class AbstractHelper implements IHelper
{
	private static final Logger lg = Logger.getLogger(AbstractHelper.class);

	 /**
	 * Validates XML file against the appropriate schema
	 * 
	 * @param PackageInfo p
	 * @return int status code
	 */
	@Override
	public int validate(PackageInfo p, final String[] schemas) throws Exception {
		int status = Constants.STATUS_CODE_INCOMPLETE;		

		try {				
			lg.info("Common validation started");
			//1. validation implementation common to all data provider goes here first
			
			lg.info("Common validation completed");
			
			//2. called hook for customized validation
			customValidation(p);

			status = Constants.STATUS_CODE_SUCCESS;
		} 
		catch (Exception e) {
			status = Constants.STATUS_CODE_ERROR;
			Utils.logError(lg, e);
			lg.error("validate: Error: " + e.getMessage());
			throw e;
		}
		finally{
		}
			
		return status;
	} 
	
	public abstract void customValidation(PackageInfo p) throws Exception;
	
	 /**
	  * Creates array of StreamSources comprised of schemas to validate against
	  * 
	  * @param xsdResourcePaths
	  * @return
	  * @throws Exception
	  */
	 private StreamSource[] generateStreamSourcesFromResourceAsStream(final String[] xsdResourcePaths)  throws Exception {  
		 if(xsdResourcePaths==null||xsdResourcePaths.length<=0)
			 return null;
		// String fp = "generateStreamSourcesFromResourceAsStream: ";
		 InputStream is = null;
		 int count = 0;
		 StreamSource[] xsds = new StreamSource[xsdResourcePaths.length];
		 ClassLoader classLoader = AbstractHelper.class.getClassLoader();		
		 
		 //getting schema(s) to validate against	 	
		 for(String s : xsdResourcePaths){
			//lg.debug(fp + "xsds[" + count + "] location: " + s);
			is = classLoader.getResourceAsStream(s);
			//is =  AbstractXmlHelper.class.getResourceAsStream("/"+s);
			xsds[count] = new StreamSource(is);
			//lg.debug(fp + "xsds[" + count + "] streamSource: " + xsds[count]);
			count++;
		 }
		 return xsds;
	 } 

/*	 public void updateGeneratedXmlFileSize(PackageInfo p){
		 try {
		    File f = null;
		    long filesize = 0;
		    String dir = p.getFileWorkingDir();
		    String fname = p.getXmlFilename();
		  
		    f = new File(dir + fname);
		    if(f.exists())
		    	filesize = f.length();
			  
			if(filesize>0){
				p.setXmlFilesize(filesize);
				 try{			
					 UpdateFileSizeObj sql = new UpdateFileSizeObj();
					 sql.invoke(p);
				 }
				 catch(Exception e){
					 Utils.logError(lg, e);
				 }
				 catch(Error e){
					 lg.error("Error updating file size: " + e.getMessage());
				 }
				 catch(Throwable t){
					 lg.error("Caught throwable updating file size: " + t.getMessage());
				 }
			}
		 }
		 catch(Exception e){
			 lg.error("updateGeneratedXmlFileSize: exception: " + e.getMessage());
		 }	
	 }*/
}
