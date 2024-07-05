package com.irononetech.android.formcomponent.view;

import java.io.File;
import java.util.ArrayList;
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
import android.support.v4.content.FileProvider;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.claimsone.BuildConfig;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.VisitObject;
import com.irononetech.android.imagedownloadcomponent.BulkImageDownloadController;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.draftserializer.FormObjectDeserializer;
import com.irononetech.android.utilities.JPGFileFilter;

public class CustomGalleryJobDocsView extends Activity {
	Logger LOG = LoggerFactory.getLogger(FormObjectDeserializer.class);
	private int count;
	// private Bitmap[] thumbnails;
	// private boolean[] thumbnailsselection;
	private String[] arrPath;
	private ImageAdapter imageAdapter;
	private static String foldername;
	private static boolean isVisit;
	public ProgressDialog dialog;
	private Context context;

	FormObject formObject;
	VisitObject visitObject;

	List<File> finalFileList;
	ArrayList<String> selectedFileList;

	ProgressDialog progressDialog;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			setContentView(R.layout.imagegalleryview);
			context =this;

			isVisit = Application.getIsVisit();
			if (isVisit) {
				visitObject = Application.getVisitObjectInstance();
				if (visitObject.getisSEARCH()) {
					progressDialog = ProgressDialog.show(this, "",
							"Downloading. Please wait...", true);
				} else {
					progressDialog = ProgressDialog.show(this, "",
							"Loading...", true);
				}
			} else {
				formObject = Application.getFormObjectInstance();
				if (formObject.getisSEARCH()) {
					progressDialog = ProgressDialog.show(this, "",
							"Downloading. Please wait...", true);
				} else {
					progressDialog = ProgressDialog.show(this, "",
							"Loading...", true);
				}
			}

			final Intent extras = this.getIntent();
			if (extras != null) {
				foldername = extras.getStringExtra("SELECTION_ID");
			}

			new SelectAsyncTask().execute();

