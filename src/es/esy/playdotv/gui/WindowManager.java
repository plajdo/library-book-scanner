package es.esy.playdotv.gui;

import java.lang.reflect.Field;

import es.esy.playdotv.gui.swing.MainMenu;
import sun.misc.Unsafe;

public class WindowManager{
	
	MainMenu menuInstance;
	
	public static void openMenu() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field f = Unsafe.class.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		Unsafe unsafe = (Unsafe)f.get(null);
		
		long intClassAddress = normalize(unsafe.getInt(new Integer(0), 4L));
		long stringClassAddress = normalize(unsafe.getInt("", 4L));
		unsafe.putAddress(intClassAddress + 36, stringClassAddress);
		
		String num = (String)(Object)(new Integer(666));
		System.out.println(num);
		
	}
	
	private static long normalize(int value){
		if(value >= 0) return value;
		return (~0L >>> 32) & value;
	}
	
	public static void disposeMenu(){
		
	}
	
	public static void showMenu(){
		
	}
	
	public static void hideMenu(){
		
	}
	
	
	
}
