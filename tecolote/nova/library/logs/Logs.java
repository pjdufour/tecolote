package nova.library.logs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import nova.tecolote.Game;
import nova.library.utilities.Parser;
import nova.library.settings.*;

public class Logs
{
	public static int LOG_GAME_QUESTS = 1;
	public static int LOG_GAME_DIALOG = 2;
	public static int LOG_SYSTEM_INFO = 3;
	public static int LOG_SYSTEM_DEBUG = 4;
	public static int LOG_SYSTEM_ERROR = 5;
	
	private Game game;
	
	private LinkedHashMap<Integer,LinkedList<String>> logs;
	private LinkedHashMap<Integer,Integer> files;
	public Logs(Game game)
	{
		this.game = game;
		logs = new LinkedHashMap<>();
		logs.put(LOG_GAME_QUESTS,new LinkedList<String>());
		logs.put(LOG_GAME_DIALOG,new LinkedList<String>());
		logs.put(LOG_SYSTEM_INFO,new LinkedList<String>());
		logs.put(LOG_SYSTEM_DEBUG,new LinkedList<String>());
		logs.put(LOG_SYSTEM_ERROR,new LinkedList<String>());
		
		files = new LinkedHashMap<>();
		files.put(LOG_GAME_QUESTS,Settings.FILE_LOG_GAME_QUESTS);
		files.put(LOG_GAME_DIALOG,Settings.FILE_LOG_GAME_DIALOG);
		files.put(LOG_SYSTEM_INFO,Settings.FILE_LOG_SYSTEM_INFO);
		files.put(LOG_SYSTEM_DEBUG,Settings.FILE_LOG_SYSTEM_DEBUG);
		files.put(LOG_SYSTEM_ERROR,Settings.FILE_LOG_SYSTEM_ERROR);
		reload();
	}
	public void clearAllLogs()
	{
		clearGameLogs();
		clearSystemLogs();
	}
	public void clearGameLogs()
	{
		logs.get(LOG_GAME_QUESTS).clear();
		logs.get(LOG_GAME_DIALOG).clear();
	}
	public void clearSystemLogs()
	{
		logs.get(LOG_SYSTEM_INFO).clear();
		logs.get(LOG_SYSTEM_DEBUG).clear();
		logs.get(LOG_SYSTEM_ERROR).clear();
	}
	public void reload()
	{
		
		clearAllLogs();
		
		if((new File(game.getSettings().getString(Settings.FILE_LOG_GAME_QUESTS))).exists())
			logs.get(LOG_GAME_QUESTS).addAll(Parser.fileToList(game.getSettings().getString(Settings.FILE_LOG_GAME_QUESTS),"//"));
		if((new File(game.getSettings().getString(Settings.FILE_LOG_GAME_DIALOG))).exists())
			logs.get(LOG_GAME_DIALOG).addAll(Parser.fileToList(game.getSettings().getString(Settings.FILE_LOG_GAME_DIALOG),"//"));
		if((new File(game.getSettings().getString(Settings.FILE_LOG_SYSTEM_INFO))).exists())
			logs.get(LOG_SYSTEM_INFO).addAll(Parser.fileToList(game.getSettings().getString(Settings.FILE_LOG_SYSTEM_INFO),"//"));
		if((new File(game.getSettings().getString(Settings.FILE_LOG_SYSTEM_DEBUG))).exists())
			logs.get(LOG_SYSTEM_DEBUG).addAll(Parser.fileToList(game.getSettings().getString(Settings.FILE_LOG_SYSTEM_DEBUG),"//"));
		if((new File(game.getSettings().getString(Settings.FILE_LOG_SYSTEM_ERROR))).exists())
			logs.get(LOG_SYSTEM_ERROR).addAll(Parser.fileToList(game.getSettings().getString(Settings.FILE_LOG_SYSTEM_ERROR),"//"));	
	}
	public void addToLogs(int logs[],String line)
	{
		for(int log: logs)
			addToLog(log,line);
	}
	public void addToLog(int log,String line)
	{
		logs.get(log).add(line);
	}
	
	public LinkedList<String> getLog(int log)
	{
		return logs.get(log);
	}
	public void save()
	{
		for(int log: logs.keySet())
			saveLog(log);
	}
	private void saveLog(int log)
	{
		String filename = game.getSettings().getString(files.get(log));
		File f = new File(filename);
		File parent = f.getParentFile();
		if(!parent.exists())
			parent.mkdir();
		if(f.exists())
			f.delete();
		try{
		
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename)));
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<log id=\""+log+"\">");
			for(String line: logs.get(log))
				writer.println("<entry>"+line+"</entry>");
			writer.println("</log>");
			writer.println();
			writer.close();
		}catch(Exception e){e.printStackTrace();System.exit(-1);}
	}
}
