package com.mycompany.myapp.main;

import java.io.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.model.PackageInfoFactory;
import com.mycompany.myapp.job.TaskManager;
import com.mycompany.myapp.util.*;

/**
 * This class runs the sample application, which is a simplified model of 
 * a real life process.
 * The purpose of this application is to show how based on some input parameters  (data source and job direction-where the package is from and where it is going)
 * 	1) how the set of tasks is configured, 
 * and then selected at runtime based on some input parameters
 *  2) how the specific implementation is invoked at runtime by loading 
 * 
 * @author Tatiana Stepourska
 *
 */
public class Main { 

	private static Logger log = Logger.getLogger(Main.class);

	private static void init(){
		
		// 1. load configuration
		try {
			//Globals.loadDomainProperties();
			Globals.loadJobs();   

			//log.info("execute: properties loaded: ");
			System.out.println(Globals.toStaticString());
		}
		catch(Exception e){
			System.out.println("Failed to load properties: " + e);
			log.error("Exception: " + e.getMessage());
		}	
	}
	
	/**
	 * Runs the process
	 *  
	 */
	public static void main(String[] args)  {		
		int status = Constants.STATUS_CODE_INCOMPLETE;
		
		try {		
			//initialize configuration
			init();
			
			//get input 
			ArrayList<String> input = emulateInput();
			
			//initialize factory object
			PackageInfoFactory factory = new PackageInfoFactory();

			//create task manager
			TaskManager taskman = new TaskManager();		
		
			PackageInfo p = null;
			String filename = null;
			String dataProvider = null;
			String jobDir = null;
			String[] arr = null;

			if(input == null || input.size()<=0){
					log.info("nothing to process, exiting process");
					//working = false;
					System.exit(0);
			}

			//loop through the input
			for (String s : input) {
				try {
					if(s==null || s.length()<=0)
						continue;
				
					arr = s.split(Constants.DASH);
					//get next input entry
					filename = arr[0];
					dataProvider = arr[1];
					jobDir = arr[2];
					log.info("found entry: " + filename + " for data provider " + dataProvider + " directed " + jobDir);

					//initialize package
					p = factory.createPackageInfo(filename, jobDir,	dataProvider);
				
					//invoke task manger to do the job
					status = taskman.invoke(p);
					log.info("Processed entry with status " + status);

					try {Thread.sleep(Constants.SLEEP_BETWEEN_JOBS_SEC);} catch( InterruptedException ex ) {}			
				}
				catch(Exception ex){
					log.error("Exception processing enrty: " + filename + ": "+ ex.getMessage());
					status = Constants.STATUS_CODE_ERROR;
				}
			}	//end of for each file
			
			//	try {Thread.sleep(Constants.SLEEP_BETWEEN_BATCHES_SEC);	}catch(Exception e){	working = false;	}		
			//}	//end of while working

			log.info("Completed input batch");			
		}
		catch(Exception e){
			status = Constants.STATUS_CODE_TRANSFORMATION_FAILED;
			log.error("Exception: " + e.getMessage());
		}	
	}

	/**
	 * Emulates input data for this sample application
	 * (filename-dataprovider-direction)
	 * by reading file and storing it in the ArrayList
	 * 
	 * @param filepath
	 * @return ArrayList<String>
	 * @throws Exception
	 */
	private static ArrayList<String> emulateInput() throws Exception { //String filepath
		ArrayList<String> entries = new ArrayList<>();		
		String inputFile = Constants.RESOURCE_BASE_PKG + "directory.list";
		
		
		try(InputStream is = Globals.class.getClassLoader().getResourceAsStream(inputFile);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));			){
			String line;
			while((line = reader.readLine())!=null){
				entries.add(line);
			}
		}
		
		return entries;
	}
}
