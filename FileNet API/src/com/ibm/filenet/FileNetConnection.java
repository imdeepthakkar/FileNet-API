package com.ibm.filenet;
import javax.security.auth.Subject;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import com.ibm.filenet.config.FileNetConfig;
import com.ibm.filenet.config.FileNetConstants;

public class FileNetConnection {
	public Connection getCEConnection() {

		Connection conn = null;
		try {
			String ceURI = FileNetConfig.getInstance().getConfiguration(FileNetConstants.CE_WSI_URI);;
			String userName = FileNetConfig.getInstance().getConfiguration(FileNetConstants.USER_NAME);
			String password = FileNetConfig.getInstance().getConfiguration(FileNetConstants.PASSWORD);
			conn = Factory.Connection.getConnection(ceURI);
			Subject subject = UserContext.createSubject(conn, userName, password, "FileNetP8WSI");
			UserContext uc = UserContext.get();
			uc.pushSubject(subject);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}
	
	public ObjectStore getObjectStore(Connection conn) {
		String objStoreName = FileNetConfig.getInstance().getConfiguration(FileNetConstants.OS_NAME);
		Domain domain = Factory.Domain.fetchInstance(conn, null, null);
		ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, objStoreName, null);
		return objStore;
	}
	
	/*public static void main(String[] args) {
		FileNetConnection objFNConn = new FileNetConnection();
		Connection objConn = objFNConn.getCEConnection();
		ObjectStore objOS = objFNConn.getObjectStore(objConn);
		System.out.println("Os: "+objOS);
	}*/
}
