package com.mycompany.myapp.step;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.util.*;

/**
 * @author Tatiana Stepourska
 */
public class CollectStatistics extends AbstractStep {
	private static Logger lg = Logger.getLogger(CollectStatistics.class);

	
	@Override
	public final int execute(PackageInfo p) {
		int status = Constants.STATUS_CODE_INCOMPLETE;
		lg.info("CollectStatistics executing");
		
		try {
			//implementation goes here
		}
		catch(Exception e){
			status = Constants.STATUS_CODE_ERROR;
			lg.error("Exception: " + e.getMessage());
		}
		//for wireframe testing only - to comment out!
		status = Constants.STATUS_CODE_SUCCESS;
		// end of to comment out

		return status;
	}	

	@Override
	public CollectStatistics cloneStep(){
		CollectStatistics t = new CollectStatistics();
		t.setId(this.id);
		t.setSequence(this.sequence);
		t.setJobId(this.jobId);

		return t;
	}

	///////////////////////////////////////////////////
	// Private methods
	///////////////////////////////////////////////////

}
