package com.irononetech.android.utilities;

import java.io.File;
import java.io.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class txtFilter implements FileFilter {
    static Logger LOG = LoggerFactory.getLogger(JPGFileFilter.class);
    public boolean accept(File pathname) {
        try {
            String suffix = ".txt";
            String pName = pathname.getName();
            LOG.debug("path",pName);
            if (pathname.getName().toLowerCase().endsWith(suffix)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            LOG.error("getInstance:10399");
            if(e != null){
                LOG.error("Message: " + e.getMessage());
                LOG.error("StackTrace: " + e.getStackTrace());
            }
            return false;
        }
    }
}

