package com.irononetech.android.database;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import com.irononetech.android.Webservice.URL;
import com.irononetech.android.fileio.FileOperations;

public class DBHandler {

	static final String DB_NAME = "slic.db";
	static final int DB_VERSION = 2;

	static final String JOBS_TABLE = "Jobs";
	static final String IMAGE_GAL_TABLE = "ImageGal";
	static final String USERS_TABLE = "Users";
	static final String JT_ID = BaseColumns._ID;

	// Accident images list details
	public static final String JT_FIELD = "Field";
	public static final String JT_IMAGE_PATH = "ImagePath";
	public static final String JT_RESUBMIT = "Resubmit";
	public static final String JT_REFNO = "RefNo";
	public static final String JT_STATUS = "Status";

	// Job details
	public static final String JT_JOB_NO = "JobNo";
	public static final String JT_TIME_REPORTED = "TimeReported";
	public static final String JT_VEHICLE_NO = "VehicleNo";
	public static final String JT_JOBSTATUS = "JobStatus";

	// User details
	//public static final String JT_UID = BaseColumns._ID;
	public static final String JT_USERNAME = "Username";
	public static final String JT_PASSWORD = "Password";
	
	static final String POLICESTATIONS_TABLE = "PoliceStations";
	static final String PT_ID = "PoliceStationID";
	static final String PT_Name = "PoliceStationName";
	static Logger LOG = LoggerFactory.getLogger(DBHandler.class);
	private DBHelper dbHelper;
	private static DBHandler dbHandler;

	public DBHandler(Context context) {
		this.dbHelper = new DBHelper(context);
		//Log.i(TAG, "Initialized data");
	}

