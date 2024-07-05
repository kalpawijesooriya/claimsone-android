package com.irononetech.android.fileio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.database.DBService;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.formcomponent.VisitObjectDraft;
import com.irononetech.android.homecomponent.HomeActivity;
import com.irononetech.android.utilities.DirFilter;
import com.irononetech.android.utilities.ImageCompressionUtil;
import com.irononetech.android.utilities.JPGFileFilter;

public class FileOperations {

	static Logger LOG = LoggerFactory.getLogger(FileOperations.class);

	public static ArrayList<String> getFileList(String filepath) {

		ArrayList<String> fileNames = new ArrayList<String>();

		try {
			File f = new File(filepath);
			List<File> fileList = FileOperations.listFiles(f, new JPGFileFilter(),
					true);

			for (int i = 0; i < fileList.size(); i++) {
				fileNames.add(fileList.get(i).getPath());
			}
		} catch (Exception e) {
			LOG.error("etFileList:10126");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}

		return fileNames;
	}

	/**
	 * Returns files from given rootDir according to used filter
	 * 
	 * @param rootDir
	 *            directory to list files
	 * @param filter
	 *            filter to use
	 */
	public static List<File> listFiles(File rootDir, FileFilter filter,	boolean recursive) {

		List<File> result = new ArrayList<File>();

		try {
			if (!rootDir.exists() || !rootDir.isDirectory()){
				boolean exists = rootDir.exists();
				boolean isDir = rootDir.isDirectory();
				LOG.debug("bools",exists);
				LOG.debug("bools",isDir);
				return result;
			}

			// Add all files that comply with the given filter
			File[] files = rootDir.listFiles(filter);
			LOG.debug("files",files);
			for (File f : files) {
				if (!result.contains(f))
					result.add(f);
			}

			// Recurse through all available dirs if we are scanning recursively
			if (recursive) {
				File[] dirs = rootDir.listFiles(new DirFilter());
				for (File f : dirs) {
					if (f.canRead()) {
						result.addAll(listFiles(f, filter, recursive));
					}
				}
			}
		} catch (Exception e) {
			LOG.error("listFiles:10127");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}

		Pair[] pairs = new Pair[result.size()];
		for (int i = 0; i < result.size(); i++)
		    pairs[i] = new Pair(result.get(i));

		// Sort them by time-stamp.
		Arrays.sort(pairs, Collections.reverseOrder());

		// Take the sorted pairs and extract only the file part, discarding the time-stamp.
		for (int i = 0; i < result.size(); i++)
			result.set(i, pairs[i].f);
		
		return result;
	}

