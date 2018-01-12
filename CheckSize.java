package com.mycompany.myapp.step;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.util.*;

/**
 * @author Tatiana Stepourska
 */
public class CheckSize extends AbstractStep {
	private static Logger lg = Logger.getLogger(CheckSize.class);

	
	@Override
	public final int execute(PackageInfo p) {
		int status = Constants.STATUS_CODE_INCOMPLETE;
		lg.info("CheckSize executing");
		
		try {
		/*	String filename = p.getFileWorkingDir() + p.getOrigFilename();		
			String country = p.getReceivingCountry();
			File file = new File(filename);
			if(!file.exists())
				throw new FileNotFoundException();
			long filesize = file.length();
			lg.info("file size: " + filesize);
			p.setOrigUncompressFileSizeKBQty(BigInteger.valueOf(filesize));
			
			//get allowed maximum file size from configuration
			//Globals.FileSize fs = Globals.specificFileSizes.get(country);
			//if(lg.isDebugEnabled())
			//	lg.debug("FileSize object with specific sizes: " + fs);

			//estimate final file size and set split file count to package
			double splitFactor = estimateSize(filesize,p, null); //fs);
			//if(lg.isDebugEnabled())
			lg.debug("splitFactor: " + splitFactor);
			
			//estimated file size exceeds maximum allowed
			if(splitFactor>Constants.NO_SPLIT){
				lg.debug("splitFactor>Constants.NO_SPLIT - might need to split");
				//no splitting of XML files
					
					//split flat file into chunks and set split count to package info
					int splitCount = 0;
					//if(p.getDataProvider().equals(Constants.DATA_PROVIDER_CRS)){
						lg.debug("splitting CRS file");
						splitCount = splitCRSFile(filename, splitFactor, p);
				
					lg.info("flat file splitCount: " + splitCount);
			
					//set status for TaskManager
					status = Constants.STATUS_CODE_FILE_SPLIT; //    STATUS_CODE_CREATE_JOB_LOOP;
			//	}
				
				//insert into database information about original file
				try {
					// insert into TPKG table and get back package_id
					int pkgId = p.getPackageId();
					lg.debug("pkgId: " + pkgId);
					//p.setPackageId(pkgId);
					p.setMessageRefId(Constants.CANADA + p.getReportingPeriod().getYear() + p.getReceivingCountry() + "EEIsplit"+pkgId);
					
					//InsertRecordToSplitObj insertRec = new InsertRecordToSplitObj();
					//insertRec.invoke(p, status);
				}
				catch(Exception e){
					lg.error("Exception inserting record for split: " + e.getMessage());
				}
				catch(Error e){
					lg.error("Error inserting record for split: " + e.getMessage());
				}			
			}
			else { //estimated file size is within the limit, no split
				lg.info("file size is within the limit, no split");
				if(filename.endsWith(Constants.FILE_EXT_XML)){
					p.setXmlFilesize(filesize);
				}
				status = Constants.STATUS_CODE_SUCCESS;
			}*/
		}
		catch(Exception e){
			status = Constants.STATUS_CODE_ERROR;
			Utils.logError(lg, e);
		}
		
		//for wireframe testing only - to comment out!
		status = Constants.STATUS_CODE_SUCCESS;
		// end of to comment out

		return status;
	}	

	@Override
	public CheckSize cloneStep(){
		CheckSize t = new CheckSize();
		t.setId(this.id);
		t.setSequence(this.sequence);
		t.setJobId(this.jobId);

		return t;
	}

	///////////////////////////////////////////////////
	// Private methods
	///////////////////////////////////////////////////

}