			final Button selectBtn = (Button) findViewById(R.id.selectBtn);
			selectBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					finish();
				}
			});

			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10445");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}

		}
	}

	private class SelectAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPostExecute(Void result) {
			try {
				selectedFileList = new ArrayList<String>();
				count = finalFileList.size();
				arrPath = new String[count];
				// thumbnailsselection = new boolean[count];
				for (int i = 0; i < count; i++) {
					arrPath[i] = finalFileList.get(i).toString();
				}
				GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
				imageAdapter = new ImageAdapter(context);
				imagegrid.setAdapter(imageAdapter);

				progressDialog.dismiss();
				super.onPostExecute(result);
			} catch (Exception e) {
				LOG.error("onPostExecute:10443");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}

			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				finalFileList = getImageList(foldername);
			} catch (Exception e) {
				LOG.error("doInBackground:10444");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}

			}
			return null;
		}
	}

	public class ImageAdapter extends BaseAdapter {

		Logger LOG = LoggerFactory.getLogger(ImageAdapter.class);
		private LayoutInflater mInflater;
		private  Context ctx;

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
			ViewHolderView holder;
			if (convertView == null) {
				holder = new ViewHolderView();
				convertView = mInflater
						.inflate(R.layout.galleryitem_view, null);
				holder.imageview = (ImageView) convertView
						.findViewById(R.id.thumbImage);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolderView) convertView.getTag();
			}

			holder.imageview.setId(position);
			holder.imageview.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					try {
						// Removed due to high memory consumption (3MB > images
						// will generate an exception)
						/*
						 * Intent mIntent = new
						 * Intent(CustomGalleryJobDocsView.this,
						 * PopupImage.class); mIntent.putExtra("selectedImage",
						 * arrPath[v.getId()]); startActivity(mIntent);
						 */

						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						File file = new File(arrPath[v.getId()]);
						Uri contentUri = FileProvider.getUriForFile(ctx,
								BuildConfig.APPLICATION_ID + ".provider",
								file);
						intent.setDataAndType(contentUri, "image/*");
						intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

						startActivity(intent);

					} catch (Exception e) {
						LOG.error("onClick:10446");
						if (e != null) {
							LOG.error("Message: " + e.getMessage());
							LOG.error("StackTrace: " + e.getStackTrace());
						}

					}
				}
			});

			Bitmap bitmapImage = FileOperations.ShrinkBitmap(
					finalFileList.get(position).toString(), 150, 200);
			BitmapDrawable drawableImage = new BitmapDrawable(bitmapImage);

			holder.imageview.setImageDrawable(drawableImage);
			holder.id = position;

			return convertView;
		}
	}

	public List<File> getImageList(String foldername) {
		try {
			/*
			 * File myDirectory = new
			 * File(Environment.getExternalStorageDirectory(), "mydirectory");
			 * if(!myDirectory.exists()) { myDirectory.mkdirs(); }
			 */

			// FormResubmitObject fro =
			// Application.createFormResubmitObjectInstance();
			String filePath = "";
			String destinationFolder = "";

			if (!isVisit) {
				destinationFolder = formObject.getJobNo();
				filePath = URL.getSLIC_JOBS() + destinationFolder
						+ "/DocImages/" + foldername;
			} else {
				destinationFolder = visitObject.getVisitFolderName();
				filePath = URL.getSLIC_VISITS() + destinationFolder
						+ "/DocImages/" + foldername;
			}

			if (formObject != null && formObject.getisSEARCH()) {
				if (foldername.equals("DLStatement")) {
					if (formObject.getDLStatementImageList() != null
							&& formObject.getDLStatementImageList().size() > 0) {
						doTask(formObject.getDLStatementImageList(), filePath);
					}
				}

				if (foldername.equals("TechnicalOfficerComments")) {
					if (formObject.getTechnicalOfficerCommentsImageList() != null
							&& formObject
									.getTechnicalOfficerCommentsImageList()
									.size() > 0) {
						doTask(formObject
								.getTechnicalOfficerCommentsImageList(),
								filePath);
					}
				}

				if (foldername.equals("ClaimFormImage")) {
					if (formObject.getClaimFormImageImageList() != null
							&& formObject.getClaimFormImageImageList().size() > 0) {
						doTask(formObject.getClaimFormImageImageList(),
								filePath);
					}
				}
			}

			if (visitObject != null && visitObject.getisSEARCH()) {
				if (foldername.equals("TechnicalOfficerComments")) {
					if (visitObject.getTechnicalOfficerCommentsImageList() != null
							&& visitObject
									.getTechnicalOfficerCommentsImageList()
									.size() > 0) {
						doTask(visitObject
								.getTechnicalOfficerCommentsImageList(),
								filePath);
					}
				}

				if (foldername.equals("InspectionPhotosSeenVisitsAnyOther")) {
					if (visitObject
							.getInspectionPhotosSeenVisitsAnyOtherImageList() != null
							&& visitObject
									.getInspectionPhotosSeenVisitsAnyOtherImageList()
									.size() > 0) {
						doTask(visitObject
								.getInspectionPhotosSeenVisitsAnyOtherImageList(),
								filePath);
					}
				}

				if (foldername.equals("EstimateAnyotherComments")) {
					if (visitObject.getEstimateAnyotherCommentsImageList() != null
							&& visitObject
									.getEstimateAnyotherCommentsImageList()
									.size() > 0) {
						doTask(visitObject
								.getEstimateAnyotherCommentsImageList(),
								filePath);
					}
				}
			}

			// File a = Environment.getExternalStorageDirectory();
			File f = new File(filePath);
			List<File> fileList = FileOperations.listFiles(f,
					new JPGFileFilter(), true);

			/*
			 * String fileNames = ""; for (int i = 0; i < fileList.size(); i++)
			 * { fileNames += ">>" + fileList.get(i).getPath() + "\n"; }
			 */
			return fileList;
		} catch (Exception e) {
			LOG.error("imageList:10447");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}

			return null;
		}
	}

	private void doTask(ArrayList<String> svrCollection, String filePath) {
		try {
			ArrayList<String> toBeDownloaded = new ArrayList<String>();
			File imgFile;

			if (svrCollection != null && svrCollection.size() > 0) {
				for (int i = 0; i < svrCollection.size(); i++) {
					imgFile = new File(svrCollection.get(i));

					if (!imgFile.exists()) {
						// Download It
						toBeDownloaded.add(svrCollection.get(i));
					}
				}

				if (toBeDownloaded != null && toBeDownloaded.size() > 0) {

					// dialog =
					// ProgressDialog.show(CustomGalleryJobDocsView.this, "",
					// "Downloading. Please wait...", true);
					Intent intent = new Intent(getBaseContext(),
							BulkImageDownloadController.class);

					intent.putExtra("MEDIA_SAVE_PATH", filePath + "/");
					intent.putStringArrayListExtra("IMG_LIST", toBeDownloaded);
					imageDownloder(intent);
				}
			}
		} catch (Exception e) {
			LOG.error("doTask:10448");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}

		}
	}

	protected void imageDownloder(final Intent ii) {
		try {
			// Start lengthy operation in a background thread
			BulkImageDownloadController b = new BulkImageDownloadController();
			b.bulkImageDownloder(ii);
		} catch (Exception e) {
			LOG.error("imageDownloder:10449");
			if (e != null) {
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// return false;
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
