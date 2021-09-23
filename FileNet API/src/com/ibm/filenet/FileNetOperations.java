package com.ibm.filenet;
import java.io.File;
import java.io.FileInputStream;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.Id;


public class FileNetOperations {

	public void createDocument(ObjectStore os){
		Document doc = Factory.Document.createInstance(os,"Employee");
		doc.getProperties().putValue("DocumentTitle", "123456775");
		doc.getProperties().putValue("EmployeeID", 12673);
		doc.getProperties().putValue("EmployeeName", "New Employee1");
		doc.save(RefreshMode.NO_REFRESH );
		// Check in the document.
		doc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
		doc.save(RefreshMode.NO_REFRESH);

	}
	
	public void updateDocument(ObjectStore objStore, String strClassName,String strDocID){
		// Get document.
		Document doc=Factory.Document.getInstance(objStore, strClassName, new Id(strDocID) );

		// Check out the Document object and save it.
		doc.checkout(com.filenet.api.constants.ReservationType.EXCLUSIVE, null, doc.getClassName(), doc.getProperties());
		doc.save(RefreshMode.REFRESH);

		// Get the Reservation object from the Document object.
		Document reservation = (Document) doc.get_Reservation();
		
		// Specify internal and external files to be added as content.
		File internalFile = new File("G:\\Downloads\\TestVideo.mp4");
		String strFileName = internalFile.getAbsolutePath().substring(internalFile.getAbsolutePath().lastIndexOf("\\")+1);
		// Add content to the Reservation object.
		try {
		    // First, add a ContentTransfer object.
		    ContentTransfer ctObject = Factory.ContentTransfer.createInstance();
		    FileInputStream fileIS = new FileInputStream(internalFile.getAbsolutePath());
		    ContentElementList contentList = Factory.ContentTransfer.createList();
		    ctObject.setCaptureSource(fileIS);
		    ctObject.set_RetrievalName(strFileName);
		    ctObject.set_ContentType("video/mp4");
		    contentList.add(ctObject);


		    reservation.set_ContentElements(contentList);
		    reservation.save(RefreshMode.REFRESH);
		    
		

		// Check in Reservation object as major version.
		reservation.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
		reservation.save(RefreshMode.REFRESH);    
		}
		catch (Exception e)
		{
		    System.out.println(e.getMessage() );
		}
	}

	public static void main(String[] args) {
		FileNetConnection objFNConn = new FileNetConnection();
		Connection objConn = objFNConn.getCEConnection();
		ObjectStore objOS = objFNConn.getObjectStore(objConn);
		FileNetOperations obj = new FileNetOperations();
		/*try{
			obj.createDocument(objOS);
		} catch (Exception e){
			e.printStackTrace();
		}*/
		
		try{
			obj.updateDocument(objOS, "Employee", "{C810AD1D-735E-4ABC-B041-EA19021F5EC0}");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
