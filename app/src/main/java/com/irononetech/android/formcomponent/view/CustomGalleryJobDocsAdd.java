package com.irononetech.android.formcomponent.view;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.claimsone.BuildConfig;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.database.DBService;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.utilities.DirFilter;
import com.irononetech.android.utilities.JPGFileFilter;
import com.irononetech.android.utilities.txtFilter;

public class CustomGalleryJobDocsAdd extends Activity {
	Logger LOG = LoggerFactory.getLogger(CustomGalleryJobDocsAdd.class);
	private int count;
	private int selectedImageCount;
	private boolean[] thumbnailsselection;
	private String[] arrPath;
	private String[] arrName;
	private String[] arrImageName;
	private String[] arrSelectedImageName;
	private ImageAdapter imageAdapter;
	private Context context;
	public static String fName;
	public static int countGridItems;

	Intent extras;
	FormObject formObject;

	List<BitmapDrawable> drawableList = new ArrayList<BitmapDrawable>();
	List<File> finalFileList;
	List<File> alreadySelectedFileList;
	ArrayList<String> selectedFileList;
	ProgressDialog progressDialog;
	List<File> folderList;

	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			// LOG.debug("ENTRY onCreate");
			setContentView(R.layout.imagegallerymain);

			context = this;
			extras = this.getIntent();
			formObject = Application.getFormObjectInstance();
			Intent intent = getIntent();
			fName = intent.getStringExtra("SELECTION_ID");

			progressDialog = ProgressDialog.show(context, "", "Loading...", true);

			final Button selectBtn = (Button) findViewById(R.id.selectBtn);
			selectBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					LOG.debug("ENTRY ", "selectBtn-onClick");

//					progressDialog = ProgressDialog.show(CustomGalleryJobDocsAdd.this, "", "Deleting old files...", true);
//					new DeleteOldAsyncTask().execute();

					final int len = thumbnailsselection.length;
					String selectImages = "";
					for (int i = 0; i < len; i++) {
						if (thumbnailsselection[i]) {
							selectImages = selectImages + arrPath[i] + "|";
							selectedFileList.add(arrPath[i]);
						}
					}

					// Suren
					// identify the image category
					String foldername = "";

					boolean isVisit = Application.getIsVisit();
					if (extras != null) {
						foldername = fName;
					}

					String destinationFolder = "";

					if (!isVisit) { // SA Form
						FormObject formObject = Application.getFormObjectInstance();

						if (foldername.equals("DLStatement")) {
							formObject.setselectedDLStatementImages(thumbnailsselection);
						}
						if (foldername.equals("TechnicalOfficerComments")) {
							formObject.setselectedTechnicalOfficerCommentsImages(thumbnailsselection);
						}
						if (foldername.equals("ClaimFormImage")) {
							formObject.setselectedClaimFormImageImages(thumbnailsselection);
						}
						if (foldername.equals("ARI")) {
							formObject.setselectedARIImages(thumbnailsselection);
						}
						if (foldername.equals("DR")) {
							formObject.setselectedDRImages(thumbnailsselection);
						}
						if (foldername.equals("SeenVisit")) {
							formObject.setselectedSeenVisitImages(thumbnailsselection);
						}
						if (foldername.equals("SpecialReport1")) {
							formObject.setselectedSpecialReport1Images(thumbnailsselection);
						}
						if (foldername.equals("SpecialReport2")) {
							formObject.setselectedSpecialReport2Images(thumbnailsselection);
						}
						if (foldername.equals("SpecialReport3")) {
							formObject.setselectedSpecialReport3Images(thumbnailsselection);
						}
						if (foldername.equals("Supplementary1")) {
							formObject.setselectedSupplementary1Images(thumbnailsselection);
						}
						if (foldername.equals("Supplementary2")) {
							formObject.setselectedSupplementary2Images(thumbnailsselection);
						}
						if (foldername.equals("Supplementary3")) {
							formObject.setselectedSupplementary3Images(thumbnailsselection);
						}
						if (foldername.equals("Supplementary4")) {
							formObject.setselectedSupplementary4Images(thumbnailsselection);
						}
						if (foldername.equals("Acknowledgment")) {
							formObject.setselectedAcknowledgmentImages(thumbnailsselection);
						}
						if (foldername.equals("SalvageReport")) {
							formObject.setselectedSalvageReportImages(thumbnailsselection);
						}

						destinationFolder = formObject.getJobNo(); //changedFF +""

					} else {
						VisitObject VisitObject = Application.getVisitObjectInstance();

						if (foldername.equals("TechnicalOfficerComments")) {
							VisitObject.setselectedTechnicalOfficerCommentsImages(thumbnailsselection);
						}
						if (foldername.equals("EstimateAnyotherComments")) {
							VisitObject.setselectedEstimateAnyotherComments(thumbnailsselection);
						}
						if (foldername.equals("InspectionPhotosSeenVisitsAnyOther")) {
							VisitObject.setselectedInspectionPhotosSeenVisitsAnyOther(thumbnailsselection);
						}

						destinationFolder = VisitObject.getVisitFolderName();
					}

