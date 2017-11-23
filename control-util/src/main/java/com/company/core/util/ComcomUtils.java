package com.company.core.util;

public class ComcomUtils {
    
    public static String getSourceName(Thread thread){
        
        String str = thread.getStackTrace()[1].getMethodName();
        if(str.length() > 8){
            str = str.substring(0, 8);
        }
        return str;
    }
    
}

