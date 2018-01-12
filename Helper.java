package com.mycompany.myapp.step.xml.data1;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.step.xml.AbstractHelper;
import com.mycompany.myapp.util.Constants;

/**
 * 
 * @author Tatiana Stepourska
 *
 */
public class Helper extends AbstractHelper {
	private static Logger lg = Logger.getLogger(Helper.class);	
	
	public Helper(){}
	
	@Override
	public final int invoke(PackageInfo p){
		lg.info("Data1 Helper started");
		int status = Constants.STATUS_CODE_INCOMPLETE;
		String[] xsdpaths = getSchemas();
					
		try {
			status = this.transform(p);
			lg.info("Transformation completed with status " + status);
			
			status = this.validate(p, xsdpaths);
			lg.info("Validation completed with status " + status);
		}
		catch(Exception e){
			lg.error("Exception: " + e.getMessage());
			status = Constants.STATUS_CODE_ERROR;
		}
		
		//For dry run wireframes only - to remove
		status= Constants.STATUS_CODE_SUCCESS;
		//end of to remove
		
		return status;
	}
	
	/**
	 * Transforms data into XML
	 */
	@Override
	public int transform(PackageInfo p){
		String fp = "data1 transform: ";
		lg.info(fp + "started");
		int status = Constants.STATUS_CODE_INCOMPLETE;
		
		try {
			//transformation implementation goes here
			status = Constants.STATUS_CODE_SUCCESS;
		}
		catch(Exception e){
			lg.error("Exception: " + e.getMessage());
			status = Constants.STATUS_CODE_ERROR;
		}
		
		return status;
	}
	
	@Override
	public String[] getSchemas(){
		String[] xsdpaths = new String[] {
				Constants.RESOURCE_BASE_PKG +"schema/data1/schema1.xsd",
				Constants.RESOURCE_BASE_PKG +"schema/data1/schema2.xsd",
				Constants.RESOURCE_BASE_PKG +"schema/data1/schema3.xsd"
				  };
		return xsdpaths;
	}
	
	@Override
	public void customValidation(PackageInfo p) throws Exception  {	
		lg.info("Custom validation started");
		//any validation specific for this data provider, if any, goes here	
		
		lg.info("Custom validation completed");
	}
}