	public static void copyFilesFromDirToDir(List<File> files, String destinationFolder) {
		try {
			File myDirectory = new File(destinationFolder);

			//Delete existing images in 'destinationFolder' folder  ----SLIC/Copied
			//if(myDirectory.exists()){
				//Suren
				//DeleteRecursive(myDirectory);
			//}

			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}

			for (int i = 0; i < files.size(); i++) {
				Thread.sleep(1000);

				if (files.get(i).exists()) {
					OutputStream out = null;
					String filePath = files.get(i).getPath();
					String fileName = files.get(i).getName();
					File destination = new File(myDirectory, fileName);

					// Image Compression
					Bitmap bm = ShrinkBitmap(filePath, 1024, 1024);
					ByteArrayOutputStream bytes = new ByteArrayOutputStream();
					bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

					out = new FileOutputStream(destination);
					copyFile(bytes.toByteArray(), out);

					out.flush();
					out.close();
					out = null;
				}
			}
		} catch (Exception e) {
			LOG.error("copyFilesFromDirToDir:10128");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public static Bitmap ShrinkBitmap(String file, int resize_width, int resize_height) {
 		 
		Bitmap bitmap = BitmapFactory.decodeFile(file);
		int width = bitmap.getWidth();
	    int height = bitmap.getHeight();
 
	    if (width > height) {
 	        float ratio = (float) width / resize_width;
	        width = resize_width;
	        height = (int)(height / ratio);
	    } else if (height > width) {
 	        float ratio = (float) height / resize_height;
	        height = resize_height;
	        width = (int)(width / ratio);
	    } else {
 	        height = resize_height;
	        width = resize_width;
	    }
  
	    bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
	    return bitmap;
	}

	private static void copyFile(byte[] byteArray, OutputStream out) {
		try {
			out.write(byteArray);
		} catch (IOException e) {
			LOG.error("copyFile:10129");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} catch (Exception e) {
			LOG.error("copyFile:10130");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public static void copyBitmapToDir(Bitmap bm,
			String destinationFolder, String fileName) {
		try {
			File myDirectory = new File(Environment
					.getExternalStorageDirectory(), destinationFolder);

			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}

			try {
				OutputStream out = null;
				File destination = new File(myDirectory, fileName);

				// Image Compression 
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

				out = new FileOutputStream(destination);
				copyFile(bytes.toByteArray(), out);
				out.flush();
				out.close();
				out = null;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			LOG.error("copyBitmapToDir:10131");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public static void copyFilesFromDirToDir(List<File> files) {
		try {
			File myDirectory = new File(Environment
					.getExternalStorageDirectory(), "SLIC");
			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}

			for (int i = 0; i < files.size(); i++) {
				InputStream in = null;
				OutputStream out = null;
				String filePath = files.get(i).getPath();
				String fileName = files.get(i).getName();
				File destination = new File(myDirectory, fileName);
				in = new FileInputStream(filePath);
				out = new FileOutputStream(destination);
				copyFile(in, out);
				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;
			}
		} catch (Exception e) {
			LOG.error("copyFilesFromDirToDir:10132");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public static void copyFilesFromDirToDir(ArrayList<String> files,
			String destinationFolder) {
		try {
			File myDirectory = new File(URL.getSLIC_JOBS()	+ destinationFolder);

			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}

			for (int i = 0; i < files.size(); i++) {
				InputStream in = null;
				OutputStream out = null;
				String filePath = files.get(i).toString();
				File a = new File(filePath);
				String fileName = a.getName();
				File destination = new File(myDirectory, fileName);
				in = new FileInputStream(filePath);
				out = new FileOutputStream(destination);
				copyFile(in, out);
				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;
			}
		} catch (Exception e) {
			LOG.error("copyFilesFromDirToDir:10133");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public static void DeleteRecursive(File fileOrDirectory) {
		try {
			if (fileOrDirectory.isDirectory())
				for (File child : fileOrDirectory.listFiles())
					DeleteRecursive(child);

			fileOrDirectory.delete();
		} catch (Exception e) {
			LOG.error("DeleteRecursive:10134");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private static void DeleteFiles(File fileOrDirectory, FormObject fo, String destinationFolder) {
		try {
			File[] listFiles =  fileOrDirectory.listFiles();
			String category = destinationFolder.split("/")[destinationFolder.split("/").length -1].trim();

			if(category.equalsIgnoreCase("DLStatement")){
				if(fo.getDLStatementImageList() != null && fo.getDLStatementImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();	// + "/" + listFiles[i].getName();

						if(fo.getDLStatementImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("TechnicalOfficerComments")){
				if(fo.getTechnicalOfficerCommentsImageList() != null && fo.getTechnicalOfficerCommentsImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getTechnicalOfficerCommentsImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("ClaimFormImage")){
				if(fo.getClaimFormImageImageList() != null && fo.getClaimFormImageImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getClaimFormImageImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("ARI")){
				if(fo.getARIImageList() != null && fo.getARIImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getARIImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("DR")){
				if(fo.getDRImageList() != null && fo.getDRImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getDRImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("SeenVisit")){
				if(fo.getSeenVisitImageList() != null && fo.getSeenVisitImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getSeenVisitImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("SpecialReport1")){
				if(fo.getSpecialReport1ImageList() != null && fo.getSpecialReport1ImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getSpecialReport1ImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("SpecialReport2")){
				if(fo.getSpecialReport2ImageList() != null && fo.getSpecialReport2ImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getSpecialReport2ImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("SpecialReport3")){
				if(fo.getSpecialReport3ImageList() != null && fo.getSpecialReport3ImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getSpecialReport3ImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("Supplementary1")){
				if(fo.getSupplementary1ImageList() != null && fo.getSupplementary1ImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getSupplementary1ImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("Supplementary2")){
				if(fo.getSupplementary2ImageList() != null && fo.getSupplementary2ImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getSupplementary2ImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("Supplementary3")){
				if(fo.getSupplementary3ImageList() != null && fo.getSupplementary3ImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getSupplementary3ImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("Supplementary4")){
				if(fo.getSupplementary4ImageList() != null && fo.getSupplementary4ImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getSupplementary4ImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("Acknowledgment")){
				if(fo.getAcknowledgmentImageList() != null && fo.getAcknowledgmentImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getAcknowledgmentImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}

			if(category.equalsIgnoreCase("SalvageReport")){
				if(fo.getSalvageReportImageList() != null && fo.getSalvageReportImageList().size() > 0){

					for (int i = 0; i < listFiles.length; i++) {
						String tmp = listFiles[i].getPath();

						if(fo.getSalvageReportImageList().contains(tmp)){
							listFiles[i].delete();
						}
					}}}
		} catch (Exception e) {
			LOG.error("DeleteFiles:10135");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}

	}

	public static void copyFilesFromDirToDirAddImages(ArrayList<String> files,
			String destinationFolder) {
		try {
			File myDirectory = new File(URL.getSLIC_JOBS() + destinationFolder);
			if(myDirectory.exists()){
				DeleteRecursive(myDirectory);
			}
			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}

			File textFile = new File(myDirectory, "imgNames.txt");

			String imgNames = "";

			for (int i = 0; i < files.size(); i++) {
				InputStream in = null;
				OutputStream out = null;
				String filePath = files.get(i);
				File a = new File(filePath);
				String fileName = a.getName();

				if(i == files.size()-1){
					imgNames += fileName;
				}else{
					imgNames += (fileName + ",");
				}

				File destination = new File(myDirectory, fileName);
				in = new FileInputStream(filePath);

				ImageCompressionUtil.compressImage(filePath, destination);

				/*// Image Compression
				Bitmap bm = ShrinkBitmap(filePath, 1024, 1024);
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

				out = new FileOutputStream(destination);
				copyFile(bytes.toByteArray(), out);

				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;*/
			}

			FileOutputStream stream = new FileOutputStream(textFile);
			try {
				stream.write(imgNames.getBytes());
			} finally {
				stream.flush();
				stream.close();
				stream = null;
			}
		} catch (Exception e) {
			LOG.error("copyFilesFromDirToDirAddImages:10136");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public static void copyFilesFromDirToDirAddDocs(ArrayList<String> files,
			String destinationFolder) {
		FormObject fo = Application.getFormObjectInstance();
		try {
			File myDirectory = new File(URL.getSLIC_JOBS() + destinationFolder);

			//Suren restricted only for normal submissions coz re submission fails otherwise
			if(fo.getisSEARCH()){
				if(myDirectory.exists()){
					DeleteFiles(myDirectory, fo, destinationFolder);
				}
			}else{
				if(myDirectory.exists()){
					DeleteRecursive(myDirectory);
				}
			}
			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}

			File textFile = new File(myDirectory, "imgNames.txt");

//			Random randomGenerator = new Random();
//			int posRandInt = randomGenerator.nextInt( Integer.MAX_VALUE );

			String imgNames = "";

			ArrayList<String> tempArr = new ArrayList<String>();

			for (int i = 0; i < files.size(); i++) {
				InputStream in = null;
				OutputStream out = null;
				String filePath = files.get(i);
				File a = new File(filePath);

				//addomg posRandom Integer will be hard to mark the selected images
//				posRandInt = randomGenerator.nextInt( Integer.MAX_VALUE );
//				String fileName = posRandInt + "_" + a.getName();
				String fileName = a.getName();

				if(i == files.size()-1){
					imgNames += fileName;
				}else{
					imgNames += (fileName + ",");
				}

				if(fo.getisSEARCH()){
					tempArr.add(myDirectory +"/"+ fileName);
					LOG.info("\n\nFileCopyLOCAL ", myDirectory +"/"+ fileName);
				}
				File destination = new File(myDirectory, fileName);
//				in = new FileInputStream(filePath);

				ImageCompressionUtil.compressImage(filePath,destination);

				/*// Image Compression
				Bitmap bm = ShrinkBitmap(filePath, 1024, 1024);
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

				out = new FileOutputStream(destination);
				copyFile(bytes.toByteArray(), out);

				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;*/
			}
			FileOutputStream stream = new FileOutputStream(textFile);
			try {
				stream.write(imgNames.getBytes());
			} finally {
				stream.flush();
				stream.close();
				stream = null;
			}
			if(fo.getisSEARCH()){
				String category = destinationFolder.split("/")[destinationFolder.split("/").length -1].trim();

				if(category.equalsIgnoreCase("DLStatement")){
					if(fo.getDLStatementImageList() != null && fo.getDLStatementImageList().size() > 0){
						fo.setDLStatementImageList(null);
						fo.setDLStatementImageList(tempArr);
					}else{
						fo.setDLStatementImageList(tempArr);
					}
				}
				if(category.equalsIgnoreCase("TechnicalOfficerComments")){
					if(fo.getTechnicalOfficerCommentsImageList() != null && fo.getTechnicalOfficerCommentsImageList().size() > 0){
						fo.setTechnicalOfficerCommentsImageList(null);
						fo.setTechnicalOfficerCommentsImageList(tempArr);
					}else{
						fo.setTechnicalOfficerCommentsImageList(tempArr);
					}
				}
				if(category.equalsIgnoreCase("ClaimFormImage")){
					if(fo.getClaimFormImageImageList() != null && fo.getClaimFormImageImageList().size() > 0){
						fo.setClaimFormImageImageList(null);
						fo.setClaimFormImageImageList(tempArr);
					}else{
						fo.setClaimFormImageImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("ARI")){
					if(fo.getARIImageList() != null && fo.getARIImageList().size() > 0){
						fo.setARIImageList(null);
						fo.setARIImageList(tempArr);
					}else{
						fo.setARIImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("DR")){
					if(fo.getDRImageList() != null && fo.getDRImageList().size() > 0){
						fo.setDRImageList(null);
						fo.setDRImageList(tempArr);
					}else{
						fo.setDRImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("SeenVisit")){
					if(fo.getSeenVisitImageList() != null && fo.getSeenVisitImageList().size() > 0){
						fo.setSeenVisitImageList(null);
						fo.setSeenVisitImageList(tempArr);
					}else{
						fo.setSeenVisitImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("SpecialReport1")){
					if(fo.getSpecialReport1ImageList() != null && fo.getSpecialReport1ImageList().size() > 0){
						fo.setSpecialReport1ImageList(null);
						fo.setSpecialReport1ImageList(tempArr);
					}else{
						fo.setSpecialReport1ImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("SpecialReport2")){
					if(fo.getSpecialReport2ImageList() != null && fo.getSpecialReport2ImageList().size() > 0){
						fo.setSpecialReport2ImageList(null);
						fo.setSpecialReport2ImageList(tempArr);
					}else{
						fo.setSpecialReport2ImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("SpecialReport3")){
					if(fo.getSpecialReport3ImageList() != null && fo.getSpecialReport3ImageList().size() > 0){
						fo.setSpecialReport3ImageList(null);
						fo.setSpecialReport3ImageList(tempArr);
					}else{
						fo.setSpecialReport3ImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("Supplementary1")){
					if(fo.getSupplementary1ImageList() != null && fo.getSupplementary1ImageList().size() > 0){
						fo.setSupplementary1ImageList(null);
						fo.setSupplementary1ImageList(tempArr);
					}else{
						fo.setSupplementary1ImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("Supplementary2")){
					if(fo.getSupplementary2ImageList() != null && fo.getSupplementary2ImageList().size() > 0){
						fo.setSupplementary2ImageList(null);
						fo.setSupplementary2ImageList(tempArr);
					}else{
						fo.setSupplementary2ImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("Supplementary3")){
					if(fo.getSupplementary3ImageList() != null && fo.getSupplementary3ImageList().size() > 0){
						fo.setSupplementary3ImageList(null);
						fo.setSupplementary3ImageList(tempArr);
					}else{
						fo.setSupplementary3ImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("Supplementary4")){
					if(fo.getSupplementary4ImageList() != null && fo.getSupplementary4ImageList().size() > 0){
						fo.setSupplementary4ImageList(null);
						fo.setSupplementary4ImageList(tempArr);
					}else{
						fo.setSupplementary4ImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("Acknowledgment")){
					if(fo.getAcknowledgmentImageList() != null && fo.getAcknowledgmentImageList().size() > 0){
						fo.setAcknowledgmentImageList(null);
						fo.setAcknowledgmentImageList(tempArr);
					}else{
						fo.setAcknowledgmentImageList(tempArr);
					}	
				}

				if(category.equalsIgnoreCase("SalvageReport")){
					if(fo.getSalvageReportImageList() != null && fo.getSalvageReportImageList().size() > 0){
						fo.setSalvageReportImageList(null);
						fo.setSalvageReportImageList(tempArr);
					}else{
						fo.setSalvageReportImageList(tempArr);
					}	
				}}
		} catch (Exception e) {
			LOG.error("copyFilesFromDirToDirAddDocs:10137");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public static void copyFilesFromDirToDir(ArrayList<String> files) {
		try {
			File myDirectory = new File(Environment
					.getExternalStorageDirectory(), "copydirectory");
			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}

			for (int i = 0; i < files.size(); i++) {
				InputStream in = null;
				OutputStream out = null;
				String filePath = files.get(i).toString();
				File a = new File(filePath);
				String fileName = a.getName();
				File destination = new File(myDirectory, fileName);
				in = new FileInputStream(filePath);
				out = new FileOutputStream(destination);
				copyFile(in, out);
				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;
			}
		} catch (Exception e) {
			LOG.error("copyFilesFromDirToDir:10137");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public static void copyFile(InputStream in, OutputStream out)
			throws IOException {
		try {
			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read); 
			}

		} catch (Exception e) {
			LOG.error("copyFile:10138");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public static Bitmap getBitMap(String filePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitMap = BitmapFactory.decodeFile(filePath, options);
		return bitMap;
	}


	//------ForVisits--------
	public static void copyFilesFromDirToDirAddDocsForVisits(ArrayList<String> files, String destinationFolder) {

		VisitObject vo = Application.getVisitObjectInstance();
		try {
			File myDirectory = new File(URL.getSLIC_VISITS() + destinationFolder);

			//Suren restricted only for normal submissions coz re submission fails otherwise
			if(vo.getisSEARCH()){
				//if(myDirectory.exists()){
					//DeleteFiles(myDirectory, fo, destinationFolder);
				//}
			}else{
				if(myDirectory.exists()){
					DeleteRecursive(myDirectory);
				}
			}
			if (!myDirectory.exists()) {
				myDirectory.mkdirs();
			}

			Random randomGenerator = new Random();
			int posRandInt = randomGenerator.nextInt( Integer.MAX_VALUE );

			ArrayList<String> tempArr = new ArrayList<String>();

			for (int i = 0; i < files.size(); i++) {
				InputStream in = null;
				OutputStream out = null;
				String filePath = files.get(i).toString();
				File a = new File(filePath);

				//adding random number will change file name and checking the same file is impossible
				//posRandInt = randomGenerator.nextInt( Integer.MAX_VALUE );
				//String fileName = posRandInt + "_" + a.getName();
				String fileName = a.getName();
				if(vo.getisSEARCH()){
					tempArr.add(myDirectory +"/"+ fileName);
					LOG.info("\n\nFileCopyLOCAL ", myDirectory +"/"+ fileName);
				}


				File destination = new File(myDirectory, fileName);
				ImageCompressionUtil.compressImage(filePath, destination);
				/*in = new FileInputStream(filePath);
				// Image Compression
				Bitmap bm = ShrinkBitmap(filePath, 1024, 1024);
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
				
				out = new FileOutputStream(destination);
				copyFile(bytes.toByteArray(), out);
				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;*/
			}			

			if(vo.getisSEARCH()){
				String category = destinationFolder.split("/")[destinationFolder.split("/").length -1].trim();

				//TechnicalOfficerComments
				//EstimateAnyotherComments
				//InspectionPhotosSeenVisitsAnyOther
				//if(category.equalsIgnoreCase("TechnicalOfficerComments")){
					//if(vo.getDLStatementImageList() != null && fo.getDLStatementImageList().size() > 0){
					//vo.setDLStatementImageList(null);
					//vo.setDLStatementImageList(tempArr);
					//}else{
					//vo.setDLStatementImageList(tempArr);
					//}
				//}
			}
		} catch (Exception e) {
			LOG.error("copyFilesFromDirToDirAddDocsForVisits:11137");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}

	}

	/**
	 * @param filepath
	 * @param isSAForm true if SAForm draft else false
	 * @return ArrayList<String> file names without extension
	 */
	public static ArrayList<String> fileFilter(String filepath){
		//file filter
		ArrayList<String> draftfiles = new ArrayList<String>();
		try {
			File fil = new File(filepath);
			String name = "";

			if(fil.length() > 0){
				File[] files = fil.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (!files[i].isDirectory()){
						name = files[i].getName().replace(".ssm", "");
						draftfiles.add(name);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("fileFilter:10139");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		return draftfiles;
	}

	public static ArrayList<String> fileFilterForVisits(String filepath){
		//file filter
		ArrayList<String> draftfiles = new ArrayList<String>();
		try {
			File fil = new File(filepath);
			String name = "";

			if(fil.length() > 0){
				File[] files = fil.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (!files[i].isDirectory()){
						name = files[i].getName().replace(".ssmv", "");
						draftfiles.add(name);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("fileFilterForVisits:11139");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		return draftfiles;
	}

	public boolean draftFileDeleter(boolean[] booleanSelectedArr){
		String filename = URL.getSLIC_DRAFTS_JOBS(); 
		String jobname = URL.getSLIC_JOBS();

		try {
			if(booleanSelectedArr.length > 0){
				ArrayList<String> files = fileFilter(filename);
				File f1 = null;
				File f2 = null;
				String name = "";
				if(files.size() > 0){
					for (int i = 0; i < booleanSelectedArr.length; i++) {
						if(booleanSelectedArr[i]){
							f1 = new File(filename + files.get(i) + ".ssm");
							f1.delete();

							name = "";
							name = files.get(i).split("_")[0].trim();
							if(!name.isEmpty()){
								f2 = new File(jobname+name);
								DeleteRecursive(f2);
							}
						}
						else{
							continue;
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			LOG.error("draftFileDeleter:10140");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	public boolean draftFileDeleterForVisits(boolean[] booleanSelectedArr){
		String filename = URL.getSLIC_DRAFTS_VISITS(); 
		String visitname = URL.getSLIC_VISITS();

		try {
			if(booleanSelectedArr.length > 0){
				ArrayList<String> files = fileFilterForVisits(filename);
				File f1 = null;
				File f2 = null;

				if(files.size() > 0){
					for (int i = 0; i < booleanSelectedArr.length; i++) {
						if(booleanSelectedArr[i]){

							String VisitFolderName = getVisitFolderNameDez(filename + files.get(i));
							if(VisitFolderName != null && !VisitFolderName.isEmpty()){
								f2 = new File(visitname + VisitFolderName);
								DeleteRecursive(f2);
							}

							f1 = new File(filename + files.get(i) + ".ssmv");
							f1.delete();
						}
						else{
							continue;
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			LOG.error("draftFileDeleterForVisits:11140");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	private String getVisitFolderNameDez(String fileName) {
		try {
			String fname = fileName;
			FileInputStream	f2 = new FileInputStream(fname + ".ssmv");

			ObjectInputStream in = new ObjectInputStream(f2);
			VisitObjectDraft fod = (VisitObjectDraft) in.readObject();
			f2.close();
			in.close();

			return fod.getVisitFolderName();
		} catch (Exception e) {
			LOG.error("getVisitFolderNameDez:11390");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return "";
		}
	}

	public static void draftFileDeleterUsingFilename(String filename){

		try {
			String file = URL.getSLIC_DRAFTS_JOBS() + filename;
			File f1 = new File(file);
			f1.delete();

		} catch (Exception e) {
			LOG.error("draftFileDeleterUsingFilename:10141");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}


	public static void draftFileDeleterUsingFilenameForVisits(String filename){

		try {
			String file = URL.getSLIC_DRAFTS_VISITS() + filename;
			File f1 = new File(file);
			f1.delete();

		} catch (Exception e) {
			LOG.error("draftFileDeleterUsingFilenameForVisits:11141");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public static boolean draftRenamer(FormObject fo){
		try {
			String existname = fo.getDraftFileName();
			String jobno = fo.getJobNo();
			String vehino = fo.getVehicleNo();
			if (existname != null && !existname.isEmpty()) {
				String delimiter = "\\(";
				String[] dateArr = existname.split(delimiter); //jobno_vehno (12-12-12 18.18.36 PM).ssm
				String datepart = dateArr[1];
				datepart = "(" + datepart;
				String newname = jobno + "_" + vehino + " " + datepart;
				if (existname.compareTo(newname) != 0) {

					draftFileDeleterUsingFilename(existname/*.replace(".ssm", "")*/);
					fo.setDraftFileName("");
				}
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			LOG.error("draftRenamer:10142");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	public static boolean draftRenamerForVisits(VisitObject vo){
		try {
			String existname = vo.getDraftFileName();
			String jobno = vo.getJobNo();
			String vehino = vo.getVehicleNo();
			String inspecType = vo.getInspectionTypeInText();
			if (existname != null && !existname.isEmpty()) {
				String delimiter = "\\(";
				String[] dateArr = existname.split(delimiter); //jobno_vehno_inspType (12-12-12 18.18.36 PM).ssmv
				String datepart = dateArr[1];
				datepart = "(" + datepart;
				String newname = jobno + "_" + vehino + "_" + inspecType  + " " + datepart;
				if (existname.compareTo(newname) != 0) {
					draftFileDeleterUsingFilenameForVisits(existname/*.replace(".ssmv", "")*/);
					vo.setDraftFileName("");
				}
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			LOG.error("draftRenamerForVisits:11142");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	public static boolean draftsMaxReached(Context context){
		try {
			String filepath = URL.getSLIC_DRAFTS_JOBS(); 
			ArrayList<String> files = fileFilter(filepath);

			SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            int val = sharedPref.getInt(context.getString(R.string.draftMaxReachedCount), files.size()+5);

			if(files.size() >= val){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOG.error("draftsMaxReached:10143");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	public static boolean draftsMaxReachedCHeck9(Context context){
		try {
			String filepath = URL.getSLIC_DRAFTS_JOBS(); 
			ArrayList<String> files = fileFilter(filepath);

			SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			int val = sharedPref.getInt(context.getString(R.string.draftMaxReachedCount), files.size()+5);

//			int val = URL.getMAXREACHEDWARNING();  //9
			if(files.size() >= val-1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOG.error("draftsMaxReached:10143");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	public static boolean draftsMaxReachedForVisits(Context context){
		try {
			String filepath = URL.getSLIC_DRAFTS_VISITS();
			ArrayList<String> files = fileFilter(filepath);

			SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			int val = sharedPref.getInt(context.getString(R.string.draftMaxReachedCount), files.size()+5);

			if(files.size() >= val){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOG.error("draftsMaxReachedForVisits:10143");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}


	public static boolean draftsMaxReachedWarning(Context context){
		try {
			String filepath = URL.getSLIC_DRAFTS_JOBS(); 
			ArrayList<String> files = fileFilter(filepath);

			SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			int val = sharedPref.getInt(context.getString(R.string.draftMaxReachedCount), files.size()+5);

//			int val = URL.getMAXREACHEDWARNING();  //9
			if(files.size() == val-1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOG.error("draftsMaxReachedWarning:10559");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	public static boolean draftsMaxReachedWarningForVisits(Context context){
		try {
			String filepath = URL.getSLIC_DRAFTS_VISITS(); 
			ArrayList<String> files = fileFilterForVisits(filepath);

			SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			int val = sharedPref.getInt(context.getString(R.string.draftMaxReachedCount), files.size()+5);

//			int val = URL.getMAXREACHEDWARNING();  //9
			if(files.size() == val -1){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOG.error("draftsMaxReachedWarningForVisits:11559");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}


	public static boolean  draftsWarningMsg(Context context){
		try {
			String filepath = URL.getSLIC_DRAFTS_JOBS();
			ArrayList<String> files = fileFilter(filepath);

			SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			int val = sharedPref.getInt(context.getString(R.string.draftMaxReachedCount), files.size()+5);

//			int val = URL.getWARNING();  //8

			if(files.size() == val-2){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOG.error("draftsWarningMsg:10415");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	public static boolean draftsWarningMsgForVisits(Context context){
		try {
			String filepath = URL.getSLIC_DRAFTS_VISITS();
			ArrayList<String> files = fileFilterForVisits(filepath);

			SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
			int val = sharedPref.getInt(context.getString(R.string.draftMaxReachedCount), files.size()+5) - 1;

//			int val = URL.getWARNING();  //8
			if(files.size() == val-2){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			LOG.error("draftsWarningMsgForVisits:11415");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		}
	}

	public static String getFileNameFromIndex(int index){
		try {
			String filepath = URL.getSLIC_DRAFTS_JOBS();
			ArrayList<String> draftfiles = FileOperations.fileFilter(filepath);
			String filname =  filepath + draftfiles.get(index).toString();
			return filname;
		} catch (Exception e) {
			LOG.error("getFileNameFromIndex:10145");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return "";
		}
	}

	public static String getFileNameFromIndexForVisits(int index){
		try {
			String filepath = URL.getSLIC_DRAFTS_VISITS();
			ArrayList<String> draftfiles = FileOperations.fileFilterForVisits(filepath);
			String filname =  filepath + draftfiles.get(index).toString();
			return filname;
		} catch (Exception e) {
			LOG.error("getFileNameFromIndexForVisits:11145");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return "";
		}
	}

	public static ArrayList<String> getListComparison(ArrayList<String> fileListSource,	ArrayList<String> fileListDestination) {

		try{
			if(fileListSource != null && fileListDestination != null){
				for (int i = 0; i < fileListDestination.size(); i++) {

					if(fileListSource.contains(fileListDestination.get(i))){
						boolean x = fileListSource.remove(fileListDestination.get(i));
					}
				}

				if(fileListSource != null && fileListSource.size() > 0){
					ArrayList<String> pendingJobsList = DBService.getPendingJobNoList();
					ArrayList<ArrayList<String>> jobImages;
					boolean y;
					if(pendingJobsList != null && pendingJobsList.size() > 0){
						for (int j = 0; j < pendingJobsList.size(); j++) {
							jobImages = DBService
									.getPendingImagesForJobNo(pendingJobsList.get(j));

							if(jobImages != null && jobImages.size() > 0){
								for (int i = 0; i < jobImages.size(); i++) {

									ArrayList<String> oneJobList = jobImages.get(i);
									if (fileListSource.contains(oneJobList.get(2))){
										y = fileListSource.remove(oneJobList.get(2));
									}
								}}
						}}
				}
			}
		}catch (Exception e) {
			LOG.error("getListComparison:10144");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		return fileListSource;
	}
}

class Pair implements Comparable {
    public long t;
    public File f;

    public Pair(File file) {
        f = file;
        t = file.lastModified();
    }

    public int compareTo(Object o) {
        long u = ((Pair) o).t;
        return t < u ? -1 : t == u ? 0 : 1;
    }
};