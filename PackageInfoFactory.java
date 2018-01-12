package com.mycompany.myapp.model;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * 
 * @author Tatiana Stepourska
 *
 */
public class PackageInfoFactory {
	private static Logger lg = Logger.getLogger(PackageInfoFactory.class);
	
	/**
	 * Initializes new package info object and sets variables 
	 * necessary to find a job appropriate for processing this package: 
	 * job direction, data provider etc
	 * 
	 */
	public PackageInfo createPackageInfo(String filename, String jobDirection, String dataProvider) throws Exception{	
		String fp = "createPackageInfo: ";
		PackageInfo p = new PackageInfo();		
		p.setFilename(filename);
		p.setJobDirection(jobDirection);
		p.setDataProvider(dataProvider);
		
		lg.info(fp + "package info: " + p);
	
		return p;
	}

	/**
	 * Initializes list of PackageInfo objects, each corresponding to the chunk of 
	 * original file created by splitting
	 * 
	 * @param p
	 * @return
	 * @throws Exception
	 */
	public	ArrayList<PackageInfo> initPackageList(PackageInfo p) throws Exception {
		String fp = "initPackageList: ";
		int splitCnt = p.getSplitFileCount();
		lg.info(fp + "splitCnt: " + splitCnt);
		ArrayList<PackageInfo> pList = new ArrayList<>(splitCnt);
/*		//PackageInfo[] pList = new PackageInfo[splitCnt];
		
		String origFileName = p.getOrigFilename();
		lg.info(fp + "origFileName: " + origFileName);
		PackageInfo pi = null;
		int num = 0;
		
		for(int i=0;i<splitCnt;i++){
			pi = p.clone();
			pi.setSplitFileCount(splitCnt);
			num = i +1;
			
			pi.setOrigFilename(origFileName+"_" + num);
			//generate XML filename and set it to package info object
			pi.setXmlFilename     (Utils.generateXMLFileName(p, false) + Constants.UNDERSCORE + num + Constants.FILE_EXT_XML);
			//pi.setMetadataFilename(Utils.generateMetadataFilename(p, false) + Constants.UNDERSCORE + i + Constants.FILE_EXT_XML);
			//if(lg.isDebugEnabled()){
			//	lg.debug(fp + "XMLFile: " + pi.getXmlFilename() + ", Metadata name: " + pi.getMetadataFilename());
			//}
			//reset messageRefId from cloned object
			pi.setMessageRefId(null);
			
			//set cloned messageRefID and packageId as originals to associate
			pi.setOrigMessageRefId(p.getMessageRefId());
			pi.setOrigPackageId(p.getPackageId());
			
			int pkgId = DataFactory.INSTANCE.generatePkgId(p.getCtsCommunicationType(), 
					Constants.JOB_INBOUND, 
					pi.getDataProvider(), 
					false,  //isRevision,
					pi.getUserId()); 
			if(pkgId<=0)
				throw new Exception("Failed to generate new package ID");
			pi.setPackageId(pkgId);
			
			pList.add(pi);
		}
		
		if(lg.isDebugEnabled())
		lg.debug(fp + "created PackageInfo list with size: " + pList.size());
		*/
		return pList;
	}


}	//end of class
