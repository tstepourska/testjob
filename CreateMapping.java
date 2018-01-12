package com.mycompany.myapp.step;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.util.*;

/**
 * @author Tatiana Stepourska
 */
public class CreateMapping extends AbstractStep {
	private static Logger lg = Logger.getLogger(CreateMapping.class);
	
	@Override
	public final int execute(PackageInfo p) {
		int status = Constants.STATUS_CODE_INCOMPLETE;
		lg.info("CreateMapping executing");
		
		try {
			//implementation goes here
		}
		catch(Exception e){
			status = Constants.STATUS_CODE_ERROR;
			Utils.logErrorDetails(lg, e);
		}
		
		//for wireframe dry run only - to comment out!
		status = Constants.STATUS_CODE_SUCCESS;
		// end of to comment out

		return status;
	}	

	@Override
	public CreateMapping cloneStep(){
		CreateMapping t = new CreateMapping();
		t.setId(this.id);
		t.setSequence(this.sequence);
		t.setJobId(this.jobId);

		return t;
	}
}
