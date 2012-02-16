package nova.tecolote.examples;

import nova.tecolote.Game;
import nova.library.settings.*;
import nova.library.logs.*;

public class Example
	implements Game
{
	private Settings settings;
	private Logs logs;
	
	public Example(String []args)
	{
		try
		{
			settings = new Settings(this,"nova/tecolote/examples/settings.xml");
			logs = new Logs(this);
			settings.printSettingsToConsole();
			settings.printSettingsToLog();
			logs.save();
		}catch(Exception e){e.printStackTrace();System.exit(-1);}
	}
	@Override
	public Logs getLogs()
	{
		return logs;
	}
	@Override
	public Settings getSettings()
	{
		return settings;
	}
	public static void main(String []args)
	{
		new Example(args);
	}
}
