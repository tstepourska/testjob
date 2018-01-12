package com.mycompany.myapp.step;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.step.xml.IHelper;
import com.mycompany.myapp.util.*;

/**
 * This class loads appropriate Helper class at runtime depending on a data provider
 * 
 * @author Tatiana Stepourska
 *
 */
public class ProcessXML extends AbstractStep {

	private static Logger log = Logger.getLogger(ProcessXML.class);

	@Override
	public final int execute(PackageInfo p) {
		int status = Constants.STATUS_CODE_INCOMPLETE;
		
		try {			
			String dataProvider		= p.getDataProvider();
			//load appropriate helper 
			ClassLoader classLoader = ProcessXML.class.getClassLoader();
			Class<?> myObjectClass 	= classLoader.loadClass(Constants.JAVA_PKG_TASK + "xml." + dataProvider+ ".Helper");
			
			IHelper helper 			= (IHelper) myObjectClass.newInstance();
			status 					= helper.invoke(p);
		}
		catch(Exception e){
			status = Constants.STATUS_CODE_ERROR;
			Utils.logError(log, e);
		}

		return status;
	}
	
	@Override
	public ProcessXML cloneStep(){
		ProcessXML t = new ProcessXML();
		t.setId(this.id);
		t.setSequence(this.sequence);
		t.setJobId(this.jobId);
		
		return t;
	}
}