					if (!destinationFolder.equals("")) {
						LOG.debug("dest",destinationFolder);
						LOG.debug("foldername",foldername);
						if(!isVisit)
							FileOperations.copyFilesFromDirToDirAddDocs(selectedFileList, destinationFolder	+ "/DocImages/" + foldername); //changedFF +"/"
						else
							FileOperations.copyFilesFromDirToDirAddDocsForVisits(selectedFileList, destinationFolder + "/DocImages/" + foldername); //changedFF +"/"
					}
					/*else {
						new AlertDialog.Builder(CustomGalleryJobDocsAdd.this)
						.setTitle(R.string.alert_dialog_two_buttons_title)
						.setPositiveButton(R.string.alert_dialog_ok,
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int whichButton) {
							}
						})
						.setNegativeButton(R.string.alert_dialog_cancel,
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int whichButton) {
							}
						}).create();
					}*/
					finish();

					// LOG.debug("SUCCESS ","selectBtn-onClick");
				}
			});

			
			LOG.debug("onClick");
			new SelectAsyncTask().execute();
//			Handler delayHandler = new Handler();
//			delayHandler.postDelayed(
//					new Runnable() {
//						@Override
//						public void run() {
//                            new SelectAsyncTask().execute();
//						}
//					}, 100
//			);





		}
		catch (Exception e) {
			LOG.error("onCreate:10441");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private class DeleteOldAsyncTask extends AsyncTask<Void,Void, Void>{
		@Override
		protected Void doInBackground(Void... voids) {

			//delete the files
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
			progressDialog.dismiss();
		}
	}

	private class SelectAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//
		}

		@Override
		protected void onPostExecute(Void result) {
			try {
				super.onPostExecute(result);
				selectedFileList = new ArrayList<String>();

				count = finalFileList.size();
				// selectedImageCount = alreadySelectedFileList.size(); changedFF

				arrPath = new String[count];
				arrName = new String[count];
				arrImageName = new String[count];
				arrSelectedImageName = new String[selectedImageCount];
				thumbnailsselection = new boolean[count];

				boolean[] arr = null;
				String foldername = "";
				boolean isVisit = Application.getIsVisit();

				if (extras != null) {
					foldername = fName;
				}

				if (!isVisit) { // SA Form
					FormObject formObject = Application.getFormObjectInstance();
					if (foldername.equals("DLStatement")) {
						arr = formObject.getselectedDLStatementImages();
					}
					if (foldername.equals("TechnicalOfficerComments")) {
						arr = formObject.getselectedTechnicalOfficerCommentsImages();
					}
					if (foldername.equals("ClaimFormImage")) {
						arr = formObject.getselectedClaimFormImageImages();
					}
					if (foldername.equals("ARI")) {
						arr = formObject.getselectedARIImages();
					}
					if (foldername.equals("DR")) {
						arr = formObject.getselectedDRImages();
					}
					if (foldername.equals("SeenVisit")) {
						arr = formObject.getselectedSeenVisitImages();
					}
					if (foldername.equals("SpecialReport1")) {
						arr = formObject.getselectedSpecialReport1Images();
					}
					if (foldername.equals("SpecialReport2")) {
						arr = formObject.getselectedSpecialReport2Images();
					}
					if (foldername.equals("SpecialReport3")) {
						arr = formObject.getselectedSpecialReport3Images();
					}
					if (foldername.equals("Supplementary1")) {
						arr = formObject.getselectedSupplementary1Images();
					}
					if (foldername.equals("Supplementary2")) {
						arr = formObject.getselectedSupplementary2Images();
					}
					if (foldername.equals("Supplementary3")) {
						arr = formObject.getselectedSupplementary3Images();
					}
					if (foldername.equals("Supplementary4")) {
						arr = formObject.getselectedSupplementary4Images();
					}
					if (foldername.equals("Acknowledgment")) {
						arr = formObject.getselectedAcknowledgmentImages();
					}
					if (foldername.equals("SalvageReport")) {
						arr = formObject.getselectedSalvageReportImages();
					}
				} else {
					VisitObject visitObject = Application.getVisitObjectInstance();

					if (foldername.equals("TechnicalOfficerComments")) {
						arr = visitObject.getselectedTechnicalOfficerCommentsImages();
					}
					if (foldername.equals("EstimateAnyotherComments")) {
						arr = visitObject.getselectedEstimateAnyotherComments();
					}
					if (foldername.equals("InspectionPhotosSeenVisitsAnyOther")) {
						arr = visitObject.getselectedInspectionPhotosSeenVisitsAnyOther();
					}
				}

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

				//temporary changed
				for (int i = 0; i < count; i++) {
					arrPath[i] = finalFileList.get(i).toString();
					arrName[i] = finalFileList.get(i).getName();
					//ChangedFF commented 336-342
//					String[] splittedPathName = arrPath[i].split("/");
//					arrImageName[i] = splittedPathName[6].trim();
//				}
//				// LOG.debug("pathnames",arrImageName);
//
//				for(int i = 0; i < selectedImageCount; i++){
//					arrSelectedImageName[i] = alreadySelectedFileList.get(i).toString().split("/")[9].trim();
				}
				new ImageShrinkAsyncTask().execute();
				LOG.debug("Came Out!!!!!!");

			} catch (Exception e) {
				//progressDialog.dismiss();
				LOG.error("onPostExecute:10439");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				finalFileList = imageList();
				folderList = getFolderList();
//				LOG.debug("list",finalFileList);
//				alreadySelectedFileList = selectedImageList(); //changedFF cmntd
			} catch (Exception e) {
				LOG.error("doInBackground:10440");
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
			imageAdapter = new ImageAdapter(context);
			imagegrid.setAdapter(imageAdapter);
			progressDialog.dismiss();
			Toast.makeText(context, "long click the thumbnails to view", Toast.LENGTH_SHORT).show();
		}
	}

	public class ImageAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context ctx;

		public ImageAdapter(Context context) {
			ctx = context;
			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return finalFileList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			try {
                ViewHolder holder;

                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.galleryitem, null);
                    holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);
                    holder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                final ViewHolder holder1 = holder;
                holder.checkbox.setId(position);
                holder.checkbox.setChecked(thumbnailsselection[position]);
                holder.imageview.setId(position);
                holder.checkbox.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        int id = cb.getId();
                        if (thumbnailsselection[id]) {
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
                        if (thumbnailsselection[id]) {
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
                            File file = new File(arrPath[v.getId()]);
                            //intent.setDataAndType(Uri.parse("file://" + arrPath[v.getId()]), "image/*"); changedFF original line
                            Uri contentUri = FileProvider.getUriForFile(ctx,
                                    BuildConfig.APPLICATION_ID + ".provider",
                                    file);
                            intent.setDataAndType(contentUri, "image/*");
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
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

//                Bitmap bitmapImage = FileOperations.ShrinkBitmap(finalFileList
//                        .get(position).toString(), 150, 200);

//                BitmapDrawable imageDrawable = new BitmapDrawable(bitmapImage);
				LOG.debug("drwList",drawableList);
                holder.imageview.setImageDrawable(drawableList.get(position));
                holder.checkbox.setChecked(thumbnailsselection[position]); //changedFF
                holder.id = position;

//                if(position == finalFileList.size()-1 || position == 27){
//					progressDialog.dismiss();
//				}


                return convertView;
			} catch (Exception e) {
				LOG.error("getView:10442");
				if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
				return convertView;
			}
		}
	}

	public List<File> imageList() throws FileNotFoundException {
	    List<File> fileList;
	    List<File> capturedList;

		File f = new File(URL.getSLIC_COPIED()+"/Document");
        if(f.exists()){
            fileList = FileOperations.listFiles(f, new JPGFileFilter(), true);
        }
		else{
		    File fNew = new File(URL.getSLIC_COPIED());
            fileList = FileOperations.listFiles(fNew, new JPGFileFilter(), false);
        }
		File captured = new File(URL.getSLIC_DOCUMENTS());
        capturedList = FileOperations.listFiles(captured, new JPGFileFilter(), true);

        capturedList.addAll(fileList);
        return capturedList;
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

//	public List<File> selectedImageList(){
//		String foldername = "";
//
//		if (extras != null) {
//			foldername = extras.getStringExtra("SELECTION_ID");
//		}
//
//		String path;
//		if(!Application.getIsVisit()){
//			path =  Environment.getExternalStorageDirectory() + "/SLIC/Jobs/" + formObject.getJobNo() + "/DocImages/" + foldername;
//		} else {
//			VisitObject visitObject = Application.getVisitObjectInstance();
//			path =  Environment.getExternalStorageDirectory() + "/SLIC/Visits/" + visitObject.getVisitFolderName() + "/DocImages/" + foldername;
//		}
//		File f = new File(path);
//		List<File> selectedFileList = FileOperations.listFiles(f, new JPGFileFilter(), true);
//		return selectedFileList;
//	}

	private boolean checkImagedAlreadySelected(int position){
		boolean isAlreadySelected = false;
		String imageName = arrImageName[position];
		for(int j = 0; j< arrSelectedImageName.length; j++){
			if(arrSelectedImageName[j].equals(imageName)){
				isAlreadySelected = true;
				break;
			}
		}
		thumbnailsselection[position] = isAlreadySelected;
		return isAlreadySelected;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void on_click_back_button(View v){
		finish();
	}

    class ViewHolder {
        ImageView imageview;
        CheckBox checkbox;
        int id;
    }
}
