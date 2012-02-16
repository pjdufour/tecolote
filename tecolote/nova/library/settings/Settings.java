package nova.library.settings;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nova.game.Tecolote;
import nova.library.logs.Logs;
import nova.library.xml.XMLElementList;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Settings
{
	public static final int DISPLAY_REAL_WORLD_TIME = 1;
	public static final int DISPLAY_IN_GAME_TIME = 2;
	public static final int DISPLAY_PLAY_TIME = 3;
	public static final int DISPLAY_MAP_NAME = 4;
	public static final int DISPLAY_WORLD_NAME = 5;
	public static final int DISPLAY_REGION_NAME = 6;
	public static final int DISPLAY_LOCATION = 7;
	public static final int DISPLAY_ORIENTATION = 8;
	
	public static final int FULLSCREEN = 9;
	public static final int RESOLUTION = 10;
	public static final int DISPLAY_GRID = 11;
	public static final int GRID_COLOR = 12;
	public static final int GRID_WIDTH = 13;
	
	public static final int UI_OPACITY = 14;
	public static final int UI_COLOR_TOOL_BACKGROUND = 15;
	public static final int UI_COLOR_TOOL_SELECTED = 16;
	public static final int UI_COLOR_TOOL_HOVER = 17;
	
	public static final int SOUND = 100;
	public static final int SFXVOLUME = 101;
	public static final int AMBIENTVOLUME = 102;
	public static final int MUSIC = 103;
	public static final int MUSICVOLUME = 104;
	
	public static final int FILE_MAPS = 200;
	public static final int FILE_TEXTURES = 201;
	public static final int FILE_PROTO = 202;
	public static final int FILE_MODELS = 203;
	public static final int DEBUG = 204;
	public static final int CACHE_TEXTURES = 205;
	public static final int FILE_LOG_GAME_QUESTS = 206;
	public static final int FILE_LOG_GAME_DIALOG = 207;
	public static final int FILE_LOG_SYSTEM_INFO = 208;
	public static final int FILE_LOG_SYSTEM_DEBUG = 209;
	public static final int FILE_LOG_SYSTEM_ERROR = 210;
	public static final int FILE_ICONS = 211;
	public static final int FILE_LANDCOVER = 212;
	
	public static final int KEY_BINDING_FORWARD = 300;
	public static final int KEY_BINDING_BACKWARD = 301;
	public static final int KEY_BINDING_TURN_LEFT = 302;
	public static final int KEY_BINDING_TURN_RIGHT = 303;
	public static final int KEY_BINDING_STRAFE_LEFT = 304;
	public static final int KEY_BINDING_STRAFE_RIGHT = 305;
	public static final int KEY_BINDING_RUN = 306;
	public static final int KEY_BINDING_JUMP = 307;
	
	public static final int KEY_BINDING_MENU = 308;
	
	public static final int KEY_BINDING_MOUSELOOK = 309;
	
	public static final int KEY_BINDING_PAN_UP = 320;
	public static final int KEY_BINDING_PAN_DOWN = 321;
	public static final int KEY_BINDING_PAN_LEFT = 322;
	public static final int KEY_BINDING_PAN_RIGHT = 323;
	
	////////////////////////////////
	private Tecolote tecolote;
	private String filename;
	private LinkedHashMap<Integer,Boolean> settings_default_boolean;
	private LinkedHashMap<Integer,Boolean> settings_boolean;
	
	private LinkedHashMap<Integer,String> settings_default_string;
	private LinkedHashMap<Integer,String> settings_string;
	
	private LinkedHashMap<Integer,Integer> settings_default_int;
	private LinkedHashMap<Integer,Integer> settings_int;
	
	private LinkedHashMap<Integer,Color> settings_default_color;
	private LinkedHashMap<Integer,Color> settings_color;
		
	private KeyBindings keyBindings; 
	
	public Settings(Tecolote tecolote,String filename)
	{
		//...Currently Working on Loading Setting From XML instead of hardcoding it
		//Should default setting values be separated from actual values?
		//For now keep in same file as their are no users yet
		this.tecolote = tecolote;
		this.filename = filename;
		reload(filename);
	}
	public KeyBindings getKeyBindings()
	{
		return keyBindings;
	}
	public void reload(String filename)
	{
		try{
			
		settings_boolean = new LinkedHashMap<Integer,Boolean>();
		settings_default_boolean = new LinkedHashMap<Integer,Boolean>();
		
		settings_string = new LinkedHashMap<Integer,String>();
		settings_default_string = new LinkedHashMap<Integer,String>();
		
		settings_int = new LinkedHashMap<Integer,Integer>();
		settings_default_int = new LinkedHashMap<Integer,Integer>();
		
		settings_color = new LinkedHashMap<Integer,Color>();
		settings_default_color = new LinkedHashMap<Integer,Color>();
		
		keyBindings = new KeyBindings();
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new FileInputStream(new File(filename)));
		for(Node xmlSetting: XMLElementList.createListWithTagSelector("setting",doc.getElementsByTagName("settings").item(0)))
		{
			NamedNodeMap attributes = xmlSetting.getAttributes();
			int id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
			String name = attributes.getNamedItem("name").getNodeValue();
			String type = attributes.getNamedItem("type").getNodeValue();
			if(type.equalsIgnoreCase("boolean"))
			{
				boolean defaultValue = Boolean.parseBoolean(attributes.getNamedItem("default").getNodeValue());
				boolean value = Boolean.parseBoolean(xmlSetting.getTextContent());
				settings_default_boolean.put(id,defaultValue);
				settings_boolean.put(id,value);
			}
			else if(type.equalsIgnoreCase("string"))
			{
				String defaultValue = attributes.getNamedItem("default").getNodeValue();
				String value = xmlSetting.getTextContent();
				settings_default_string.put(id,defaultValue);
				settings_string.put(id,value);
			}
			else if(type.equalsIgnoreCase("int"))
			{
				int defaultValue = Integer.parseInt(attributes.getNamedItem("default").getNodeValue());
				int value = Integer.parseInt(xmlSetting.getTextContent());
				settings_default_int.put(id,defaultValue);
				settings_int.put(id,value);
			}
			else if(type.equalsIgnoreCase("color"))
			{
				String defaultValue[] = attributes.getNamedItem("default").getNodeValue().trim().split(",");
				String value[] = xmlSetting.getTextContent().trim().split(",");
				settings_default_color.put(id,new Color(Integer.parseInt(defaultValue[0]),Integer.parseInt(defaultValue[1]),Integer.parseInt(defaultValue[2])));
				settings_color.put(id,new Color(Integer.parseInt(value[0]),Integer.parseInt(value[1]),Integer.parseInt(value[2])));
			}
			else if(type.equalsIgnoreCase("key"))
			{
				if(id==KEY_BINDING_FORWARD)
				{
					keyBindings.forward_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.forward_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_BACKWARD)
				{
					keyBindings.backward_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.backward_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_TURN_LEFT)
				{
					keyBindings.turn_left_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.turn_left_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_TURN_RIGHT)
				{
					keyBindings.turn_right_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.turn_right_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_STRAFE_LEFT)
				{
					keyBindings.strafe_left_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.strafe_left_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_STRAFE_RIGHT)
				{
					keyBindings.strafe_right_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.strafe_right_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_RUN)
				{
					keyBindings.run_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.run_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_JUMP)
				{
					keyBindings.jump_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.jump_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_MENU)
				{
					keyBindings.ui_menu_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.ui_menu = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_PAN_UP)
				{
					keyBindings.pan_up_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.pan_up_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_PAN_DOWN)
				{
					keyBindings.pan_down_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.pan_down_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_PAN_LEFT)
				{
					keyBindings.pan_left_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.pan_left_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_PAN_RIGHT)
				{
					keyBindings.pan_right_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.pan_right_1 = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
				else if(id==KEY_BINDING_MOUSELOOK)
				{
					keyBindings.toggle_mouselook_default = keyBindings.getKeyCode(attributes.getNamedItem("default").getNodeValue());
					keyBindings.toggle_mouselook = keyBindings.getKeyCode(xmlSetting.getTextContent());
				}
			}
		}
		
		}catch(Exception e){System.err.println("Could not load settings");e.printStackTrace();System.exit(-1);}
		/*
		
		settings_boolean = new LinkedHashMap<Integer,Boolean>();
		settings_default_boolean = new LinkedHashMap<Integer,Boolean>();
		
		settings_string = new LinkedHashMap<Integer,String>();
		settings_default_string = new LinkedHashMap<Integer,String>();
		//Load Defaults
		settings_default_boolean.put(DISPLAY_REAL_WORLD_TIME,false);
		settings_default_boolean.put(DISPLAY_IN_GAME_TIME,false);
		settings_default_boolean.put(DISPLAY_PLAY_TIME,true);
		settings_default_boolean.put(DISPLAY_MAP_NAME,true);
		settings_default_boolean.put(DISPLAY_WORLD_NAME,true);
		settings_default_boolean.put(DISPLAY_REGION_NAME,true);
		settings_default_boolean.put(FULLSCREEN,true);
		
		settings_default_string.put(RESOLUTION,"1280 x 800");
		//Load Values
		settings_boolean.put(DISPLAY_REAL_WORLD_TIME,false);
		settings_boolean.put(DISPLAY_IN_GAME_TIME,false);
		settings_boolean.put(DISPLAY_PLAY_TIME,true);
		settings_boolean.put(DISPLAY_MAP_NAME,true);
		settings_boolean.put(DISPLAY_WORLD_NAME,true);
		settings_boolean.put(DISPLAY_REGION_NAME,true);
		settings_boolean.put(FULLSCREEN,true);
		
		settings_string.put(RESOLUTION,"1280 x 800");*/
	}
	public void save()
	{
		Color dc;
		Color c;
		File f = new File(filename);
		
		if(f.exists())
			f.delete();
		try{
		
			PrintWriter writer = new PrintWriter(new FileOutputStream(new File(filename)));
			writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.println("<settings>");
			writer.println("\t<setting id=\""+DISPLAY_REAL_WORLD_TIME+"\" name=\"DISPLAY_REAL_WORLD_TIME\" type=\"boolean\" default=\""+settings_default_boolean.get(DISPLAY_REAL_WORLD_TIME)+"\">"+settings_boolean.get(DISPLAY_REAL_WORLD_TIME)+"</setting>");
			writer.println("\t<setting id=\""+DISPLAY_IN_GAME_TIME+"\" name=\"DISPLAY_IN_GAME_TIME\" type=\"boolean\" default=\""+settings_default_boolean.get(DISPLAY_IN_GAME_TIME)+"\">"+settings_boolean.get(DISPLAY_IN_GAME_TIME)+"</setting>");
			writer.println("\t<setting id=\""+DISPLAY_PLAY_TIME+"\" name=\"DISPLAY_PLAY_TIME\" type=\"boolean\" default=\""+settings_default_boolean.get(DISPLAY_PLAY_TIME)+"\">"+settings_boolean.get(DISPLAY_PLAY_TIME)+"</setting>");
			writer.println("\t<setting id=\""+DISPLAY_MAP_NAME+"\" name=\"DISPLAY_MAP_NAME\" type=\"boolean\" default=\""+settings_default_boolean.get(DISPLAY_MAP_NAME)+"\">"+settings_boolean.get(DISPLAY_MAP_NAME)+"</setting>");
			writer.println("\t<setting id=\""+DISPLAY_WORLD_NAME+"\" name=\"DISPLAY_WORLD_NAME\" type=\"boolean\" default=\""+settings_default_boolean.get(DISPLAY_WORLD_NAME)+"\">"+settings_boolean.get(DISPLAY_WORLD_NAME)+"</setting>");
			writer.println("\t<setting id=\""+DISPLAY_REGION_NAME+"\" name=\"DISPLAY_REGION_NAME\" type=\"boolean\" default=\""+settings_default_boolean.get(DISPLAY_REGION_NAME)+"\">"+settings_boolean.get(DISPLAY_REGION_NAME)+"</setting>");
			writer.println("\t<setting id=\""+DISPLAY_LOCATION+"\" name=\"DISPLAY_LOCATION\" type=\"boolean\" default=\""+settings_default_boolean.get(DISPLAY_LOCATION)+"\">"+settings_boolean.get(DISPLAY_LOCATION)+"</setting>");
			writer.println("\t<setting id=\""+DISPLAY_ORIENTATION+"\" name=\"DISPLAY_ORIENTATION\" type=\"boolean\" default=\""+settings_default_boolean.get(DISPLAY_ORIENTATION)+"\">"+settings_boolean.get(DISPLAY_ORIENTATION)+"</setting>");
			writer.println("\t<setting id=\""+FULLSCREEN+"\" name=\"FULLSCREEN\" type=\"boolean\" default=\""+settings_default_boolean.get(FULLSCREEN)+"\">"+settings_boolean.get(FULLSCREEN)+"</setting>");
			writer.println("\t<setting id=\""+RESOLUTION+"\" name=\"RESOLUTION\" type=\"string\" default=\""+settings_default_string.get(RESOLUTION)+"\">"+settings_string.get(RESOLUTION)+"</setting>");
			writer.println("\t<setting id=\""+DISPLAY_GRID+"\" name=\"DISPLAY_GRID\" type=\"boolean\" default=\""+settings_default_boolean.get(DISPLAY_GRID)+"\">"+settings_boolean.get(DISPLAY_GRID)+"</setting>");
			dc = settings_default_color.get(GRID_COLOR);
			c = settings_color.get(GRID_COLOR);
			writer.println("\t<setting id=\""+GRID_COLOR+"\" name=\"GRID_COLOR\" type=\"color\" default=\""+dc.getRed()+","+dc.getGreen()+","+dc.getBlue()+"\">"+c.getRed()+","+c.getGreen()+","+c.getBlue()+"</setting>");
			writer.println("\t<setting id=\""+GRID_WIDTH+"\" name=\"GRID_WIDTH\" type=\"string\" default=\""+settings_default_string.get(GRID_WIDTH)+"\">"+settings_string.get(GRID_WIDTH)+"</setting>");
			
			//////////////////////////////////////////////////////////////////////////////////////////
			writer.println("\t<setting id=\""+UI_OPACITY+"\" name=\"UI_OPACITY\" type=\"int\" default=\""+settings_default_int.get(UI_OPACITY)+"\">"+settings_int.get(UI_OPACITY)+"</setting>");
			dc = settings_default_color.get(UI_COLOR_TOOL_BACKGROUND);
			c = settings_color.get(UI_COLOR_TOOL_BACKGROUND);
			writer.println("\t<setting id=\""+UI_COLOR_TOOL_BACKGROUND+"\" name=\"UI_COLOR_TOOL_BACKGROUND\" type=\"color\" default=\""+dc.getRed()+","+dc.getGreen()+","+dc.getBlue()+"\">"+c.getRed()+","+c.getGreen()+","+c.getBlue()+"</setting>");
			dc = settings_default_color.get(UI_COLOR_TOOL_SELECTED);
			c = settings_color.get(UI_COLOR_TOOL_SELECTED);
			writer.println("\t<setting id=\""+UI_COLOR_TOOL_SELECTED+"\" name=\"UI_COLOR_TOOL_SELECTED\" type=\"color\" default=\""+dc.getRed()+","+dc.getGreen()+","+dc.getBlue()+"\">"+c.getRed()+","+c.getGreen()+","+c.getBlue()+"</setting>");
			dc = settings_default_color.get(UI_COLOR_TOOL_HOVER);
			c = settings_color.get(UI_COLOR_TOOL_HOVER);
			writer.println("\t<setting id=\""+UI_COLOR_TOOL_HOVER+"\" name=\"UI_COLOR_TOOL_HOVER\" type=\"color\" default=\""+dc.getRed()+","+dc.getGreen()+","+dc.getBlue()+"\">"+c.getRed()+","+c.getGreen()+","+c.getBlue()+"</setting>");
			//////////////////////////////////////////////////////////////////////////////////////////
			
			writer.println("\t<setting id=\""+SOUND+"\" name=\"SOUND\" type=\"boolean\" default=\""+settings_default_boolean.get(SOUND)+"\">"+settings_boolean.get(SOUND)+"</setting>");
			writer.println("\t<setting id=\""+SFXVOLUME+"\" name=\"SFXVOLUME\" type=\"int\" default=\""+settings_default_int.get(SFXVOLUME)+"\">"+settings_int.get(SFXVOLUME)+"</setting>");
			writer.println("\t<setting id=\""+AMBIENTVOLUME+"\" name=\"AMBIENTVOLUME\" type=\"int\" default=\""+settings_default_int.get(AMBIENTVOLUME)+"\">"+settings_int.get(AMBIENTVOLUME)+"</setting>");
			writer.println("\t<setting id=\""+MUSIC+"\" name=\"MUSIC\" type=\"boolean\" default=\""+settings_default_boolean.get(MUSIC)+"\">"+settings_boolean.get(MUSIC)+"</setting>");
			writer.println("\t<setting id=\""+MUSICVOLUME+"\" name=\"MUSICVOLUME\" type=\"int\" default=\""+settings_default_int.get(MUSICVOLUME)+"\">"+settings_int.get(MUSICVOLUME)+"</setting>");
			
			writer.println("\t<setting id=\""+FILE_MAPS+"\" name=\"FILE_MAPS\" type=\"string\" default=\""+settings_default_string.get(FILE_MAPS).replace("\\","/")+"\">"+settings_string.get(FILE_MAPS).replace("\\","/")+"</setting>");
			writer.println("\t<setting id=\""+FILE_TEXTURES+"\" name=\"FILE_TEXTURES\" type=\"string\" default=\""+settings_default_string.get(FILE_TEXTURES).replace("\\","/")+"\">"+settings_string.get(FILE_TEXTURES).replace("\\","/")+"</setting>");
			writer.println("\t<setting id=\""+FILE_PROTO+"\" name=\"FILE_PROTO\" type=\"string\" default=\""+settings_default_string.get(FILE_PROTO).replace("\\","/")+"\">"+settings_string.get(FILE_PROTO).replace("\\","/")+"</setting>");
			writer.println("\t<setting id=\""+FILE_MODELS+"\" name=\"FILE_MODELS\" type=\"string\" default=\""+settings_default_string.get(FILE_MODELS).replace("\\","/")+"\">"+settings_string.get(FILE_MODELS).replace("\\","/")+"</setting>");
			writer.println("\t<setting id=\""+DEBUG+"\" name=\"DEBUG\" type=\"string\" default=\""+settings_default_string.get(DEBUG)+"\">"+settings_string.get(DEBUG)+"</setting>");
			writer.println("\t<setting id=\""+CACHE_TEXTURES+"\" name=\"CACHE_TEXTURES\" type=\"string\" default=\""+settings_default_string.get(CACHE_TEXTURES)+"\">"+settings_string.get(CACHE_TEXTURES)+"</setting>");
			writer.println("\t<setting id=\""+FILE_LOG_GAME_QUESTS+"\" name=\"FILE_LOG_GAME_QUESTS\" type=\"string\" default=\""+settings_default_string.get(FILE_LOG_GAME_QUESTS)+"\">"+settings_string.get(FILE_LOG_GAME_QUESTS)+"</setting>");
			writer.println("\t<setting id=\""+FILE_LOG_GAME_DIALOG+"\" name=\"FILE_LOG_GAME_DIALOG\" type=\"string\" default=\""+settings_default_string.get(FILE_LOG_GAME_DIALOG)+"\">"+settings_string.get(FILE_LOG_GAME_DIALOG)+"</setting>");
			writer.println("\t<setting id=\""+FILE_LOG_SYSTEM_INFO+"\" name=\"FILE_LOG_SYSTEM_INFO\" type=\"string\" default=\""+settings_default_string.get(FILE_LOG_SYSTEM_INFO)+"\">"+settings_string.get(FILE_LOG_SYSTEM_INFO)+"</setting>");
			writer.println("\t<setting id=\""+FILE_LOG_SYSTEM_DEBUG+"\" name=\"FILE_LOG_SYSTEM_DEBUG\" type=\"string\" default=\""+settings_default_string.get(FILE_LOG_SYSTEM_DEBUG)+"\">"+settings_string.get(FILE_LOG_SYSTEM_DEBUG)+"</setting>");
			writer.println("\t<setting id=\""+FILE_LOG_SYSTEM_ERROR+"\" name=\"FILE_LOG_SYSTEM_ERROR\" type=\"string\" default=\""+settings_default_string.get(FILE_LOG_SYSTEM_ERROR)+"\">"+settings_string.get(FILE_LOG_SYSTEM_ERROR)+"</setting>");
			writer.println("\t<setting id=\""+FILE_ICONS+"\" name=\"FILE_ICONS\" type=\"string\" default=\""+settings_default_string.get(FILE_ICONS)+"\">"+settings_string.get(FILE_ICONS)+"</setting>");
			writer.println("\t<setting id=\""+FILE_LANDCOVER+"\" name=\"FILE_LANDCOVER\" type=\"string\" default=\""+settings_default_string.get(FILE_LANDCOVER)+"\">"+settings_string.get(FILE_LANDCOVER)+"</setting>");
			
			writer.println("\t<setting id=\""+KEY_BINDING_FORWARD+"\" name=\"KEY_BINDING_FORWARD\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.forward_default)+"\">"+keyBindings.getkeyText(keyBindings.forward_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_BACKWARD+"\" name=\"KEY_BINDING_BACKWARD\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.backward_default)+"\">"+keyBindings.getkeyText(keyBindings.backward_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_TURN_LEFT+"\" name=\"KEY_BINDING_TURN_LEFT\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.turn_left_default)+"\">"+keyBindings.getkeyText(keyBindings.turn_left_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_TURN_RIGHT+"\" name=\"KEY_BINDING_TURN_RIGHT\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.turn_right_default)+"\">"+keyBindings.getkeyText(keyBindings.turn_right_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_STRAFE_LEFT+"\" name=\"KEY_BINDING_STRAFE_LEFT\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.strafe_left_default)+"\">"+keyBindings.getkeyText(keyBindings.strafe_left_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_STRAFE_RIGHT+"\" name=\"KEY_BINDING_STRAFE_RIGHT\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.strafe_right_default)+"\">"+keyBindings.getkeyText(keyBindings.strafe_right_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_RUN+"\" name=\"KEY_BINDING_RUN\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.run_default)+"\">"+keyBindings.getkeyText(keyBindings.run_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_JUMP+"\" name=\"KEY_BINDING_JUMP\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.jump_default)+"\">"+keyBindings.getkeyText(keyBindings.jump_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_MENU+"\" name=\"KEY_BINDING_MENU\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.ui_menu_default)+"\">"+keyBindings.getkeyText(keyBindings.ui_menu)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_MOUSELOOK+"\" name=\"KEY_BINDING_MOUSELOOK\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.toggle_mouselook_default)+"\">"+keyBindings.getkeyText(keyBindings.toggle_mouselook)+"</setting>");
			
			writer.println("\t<setting id=\""+KEY_BINDING_PAN_UP+"\" name=\"KEY_BINDING_PAN_UP\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.pan_up_default)+"\">"+keyBindings.getkeyText(keyBindings.pan_up_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_PAN_DOWN+"\" name=\"KEY_BINDING_PAN_DOWN\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.pan_down_default)+"\">"+keyBindings.getkeyText(keyBindings.pan_down_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_PAN_LEFT+"\" name=\"KEY_BINDING_PAN_LEFT\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.pan_left_default)+"\">"+keyBindings.getkeyText(keyBindings.pan_left_1)+"</setting>");
			writer.println("\t<setting id=\""+KEY_BINDING_PAN_RIGHT+"\" name=\"KEY_BINDING_PAN_RIGHT\" type=\"key\" default=\""+keyBindings.getkeyText(keyBindings.pan_right_default)+"\">"+keyBindings.getkeyText(keyBindings.pan_right_1)+"</setting>");
						
			writer.println("</settings>");
			writer.println();
			writer.close();
		
		}catch(Exception e){e.printStackTrace();System.exit(-1);}		
	}
	public void set(int setting,boolean value)
	{
		settings_boolean.put(setting,value);
	}
	public void set(int setting,int value)
	{
		settings_int.put(setting,value);
	}
	public void set(int setting,String value)
	{
		settings_string.put(setting,value);
	}
	public void set(int setting,Color color)
	{
		settings_color.put(setting,color);
	}
	public boolean getBoolean(int setting)
	{
		return settings_boolean.get(setting);
	}
	public boolean getDefaultBoolean(int setting)
	{
		return settings_default_boolean.get(setting);
	}
	public String getString(int setting)
	{
		return settings_string.get(setting);
	}
	public String getDefaultString(int setting)
	{
		return settings_default_string.get(setting);
	}
	public int getInt(int setting)
	{
		return settings_int.get(setting);
	}
	public int getDefaultInt(int setting)
	{
		return settings_default_int.get(setting);
	}
	public Color getDefaultColor(int setting)
	{
		return settings_default_color.get(setting);
	}
	public Color getColor(int setting)
	{
		return settings_color.get(setting);
	}
	public void printSettingsToConsole()
	{
		System.out.println();
		for(int id: settings_int.keySet())
			System.out.println(id+": "+getInt(id));
		for(int id: settings_boolean.keySet())
			System.out.println(id+": "+getBoolean(id));
		for(int id: settings_string.keySet())
			System.out.println(id+": "+getString(id));
		for(int id: settings_color.keySet())
		{
			Color c = getColor(id);
			System.out.println(id+": "+(c.getRed()+", "+c.getGreen()+", "+c.getBlue()));
		}
	}
	public void printSettingsToLog()
	{
		Logs logs = tecolote.getLogs();
		for(int id: settings_int.keySet())
			logs.addToLog(Logs.LOG_SYSTEM_INFO,id+": "+getInt(id));
		for(int id: settings_boolean.keySet())
		logs.addToLog(Logs.LOG_SYSTEM_INFO,id+": "+getBoolean(id));
		for(int id: settings_string.keySet())
			logs.addToLog(Logs.LOG_SYSTEM_INFO,id+": "+getString(id));
		for(int id: settings_color.keySet())
		{
			Color c = getColor(id);
			System.out.println();
			logs.addToLog(Logs.LOG_SYSTEM_INFO,id+": "+(c.getRed()+", "+c.getGreen()+", "+c.getBlue()));
		}
	}
}

