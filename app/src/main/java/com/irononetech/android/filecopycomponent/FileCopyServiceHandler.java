package com.irononetech.android.filecopycomponent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.irononetech.android.Webservice.ServiceHandler;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.Webservice.WebServiceObject;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.utilities.JPGFileFilter;

public class FileCopyServiceHandler extends ServiceHandler {

	static Logger LOG = LoggerFactory.getLogger(FileCopyServiceHandler.class);

	public FileCopyServiceHandler(WebServiceObject webServiceObject) {
		super(webServiceObject);
	}

	@Override
	public void excecute() {

		try {
			List<File> srcFileList = getSrcImageList();
			List<File> destFileList = getDestImageList();

			List<File> actualList = compareImageDir(srcFileList, destFileList);
			if(actualList != null && actualList.size() > 0){
				FileOperations.copyFilesFromDirToDir(actualList, URL.getSLIC_COPIED());
			}
		} catch (Exception e) {
			LOG.error("FileCopyServiceHandler:10122");
			   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
		}
	}

	public static List<File> getSrcImageList(){
		try {
			File f = new File(URL.getSLIC_USB());
			List<File> fileList = FileOperations.listFiles(f, new JPGFileFilter(), true);

			return fileList;
		} catch (Exception e) {
			LOG.error("getSrcImageList:10123");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}

	//Suren
	public static List<File> getDestImageList()
	{  		
		try {
			File f = new File(URL.getSLIC_COPIED());
			List<File> fileList = FileOperations.listFiles(f, new JPGFileFilter(), true);

			return fileList;
		} catch (Exception e) {
			LOG.error("getDestImageList:10124");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}

	//Suren 
	public static List<File> compareImageDir(List<File> srcFileList, List<File> destFileList)
	{
		try {
			//finalFileList - in the camera
			//destFileList  - in the Copied folder
			List<File> resultList = new ArrayList<File>(); 
			Boolean isExists = false;

			// Check if destination empty then return src
			if(destFileList.size() == 0)
			{
				//LogFile.d("Logging","Source list returned"); 
				return srcFileList;
			}

			// Start Copy
			for(int i = 0; i < srcFileList.size(); i++ )
			{	
				for(int j = 0; j < destFileList.size(); j++)
				{
					if (!(srcFileList.get(i).getName().equalsIgnoreCase(destFileList.get(j).getName())))
					{ // name have no match
						isExists = true;
					}else{ // name have a match
						isExists = false;
						break;
					}
				} //end for

				if (isExists)
				{
					//LogFile.d("Logging","Copy list: " + srcFileList.get(i).getName() );
					resultList.add(srcFileList.get(i));
				}
			}	
			return resultList;
		} catch (Exception e) {
			LOG.error("compareImageDir:10125");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}
}