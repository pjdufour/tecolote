package nova.library.utilities;
/**
 * @author pjdufour
 * @category Utility
 * @version 1.0
 * 
 * SerialCreator generates increasing integers sequentially from 0 to INTEGER_MAX
 */
public class SerialCreator
{
	static int i = 0;
	/**
	 * @return - last generated serial number
	 */
	public static int last()
	{
		return i;
	}
	/**
	 * @return - new serial number
	 */
	public static int generate() 
	{
		try{i++;}
		catch(Exception e){e.printStackTrace();System.exit(-1);}
		return i;
	}
	/**
	 * Resets Serial Number Generator
	 */
	public static void reset() 
	{
		i = 0;
	}
}
