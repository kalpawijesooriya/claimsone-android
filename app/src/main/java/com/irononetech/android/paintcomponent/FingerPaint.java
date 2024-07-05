/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.irononetech.android.paintcomponent;

import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.irononetech.android.Application.Application;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.claimsone.R;
import com.irononetech.android.formcomponent.FormObject;
import com.irononetech.android.formcomponent.view.ListViewForVehicleDetails;

public class FingerPaint extends GraphicsActivity implements ColorPickerDialog.OnColorChangedListener {
	Logger LOG = LoggerFactory.getLogger(FingerPaint.class);

	MyView mview;
	Bitmap immutableBitmap;
	Button btnGreen;
	Button btnRed;
	Button btnEraser;

	private Paint mPaint;
	//private MaskFilter mEmboss;
	//private MaskFilter mBlur;
	int strokeWidth_PaintBrush = 7;
	int strokeWidth_Erasor = 50;
	ViewGroup container = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			System.gc();
			//mview = new MyView(this);
			
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			mPaint.setColor(0xFF00a617);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			mPaint.setStrokeCap(Paint.Cap.ROUND);
			mPaint.setStrokeWidth(strokeWidth_PaintBrush);  //12

			//mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f);
			//mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);

			if(Application.goForward)
				overridePendingTransition(R.anim.slide_left, R.anim.slide_left);
			if(Application.goBackward)
				overridePendingTransition(R.anim.slide_right, R.anim.slide_right);			
			
			View view = View.inflate(this, R.layout.paintlayout, null);
			container = (ViewGroup) view.findViewById(R.id.linearLayoutCanvasWrapper);
			mview = new MyView(this, view);
			//container.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,1100));
			
			container.addView(mview);
			this.setContentView(view);

			btnGreen = (Button) view.findViewById(R.id.btnGreen);
			btnRed = (Button) view.findViewById(R.id.btnRed);
			btnEraser = (Button) view.findViewById(R.id.btnEraser);		