	public static DBHandler createInstance(Context context) {
		try {
			if (dbHandler == null) {
				dbHandler = new DBHandler(context);
			}
			return dbHandler;
		} catch (Exception e) {
			LOG.error("createInstance");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}
	
	public static DBHandler getInstance() {
		return dbHandler;
	}

	// Inner class : DBHelper
	public class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// Create Jobs Table
			String sql_jobs_table = "CREATE TABLE " + JOBS_TABLE + " (" + JT_ID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + JT_JOB_NO
					+ " TEXT, " + JT_TIME_REPORTED + " TEXT, " + JT_VEHICLE_NO
					+ " TEXT)";

			String sql_imageGal_table = "CREATE TABLE " + IMAGE_GAL_TABLE
					+ " (" 
					+ JT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ JT_JOB_NO + " TEXT, "
					+ JT_FIELD + " TEXT, "
					+ JT_IMAGE_PATH + " TEXT,"
					+ JT_STATUS + " TEXT,"
					+ JT_RESUBMIT + " TEXT,"
					+ JT_REFNO + " TEXT)";

			String sql_user_table = "CREATE TABLE " + USERS_TABLE 
					+ " (" 
					/*+ JT_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "*/
					+ JT_USERNAME + " TEXT PRIMARY KEY, "
					+ JT_PASSWORD + " TEXT"
					+ ")";
			
			try {
				LOG.debug("ENTRY onCreate");				
				db.execSQL(sql_jobs_table);
				LOG.debug("SUCCESS onCreate");
				//Log.d(TAG, "on created sql:" + sql_jobs_table);
			} catch (SQLException e) {
				LOG.error("onCreate");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				//Log.d(TAG, "sql exception while creating the database");
			}
			try {
				LOG.debug("ENTRY onCreate");
				db.execSQL(sql_imageGal_table);
				LOG.debug("SUCCESS onCreate");
				//Log.d(TAG, "on created sql:" + sql_imageGal_table);
			} catch (SQLException e) {
				LOG.error("onCreate");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				//Log.d(TAG, "sql exception while creating the database");
			}
			try {
				LOG.debug("ENTRY onCreate");
				db.execSQL(sql_user_table);
				LOG.debug("SUCCESS onCreate");
			} catch (Exception e) {
				LOG.error("onCreate");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			try {
				db.execSQL("drop table if exists" + JOBS_TABLE);
			} catch (SQLException e) {
				LOG.error("onUpgrade");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				//Log.d(TAG, "sql exception while upgrading the database");
			}

			try {
				db.execSQL("drop table if exists" + IMAGE_GAL_TABLE);
			} catch (SQLException e) {
				LOG.error("onUpgrade");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				//Log.d(TAG, "sql exception while upgrading the database");
			}

			try {
				db.execSQL("drop table if exists" + USERS_TABLE);
			} catch (Exception e) {
				LOG.error("onUpgrade");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
			
			try {
				onCreate(db);
				//Log.d(TAG, "on updated");
			} catch (Exception e) {
				LOG.error("onUpgrade");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
		}
	}

	public void insertOrIgnore(ContentValues values) throws Exception {
		//Log.d(TAG, "onInsertOrIgnore" + values);
		// open the db for writing
		SQLiteDatabase db = null;
		try {
			db = this.dbHelper.getWritableDatabase();
			db.insertWithOnConflict(JOBS_TABLE, null, values,
					SQLiteDatabase.CONFLICT_IGNORE);
		} catch (Exception e) {
			LOG.error("insertOrIgnore");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} finally {
			db.close();
		}
	}

	public void insertOrIgnoreToImageGal(ContentValues values) throws Exception {
		//Log.d(TAG, "onInsertOrIgnore" + values);
		// open the db for writing
		SQLiteDatabase db = null;
		try {
			db = this.dbHelper.getWritableDatabase();
			db.insertWithOnConflict(IMAGE_GAL_TABLE, null, values,
					SQLiteDatabase.CONFLICT_IGNORE);
		} catch (Exception e) {
			LOG.error("insertOrIgnoreToImageGal");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			//int j = 0;
			//j++;
		} finally {
			db.close();
		}
	}

	/**
	 * 
	 * @author Suren Manawatta 
	 * @return void 
	 */
	public void insertOrIgnoreToUsers(ContentValues values) throws Exception{
		SQLiteDatabase db = null;
		try {
			db = this.dbHelper.getWritableDatabase();
			db.insertWithOnConflict(USERS_TABLE, null, values, 
					SQLiteDatabase.CONFLICT_IGNORE);
		} catch (Exception e) {
			LOG.error("insertOrIgnoreToUsers");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		finally{
			db.close();
		}
	}
	
	public ArrayList<String> getPendingJobNoList() {
		ArrayList<String> pendingJobNoList = new ArrayList<String>();
		SQLiteDatabase db = null;
		String[] DB_TEXT_COLUMNS = { "JobNo" };
		try {
			db = this.dbHelper.getReadableDatabase();
			Cursor cursor = db.query(true, IMAGE_GAL_TABLE, DB_TEXT_COLUMNS,
					"Status = " + "'pending'", null, null, null, null, null);
			try {
				if (cursor.moveToFirst()) {
					do {
						pendingJobNoList.add(cursor.getString(0));
					} while (cursor.moveToNext());
				}
				return pendingJobNoList;
			}  catch (Exception e) {
				LOG.error("getPendingJobNoList");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
			}
			finally {
			}
			cursor.close();
		} catch (Exception e) {
			LOG.error("getPendingJobNoList");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} finally {
			db.close();
		}
		return pendingJobNoList;
	}

	public ArrayList<ArrayList<String>> getPendingImagesForJobNo(String jobNo) {
		ArrayList<ArrayList<String>> pendingImageDetailsList = new ArrayList<ArrayList<String>>();
		SQLiteDatabase dbForList = null;
		String[] DB_TEXT_COLUMNS = { "JobNo","Field","ImagePath","Resubmit","RefNo" };
		try {
			dbForList = this.dbHelper.getReadableDatabase();
			Cursor cursorForList = dbForList.query(true, IMAGE_GAL_TABLE,
					DB_TEXT_COLUMNS, "(JobNo = \"" + jobNo
							+ "\" AND Status = \"pending\")", null, null, null,
					null, null);
			try {
				int jobNoColumn = cursorForList.getColumnIndex("JobNo");
				int fieldColumn = cursorForList.getColumnIndex("Field");
				int pathColumn = cursorForList.getColumnIndex("ImagePath");
				int resubhColumn = cursorForList.getColumnIndex("Resubmit");
				int refnoColumn = cursorForList.getColumnIndex("RefNo");

				// Check if our result was valid.
				cursorForList.moveToFirst();
				if (cursorForList != null) {
					// Loop through all Results
					do {
						ArrayList<String> detailsList = new ArrayList<String>();
						String jobNoFromDB = cursorForList.getString(jobNoColumn);
						String fieldFromDB = cursorForList.getString(fieldColumn);
						String pathFromDB = cursorForList.getString(pathColumn);
						String resubFromDB = cursorForList.getString(resubhColumn);
						String refnoFromDB = cursorForList.getString(refnoColumn);
						detailsList.add(jobNoFromDB);
						detailsList.add(fieldFromDB);
						detailsList.add(pathFromDB);
						detailsList.add(resubFromDB);
						detailsList.add(refnoFromDB);
						pendingImageDetailsList.add(detailsList);
					} while (cursorForList.moveToNext());
				}

				return pendingImageDetailsList;
			} catch (Exception e) {
				LOG.error("getPendingImagesForJobNo");
				   if(e != null){
					LOG.error("Message: " + e.getMessage());
					LOG.error("StackTrace: " + e.getStackTrace());
				  }
				//int j = 0;
				//j++;
			} finally {
				cursorForList.close();
			}
		}catch (Exception e) {
			LOG.error("getPendingImagesForJobNo");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		}
		finally {
			dbForList.close();
		}
		return null;
	}
	
	public void updateImageGalTable(String[] imageData) throws Exception {
		// open the db for writing
		SQLiteDatabase db = null;
		try {
			db = this.dbHelper.getWritableDatabase();
			db.delete(IMAGE_GAL_TABLE, "(JobNo =?"
					+ " AND Field=? AND ImagePath =? AND Status =?)", imageData);
		}catch (Exception e) {
			LOG.error("updateImageGalTable");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
		} finally {
			db.close();
		}
	}

	/**
	 * 
	 * @author Suren Manawatta 
	 * @return no of rows affected 
	 */
	public int updateUsersTable(ContentValues values) throws Exception {
		SQLiteDatabase db = null;
		try {
			db = this.dbHelper.getWritableDatabase();

			/*ContentValues args = new ContentValues();
	        args.put(JT_USERNAME, values.getAsString(JT_USERNAME));
	        args.put(JT_PASSWORD, values.getAsString(JT_PASSWORD));*/
	        String usrnam = values.getAsString(JT_USERNAME).toString();
	        String pass = values.getAsString(JT_PASSWORD).toString();
			
			return db.updateWithOnConflict(USERS_TABLE, values, JT_USERNAME + "='" + usrnam + "' AND " + JT_PASSWORD + "='" + pass + "'", null, SQLiteDatabase.CONFLICT_IGNORE);
		} catch (Exception e) {
			LOG.error("updateUsersTable");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return 1; //just assume its updated, this is not a huge deal
		} finally{
			db.close();
		}
	}
	
	/**
	 * 
	 * @author Suren Manawatta
	 * @return no of rows affected 
	 */
	public boolean checkAuthenticationOffline(String username, String pass) throws Exception {
		SQLiteDatabase db = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + JT_USERNAME + " = '" + username + "' AND  " + JT_PASSWORD + " ='" + pass + "'" , null);
			
			if(c.getCount() == 1){
				return true;
			}
			return false;
		} catch (Exception e) {
			LOG.error("selectFromUsers");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return false;
		} finally {
			db.close();
		}
	}
	
	public String getPasswordOfCurrentuser(String username) throws Exception {
		SQLiteDatabase db = null;
		try {
			db = this.dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + JT_USERNAME + " = '" + username + "'" , null);
			
			if(c.getCount() == 1){
				if (c.moveToFirst()) {
					String dd = c.getString(1);
					return dd;
				}
			}
			return "";
		} catch (Exception e) {
			LOG.error("selectFromUsers");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return "";
		} finally {
			db.close();
		}
	}
	
	public ArrayList<String> getPendingJobDraftsList() {
		try {
			String filepath = URL.getSLIC_DRAFTS_JOBS();
			ArrayList<String> draftfiles = FileOperations.fileFilter(filepath);
			return draftfiles;
			
		} catch (Exception e) {
			LOG.error("getPendingJobDraftsList");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}
	
	public ArrayList<String> getPendingVisitDraftsList() {
		try {
			String filepath = URL.getSLIC_DRAFTS_VISITS();
			ArrayList<String> draftfiles = FileOperations.fileFilterForVisits(filepath);
			return draftfiles;
			
		} catch (Exception e) {
			LOG.error("getPendingDraftsListForVisits");
			   if(e != null){
				LOG.error("Message: " + e.getMessage());
				LOG.error("StackTrace: " + e.getStackTrace());
			  }
			return null;
		}
	}
	
}