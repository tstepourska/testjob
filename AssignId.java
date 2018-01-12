package com.mycompany.myapp.step;

import org.apache.log4j.Logger;
/*
import ca.gc.cra.fxit.xmlt.exception.OECDError;*/
import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.util.*;

/**
 * @author Tatiana Stepourska
 */
public class AssignId extends AbstractStep
{
	private static Logger lg = Logger.getLogger(AssignId.class);
	
	@Override
	public final int execute(PackageInfo p) {
		lg.info("AssignId executing");
		int status = Constants.STATUS_CODE_INCOMPLETE;
		
		try {
			
		}
		catch(Exception e){
			status = Constants.STATUS_CODE_ERROR;
			Utils.logError(lg, e);
		}
		
		//TODo for wireframe testing only - to comment out!
		status = Constants.STATUS_CODE_SUCCESS;
		// end of to comment out
		
		return status;	
	}
	
	@Override
	public AssignId cloneStep(){
		AssignId t = new AssignId();
		t.setId(this.id);
		t.setSequence(this.sequence);
		t.setJobId(this.jobId);
	
		return t;
	}
}
