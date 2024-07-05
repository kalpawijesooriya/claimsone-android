package com.irononetech.android.MultilevelListView;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;

public class DebugExpandableListView extends ExpandableListView {

	private int ROW_HEIGHT = 50;
	//private static final String LOG_TAG = "DebugExpandableListView ";
	private int rows;

	public DebugExpandableListView( Context context ) {
		super(context);
		try {
			DisplayMetrics metrics = getResources().getDisplayMetrics();
			int density = (int)metrics.density;
			if(density == 2)
				ROW_HEIGHT = 100;
			else
				ROW_HEIGHT = 50;
		} catch (Exception e) {
			ROW_HEIGHT = 50;
		}
	}

	public void setRows(int rows) {
		this.rows = rows;
		//LogFile.d( LOG_TAG, "rows set: "+rows );
	}

	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure( widthMeasureSpec, heightMeasureSpec );
		setMeasuredDimension(getMeasuredWidth(), rows * ROW_HEIGHT);

	} 

	protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
		super.onLayout( changed, left,top,right,bottom );

	}

	/*private String decodeMeasureSpec( int measureSpec ) {
		int mode = View.MeasureSpec.getMode( measureSpec );
		String modeString = "<> ";
		switch( mode ) {
		case View.MeasureSpec.UNSPECIFIED:
			modeString = "UNSPECIFIED ";
			break;

		case View.MeasureSpec.EXACTLY:
			modeString = "EXACTLY ";
			break;

		case View.MeasureSpec.AT_MOST:
			modeString = "AT_MOST ";
			break;
		}
		return modeString+Integer.toString( View.MeasureSpec.getSize( measureSpec ) );
	}*/
}

