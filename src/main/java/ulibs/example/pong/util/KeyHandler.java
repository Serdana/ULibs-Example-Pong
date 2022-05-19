package main.java.ulibs.example.pong.util;

import main.java.ulibs.engine.client.input.EnumKeyInput;
import main.java.ulibs.engine.client.input.IInputHandler;

public class KeyHandler implements IInputHandler<EnumKeyInput> {
	private static final boolean[] LEFT_INPUT = new boolean[] { false, false }, RIGHT_INPUT = new boolean[] { false, false };
	
	@Override
	public void onPress(EnumKeyInput input) { // Runs whenever a key is pressed. 'input' will be the key that was pressed
		// Stores the inputs we care about into an array
		if (input == EnumKeyInput.KEY_W) {
			LEFT_INPUT[0] = true;
		} else if (input == EnumKeyInput.KEY_S) {
			LEFT_INPUT[1] = true;
		} else if (input == EnumKeyInput.KEY_UP) {
			RIGHT_INPUT[0] = true;
		} else if (input == EnumKeyInput.KEY_DOWN) {
			RIGHT_INPUT[1] = true;
		}
	}
	
	@Override
	public void onRelease(EnumKeyInput input) { // Runs whenever a key is released. 'input' will be the key that was released
		// Stores the inputs we care about into an array
		if (input == EnumKeyInput.KEY_W) {
			LEFT_INPUT[0] = false;
		} else if (input == EnumKeyInput.KEY_S) {
			LEFT_INPUT[1] = false;
		} else if (input == EnumKeyInput.KEY_UP) {
			RIGHT_INPUT[0] = false;
		} else if (input == EnumKeyInput.KEY_DOWN) {
			RIGHT_INPUT[1] = false;
		}
	}
	
	@Override
	public void onRepeat(EnumKeyInput input) {
		
	}
	
	public static boolean[] getLeftInput() {
		return LEFT_INPUT;
	}
	
	public static boolean[] getRightInput() {
		return RIGHT_INPUT;
	}
}
