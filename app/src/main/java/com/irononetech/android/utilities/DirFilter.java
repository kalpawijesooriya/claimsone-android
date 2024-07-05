package com.irononetech.android.utilities;

import java.io.File;
import java.io.FileFilter;

public class DirFilter implements FileFilter{

	public boolean accept(File pathname) {
		if( pathname.isDirectory() ) 
            return true;

        return false;
	}
}
