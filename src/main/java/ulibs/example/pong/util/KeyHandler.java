package main.java.ulibs.example.pong.util;

import main.java.ulibs.engine.input.EnumKeyInput;
import main.java.ulibs.engine.input.IInputHandler;

public class KeyHandler implements IInputHandler<EnumKeyInput> {
	private static final boolean[] LEFT_INPUT = new boolean[] { false, false }, RIGHT_INPUT = new boolean[] { false, false };
	
	@Override
	public void onPress(EnumKeyInput input) {
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
	public void onRelease(EnumKeyInput input) {
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
