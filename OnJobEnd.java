package com.mycompany.myapp.job;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.util.Constants;

/**
 * This class contains all on job end routines that must be 
 * executed no matter at what point job ended and with what result
 * 
 * @author Tatiana Stepourska
 * @since 2017-03-14
 *
 */
public class OnJobEnd {
	private static Logger lg = Logger.getLogger(OnJobEnd.class);

	public OnJobEnd(){
	}
	
	public  void invoke(int status, PackageInfo p) {
		//cleanup
		int st = cleanup(status, p);
		lg.info("On job end completed with status: " + st);
	}
	
	///////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	///////////////////////////////////////////////////////////////////
	/**
	 * Performs all cleanup procedures for a job
	 * 
	 * @param status
	 * @param p
	 * @return
	 */
	private int cleanup(int status, PackageInfo p){
		String fp = "cleanup: ";
		
		try {
			lg.info(fp + "executing");
			//implementation for clean up 
		}
		catch(Exception e){
			status = Constants.STATUS_CODE_ERROR;
			lg.error("cleanup: Error: " + e.getMessage());
		}
		finally{

		}
		
		return status;
	}
}
