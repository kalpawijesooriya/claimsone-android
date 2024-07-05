package com.irononetech.android.formcomponent.view;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.claimsone.BuildConfig;
import com.irononetech.android.database.DBService;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.draftserializer.FormObjectDeserializer;
import com.irononetech.android.utilities.DirFilter;
import com.irononetech.android.utilities.JPGFileFilter;
import com.irononetech.android.utilities.txtFilter;

public class CustomGalleryJobImagesAdd extends Activity {
	Logger LOG = LoggerFactory.getLogger(FormObjectDeserializer.class);
	private int count;
	private boolean[] thumbnailsselection;
	private String[] arrPath;
	private String[] arrName;
	private ImageAdapter imageAdapter;
	FormObject formObject;
	/** Called when the activity is first created. */

	List<File> finalFileList;
	List<BitmapDrawable> drawableList = new ArrayList<BitmapDrawable>();
	ArrayList<String> selectedFileList;
	ProgressDialog progressDialog;
	List<File> folderList;

	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			setContentView(R.layout.imagegallerymain);
			formObject = Application.getFormObjectInstance();


			progressDialog = ProgressDialog.show(this, "",	"Loading...", true);

			final Button selectBtn = (Button) findViewById(R.id.selectBtn);
			selectBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					LOG.debug("ENTRY ","selectBtn-onClick");

					progressDialog = ProgressDialog.show(CustomGalleryJobImagesAdd.this, "",	"Deleting old files...", true);
					new DeleteOldAsyncTask().execute();


					LOG.debug("SUCCESS ", "selectBtn-onClick");
				}
			});

			new SelectAsyncTask().execute();
