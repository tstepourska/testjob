package com.mycompany.myapp.step;

import org.apache.log4j.Logger;

import com.mycompany.myapp.model.PackageInfo;
import com.mycompany.myapp.util.Constants;
import com.mycompany.myapp.util.Utils;

/**
 * This abstract implementation of the IStep interface contains all  
 * methods that are common for any Step object
 * 
 * @author Tatiana Stepourska
 */
public abstract class AbstractStep implements IStep, Comparable<IStep> {
	private static Logger log = Logger.getLogger(AbstractStep.class);
	protected int resultCode = Constants.STATUS_CODE_INCOMPLETE;
	protected String id;
	protected int sequence;
	protected String jobId;
	
	/**
	 * @return the jobId
	 */
	@Override
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	@Override
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * Allows to sort steps by sequence number
	 */
	@Override
	public int compareTo(IStep t) throws NullPointerException, ClassCastException{
		int seq = t.getSequence();
		
		if(this.sequence==seq)
			return 0;
		else if(this.sequence<seq)
			return -1;
		else
			return 1;
	}
	
	/**
	 * Overrides object equals() method to highlight the differences in comparison criteria 
	 * between equals and compareTo methods;
	 * task.equals(anotherTask)==true if the class, id, and the sequence number are the same
	 */
	@Override
	public boolean equals(Object t) throws NullPointerException{
		if(this.sequence==((IStep)t).getSequence() && 
		   this.getClass().getName().compareTo(t.getClass().getName())==0 &&
		  (this.id).compareTo(((IStep)t).getId())==0)
			return true;
		
		return false;
	}
	
	/**
	 * Overrides Object hashCode() method.
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	/**
	 * @return the id
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the sequence
	 */
	@Override
	public int getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	@Override
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * Triggers execution of the application logic for this step. 
	 * Gets called from TaskManager
	 */
	@Override
	public abstract int execute(PackageInfo p);

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		try {
		String clazz = this.getClass().getName();
		
		sb.append("\n").append(this.jobId).append(":")
		.append(this.sequence).append(".")
		.append(this.id).append(".")		
		.append(clazz.substring(clazz.lastIndexOf(".")+1));
		}
		catch(Exception e){
			Utils.logError(log, e);
		}
		
		return sb.toString();
	}
}
