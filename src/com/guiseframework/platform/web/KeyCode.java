package com.guiseframework.platform.web;

import java.util.*;
import static java.util.Collections.*;

import static com.garretwilson.lang.ObjectUtilities.*;

import com.guiseframework.event.Key;

/**A key on a keyboard as reported by a browser's Event.keyCode.
@author Garret Wilson
*/
public enum KeyCode
{
	ALT(18, Key.ALT_LEFT),
	BACKSPACE(8, Key.BACKSPACE),
	CONTROL(17, Key.CONTROL_LEFT),
	DELETE(46, Key.DELETE),
	DOWN(40, Key.DOWN),
	END(35, Key.END),
	ENTER(13, Key.ENTER),
	ESCAPE(27, Key.ESCAPE),
	F1(112, Key.F1),
	F2(113, Key.F2),
	F3(114, Key.F3),
	F4(115, Key.F4),
	F5(116, Key.F5),
	F6(117, Key.F6),
	F7(118, Key.F7),
	F8(119, Key.F8),
	F9(120, Key.F9),
	F10(121, Key.F10),
	F11(122, Key.F11),
	F12(123, Key.F12),
	HOME(36, Key.HOME),
	LEFT(37, Key.LEFT),
	PAGE_UP(33, Key.PAGE_UP),
	PAGE_DOWN(34, Key.PAGE_DOWN),
	RIGHT(39, Key.RIGHT),
	SHIFT(16, Key.SHIFT_LEFT),	
	TAB(9, Key.TAB),	
	UP(38, Key.UP);

	/**The code reported by the browser.*/
	private final int code; 

		/**@return The code reported by the browser.*/
		public int getCode() {return code;} 

	/**The key this key code represents.*/
	private final Key key; 

		/**@return The key this key code represents.*/
		public Key getKey() {return key;} 

	/**Code constructor.
	@param code The code reported by the browser.
	@param key The key this key code represents.
	@exception NullPointerException if the given key is <code>null</code>.
	*/
	private KeyCode(final int code, final Key key)
	{
		this.code=code;
		this.key=checkInstance(key, "Key cannot be null.");
	}

	/**The read-only map of key codes keyed to codes.*/
	private static Map<Integer, KeyCode> keyCodeMap=null;

	static
	{
		final Map<Integer, KeyCode> tempMap=new HashMap<Integer, KeyCode>();	//create a new hash map
		for(final KeyCode keyCode:KeyCode.values())	//for each key code
		{
			tempMap.put(Integer.valueOf(keyCode.getCode()), keyCode);	//store the key code in the map keyed to its code
		}
		keyCodeMap=unmodifiableMap(tempMap);	//save a read-only copy of the map
	}

	/**Retrieves a key code corresponding to the given code.
	@param code The code for which a key code should be returned.
	@return The key code representing the given code.
	@exception IllegalArgumentException if the given code does not match one of the known key codes.
	*/
	public static KeyCode valueOf(final int code)
	{
		final Integer codeInteger=Integer.valueOf(code);	//create an integer object from the code to serve as the map key
		final KeyCode keyCode=keyCodeMap.get(codeInteger);	//look up the corresponding key code from the map
		if(keyCode==null)	//if there is no such key code in the map
		{
			throw new IllegalArgumentException("The code "+code+" does not represent a known key code.");
		}
		return keyCode;	//return the key code we found
	}
	
}