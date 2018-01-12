package com.mycompany.myapp.step.xml;

import com.mycompany.myapp.model.PackageInfo;

/**
 * 
 * @author Tatiana Stepourska
 *
 */
public interface IHelper {
	public String[] getSchemas();
	int invoke(PackageInfo p);
	int transform(PackageInfo p);
	int validate(PackageInfo p,final String[] schemas) throws Exception;
}
