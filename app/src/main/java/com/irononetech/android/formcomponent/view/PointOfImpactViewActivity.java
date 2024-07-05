package com.irononetech.android.formcomponent.view;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.paintcomponent.FingerPaint;

public class PointOfImpactViewActivity extends Activity {

	Logger LOG = LoggerFactory.getLogger(PointOfImpactViewActivity.class);
	FormObject formObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			LOG.debug("ENTRY onCreate");
			formObject = Application.getFormObjectInstance();
			super.onCreate(savedInstanceState);
			setContentView(R.layout.pointofimpactview);
			String destinationFolder = formObject.getJobNo();
			final Button editBtn = (Button) findViewById(R.id.poinofimpacteditbutton);

			File imgFile = null;

			if(formObject.getisSEARCH()){
				imgFile = new  File(formObject.getPointOfImpactList().get(0));
			}else{
				imgFile = new  File(URL.getSLIC_JOBS() + destinationFolder + "/PointsOfImpact/" + destinationFolder + "_PointsOfImpact" + ".jpg");
			}

			if(formObject.getisSEARCH()){
				editBtn.setVisibility(View.GONE);
			}

			if(imgFile.exists()){
				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				
				int bWidth = myBitmap.getWidth();
				int bHeight = myBitmap.getHeight();
				double ratio = (double)bHeight / bWidth;
				
				int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
				int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
								
				int dynHeightofImage =  (int) (screenWidth * ratio);
				double ss = screenHeight - dynHeightofImage;
				
				//NOTE: If this height gap is not available back button will be cut off. 
				//2018-07-20 | Suren M
				if(ss < 350)
				{
					dynHeightofImage = screenHeight - 350;
					screenWidth = (int)(dynHeightofImage / ratio);
				}
				
				Bitmap temp = Bitmap.createScaledBitmap(myBitmap, screenWidth, dynHeightofImage, true);
				
				ImageView myImage = (ImageView) findViewById(R.id.pointofimpactview);
				myImage.setImageBitmap(temp);
			}
			else{
				//Toast.makeText(this, "No Image Available!", Toast.LENGTH_LONG).show();
			}
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10504");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//return false;
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void on_click_back_button(View v){
		try {
			System.gc();
			finish();
		} catch (Exception e) {
			LOG.error("on_click_back_button:10505");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_edit_point_of_impact(View v){
		try{
			String jobNo = formObject.getJobNo();

			Intent intent = new Intent(getApplicationContext(), FingerPaint.class);
			intent.putExtra("JOBNO", jobNo);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
		}catch(Exception e){
			LOG.error("on_click_edit_button:10506");
			if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			}
		}
	}
}