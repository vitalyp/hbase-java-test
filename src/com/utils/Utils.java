package com.utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.util.Time;

import com.twmacinta.util.MD5;

/**
 *  Really fast implementation, based on 'Fast implementation of RSA's MD5 hash generator'
 *  
 *  public static String generateString(Random rng, String characters, int length)
{
    char[] text = new char[length];
    for (int i = 0; i < length; i++)
    {
        text[i] = characters.charAt(rng.nextInt(characters.length()));
    }
    return new String(text);
}
 *  @author VitalyP 
*/

public class Utils {

	public static String intToHash(long value) {		
		String hash = "";
	    try {
	    	MD5 md5 = new MD5();
			md5.Update(String.valueOf(value), null);
			hash = md5.asHex();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	    return hash;
	}
	
	public static String stringToHash(String value) {
		String hash = "";	
		try {
			MD5 md5 = new MD5();
		    md5.Update(value, null);
		    hash = md5.asHex();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	    return hash;
	}
		
	public static String generateString() {
		Random ran = new Random();
		int length = ran.nextInt(199) + 1;
		return RandomStringUtils.randomAlphabetic(length);
	}
	
	public static String log(String logString) {
		try {
			String fileName= "data.log";		
			File logFile = new File(fileName);
			if(!logFile.exists()) {
			    logFile.createNewFile();
			}
			SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss.SSS");
			Writer output = new BufferedWriter(new FileWriter(logFile, true));
			output.append("[" + ft.format(new Date()) + "] " + logString + "\n");
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);			
		}
		return logString;
	}
}