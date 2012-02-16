package nova.library.settings;

import java.awt.event.KeyEvent;

public class KeyBindings
{
	public int forward_default;
	public int forward_1;
	public int forward_2;
	public int backward_default;
	public int backward_1;
	public int backward_2;
	public int pan_up_default;
	public int pan_up_1;
	public int pan_up_2;
	public int pan_down_default;
	public int pan_down_1;
	public int pan_down_2;
	public int strafe_left_default;
	public int strafe_left_1;
	public int strafe_left_2;
	public int strafe_right_default;
	public int strafe_right_1;
	public int strafe_right_2;
	public int turn_left_default;
	public int turn_left_1;
	public int turn_left_2;
	public int turn_right_default;
	public int turn_right_1;
	public int turn_right_2;
	public int pan_left_default;
	public int pan_left_1;
	public int pan_left_2;
	public int pan_right_default;
	public int pan_right_1;
	public int pan_right_2;	
	public int run_default;
	public int run_1;
	public int run_2;
	public int jump_default;
	public int jump_1;
	public int jump_2;
	
	public int toggle_mouselook_default;
	public int toggle_mouselook;
	
	public int ui_menu_default;
	public int ui_menu;
	public int ui_inventory_default;
	public int ui_inventory;
	
	
	public KeyBindings()
	{
		forward_default = -1;
		forward_1 = -1;
		forward_2 = -1;
		backward_default = -1;
		backward_1 = -1;
		backward_2 = -1;
		pan_up_default = -1;
		pan_up_1 = -1;
		pan_up_2 = -1;
		pan_down_default = -1;
		pan_down_1 = -1;
		pan_down_2 = -1;
		strafe_left_default = -1;
		strafe_left_1 = -1;
		strafe_left_2 = -1;
		strafe_right_default = -1;
		strafe_right_1 = -1;
		strafe_right_2 = -1;
		turn_left_default = -1;
		turn_left_1 = -1;
		turn_left_2 = -1;
		turn_right_default = -1;
		turn_right_1 = -1;
		turn_right_2 = -1;
		pan_left_default = -1;
		pan_left_1 = -1;
		pan_left_2 = -1;
		pan_right_default = -1;
		pan_right_1 = -1;
		pan_right_2 = -1;
		run_default = -1;
		run_1 = -1;
		run_2 = -1;
		jump_default = -1;
		jump_1 = -1;
		jump_2 = -1;
		toggle_mouselook = -1;
		ui_menu_default = -1;
		ui_menu = -1;
		ui_inventory_default = -1;
		ui_inventory = -1;
	}
	
	public String getkeyText(int keyCode)
	{
		if(keyCode==KeyEvent.VK_W)
			return "w";
		else if(keyCode==KeyEvent.VK_A)
			return "a";
		else if(keyCode==KeyEvent.VK_S)
			return "s";
		else if(keyCode==KeyEvent.VK_D)
			return "d";
		else if(keyCode==KeyEvent.VK_Q)
			return "q";
		else if(keyCode==KeyEvent.VK_E)
			return "e";
		else if(keyCode==KeyEvent.VK_C)
			return "c";
		else if(keyCode==KeyEvent.VK_UP)
			return "up";
		else if(keyCode==KeyEvent.VK_DOWN)
			return "down";
		else if(keyCode==KeyEvent.VK_LEFT)
			return "left";
		else if(keyCode==KeyEvent.VK_RIGHT)
			return "right";
		else if(keyCode==KeyEvent.VK_SHIFT)
			return "shift";
		else if(keyCode==KeyEvent.VK_ALT)
			return "alt";
		else if(keyCode==KeyEvent.VK_CONTROL)
			return "ctrl";
		else if(keyCode==KeyEvent.VK_SPACE)
			return "space";
		else if(keyCode==KeyEvent.VK_ESCAPE)
			return "escape";
		else
			return "";
	}
	public int getKeyCode(String key)
	{
		if(key.equalsIgnoreCase("w"))
			return KeyEvent.VK_W;
		else if(key.equalsIgnoreCase("a"))
			return KeyEvent.VK_A;
		else if(key.equalsIgnoreCase("s"))
			return KeyEvent.VK_S;
		else if(key.equalsIgnoreCase("d"))
			return KeyEvent.VK_D;
		else if(key.equalsIgnoreCase("q"))
			return KeyEvent.VK_Q;
		else if(key.equalsIgnoreCase("e"))
			return KeyEvent.VK_E;
		else if(key.equalsIgnoreCase("c"))
			return KeyEvent.VK_C;
		else if(key.equalsIgnoreCase("up"))
			return KeyEvent.VK_UP;
		else if(key.equalsIgnoreCase("down"))
			return KeyEvent.VK_DOWN;
		else if(key.equalsIgnoreCase("left"))
			return KeyEvent.VK_LEFT;
		else if(key.equalsIgnoreCase("right"))
			return KeyEvent.VK_RIGHT;
		else if(key.equalsIgnoreCase("shift"))
			return KeyEvent.VK_SHIFT;
		else if(key.equalsIgnoreCase("alt"))
			return KeyEvent.VK_ALT;
		else if(key.equalsIgnoreCase("ctrl"))
			return KeyEvent.VK_CONTROL;
		else if(key.equalsIgnoreCase("space"))
			return KeyEvent.VK_SPACE;
		else if(key.equalsIgnoreCase("escape"))
			return KeyEvent.VK_ESCAPE;
		else
			return -1;
	}
}

