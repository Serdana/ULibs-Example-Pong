package main.java.ulibs.example.pong.util;

import main.java.ulibs.engine.input.IInputHandler;
import main.java.ulibs.engine.input.Inputs;
import main.java.ulibs.engine.input.KeyInput;

public class KeyHandler implements IInputHandler<KeyInput> {
	private static final boolean[] LEFT_INPUT = new boolean[] { false, false }, RIGHT_INPUT = new boolean[] { false, false };
	
	@Override
	public void onPress(KeyInput input) {
		if (input.is(Inputs.KEY_W)) {
			LEFT_INPUT[0] = true;
		} else if (input.is(Inputs.KEY_S)) {
			LEFT_INPUT[1] = true;
		} else if (input.is(Inputs.KEY_UP)) {
			RIGHT_INPUT[0] = true;
		} else if (input.is(Inputs.KEY_DOWN)) {
			RIGHT_INPUT[1] = true;
		}
	}
	
	@Override
	public void onRelease(KeyInput input) {
		if (input.is(Inputs.KEY_W)) {
			LEFT_INPUT[0] = false;
		} else if (input.is(Inputs.KEY_S)) {
			LEFT_INPUT[1] = false;
		} else if (input.is(Inputs.KEY_UP)) {
			RIGHT_INPUT[0] = false;
		} else if (input.is(Inputs.KEY_DOWN)) {
			RIGHT_INPUT[1] = false;
		}
	}
	
	@Override
	public void onRepeat(KeyInput input) {
		
	}
	
	public static boolean[] getLeftInput() {
		return LEFT_INPUT;
	}
	
	public static boolean[] getRightInput() {
		return RIGHT_INPUT;
	}
}