			btnGreen.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_selected));
			btnRed.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
			btnEraser.setBackgroundDrawable(getResources().getDrawable(R.drawable.eraser));
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10365");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void colorChanged(int color) {
		mPaint.setColor(color);
	}

	public class MyView extends View {
		//private static final float MINP = 0.25f;
		//private static final float MAXP = 0.75f;

		public Bitmap mBitmap;
		//public Bitmap bitmap1;
		private Canvas mCanvas;
		private Path mPath;
		private Paint mBitmapPaint;

		public MyView(Context c, View view) {
			super(c);
			FormObject formObject = Application.getFormObjectInstance();
			String destinationFolder = formObject.getJobNo();

			//File storagePath = new File(URL.getSLIC_JOBS() + "dv_PointsOfImpact.jpg");
			//File storagePath = new File(URL.getSLIC_JOBS() + destinationFolder + "/PointsOfImpact");
			//storagePath.mkdirs();
			//File myImage = new File(storagePath, formObject.getJobNo() + "_PointsOfImpact" + ".jpg");

			//Bitmap temp = BitmapFactory.decodeFile(URL.getSLIC_JOBS() + "dv_PointsOfImpact"+ ".jpg");
			System.gc();
			
			Bitmap temp = BitmapFactory.decodeFile(URL.getSLIC_JOBS() + destinationFolder + "/PointsOfImpact/" + destinationFolder + "_PointsOfImpact"+ ".jpg");
			
			Bitmap temBtMap = temp.copy(Bitmap.Config.ARGB_8888, true);
			int bWidth = temBtMap.getWidth();
			int bHeight = temBtMap.getHeight();
			double ratio = (double)bHeight / bWidth;
			
			int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
			int dynHeightofImage =  (int) (screenWidth * ratio);
			temp = Bitmap.createScaledBitmap(temp, screenWidth, dynHeightofImage, true);

			LinearLayout ll = (LinearLayout) view.findViewById(R.id.linearLayoutCanvasWrapper);
			//ll.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, bHeight));
			ll.getLayoutParams().height = dynHeightofImage;
			ll.getLayoutParams().width = screenWidth;
			ll.requestLayout();
			
			//ARGB_8888 cause eraser to perform correctly.
			immutableBitmap = temp.copy(Bitmap.Config.ARGB_8888, true);

			//mBitmap = immutableBitmap.copy(Bitmap.Config.ARGB_8888, true);
			mBitmap = Bitmap.createBitmap(immutableBitmap.getWidth(), immutableBitmap.getHeight(), Bitmap.Config.ARGB_8888);

			//mBitmap = immutableBitmap.createBitmap(600, 960, Bitmap.Config.ARGB_8888);
			//mBitmap = Bitmap.createBitmap(mBitmap);
			//mBitmap = Bitmap.createBitmap(600, 960, Bitmap.Config.ARGB_8888);
			// this code is for set the background
			//BitmapFactory.decodeResource(res, id)
			//bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.car1);
			mCanvas = new Canvas(mBitmap);
			mCanvas.drawBitmap(immutableBitmap, 0, 0,null);
			LOG.info("Finger Paint Step 6");
			mPath = new Path();
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);
			System.gc();
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			try {
				canvas.drawColor(0xFFAAAAAA);
				// this is for set the background
				canvas.drawBitmap(immutableBitmap, 0, 0,null);
				canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

				canvas.drawPath(mPath, mPaint);
			} catch (Exception e) {
				LOG.error("onDraw:10366");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}

		private float mX, mY;
		private static final float TOUCH_TOLERANCE = 4;

		private void touch_start(float x, float y) {
			mPath.reset();
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}

		private void touch_move(float x, float y) {
			try {
				float dx = Math.abs(x - mX);
				float dy = Math.abs(y - mY);
				if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
					mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
					mX = x;
					mY = y;
				}
			} catch (Exception e) {
				LOG.error("touch_move:10367");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}

		private void touch_up() {
			try {
				mPath.lineTo(mX, mY);
				// commit the path to our offscreen
				mCanvas.drawPath(mPath, mPaint);
				// kill this so we don't double draw
				mPath.reset();
			} catch (Exception e) {
				LOG.error("touch_up:10368");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touch_start(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				touch_move(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				touch_up();
				invalidate();
				break;
			}
			return true;
		}

		public void saveToSD(Bitmap outputImage) {
			try {
				/**
				 * Point of impact has been added.
				 */
				//Application.getFormObjectInstance().setPointOfImpactAdded(true);

				FormObject formObject = Application.getFormObjectInstance();
				String destinationFolder = formObject.getJobNo();
				File storagePath = new File(URL.getSLIC_JOBS() + destinationFolder + "/PointsOfImpact");
				storagePath.mkdirs();

				File myImage = new File(storagePath, formObject.getJobNo() + "_PointsOfImpact" + ".jpg");

				try {
					FileOutputStream out = new FileOutputStream(myImage);
					//immutableBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

					Bitmap  bitmap = Bitmap.createBitmap( mCanvas.getWidth(), mCanvas.getHeight(), Bitmap.Config.ARGB_8888);
					Canvas canvas = new Canvas(bitmap);
					mview.draw(canvas); 
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); 

					//getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 80, out);
					out.flush();
					out.close();
					System.gc();
				} catch (Exception e) {
					LOG.error("saveToSD:10369");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
				}
			} catch (Exception e) {
				LOG.error("saveToSD:10370");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}
	}

	//private static final int COLOR_MENU_ID = Menu.FIRST;
	//private static final int EMBOSS_MENU_ID = Menu.FIRST + 1;
	//private static final int BLUR_MENU_ID = Menu.FIRST + 2;
	private static final int ERASE_MENU_ID = Menu.FIRST + 3;
	//private static final int SRCATOP_MENU_ID = Menu.FIRST + 4;
	//
	private static final int SAVE_MENU_ID = Menu.FIRST + 5;


	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//return false;
			try {
				commonFunc();
			} catch (Exception e) {
				LOG.error("onKeyDown:10587");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void commonFunc(){
		try {
			System.gc();
			AlertDialog.Builder alertbox = new AlertDialog.Builder(
					FingerPaint.this);
			alertbox.setTitle(R.string.saform);
			alertbox.setCancelable(false);
			alertbox.setMessage("Your sketch is not saved. Do you want to proceed?");
			alertbox.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					// deletePointOfImpactImg();
					
					Application.goForward = false;
					Application.goBackward = true;
//					Intent intent = new Intent(getApplicationContext(), ListViewForVehicleDetails.class);
//					intent.putExtra("JOBNO", Application.getFormObjectInstance().getJobNo());
//					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					startActivity(intent);
					
					finish();
				}});
			alertbox.setNegativeButton("No",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					removeDialog(arg1);
				}});
			alertbox.show();
		} catch (Exception e) {
		}
	}

	private void deletePointOfImpactImg(){
		try {
			String jobNo = "";
			Bundle extras = FingerPaint.this.getIntent().getExtras();
			if(extras != null)
			{
				jobNo = extras.getString("JOBNO");
			}

			File imgFile = new  File(URL.getSLIC_JOBS() + jobNo + "/PointsOfImpact/"
					+ jobNo + "_PointsOfImpact.jpg");
			if(imgFile.exists()){
				imgFile.delete();
			}
		} catch (Exception e) {
			LOG.error("deletePointOfImpactImg:10588");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		//menu.add(0, COLOR_MENU_ID, 0, "Color").setShortcut('3', 'c');
		//menu.add(0, EMBOSS_MENU_ID, 0, "Emboss").setShortcut('4', 's');
		//menu.add(0, BLUR_MENU_ID, 0, "Blur").setShortcut('5', 'z');
		menu.add(0, ERASE_MENU_ID, 0, "Erase").setShortcut('5', 'z');
		//menu.add(0, SRCATOP_MENU_ID, 0, "SrcATop").setShortcut('5', 'z');
		//
		menu.add(0, SAVE_MENU_ID, 0, "Save").setShortcut('5', 'z');
		//
		/****
		 * Is this the mechanism to extend with filter effects? Intent intent =
		 * new Intent(null, getIntent().getData());
		 * intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		 * menu.addIntentOptions( Menu.ALTERNATIVE, 0, new ComponentName(this,
		 * NotesList.class), null, intent, 0, null);
		 *****/
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mPaint.setXfermode(null);
		mPaint.setAlpha(0xFF);

		switch (item.getItemId()) {
		/*case COLOR_MENU_ID:
			new ColorPickerDialog(this, this, mPaint.getColor()).show();
			return true;
		case EMBOSS_MENU_ID:
			if (mPaint.getMaskFilter() != mEmboss) {
				mPaint.setMaskFilter(mEmboss);
			} else {
				mPaint.setMaskFilter(null);
			}
			return true;
		case BLUR_MENU_ID:
			if (mPaint.getMaskFilter() != mBlur) {
				mPaint.setMaskFilter(mBlur);
			} else {
				mPaint.setMaskFilter(null);
			}
			return true;*/
		case ERASE_MENU_ID:
			mPaint.setStrokeWidth(strokeWidth_Erasor);
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			return true;
			/*case SRCATOP_MENU_ID:
			mPaint
					.setXfermode(new PorterDuffXfermode(
							PorterDuff.Mode.SRC_ATOP));
			mPaint.setAlpha(0x80);
			return true;*/
		case SAVE_MENU_ID:
			//mview.onSave();
			mview.saveToSD(mview.mBitmap);
			//	startActivity(new Intent(getApplicationContext(),
			//			SAFormTabActivity.class));
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void on_click_save_button(View v){
		mview.saveToSD(mview.mBitmap);
		Application.goForward = false;
		Application.goBackward = true;
		finish();
	}

	
	public void on_click_Green_button(View v){
		try {
			mPaint.setXfermode(null);
			mPaint.setAlpha(0xFF);
			mPaint.setStrokeWidth(strokeWidth_PaintBrush);
			colorChanged(0xFF00a617);

			btnGreen.setBackgroundDrawable(getResources().getDrawable(R.drawable.green_selected));
			btnRed.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
			btnEraser.setBackgroundDrawable(getResources().getDrawable(R.drawable.eraser));
			
		} catch (Exception e) {
			LOG.error("on_click_Green_button:10372");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public void on_click_Red_button(View v){
		try {
			mPaint.setXfermode(null);
			mPaint.setAlpha(0xFF);
			mPaint.setStrokeWidth(strokeWidth_PaintBrush);
			colorChanged(0xFFa51700);

			btnGreen.setBackgroundDrawable(getResources().getDrawable(R.drawable.green));
			btnRed.setBackgroundDrawable(getResources().getDrawable(R.drawable.red_selected));
			btnEraser.setBackgroundDrawable(getResources().getDrawable(R.drawable.eraser));
			
		} catch (Exception e) {
			LOG.error("on_click_Red_button:10371");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_Eraser_button(View v){
		try {			
			mPaint.setStrokeWidth(strokeWidth_Erasor);
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			colorChanged(0xFFffffff);
			mPaint.setAlpha(0xFF);
			
			btnGreen.setBackgroundDrawable(getResources().getDrawable(R.drawable.green));
			btnRed.setBackgroundDrawable(getResources().getDrawable(R.drawable.red));
			btnEraser.setBackgroundDrawable(getResources().getDrawable(R.drawable.eraser_selected));

		} catch (Exception e) {
		}
	}

	public void on_click_back_button(View v){
		commonFunc();
	}
}
