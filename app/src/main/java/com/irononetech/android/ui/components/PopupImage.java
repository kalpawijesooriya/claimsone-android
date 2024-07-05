package com.irononetech.android.ui.components;

import com.irononetech.android.claimsone.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopupImage extends Activity {
	ImageView imageView;
	static Logger LOG = LoggerFactory.getLogger(PopupImage.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		try {
			LOG.debug("ENTRY onCreate");
			super.onCreate(savedInstanceState);
			setContentView(R.layout.popupimage);
			Bundle extras = getIntent().getExtras();
			imageView = (ImageView) findViewById(R.id.imageView1);
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			
			Bitmap bitmapImage = BitmapFactory.decodeFile(extras.getString("selectedImage"));
			BitmapDrawable drawableImage = new BitmapDrawable(bitmapImage);
			imageView.setImageDrawable(drawableImage);
			LOG.debug("SUCCESS onCreate");
		} catch (Exception e) {
			LOG.error("onCreate:10407");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}
	
	public void on_click_back_button(View v){
		finish();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