//            Handler delayHandler = new Handler();
//            delayHandler.postDelayed(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            new SelectAsyncTask().execute();
//                        }
//                    }, 100
//            );

			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10452");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			// progressDialog.dismiss();
		}
	}

	private class DeleteOldAsyncTask extends AsyncTask<Void,Void, Void>{
		@Override
		protected Void doInBackground(Void... voids) {

			List<String> drafts = DBService.getPendingJobDraftsList();
			if(drafts == null)
			    drafts = Collections.emptyList();
			ArrayList<String> jobNums = new ArrayList<>();
			ArrayList<String> fileNames = new ArrayList<>();
			LOG.debug("drafts",drafts);

			for(int i=0 ; i < drafts.size(); i++){
			    LOG.debug("ith draft",drafts.get(i));
				jobNums.add(drafts.get(i).split("_")[0]);
			}

			for(File i : folderList){
				fileNames.add(i.getName());
			}

			LOG.debug("fileNames",fileNames);
			LOG.debug("jobNums",jobNums);
			LOG.debug("folders",folderList);


			//delete the files
			if(!folderList.isEmpty()){
				for(int i=0; i<folderList.size(); i++){
					long lastModified = folderList.get(i).lastModified();
					//1209600000 = 2 weeks
					if(System.currentTimeMillis() - lastModified > 1209600000
					&& !jobNums.contains(fileNames.get(i))){

						List<File> txtList = FileOperations.listFiles(folderList.get(i), new txtFilter(),true);
						LOG.debug("txt file list",txtList);
						ArrayList<String> deleteNames = new ArrayList<>();
						for(int m = 0;m<txtList.size();m++){
							File txtFile = txtList.get(m);
							byte[] bytes = new byte[(int) txtFile.length()];
							FileInputStream in = null;
							try {
								in = new FileInputStream(txtFile);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							try {
								in.read(bytes);
							} catch (IOException e) {
								e.printStackTrace();
							} finally {
								try {
									in.close();
									in = null;
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							String contents = new String(bytes);
							String[] names = contents.split(",");
							deleteNames.addAll(Arrays.asList(names));
							LOG.debug("name array",names);
							LOG.debug("delete names",deleteNames);
						}



						ArrayList<File> toDelete = new ArrayList<>();
						for(int j = 0; j<deleteNames.size();j++){
							final String fileName = deleteNames.get(j);
							LOG.debug("fileName",fileName);
							toDelete.addAll(FileOperations.listFiles(new File(URL.getSLIC_ACC()),new FileFilter()
							{
								public boolean accept(File dir)
								{
									return dir.getName().equals(fileName);
								}
							}, true));//search the Accident folder

							toDelete.addAll(
								FileOperations.listFiles(new File(URL.getSLIC_DOC()),new FileFilter()
								{
									public boolean accept(File dir)
									{
										return dir.getName().equals(fileName);
									}
								}, true)
							); // search the doc images

							toDelete.addAll(
								FileOperations.listFiles(new File(URL.getSLIC_COPIED()),new FileFilter()
								{
									public boolean accept(File dir)
									{
										return dir.getName().equals(fileName);
									}
								}, true)
							); // search the copied folder
							LOG.debug("toDelete",toDelete);
						}
						if(!toDelete.isEmpty()){
							for(File k : toDelete){
								k.delete();
							}
						}
						FileOperations.DeleteRecursive(folderList.get(i));
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
            final int len = thumbnailsselection.length;

            String selectImages = "";
            for (int i =0; i<len; i++)
            {
                if (thumbnailsselection[i]){
                    selectImages = selectImages + arrPath[i] + "|";
                    selectedFileList.add(arrPath[i]);
                }
            }

            //SLIC Code
            String destinationFolder = formObject.getJobNo() + "";

            if(!destinationFolder.equals("")){
                FileOperations.copyFilesFromDirToDirAddImages(selectedFileList, destinationFolder + "/AccImages");
                formObject.setselectedAccidentImages(thumbnailsselection);
            }
					/*else{
						new AlertDialog.Builder(CustomGalleryJobImagesAdd.this)
						.setTitle(R.string.alert_dialog_two_buttons_title)
						.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
							}
						})
						.setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
							}
						})
						.create();
					}*/
            progressDialog.dismiss();
            finish();

		}
	}

	private class SelectAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			try {
				super.onPostExecute(result);

				selectedFileList = new ArrayList<String>();
				count= finalFileList.size();
				arrPath = new String[count];
				arrName = new String[count];
				thumbnailsselection = new boolean[count];
				boolean[] arr = formObject.getselectedAccidentImages();

				if (thumbnailsselection != null && arr != null) {
					for (int i = 0; i < thumbnailsselection.length; i++) {

						/* Need this condition on a situation where user select few images
						 * and then copy more images to Copied folder.
						 * Without this condition user gets an empty page with no images to select.
						 * */
						if(i < arr.length)
							thumbnailsselection[i] = arr[i];
						else
							break;
					}
				}

				for (int i = 0; i < count; i++) {
					arrPath[i]=finalFileList.get(i).toString();
					arrName[i]=finalFileList.get(i).getName();
				}

//				GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
//				imageAdapter = new ImageAdapter();
//				imagegrid.setAdapter(imageAdapter);
				new ImageShrinkAsyncTask().execute();
				//progressDialog.dismiss();
			} catch (Exception e) {
				LOG.error("onPostExecute:10450");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				// progressDialog.dismiss();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				finalFileList= imageList();
				folderList = getFolderList();
			} catch (Exception e) {
				LOG.error("doInBackground:10451");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
			return null;
		}
	}

	private class ImageShrinkAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {
			try{
				for(File image : finalFileList){
					Bitmap bitmapImage = FileOperations.ShrinkBitmap(image.toString(), 150, 200);
					BitmapDrawable drw = new BitmapDrawable(bitmapImage);
					drawableList.add(drw);
				}
				LOG.debug("drwList",drawableList);
			} catch (Exception e){
				LOG.error("doInBackground(Shrink):10440");
				if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
			imageAdapter = new ImageAdapter();
			imagegrid.setAdapter(imageAdapter);
			progressDialog.dismiss();
			Toast.makeText(getApplicationContext(), "long click the thumbnails to view", Toast.LENGTH_SHORT).show();
		}
	}

	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public ImageAdapter() {
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			//return count;
			return finalFileList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.galleryitem, null);
				holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
				holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);

				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			final ViewHolder holder1 = holder;
			holder.checkbox.setId(position);
			holder.imageview.setId(position);
			holder.checkbox.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					CheckBox cb = (CheckBox) v;
					int id = cb.getId();
					if (thumbnailsselection[id]){
						cb.setChecked(false);
						thumbnailsselection[id] = false;
					} else {
						cb.setChecked(true);
						thumbnailsselection[id] = true;
					}
				}
			});
			holder.imageview.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox)holder1.checkbox;
					int id = cb.getId();
					if (thumbnailsselection[id]){
						cb.setChecked(false);
						thumbnailsselection[id] = false;
					} else {
						cb.setChecked(true);
						thumbnailsselection[id] = true;
					}
				}
			});
			holder.imageview.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					try {
						// Removed due to high memory consumption (3MB >
						// images will generate an exception)
						/*
						 * Intent mIntent = new
						 * Intent(CustomGalleryJobDocsAdd.this,
						 * PopupImage.class);
						 * mIntent.putExtra("selectedImage",
						 * arrPath[v.getId()]); startActivity(mIntent);
						 */
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.parse("file://" + arrPath[v.getId()]), "image/*");
						startActivity(intent);
					} catch (Exception e) {
						LOG.error("onClick");
						   if(e != null){
							LOG.error("Message: " + e.getMessage());
							LOG.error("StackTrace: " + e.getStackTrace());
						  }
					}
					return false;
				}
			});

			holder.imageview.setImageDrawable(drawableList.get(position));
			holder.checkbox.setChecked(thumbnailsselection[position]);
			holder.id = position;
//            if(position == finalFileList.size()-1 || position == 27){
//                progressDialog.dismiss();
//            }
			return convertView;
		}
	}

	public List<File> getFolderList(){

		List<File> fileList;
		File f = new File(URL.getSLIC_JOBS());

		if(f.exists()){
			fileList = FileOperations.listFiles(f, new DirFilter(),false);
			return fileList;
		}
		return Collections.emptyList();
	}
	public List<File> imageList(){
		List<File> fileList;
		List<File> capturedList;
		File f = new File(URL.getSLIC_COPIED()+"/Accident");
		if(f.exists()){
			fileList = FileOperations.listFiles(f, new JPGFileFilter(), true);
		}
		else{
			File fNew = new File(URL.getSLIC_COPIED());
			fileList = FileOperations.listFiles(fNew, new JPGFileFilter(), false);
		}
		File captured = new File(URL.getSLIC_ACCIDENT());
		capturedList = FileOperations.listFiles(captured, new JPGFileFilter(), true);

		capturedList.addAll(fileList);
		return capturedList;
	}

	public void on_click_back_button(View v){
		finish();
	}
}

class ViewHolder {
	ImageView imageview;
	CheckBox checkbox;
	int id;
}