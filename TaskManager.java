package com.mycompany.myapp.job;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.model.PackageInfoFactory;
import com.mycompany.myapp.step.IStep;
import com.mycompany.myapp.util.Globals;
import com.mycompany.myapp.util.Constants;

/**
 * This class is responsible for: 
 * 		--finding an appropriate job configuration 
 * 		  based on the package type, direction where package should go, and package source
 * 		--executing the job according to the job configuration
 * 		--if the package is too big, initiate PackageInfo for each of the pieces into which original package is split
 * 		--triggering on job end routines
 * 
 * It is called from the BatchController EJB to trigger a batch process, 
 * or from the ActionServlet of the web application, to execute job initiated by the web user
 * There might be more than one package to process, so TaskManager class is created once,
 * then invoked inside the loop, every time with the newly initialized package
 * 
 * @author Tatiana Stepourska
 * @since  2016-10-11
 */
public class TaskManager implements Serializable {
	private static Logger log = Logger.getLogger(TaskManager.class);
	private static final long serialVersionUID = -2920777761984522115L;
	private OnJobEnd oje = null;
	private OnJobStart ojs = null;
	
	public TaskManager(){
		ojs = new OnJobStart();
		oje = new OnJobEnd();
	}
	
	/**
	 * Invokes this class main logic - resolve and execute job,
	 * split package if necessary, complete on job end routines 
	 * for each job
	 * 
	 * @param pInfo		--PackageInfo object initialized outside of this class
	 * 
	 * @return int 
	 */
	public int invoke(PackageInfo pInfo) { 
		int status = Constants.STATUS_CODE_INCOMPLETE;
		try {
			//find appropriate job using package info
			LinkedList<IStep> job = resolveJob(pInfo); 

			//all onJobStart routines, if any, go here
			onJobStart(pInfo);
			
			//do the job
			status = executeJob(job, pInfo);
			log.info("invoke: status: " + status);
			
			//single package is too big, needs to be splitted
			if(status== Constants.STATUS_CODE_FILE_SPLIT) { 
				//configure remaining tasks as a loop for more than one package
				log.info("initiating job loop for splitted package..");
				//initialize PackageInfoFactory 
				PackageInfoFactory factory = new PackageInfoFactory();
				//initialize list of PackageInfo objects, one package for each splitted piece
				ArrayList<PackageInfo> list = factory.initPackageList(pInfo);
				Iterator<PackageInfo> it 	= list.iterator();
				
				LinkedList<IStep> newjob; 		
				int pkgCounter = 0;
				//for each piece of the splitted package
				while(it.hasNext()){
					pkgCounter++;
					//make deep copy of a remaining job
					newjob = cloneJob(job);
					log.info("got a job for package #" + pkgCounter + ": " + newjob);
					
					//do the job for a single package
					PackageInfo p0=it.next();
					log.info("Executing job for package " + p0);
					status = executeJob(newjob, p0);
					log.info("job for package " + pkgCounter + " completed with status " + status);
					//all onJobEnd routines here
					onJobEnd(status,p0);
					
					try {
						Thread.sleep(Constants.SLEEP_BETWEEN_JOBS_SEC);
					}
					catch(InterruptedException ie){}
				}
				log.info("All jobs completed ");
			}
			else {
				//job for a single package completed, all onJobEnd routines here
				onJobEnd(status,pInfo);
				
				try {
					Thread.sleep(Constants.SLEEP_BETWEEN_JOBS_SEC);
				}
				catch(InterruptedException ie){}
			}
		}
		catch(Exception e){
			status = Constants.STATUS_CODE_ERROR;
			log.error("Exception: " + e.getMessage());
			if(log.isDebugEnabled()){
		  		  StackTraceElement[] trace = e.getStackTrace();
		  		  if(trace!=null){
		  			  for(StackTraceElement tLine : trace){
		  				  log.error(tLine);
		  			  }
		  		  }
		  	 }
		}
		
		return status;
	}
	
	/**
	 * Iterates through the list of steps to complete the job
	 * 
	 * @param list		--The job configuration
	 * @param p			--PackageInfo object
	 * 
	 * @return int		--job execution status
	 */
	public int executeJob(LinkedList<IStep> list, PackageInfo p) {
		String fp = "executeJob: ";
		int status = Constants.STATUS_CODE_INCOMPLETE;
		IStep step = null;
		
		__job:
		while(!list.isEmpty()){
			status = Constants.STATUS_CODE_INCOMPLETE;
			//get next step from the job
			step = list.removeFirst();
			//call each step execute() method
			status = step.execute(p);
			
			if(status!=Constants.STATUS_CODE_SUCCESS){
				//some error, stop job execution 
				break __job;
			}
		}	
		log.info(fp + "Job execution stopped with status " + status);
		return status;
	}
	
	/**
	 * Creates a deep copy of a job 
	 * 
	 * @param j
	 * @return
	 * 
	 * @throws Exception
	 */
	private LinkedList<IStep> cloneJob(LinkedList<IStep> j) throws Exception {
		LinkedList<IStep> newjob = new LinkedList<>(); 
		IStep tmp;
		for(IStep s : j){
			tmp = s.cloneStep();
			newjob.add(tmp);
		}
		return newjob;
	}
		    
	/**
	 * Loads appropriate job based on known job direction (inbound or outbound) and 
	 * package type 
	 * 
	 * @param p						--PackageInfo object, contains all information 
	 * 								  about the package being processed
	 * 
	 * @return LinkedList<ITask>	--job (list of tasks in the order of execution)
	 * 
	 * @throws Exception
	 */
	public LinkedList<IStep> resolveJob(PackageInfo p) throws Exception {
		String fp = "resolveJob: ";
		LinkedList<IStep> job = null;
		String jobDirection = p.getJobDirection(); //in or out
		String dataProvider = p.getDataProvider(); 

		//build a key to find a job
		StringBuilder sb = new StringBuilder();
		sb
		.append(jobDirection)
		;

		//get the key default for this type of job
		String defaultKey = sb.toString();
		//get the key for this type of job specific to the package source
		String specificKey = defaultKey+"_" + dataProvider.toUpperCase();
		
		//look for the job specific to the package source
		try {
			job = cloneJob(Globals.getJob(specificKey));
			//if source specific job not found, use default
			if(job==null || job.isEmpty())
				throw new Exception();
		}
		catch(Exception e){
			job = cloneJob(Globals.getJob(defaultKey));
		}
		
		log.info(fp + "Got a job: " + job);		
		if(job==null || job.isEmpty())
			throw new Exception("Configuration not found!");

	    return job;
	}
	
	/**
	 * Takes care of all on job start routines 
	 */
	private void onJobStart(PackageInfo p){
		String fp = "onJobStart: ";
		log.info(fp);
		
		//invoke OnJobStart object that encapsulates all on-job-start routines
		ojs.invoke(p);
	}
	
	/**
	 * Takes care of all on job end routines 
	 * 
	 * @param st	--execution status of the completed job
	 * @param p		--PackageInfo object
	 */
	private void onJobEnd(int st, PackageInfo p){
		String fp = "onJobEnd: ";
		log.info(fp);
		//invoke OnJobEnd object that encapsulates all on-job-end routines
		oje.invoke(st, p);
	}
}
