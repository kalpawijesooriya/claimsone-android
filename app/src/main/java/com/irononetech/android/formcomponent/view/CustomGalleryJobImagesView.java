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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.claimsone.BuildConfig;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.draftserializer.FormObjectDeserializer;
import com.irononetech.android.utilities.JPGFileFilter;

public class CustomGalleryJobImagesView extends Activity {
	Logger LOG = LoggerFactory.getLogger(FormObjectDeserializer.class);
	private int count;
	// private Bitmap[] thumbnails;
	// private boolean[] thumbnailsselection;
	private String[] arrPath;
	private ImageAdapter imageAdapter;
	private Context context;

	/** Called when the activity is first created. */

	List<File> finalFileList;
	ArrayList<String> selectedFileList;

	ProgressDialog progressDialog;

	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			setContentView(R.layout.imagegalleryview);

			context = this;
			new SelectAsyncTask().execute();
			progressDialog = ProgressDialog.show(this, "", "Loading...", true);

			final Button selectBtn = (Button) findViewById(R.id.selectBtn);
			selectBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					finish();
				}

			});
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10456");
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

				// final String[] columns = { MediaStore.Images.Media.DATA,
				// MediaStore.Images.Media._ID };
				// final String orderBy = MediaStore.Images.Media._ID;
				// Cursor imagecursor = managedQuery(
				// MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				// null, orderBy);
				// int image_column_index =
				// imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
				// this.count = imagecursor.getCount();

				count = finalFileList.size();
				// this.thumbnails = new Bitmap[this.count];
				arrPath = new String[count];
				// thumbnailsselection = new boolean[count];
				for (int i = 0; i < count; i++) {

					// imagecursor.moveToPosition(i);
					// int id = imagecursor.getInt(image_column_index);
					// int dataColumnIndex =
					// imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
					// thumbnails[i] =
					// MediaStore.Images.Thumbnails.getThumbnail(
					// getApplicationContext().getContentResolver(), id,
					// MediaStore.Images.Thumbnails.MICRO_KIND, null);
					// arrPath[i]= imagecursor.getString(dataColumnIndex);

					arrPath[i] = finalFileList.get(i).toString();
				}
				GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
				imageAdapter = new ImageAdapter(context);
				imagegrid.setAdapter(imageAdapter);

				progressDialog.dismiss();
				super.onPostExecute(result);
			} catch (Exception e) {
				LOG.error("onPostExecute:10454");
				if (e != null) {
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				}
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				finalFileList = imageList();
			} catch (Exception e) {
				LOG.error("doInBackground:10455");
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
			ViewHolderView holder;
			if (convertView == null) {
				holder = new ViewHolderView();
				convertView = mInflater
						.inflate(R.layout.galleryitem_view, null);
				holder.imageview = (ImageView) convertView
						.findViewById(R.id.thumbImage);
				// holder.checkbox = (CheckBox)
				// convertView.findViewById(R.id.itemCheckBox);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolderView) convertView.getTag();
			}
			// holder.checkbox.setId(position);
			holder.imageview.setId(position);

			/*
			 * holder.checkbox.setOnClickListener(new OnClickListener() {
			 * 
			 * public void onClick(View v) {
			 * 
			 * CheckBox cb = (CheckBox) v; int id = cb.getId(); if
			 * (thumbnailsselection[id]){ cb.setChecked(false);
			 * thumbnailsselection[id] = false; } else { cb.setChecked(true);
			 * thumbnailsselection[id] = true; } } });
			 */

			holder.imageview.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					// int id = v.getId();
					// Intent intent = new Intent();
					// intent.setAction(Intent.ACTION_VIEW);
					// //intent.setDataAndType(Uri.parse("file://" +
					// arrPath[id]), "image/*");
					// intent.setDataAndType(Uri.fromFile(new
					// File(arrPath[id])), "image/jpg");
					// startActivity(intent);

					// Suren
					// This code will open the image in default OS
					// Image viewer with black bg
					/*
					 * try { int id = v.getId(); Intent myIntent = new
					 * Intent(android.content.Intent.ACTION_VIEW); File file =
					 * new File(arrPath[id]);
					 * 
					 * String type = null; URL u = new
					 * URL(Uri.fromFile(file).toString()); URLConnection uc =
					 * null; uc = u.openConnection(); type =
					 * uc.getContentType(); // LogFile.d("mime", type);
					 * 
					 * myIntent.setDataAndType(Uri.fromFile(file),type);
					 * startActivity(myIntent);
					 * 
					 * } catch (Exception e) { LogFile.d(TAG, e.getMessage()); }
					 */

					try {
						// Removed due to high memory consumption (3MB > images
						// will generate an exception)
						/*
						 * Intent mIntent = new
						 * Intent(CustomGalleryJobImagesView.this,
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
						 LOG.error("onClick:10457");
						   if(e != null){
							LOG.error("Message: " + e.getMessage());
							LOG.error("StackTrace: " + e.getStackTrace());
						  }
					}
				}
			});

			// ImageView imageView = new ImageView(mContext);
			// Bitmap bitmapImage =
			// BitmapFactory.decodeFile(finalFileList.get(position).toString());
			Bitmap bitmapImage = FileOperations.ShrinkBitmap(
					finalFileList.get(position).toString(), 150, 200);

			BitmapDrawable drawableImage = new BitmapDrawable(bitmapImage);
			// imageView.setImageDrawable(drawableImage );
			holder.imageview.setImageDrawable(drawableImage);
			// holder.imageview.setImageBitmap(thumbnails[position]);
			// holder.checkbox.setChecked(thumbnailsselection[position]);
			holder.id = position;
			return convertView;
		}
	}

	public List<File> imageList() {

		/*
		 * File myDirectory = new
		 * File(Environment.getExternalStorageDirectory(), "mydirectory");
		 * if(!myDirectory.exists()) { myDirectory.mkdirs(); }
		 */

		FormObject formObject = Application.getFormObjectInstance();
		String destinationFolder = formObject.getJobNo();

		// File a = Environment.getExternalStorageDirectory();
		File f = new File(URL.getSLIC_JOBS() + destinationFolder + "/AccImages");
		// File f = new File("/Root/DICM/Camera");
		List<File> fileList = FileOperations.listFiles(f, new JPGFileFilter(),
				true);

		/*
		 * String fileNames = ""; for (int i = 0; i < fileList.size(); i++) {
		 * fileNames += ">>" + fileList.get(i).getPath() + "\n"; }
		 */
		return fileList;
		// tv.setText(fileNames);
	}

	public void on_click_back_button(View v) {
		finish();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// return false;
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}

class ViewHolderView {
	ImageView imageview;
	// CheckBox checkbox;
	int id;
}