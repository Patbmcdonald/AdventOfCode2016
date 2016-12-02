package com.patrickmcdonald.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Util {
	  public static String getStringFromFile(File f) throws IOException
	    {
	    	String result = "";
	    	
	        if (f.exists())
	        {
	        	StringBuffer sb2 = new StringBuffer(5000);
	            FileReader reader = new FileReader(f);
	            BufferedReader buffer = new BufferedReader(reader);
	            
	            String st = buffer.readLine();
	            while(st != null)
	            {
	                sb2.append ("\n"+st);
	                st = buffer.readLine();
	            }
	            
	            buffer.close();
	            reader.close();
	            
	            result = sb2.toString();
	        }
	        
			return result;
	    }
}
