package com.irononetech.android.filecopycomponent.mtp;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.mtp.MtpConstants;
import android.mtp.MtpDevice;
import android.mtp.MtpObjectInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.irononetech.android.Webservice.URL;
import com.irononetech.android.fileio.FileOperations;
import com.irononetech.android.claimsone.R;

public class MTPPopup extends Activity {
	static Logger LOG = LoggerFactory.getLogger(MTPPopup.class);
	private static final int MAX_IMAGE_WIDTH = 750;
	private static final int MAX_IMAGE_HEIGHT = 600;
	UsbDevice device;
	ProgressDialog progressDialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		try {
			super.onCreate(savedInstanceState);
			LOG.debug("ENTRY onCreate");
			setContentView(R.layout.mtppopup);

			Intent intent = this.getIntent();
			device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
			LOG.debug("SUCCESS onCreate");
			//LogFile.d(TAG, device.toString());
			/*
			 * do nothing if UsbDevice instance isn't in the intent.
			 */
		} catch (Exception e) {
			LOG.error("onCreate:10114");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	public void on_click_file_copy(View v) {

		try {
			if (device != null) {
				progressDialog = ProgressDialog.show(MTPPopup.this, "", 
						"Copying. Please wait...", true);
				/*
				 * acquire UsbDeviceConnection instance
				 */
				UsbManager usbManager = (UsbManager) this
						.getSystemService(Context.USB_SERVICE);
				UsbDeviceConnection usbDeviceConnection = usbManager
						.openDevice(device);

				/*
				 * create MtpDevice instance and open it
				 */
				MtpDevice mtpDevice = new MtpDevice(device);
				if (!mtpDevice.open(usbDeviceConnection)) {

				}
				SlideShowImageTask task = new SlideShowImageTask(this);
				task.execute(mtpDevice);
			}
		} catch (Exception e) {
			LOG.error("onCreate:10115");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
	}

	private static class SlideShowImageTask extends
	AsyncTask<MtpDevice, Bitmap, Integer> {
		MTPPopup mtpPopup;

		public SlideShowImageTask(MTPPopup mtpPopup) {
			this.mtpPopup = mtpPopup;
		}

		@Override
		protected void onPostExecute(Integer result) {
			try {
				mtpPopup.progressDialog.dismiss();
				mtpPopup.finish();
				super.onPostExecute(result);
			} catch (Exception e) {
				LOG.error("onPostExecute:10116");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				mtpPopup.progressDialog.dismiss();
			}
		}

		@Override
		protected Integer doInBackground(MtpDevice... args) {
			try {
				MtpDevice mtpDevice = args[0];
				/*
				 * acquire storage IDs in the MTP device
				 */
				int[] storageIds = mtpDevice.getStorageIds();
				if (storageIds == null) {
					return null;
				}

				/*
				 * scan each storage
				 */
				for (int storageId : storageIds) {
					scanObjectsInStorage(mtpDevice, storageId, 0, 0);
				}

				/* close MTP device */
				mtpDevice.close();
				return null;
			} catch (Exception e) {
				LOG.error("doInBackground:10117");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				return null;
			}
		}

		private void scanObjectsInStorage(MtpDevice mtpDevice, int storageId,
				int format, int parent) {
			try {
				int[] objectHandles = mtpDevice.getObjectHandles(storageId, format,
						parent);
				if (objectHandles == null) {
					return;
				}
				try {
					File myDirectory = new File(URL.getSLIC_COPIED());

					// Delete existing images in 'destinationFolder' folder
					if (myDirectory.exists()) {
						//FileOperations.DeleteRecursive(myDirectory);
					}
				} catch (Exception e) {
					LOG.error("scanObjectsInStorage:10118");
					   if(e != null){
						LOG.error("Message: " + e.getMessage());
						LOG.error("StackTrace: " + e.getStackTrace());
					  }
				}
				for (int objectHandle : objectHandles) {
					/*
					 * It's an abnormal case that you can't acquire MtpObjectInfo
					 * from MTP device
					 */
					MtpObjectInfo mtpObjectInfo = mtpDevice
							.getObjectInfo(objectHandle);
					if (mtpObjectInfo == null) {
						continue;
					}
					//LogFile.d(TAG, mtpObjectInfo.toString());
					/*
					 * Skip the object if parent doesn't match
					 */
					int parentOfObject = mtpObjectInfo.getParent();
					if (parentOfObject != parent) {
						continue;
					}

					int associationType = mtpObjectInfo.getAssociationType();
					if (associationType == MtpConstants.ASSOCIATION_TYPE_GENERIC_FOLDER) {
						/* Scan the child folder */
						scanObjectsInStorage(mtpDevice, storageId, format,
								objectHandle);
						//LogFile.d(TAG, "Scan");
					} else if (mtpObjectInfo.getFormat() == MtpConstants.FORMAT_EXIF_JPEG
							&& mtpObjectInfo.getProtectionStatus() != MtpConstants.PROTECTION_STATUS_NON_TRANSFERABLE_DATA) {
						/*
						 * get bitmap data from the object
						 */
						LOG.info("XXXXXXX ", mtpObjectInfo.getParent() + "");
						mtpDevice.getStorageInfo(mtpObjectInfo.getStorageId());
						
						byte[] rawObject = mtpDevice.getObject(objectHandle,
								mtpObjectInfo.getCompressedSize());
						Bitmap bitmap = null;
						if (rawObject != null) {
							BitmapFactory.Options options = new BitmapFactory.Options();
							int scaleW = (mtpObjectInfo.getImagePixWidth() - 1)
									/ MAX_IMAGE_WIDTH + 1;
							int scaleH = (mtpObjectInfo.getImagePixHeight() - 1)
									/ MAX_IMAGE_HEIGHT + 1;
							int scale = Math.max(scaleW, scaleH);
							if (scale > 0) {
								options.inSampleSize = scale;
								bitmap = BitmapFactory.decodeByteArray(rawObject,
										0, rawObject.length, options);
							}
						}
						if (bitmap != null) {
							FileOperations.copyBitmapToDir(bitmap, "SLIC/Copied",
									mtpObjectInfo.getName().toString());
							//LogFile.d(TAG, mtpObjectInfo.getName().toString());
							/* show the bitmap in UI thread */
							// publishProgress(bitmap);
						}
					}
				}
			} catch (Exception e) {
				LOG.error("scanObjectsInStorage:10119");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}
	}
}
