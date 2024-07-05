/**
 * Copyright 2010 Lukasz Szmit <devmail@szmit.eu>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
package com.irononetech.android.utilities;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.irononetech.android.claimsone.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;


public class DateTimePicker extends RelativeLayout implements View.OnClickListener, OnDateChangedListener, OnTimeChangedListener {

	public static final long MILLISECONDS_FOR_DAY = 1000*60*60*24l;
	public static final long MILLISECONDS_FOR_MINTUE = 1000*60l;
	public static final long MILLISECONDS_FOR_30_MINTUE = 1000*60*30l;
	public static final long MILLISECONDS_FOR_HOUR = 1000*60*60l;
	
	// DatePicker reference
	private DatePicker		datePicker;
	// TimePicker reference
	private TimePicker		timePicker;
	// ViewSwitcher reference
	private ViewSwitcher	viewSwitcher;
	// Calendar reference
	private Calendar		mCalendar;
	
	private int minHour, minMinute, minYear, minMonth, minDay;
	private int maxHour, maxMinute, maxYear, maxMonth, maxDay;
	private boolean isMinValueSetFromOutSide, isMaxValueSetFromOutSide;
	
	private int previousHour;
	
	public boolean dateTimeChanged;
	public boolean isViewingTime;
	//public boolean isFirstTime;

	// Constructor start
	public DateTimePicker(Context context) {
		this(context, null);
	}

	public DateTimePicker(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DateTimePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// Get LayoutInflater instance
		final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// Inflate myself
		inflater.inflate(R.layout.datetimepicker, this, true);

		// Inflate the date and time picker views
		final LinearLayout datePickerView = (LinearLayout) inflater.inflate(R.layout.datepicker, null);
		final LinearLayout timePickerView = (LinearLayout) inflater.inflate(R.layout.timepicker, null);

		// Grab a Calendar instance
		mCalendar = Calendar.getInstance();
		
		long timeInMillies = mCalendar.getTimeInMillis();
		if (timeInMillies%MILLISECONDS_FOR_30_MINTUE != 0) {
			int lakOfMilliesToNext30Min = (int)(timeInMillies/MILLISECONDS_FOR_30_MINTUE);
			Log.v(this.getClass().getName(), "time in millisecinds: "+timeInMillies+" lakOfMilliesToNext30Min: "+lakOfMilliesToNext30Min);	
			mCalendar.setTimeInMillis((1+lakOfMilliesToNext30Min)*MILLISECONDS_FOR_30_MINTUE);
		}
		// Grab the ViewSwitcher so we can attach our picker views to it
		viewSwitcher = (ViewSwitcher) this.findViewById(R.id.DateTimePickerVS);

		// Init date picker
		datePicker = (DatePicker) datePickerView.findViewById(R.id.DatePicker);
		minYear = mCalendar.get(Calendar.YEAR);
		minMonth = mCalendar.get(Calendar.MONTH);
		minDay = mCalendar.get(Calendar.DAY_OF_MONTH);
		datePicker.init(minYear, minMonth, minDay, this);

		// Init time picker
		timePicker = (TimePicker) timePickerView.findViewById(R.id.TimePicker);
		minMinute = mCalendar.get(Calendar.MINUTE);
		minHour = mCalendar.get(Calendar.HOUR_OF_DAY);
		
		/*String dateTime = (minMonth+1)+"/"+minDay+"/"+minYear+" "+minHour+":"+minMinute;
		Log.v(this.getClass().getName(), "date time in format time: "+dateTime);
		SimpleDateFormat formater = new SimpleDateFormat("MM/d/yyyy h:mm");
		formater.setTimeZone(TimeZone.getDefault());
		Date date = formater.parse(dateTime, new ParsePosition(0));
		long timeInMillies = date.getTime();
		long lakOfMilliesToNext30Min = timeInMillies%MILLISECONDS_FOR_30_MINTUE;
		date.setTime(timeInMillies+lakOfMilliesToNext30Min);
		mCalendar.setTime(date);
		
		minYear = mCalendar.get(Calendar.YEAR);
		minMonth = mCalendar.get(Calendar.MONTH);
		minDay = mCalendar.get(Calendar.DAY_OF_MONTH);
		datePicker.updateDate(minYear, minMonth, minDay);
		
		minMinute = mCalendar.get(Calendar.MINUTE);
		minHour = mCalendar.get(Calendar.HOUR_OF_DAY);
		timePicker.setCurrentHour(minHour);
		timePicker.setCurrentMinute(minMinute);*/
	
		/*if (minMinute > 0 && minMinute <= 29) {
			minMinute = 30;
		} else if (minMinute >= 31 && minMinute <= 59){
			minMinute = 0;
			minHour +=1;
		}*/
		previousHour = minHour;
		mCalendar.set(minYear, minMonth, minDay, minHour, minMinute);

		timePicker.setCurrentMinute(minMinute);
		timePicker.setCurrentHour(minHour);
		timePicker.setOnTimeChangedListener(this);
		timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

		// Handle button clicks
		((Button) findViewById(R.id.SwitchToTime)).setOnClickListener(this); // shows the time picker
		((Button) findViewById(R.id.SwitchToDate)).setOnClickListener(this); // shows the date picker

		// Populate ViewSwitcher
		viewSwitcher.addView(datePickerView, 0);
		viewSwitcher.addView(timePickerView, 1);
		
		Log.v(this.getClass().getName(), ""+minYear+"/"+minMonth+"/"+minDay+" "+minHour+":"+minMinute);
		
		//isFirstTime = true;
		dateTimeChanged = false;
		isViewingTime = false;
	}
	// Constructor end

	// Called every time the user changes DatePicker values
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		// Update the internal Calendar instance
		//mCalendar.set(year, monthOfYear, dayOfMonth, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE));
		updateDatePickerDisplay(view, year, monthOfYear, dayOfMonth);
	}

	// Called every time the user changes TimePicker values
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		// Update the internal Calendar instance
		//mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
		updateTimePickerDisplay(view, hourOfDay, minute);
	}

	private TimePicker.OnTimeChangedListener nullTimeChangedListener = new TimePicker.OnTimeChangedListener() {

        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        }
    };
    
    /*private DatePicker.OnDateChangedListener nullDateChangedListener = new DatePicker.OnDateChangedListener() {

        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        }
    };*/
    
    
    private void updateDatePickerDisplay(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    	dateTimeChanged = true;
    	
    	Log.v("updateDatePickerDisplay", "year: "+year+" monthOfYear: "+monthOfYear+" dayOfMonth: "+dayOfMonth);
    	int currentHour = mCalendar.get(Calendar.HOUR_OF_DAY);
    	int currentMinute = mCalendar.get(Calendar.MINUTE);
    	
    	boolean isDateTimeAfterDateTime = dateTimeAfterDateTime(minYear, minMonth, minDay, minHour, minMinute, year, monthOfYear, dayOfMonth, currentHour, currentMinute);
    	boolean isDateTimeBeforeDateTime = dateTimeBeforeDateTime(maxYear, maxMonth, maxDay, maxHour, maxMinute, year, monthOfYear, dayOfMonth, currentHour, currentMinute);
    	boolean isDateTimeEqualMinDateTime = dateTimeEqualDateTime(minYear, minMonth, minDay, minHour, minMinute, year, monthOfYear, dayOfMonth, currentHour, currentMinute);
    	boolean isDateTimeEqualMaxDateTime = dateTimeEqualDateTime(maxYear, maxMonth, maxDay, maxHour, maxMinute, year, monthOfYear, dayOfMonth,currentHour, currentMinute);
    	
    	int nextYear = minYear;
    	int nextMonth = minMonth;
    	int nextDay = minDay;
    	
    	if (isMaxValueSetFromOutSide) {
    		Log.v(this.getClass().getName(), "isMaxValueSetFromOutSide");
    		if (isDateTimeAfterDateTime && isDateTimeBeforeDateTime) {
	    		nextYear = year;
	    		nextMonth = monthOfYear;
	    		nextDay = dayOfMonth;
	    		Log.v(this.getClass().getName(), "date is within the given date range."+nextYear+" "+nextMonth+" "+nextDay);
	    	} else if (isDateTimeEqualMinDateTime || isDateTimeEqualMaxDateTime) {
	    		nextYear = year;
	    		nextMonth = monthOfYear;
	    		nextDay = dayOfMonth;
	    		Log.v(this.getClass().getName(), "date is equal to given date range.");
	    	} else {
	    		/*currentHour = minHour-1;
	    		currentMinute = minMinute+30;
	    		if (currentMinute == 60) currentMinute = 0;*/
	    		Log.v(this.getClass().getName(), "date is not in the given date range.");
	    		if (!isDateTimeBeforeDateTime) {
	    			Log.v(this.getClass().getName(), "! isDateTimeBeforeDateTime");
	    			currentHour = maxHour;
		    		currentMinute = maxMinute;
		    		nextYear = maxYear;
		        	nextMonth = maxMonth;
		        	nextDay = maxDay;
	    		} else if (!isDateTimeAfterDateTime) {
	    			Log.v(this.getClass().getName(), "! isDateTimeAfterDateTime");
	    			currentHour = minHour;
		    		currentMinute = minMinute;
		    		nextYear = minYear;
		        	nextMonth = minMonth;
		        	nextDay = minDay;
	    		} else {
	    			Log.v(this.getClass().getName(), "! isDateTimeAfterDateTime && ! isDateTimeBeforeDateTime");
	    		}	    		
	    	}
    	} else {
	    	if (isDateTimeAfterDateTime || isDateTimeEqualMinDateTime) {
	    		nextYear = year;
	    		nextMonth = monthOfYear;
	    		nextDay = dayOfMonth;
	    		Log.v(this.getClass().getName(), "date is within the given date range.");
	    	} else {
	    		currentHour = minHour;
	    		currentMinute = minMinute;
	    		Log.v(this.getClass().getName(), "date is not in the given date range.");
	    	}
    	}
    	
    	Log.v(this.getClass().getName(), "nextYear: "+nextYear+" nextMonth: "+nextMonth+" nextDay: "+nextDay);
    	datePicker.updateDate(nextYear, nextMonth, nextDay);
    	timePicker.setOnTimeChangedListener(nullTimeChangedListener);
    	timePicker.setCurrentHour(currentHour);
    	timePicker.setCurrentMinute(currentMinute);
        timePicker.setOnTimeChangedListener(this);
    	mCalendar.set(nextYear, nextMonth, nextDay, currentHour, currentMinute);
    	
    }

    private void updateTimePickerDisplay(TimePicker timePicker, int hourOfDay, int minute) {     
    	dateTimeChanged = true;
    	
    	/* **************************************************** */
    	int currentYear = mCalendar.get(Calendar.YEAR);
    	int currentMonth = mCalendar.get(Calendar.MONTH);
    	int currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
    	
    	Log.v("updateTimePickerDisplay", "currentYear: "+currentYear+" currentMonth: "+currentMonth+" currentDay: "+currentDay+" hourOfDay: "+hourOfDay+" minute: "+minute); 
    	
    	if (previousHour == 11 && hourOfDay == 0) hourOfDay = 12;
    	else if (previousHour == 23 && hourOfDay == 12) hourOfDay = 0;
    	else if (previousHour == 12 && hourOfDay == 23) hourOfDay = 11;
    	else if (previousHour == 0 && hourOfDay == 11) hourOfDay = 23;
    	
    	boolean isDateTimeAfterDateTime = dateTimeAfterDateTime(minYear, minMonth, minDay, minHour, minMinute, currentYear, currentMonth, currentDay, hourOfDay, minute);
    	//boolean isDateTimeBeforeDateTime = dateTimeBeforeDateTime(maxYear, maxMonth, maxDay, maxHour, maxMinute, currentYear, currentMonth, currentDay, hourOfDay, minute);
    	
        int nextMinute = minMinute;
        int nextHour = minHour;        

        if (isMaxValueSetFromOutSide) {
        	Log.v("updateTimePickerDisplay", "isMaxValueSetFromOutSide");
        	//if (isDateTimeAfterDateTime && isDateTimeBeforeDateTime) {
        		//Log.v("updateTimePickerDisplay", "isDateTimeAfterDateTime && isDateTimeBeforeDateTime");
	        	if (minute == 1) {
	            	nextMinute = 30;
	            	nextHour = hourOfDay;
	            } else if (minute == 31) {
	    	        nextMinute = 0;
	    	        nextHour = hourOfDay + 1;
	    	        if (nextHour > 23)  nextHour = 0;
	    	        /*boolean isNewDateTimeAfterDateTime = dateTimeAfterDateTime(minYear, minMonth, minDay, minHour, minMinute, currentYear, currentMonth, currentDay, nextHour, nextMinute);
	    	        if (!isNewDateTimeAfterDateTime) {
	    	        	nextMinute = 30;
	    	        	nextHour = 23;
	    	        }*/
	    	    } else if (minute == 29) {              	
	            	nextMinute = 0;          	
	            	nextHour = hourOfDay;
	            } else if (minute == 59) {
	    	        nextMinute = 30;
	    	        nextHour = hourOfDay-1;
	    	        if (nextHour < 0) nextHour = 23;  
	    	        /*boolean isNewDateTimeAfterDateTime = dateTimeAfterDateTime(minYear, minMonth, minDay, minHour, minMinute, currentYear, currentMonth, currentDay, nextHour, nextMinute);
	    	        if (!isNewDateTimeAfterDateTime) {
	    	        	nextMinute = minMinute;
	    	        	nextHour = minHour;
	    	        }*/
	            } else {
	            	nextHour = hourOfDay;
	        		nextMinute = minute;
	        		/*boolean isNewDateTimeAfterDateTime = dateTimeAfterDateTime(minYear, minMonth, minDay, minHour, minMinute, currentYear, currentMonth, currentDay, nextHour, nextMinute);
	     	        if (!isNewDateTimeAfterDateTime) {
	     	        	nextMinute = minMinute;
	     	        	nextHour = minHour;
	     	        }*/
	            }
	        	
	        	boolean isNewDateTimeAfterDateTime = dateTimeAfterDateTime(minYear, minMonth, minDay, minHour, minMinute, currentYear, currentMonth, currentDay, nextHour, nextMinute);
	        	boolean isNewDateTimeBeforeDateTime = dateTimeBeforeDateTime(maxYear, maxMonth, maxDay, maxHour, maxMinute, currentYear, currentMonth, currentDay, nextHour, nextMinute);
     	        
	        	if (!(isNewDateTimeAfterDateTime && isNewDateTimeBeforeDateTime)) {
	        		Log.v("updateTimePickerDisplay", "! (isDateTimeAfterDateTime && isDateTimeBeforeDateTime)");
	        		if (!isNewDateTimeAfterDateTime) {
	     	        	currentYear = minYear;
			        	currentMonth = minMonth;
			        	currentDay = minDay;
	     	        	nextMinute = minMinute;
	     	        	nextHour = minHour;
	     	        } else if (!isNewDateTimeBeforeDateTime) {
	    	        	currentYear = maxYear;
			        	currentMonth = maxMonth;
			        	currentDay = maxDay;
			        	nextHour = maxHour;
			        	nextMinute = maxMinute;
	    	        }
	        	} else {
	        		Log.v("updateTimePickerDisplay", "(isDateTimeAfterDateTime && isDateTimeBeforeDateTime)");
	        	}
	        	
	        /*} else {
	        	Log.v("updateTimePickerDisplay", "! (isDateTimeAfterDateTime && isDateTimeBeforeDateTime)");
	        	       	
	        		
	        	if (!isDateTimeBeforeDateTime) {
	        		Log.v("updateTimePickerDisplay", "! isDateTimeBeforeDateTime");
	        		if (minute == 59) {
		    	        nextMinute = 30;
		    	        nextHour = hourOfDay-1;
		    	        if (nextHour < 0) nextHour = 23;  
		    	        boolean isNewDateTimeBeforeDateTime = dateTimeAfterDateTime(maxYear, maxMonth, maxDay, maxHour, maxMinute, currentYear, currentMonth, currentDay, nextHour, nextMinute);
		    	        if (!isNewDateTimeBeforeDateTime) {
		    	        	currentYear = maxYear;
				        	currentMonth = maxMonth;
				        	currentDay = maxDay;
				        	nextHour = maxHour;
				        	nextMinute = maxMinute;
		    	        } 
	        		}
	        	} else if (!isDateTimeAfterDateTime) {
	        		Log.v("updateTimePickerDisplay", "! isDateTimeAfterDateTime");
	        		currentYear = minYear;
		        	currentMonth = minMonth;
		        	currentDay = minDay;
		        	nextHour = minHour;
		        	nextMinute = minMinute;
	        	} else {
	        		Log.v("updateTimePickerDisplay", "! (! isDateTimeAfterDateTime && !isDateTimeBeforeDateTime)");
	        		currentYear = minYear;
		        	currentMonth = minMonth;
		        	currentDay = minDay;
	        	}
	        	
	        }*/
        } else {
        	Log.v("updateTimePickerDisplay", "! isMaxValueSetFromOutSide");
	        if (isDateTimeAfterDateTime) {
	        	if (minute == 1) {
	            	nextMinute = 30;
	            	nextHour = hourOfDay;
	            } else if (minute == 31) {
	    	        nextMinute = 0;
	    	        nextHour = hourOfDay + 1;
	    	        if (nextHour > 23)  nextHour = 0;
	    	        boolean isNewDateTimeAfterDateTime = dateTimeAfterDateTime(minYear, minMonth, minDay, minHour, minMinute, currentYear, currentMonth, currentDay, nextHour, nextMinute);
	    	        if (!isNewDateTimeAfterDateTime) {
	    	        	nextMinute = 30;
	    	        	nextHour = 23;
	    	        }
	    	    } else if (minute == 29) {              	
	            	nextMinute = 0;          	
	            	nextHour = hourOfDay;
	            } else if (minute == 59) {
	    	        nextMinute = 30;
	    	        nextHour = hourOfDay-1;
	    	        if (nextHour < 0) nextHour = 23;  
	    	        boolean isNewDateTimeAfterDateTime = dateTimeAfterDateTime(minYear, minMonth, minDay, minHour, minMinute, currentYear, currentMonth, currentDay, nextHour, nextMinute);
	    	        if (!isNewDateTimeAfterDateTime) {
	    	        	nextMinute = minMinute;
	    	        	nextHour = minHour;
	    	        }
	            } else {
	            	nextHour = hourOfDay;
	        		nextMinute = minute;
	        		boolean isNewDateTimeAfterDateTime = dateTimeAfterDateTime(minYear, minMonth, minDay, minHour, minMinute, currentYear, currentMonth, currentDay, nextHour, nextMinute);
	     	        if (!isNewDateTimeAfterDateTime) {
	     	        	nextMinute = minMinute;
	     	        	nextHour = minHour;
	     	        }
	            }
	        } else {
	        	currentYear = minYear;
	        	currentMonth = minMonth;
	        	currentDay = minDay;
	        }
        }
        
        
    	/* **************************************************** */
        previousHour = nextHour;
        // remove ontimechangedlistener to prevent stackoverflow/infinite loop
        timePicker.setOnTimeChangedListener(nullTimeChangedListener);
        timePicker.setCurrentMinute(nextMinute);
        timePicker.setCurrentHour(nextHour);
        timePicker.setOnTimeChangedListener(this);
        datePicker.updateDate(currentYear, currentMonth, currentDay);
        mCalendar.set(currentYear, currentMonth, currentDay, nextHour, nextMinute);

    }
    
	// Handle button clicks
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.SwitchToDate:
				isViewingTime = false;
				v.setEnabled(false);
				findViewById(R.id.SwitchToTime).setEnabled(true);
				viewSwitcher.showPrevious();
				break;

			case R.id.SwitchToTime:
				isViewingTime = true;
				v.setEnabled(false);
				findViewById(R.id.SwitchToDate).setEnabled(true);
				viewSwitcher.showNext();
				break;
		}
	}

	// Convenience wrapper for internal Calendar instance
	public int get(final int field) {
		return mCalendar.get(field);
	}

	// Reset DatePicker, TimePicker and internal Calendar instance
	public void reset() {
		//final Calendar c = Calendar.getInstance();
		updateDate(minYear, minMonth, minDay);
		updateTime(minHour,minMinute);
	}

	// Convenience wrapper for internal Calendar instance
	public long getDateTimeMillis() {
		return mCalendar.getTimeInMillis();
	}

	// Convenience wrapper for internal TimePicker instance
	public void setIs24HourView(boolean is24HourView) {
		timePicker.setIs24HourView(is24HourView);
	}
	
	// Convenience wrapper for internal TimePicker instance
	public boolean is24HourView() {
		return timePicker.is24HourView();
	}

	// Convenience wrapper for internal DatePicker instance
	public void updateDate(int year, int monthOfYear, int dayOfMonth) {
		datePicker.updateDate(year, monthOfYear, dayOfMonth);
	}

	// Convenience wrapper for internal TimePicker instance
	public void updateTime(int currentHour, int currentMinute) {
		timePicker.setOnTimeChangedListener(nullTimeChangedListener);
		timePicker.setCurrentHour(currentHour);
		timePicker.setCurrentMinute(currentMinute);
		timePicker.setOnTimeChangedListener(this);
	}
	
	/*
	private boolean dateAfterDate(int... dates) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		formatter.setTimeZone(TimeZone.getDefault());
		Date firstDate = formatter.parse(dates[1]+"/"+dates[2]+"/"+dates[0], new ParsePosition(0));
		Date secondDate = formatter.parse(dates[4]+"/"+dates[5]+"/"+dates[3], new ParsePosition(0));
		return secondDate.after(firstDate);
	}
	
	private boolean dateBeforeDate(int... dates) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		formatter.setTimeZone(TimeZone.getDefault());
		Date firstDate = formatter.parse(dates[1]+"/"+dates[2]+"/"+dates[0], new ParsePosition(0));
		Date secondDate = formatter.parse(dates[4]+"/"+dates[5]+"/"+dates[3], new ParsePosition(0));
		return secondDate.before(firstDate);
	}

	private boolean dateEqualDate(int... dates) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		formatter.setTimeZone(TimeZone.getDefault());
		Date firstDate = formatter.parse(dates[1]+"/"+dates[2]+"/"+dates[0], new ParsePosition(0));
		Date secondDate = formatter.parse(dates[4]+"/"+dates[5]+"/"+dates[3], new ParsePosition(0));
		return secondDate.equals(firstDate);
	}
	*/
	
	private boolean dateTimeAfterDateTime(int... dates) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy H:mm");
		formatter.setTimeZone(TimeZone.getDefault());
		Date firstDate = formatter.parse(dates[1]+"/"+dates[2]+"/"+dates[0]+" "+dates[3]+":"+dates[4], new ParsePosition(0));
		Date secondDate = formatter.parse(dates[6]+"/"+dates[7]+"/"+dates[5]+" "+dates[8]+":"+dates[9], new ParsePosition(0));
		return secondDate.after(firstDate);
	}
	
	
	private boolean dateTimeBeforeDateTime(int... dates) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy H:mm");
		formatter.setTimeZone(TimeZone.getDefault());
		Date firstDate = formatter.parse(dates[1]+"/"+dates[2]+"/"+dates[0]+" "+dates[3]+":"+dates[4], new ParsePosition(0));
		Date secondDate = formatter.parse(dates[6]+"/"+dates[7]+"/"+dates[5]+" "+dates[8]+":"+dates[9], new ParsePosition(0));
		return secondDate.before(firstDate);
	}
	
	private boolean dateTimeEqualDateTime(int... dates) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy H:mm");
		formatter.setTimeZone(TimeZone.getDefault());
		Date firstDate = formatter.parse(dates[1]+"/"+dates[2]+"/"+dates[0]+" "+dates[3]+":"+dates[4], new ParsePosition(0));
		Date secondDate = formatter.parse(dates[6]+"/"+dates[7]+"/"+dates[5]+" "+dates[8]+":"+dates[9], new ParsePosition(0));
		return secondDate.equals(firstDate);
	}
	
	/**
	 * @return the minHour
	 */
	public int getMinHour() {
		return minHour;
	}

	/**
	 * @param minHour the minHour to set
	 */
	public void setMinHour(int minHour) {
		this.minHour = minHour;
	}

	/**
	 * @return the minMinute
	 */
	public int getMinMinute() {
		return minMinute;
	}

	/**
	 * @param minMinute the minMinute to set
	 */
	public void setMinMinute(int minMinute) {
		this.minMinute = minMinute;
	}

	/**
	 * @return the minYear
	 */
	public int getMinYear() {
		return minYear;
	}

	/**
	 * @param minYear the minYear to set
	 */
	public void setMinYear(int minYear) {
		this.minYear = minYear;
	}

	/**
	 * @return the minMonth
	 */
	public int getMinMonth() {
		return minMonth;
	}

	/**
	 * @param minMonth the minMonth to set
	 */
	public void setMinMonth(int minMonth) {
		this.minMonth = minMonth;
	}

	/**
	 * @return the minDay
	 */
	public int getMinDay() {
		return minDay;
	}

	/**
	 * @param minDay the minDay to set
	 */
	public void setMinDay(int minDay) {
		this.minDay = minDay;
	}

	/**
	 * @return the maxHour
	 */
	public int getMaxHour() {
		return maxHour;
	}

	/**
	 * @param maxHour the maxHour to set
	 */
	public void setMaxHour(int maxHour) {
		this.maxHour = maxHour;
	}

	/**
	 * @return the maxMinute
	 */
	public int getMaxMinute() {
		return maxMinute;
	}

	/**
	 * @param maxMinute the maxMinute to set
	 */
	public void setMaxMinute(int maxMinute) {
		this.maxMinute = maxMinute;
	}

	/**
	 * @return the maxYear
	 */
	public int getMaxYear() {
		return maxYear;
	}

	/**
	 * @param maxYear the maxYear to set
	 */
	public void setMaxYear(int maxYear) {
		this.maxYear = maxYear;
	}

	/**
	 * @return the maxMonth
	 */
	public int getMaxMonth() {
		return maxMonth;
	}

	/**
	 * @param maxMonth the maxMonth to set
	 */
	public void setMaxMonth(int maxMonth) {
		this.maxMonth = maxMonth;
	}

	/**
	 * @return the maxDay
	 */
	public int getMaxDay() {
		return maxDay;
	}

	/**
	 * @param maxDay the maxDay to set
	 */
	public void setMaxDay(int maxDay) {
		this.maxDay = maxDay;
	}

	/**
	 * @return the isMinValueSetFromOutSide
	 */
	public boolean isMinValueSetFromOutSide() {
		return isMinValueSetFromOutSide;
	}

	/**
	 * @param isMinValueSetFromOutSide the isMinValueSetFromOutSide to set
	 */
	public void setMinValueSetFromOutSide(boolean isMinValueSetFromOutSide) {
		//isFirstTime = true;
		dateTimeChanged = false;
		this.isMinValueSetFromOutSide = isMinValueSetFromOutSide;
		timePicker.setOnTimeChangedListener(nullTimeChangedListener);
		timePicker.setCurrentMinute(minMinute);
		timePicker.setCurrentHour(minHour);
		timePicker.setOnTimeChangedListener(this);
		datePicker.updateDate(minYear, minMonth, minDay);
		mCalendar.set(minYear, minMonth, minDay, minHour, minMinute);
	}

	/**
	 * @return the isMaxValueSetFromOutSide
	 */
	public boolean isMaxValueSetFromOutSide() {
		return isMaxValueSetFromOutSide;
	}

	/**
	 * @param isMaxValueSetFromOutSide the isMaxValueSetFromOutSide to set
	 */
	public void setMaxValueSetFromOutSide(boolean isMaxValueSetFromOutSide) {
		this.isMaxValueSetFromOutSide = isMaxValueSetFromOutSide;
	}

	/**
	 * @return the mCalendar
	 */
	public Calendar getmCalendar() {
		return mCalendar;
	}

	/**
	 * @param mCalendar the mCalendar to set
	 */
	public void setmCalendar(Calendar mCalendar) {
		this.mCalendar = mCalendar;
	}
}