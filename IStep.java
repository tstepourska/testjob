package com.mycompany.myapp.step;

import com.mycompany.myapp.model.PackageInfo;

/**
 * 
 * @author Tatiana Stepourska
 */
public interface IStep extends Comparable<IStep> {
	
	public int execute(PackageInfo p);
	
	public String getId();
	public void setId(String id);
	public int getSequence();
	public void setSequence(int sequence);

	public String getJobId();
	public void setJobId(String jobId);
	
	/**
	 * Enables cloning of the Step object from existing one
	 * @return IStep
	 * @throws Exception
	 */
	public IStep cloneStep() throws Exception;
}
