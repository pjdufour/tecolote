package nova.library.utilities;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;
/**
 * @author pjdufour
 * @category Utility
 * @version 1.0
 * 
 * Parser can parse Multiline Strings and ASCII Files into a list or array of strings.    
 */
public class Parser
{
	/**
	 * 
	 * @param str - string to be parsed
	 * @param line_delim - line delimiter
	 * @param exception - prefix of a comment line
	 * @return - list of strings with one line per element
	 */
	public static LinkedList<String> stringToList(String str,String line_delim,String exception)
	{
		if(str.startsWith(exception)) return null;
		LinkedList<String> lines = new LinkedList<String>();
		StringTokenizer tokenizer = new StringTokenizer(str,line_delim);
		while(tokenizer.hasMoreTokens())
		{
			String temp = tokenizer.nextToken().trim();
			if(!temp.startsWith(exception)&&temp.length()>0)
				lines.add(temp);
		}
		return lines; 
	}
	/**
	 * 
	 * @param str - string to be parsed
	 * @param line_delim - line delimiter
	 * @param exception - prefix of a comment line
	 * @return - array of strings with one line per element
	 */
	public static String[] stringToArray(String str,String line_delim,String exception)
	{
		return stringToList(str,line_delim,exception).toArray(new String[]{});
	}
	/**
	 * 
	 * @param str - string to be parsed
	 * @param property_delim - delimiter between property and value
	 * @param line_delim - line delimiter
	 * @param exception - prefix of a comment line
	 * @return - HashMap<String,String> of properties as keys and corresponding values  
	 */
	public static HashMap<String,String> stringtoHashMap(String str,String property_delim,String line_delim,String exception)
	{
		if(str.startsWith(exception)) return null;
		HashMap<String,String> map = new HashMap<String,String>();
		StringTokenizer tokenizer = new StringTokenizer(str,line_delim);
		while(tokenizer.hasMoreTokens())
		{
			String temp = tokenizer.nextToken().trim();
			if(!temp.startsWith(exception)&&temp.length()>0)
			{
				int loc = temp.indexOf(property_delim);
				map.put(temp.substring(0,loc).trim(),temp.substring(loc+1).trim());
			}
		}
		return map;
	}
	
	
	
	/**
	 * 
	 * @param filename - name of file to be parsed
	 * @param exception - prefix of a comment line
	 * @return - list of strings with one line per element
	 */
	public static LinkedList<String> fileToList(String filename,String exception)
	{
		//System.err.println("loading file "+filename);
		LinkedList<String> lines = new LinkedList<String>();	
		try{
			BufferedReader file;
			try{file = new BufferedReader(new InputStreamReader(Parser.class.getResourceAsStream(filename)));}
			catch(Exception e)
			{
				//e.printStackTrace();
				file = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
			};
			String line;
			while((line = file.readLine())!=null)
			{
				line = line.trim();
				if(!line.startsWith(exception)&&line.length()>0)
					lines.add(line);
			}
		}catch(Exception e){System.err.println("Read Error from " + filename);e.printStackTrace();System.exit(-1);}
		return lines;
	}
	/**
	 * 
	 * @param filename - name of file to be parsed
	 * @param exception - prefix of a comment line
	 * @return - array of strings with one line per element
	 */
	public static String[] fileToArray(String filename,String exception)
	{
		return fileToList(filename,exception).toArray(new String[]{});
	}
	/**
	 * 
	 * @param filename - name of file to be parsed
	 * @param property_delim - delimiter between property and value
	 * @param exception - prefix of a comment line
	 * @return - HashMap<String,String> of properties as keys and corresponding values  
	 */
	public static HashMap<String,String> fileToHashmap(String filename,String property_delim,String exception)
	{
		HashMap<String,String> map = new HashMap<String,String>();
		try{
			BufferedReader file;
			try{file = new BufferedReader(new InputStreamReader(Parser.class.getResourceAsStream(filename)));}
			catch(Exception e){file = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));};
			String line;
			while((line = file.readLine())!=null)
			{
				line = line.trim();
				if(!line.startsWith(exception)&&line.length()>0)
				{
					int loc = line.indexOf(property_delim);
					map.put(line.substring(0,loc).trim(),line.substring(loc+1).trim());
				}
			}
		}catch(Exception e){e.printStackTrace();System.exit(-1);}
		return map;
	}
	
	public static Dimension parseDimension(String str)
	{
		String a[] = str.split("x");
		return new Dimension(Integer.parseInt(a[0].trim()),Integer.parseInt(a[1].trim()));
	}
	
	/*
	public static LinkedList<Dimension> parseDimensions(String line)
	{
		LinkedList<Dimension> dimensions_list = new LinkedList<Dimension>();
		StringTokenizer tokenizer1 = new StringTokenizer(line,",");
		while(tokenizer1.hasMoreTokens())
		{
			StringTokenizer tokenizer2 = new StringTokenizer(tokenizer1.nextToken(),"x");
			while(tokenizer2.hasMoreTokens())
			{
				int height = Integer.parseInt(tokenizer2.nextToken());
				int width = Integer.parseInt(tokenizer2.nextToken());
				dimensions_list.add(new Dimension(width,height));
			}
		}		
		return dimensions_list;
	}
	*/
}
