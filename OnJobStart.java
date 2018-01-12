package com.mycompany.myapp.job;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.util.Constants;

/**
 * This class contains all on job start routines that must be 
 * executed no matter what job is being executed
 * 
 * @author Tatiana Stepourska
 *
 */
public class OnJobStart {
	private static Logger lg = Logger.getLogger(OnJobStart.class);

	public OnJobStart(){
	}
	
	public  void invoke(PackageInfo p) {
		// backup
		int st = backup(p);
		lg.info("OnJobStart completed with status: " + st);
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// PRIVATE METHODS
	///////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param p
	 * @return
	 */
	private int backup(PackageInfo p){
		String fp = "backup: ";
		int status = Constants.STATUS_CODE_INCOMPLETE;
		try {
			lg.info(fp + "executing");
			//implementation goes here
		}
		catch(Exception e){
			
		}
		finally {
			
		}
		
		return status;
	}
}
