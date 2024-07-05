package com.irononetech.android.formcomponent.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.paintcomponent.FingerPaint;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.draftserializer.FormObjectDeserializer;

public class ListViewForVehicleDetails extends ListActivity {
	Logger LOG = LoggerFactory.getLogger(FormObjectDeserializer.class);

	private LayoutInflater mInflater;
	private Vector<RowData> data;
	RowData rd;
	static final String[] title = new String[] { "Car", "Lorry", "Bus",
		"Motor Cycle", "Three Wheel", "Other" };
	static final String[] detail = new String[] { "1", "2", "3", "4" , "5", "6"};
	private Integer[] imgid = { R.drawable.car1_t, R.drawable.car2_t,
			R.drawable.car3_t, R.drawable.car4_t , R.drawable.car5_t, R.drawable.car6_t };

	ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			
			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);
			setContentView(R.layout.imagelistview);

			data = new Vector<RowData>();
			for (int i = 0; i < title.length; i++) {
				try {
					rd = new RowData(i, title[i], detail[i]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				data.add(rd);
			}
			CustomAdapter adapter = new CustomAdapter(this, data);
			setListAdapter(adapter);
			getListView().setTextFilterEnabled(true);
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10489");
			   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
		}
	}

	public void imagelist_back_button(View v) {
		try {
			Application.goForward = false;
			Application.goBackward = true;
			finish();
		} catch (Exception e) {
			LOG.error("imagelist_back_button:10490");
			   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class SelectAsyncTask extends AsyncTask<String, Void, String> {

		/*
		 * 2013-04-23 : Bug Fix -  Using "BitmapFactory.decodeResource" will cause out of memory exception
		 * Instead of using that method used "getResources().openRawResource" to get the resource images as a file and
		 * copied it to the correct location.
		 * 
		 * */
		protected String doInBackground(String... urls) {
			try {
				int position = Integer.parseInt(urls[0]);
				String destinationFolder = urls[1];

				InputStream in = null;
				OutputStream out = null;

				File storagePath = new File(URL.getSLIC_JOBS() + destinationFolder + "/PointsOfImpact");
				storagePath.mkdirs();
				out = new FileOutputStream(URL.getSLIC_JOBS() + destinationFolder + "/PointsOfImpact/" + destinationFolder + "_PointsOfImpact" + ".jpg");

				switch (position) {
				case 0:
					in = getResources().openRawResource(R.drawable.car1);
					break;
				case 1:
					in = getResources().openRawResource(R.drawable.car2);
					break;
				case 2:
					in = getResources().openRawResource(R.drawable.car3);
					break;
				case 3:
					in = getResources().openRawResource(R.drawable.car4);
					break;
				case 4:
					in = getResources().openRawResource(R.drawable.car5);
					break;
				case 5:
					in = getResources().openRawResource(R.drawable.car6);
					break;
				default:
					in = getResources().openRawResource(R.drawable.car1);
				}

				FileOperations.copyFile(in, out);
				in.close();
				out.flush();
				out.close();
			} catch (Exception e) {
				LOG.error("doInBackground:10491");
				   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
			}
			return null;
		}

		protected void onPostExecute(String dd) {
			try {
				String jobNo = "";
				Bundle extras = ListViewForVehicleDetails.this.getIntent().getExtras();
				if(extras != null)
				{
					jobNo = extras.getString("JOBNO");
				}

				Application.goForward = true;
				Application.goBackward = false;
				
				progressDialog.dismiss();
				
				Intent intent = new Intent(getApplicationContext(), FingerPaint.class);
				intent.putExtra("JOBNO", jobNo);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
				// startActivity(new Intent(getApplicationContext(), FingerPaint.class));
				// Finish the activity
				
				System.gc();
				finish();
			} catch (Exception e) {
				LOG.error("onPostExecute:10586");
				   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
			}
		}
	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		try {
			parent.getChildAt(position).setBackgroundResource(R.drawable.listview_select_imagelist);

			FormObject formObject = Application.getFormObjectInstance();
			String destinationFolder = formObject.getJobNo();
			
			new SelectAsyncTask().execute(Integer.toString(position), destinationFolder);
			progressDialog = ProgressDialog.show(this, "",	"Loading...", true);
		} catch (Exception e) {
			LOG.error("onListItemClick:10492");
			   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
		}
	}

	private class RowData {
		protected int mId;
		protected String mTitle;
		protected String mDetail;

		RowData(int id, String title, String detail) {
			mId = id;
			mTitle = title;
			mDetail = detail;
		}

		@Override
		public String toString() {
			return mId + " " + mTitle + " " + mDetail;
		}
	}

	private class CustomAdapter extends BaseAdapter {
		List<RowData> objects;
		public CustomAdapter(Context context, List<RowData> objects) {
			mInflater = LayoutInflater.from(context);
			this.objects=objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			TextView title = null;

			ImageView i11 = null;
			RowData rowData = objects.get(position);
			if (null == convertView) {
				if (getCount() == 1) {
					convertView = mInflater.inflate(
							R.layout.imagelistview_middle_row, null);
				} else if (position == 0) {
					convertView = mInflater.inflate(
							R.layout.imagelistview_middle_row, null);
				} else if (position == getCount()-1) {
					convertView = mInflater.inflate(
							R.layout.imagelistview_middle_row, null);
				} else {
					convertView = mInflater.inflate(
							R.layout.imagelistview_middle_row, null);
				}

				// convertView =
				// mInflater.inflate(R.layout.imagelistviewchildrow, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			title = holder.gettitle();
			title.setText(rowData.mTitle);

			i11 = holder.getImage();
			i11.setImageResource(imgid[rowData.mId]);
			return convertView;
		}

		private class ViewHolder {
			private View mRow;
			private TextView title = null;
			//private TextView detail = null;
			private ImageView i11 = null;

			public ViewHolder(View row) {
				mRow = row;
			}

			public TextView gettitle() {
				if (null == title) {
					title = (TextView) mRow.findViewById(R.id.title);
				}
				return title;
			}

			public ImageView getImage() {
				if (null == i11) {
					i11 = (ImageView) mRow.findViewById(R.id.img);
				}
				return i11;
			}
		}

		@Override
		public int getCount() {
			return objects.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}
}
