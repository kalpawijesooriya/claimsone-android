package com.irononetech.android.template.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.irononetech.android.Webservice.URL;

import android.os.Environment;

/**
 * 
 * @author Adeesha Wijayasiri @ this class is for write a log file for the
 *         application
 */
	public class LogFile implements Runnable {
		
		private static final String TAG = "LogFile ";
		public static BufferedWriter out;
		private static int firsttimerun = 0;
		String tag;
		String message;
		static File LogFilePath;
		public static LogFile logFile;
		static SimpleDateFormat formatter;
		static StringBuilder builder;

		public static LogFile getInstance() {
			try {
				if (logFile == null) {
					logFile = new LogFile();
				}
				return logFile;
			} catch (Exception e) {
				LogFile.d("EXCEPTION ", TAG + "getInstance:10399");
				return null;
			}
		}

	public LogFile() {
		try {
			if (firsttimerun == 0) {
				try {
					createFileOnDevice(true);
				} catch (IOException e) {
					LogFile.d("EXCEPTION ", TAG + "LogFile:10403");
				}
			}
			FileWriter LogWriter = null;
			try {
				LogWriter = new FileWriter(LogFilePath, true);
			} catch (IOException e) {
				LogFile.d("EXCEPTION ", TAG + "LogFile:10401");
			}
			out = new BufferedWriter(LogWriter);
			formatter = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
		} catch (Exception e) {
			LogFile.d("EXCEPTION ", TAG + "LogFile:10400");
		}
	}

	private static void createFileOnDevice(Boolean append) throws IOException {
		try {
			firsttimerun = 1;
			File logDirectory = new File(URL.getSLIC_LOGS());
			if (!logDirectory.exists()) {
				logDirectory.mkdirs();
			}
			if (logDirectory.canWrite()) {
				LogFilePath = new File(logDirectory, "Log.txt");
			}
		} catch (Exception e) {
			LogFile.d("EXCEPTION ", TAG + "createFileOnDevice:10402");
		}
	}

	
	private static StringBuilder getSBInstance() {
		try {
			if (builder == null) {
				builder = new StringBuilder();
			}
			return builder;
		} catch (Exception e) {
			LogFile.d("EXCEPTION ", TAG + "getSBInstance:10552");
			return null;
		}
	}
	
	/**
	 * function to call the method for writing,
	 * 
	 * @param tag
	 *            is for display the tag of the message
	 * @param message
	 *            for display the message
	 */
	public static void d(String tag1, String message1) {
		try {
			LogFile f = LogFile.getInstance();
			/*
			try {
				LogFile.getInstance().tag = tag;
				LogFile.getInstance().message = message;
				Thread th = new Thread(LogFile.getInstance());
				th.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			*/
			
			String date = formatter.format(Calendar.getInstance().getTime());
			
			try {
				/*StringBuilder sb = getSBInstance();
				sb.append(tag1);
				sb.append(" : ");
				sb.append(message1);
				sb.append("  : ");
				
				sb.append(date);
				sb.append("\r\n");*/
	
				//out.write(tag1 + " : " + message1 + "\t\t\t: " + date + "\r\n");


				String formatted = String.format("%-12s %-100s %-10s %n", tag1, message1, date);
				out.write(formatted);
				
				//out.write(sb.toString());
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			String date = formatter.format(Calendar.getInstance().getTime());
			out.write(tag + ":" + message + ":" + date + "\r\n");
		} catch (IOException e1) {
			LogFile.d("EXCEPTION ", TAG + "run:10405");
		}
		try {
			out.flush();
		} catch (IOException e) {
			LogFile.d("EXCEPTION ", TAG + "run:10406");
		}
	}
}