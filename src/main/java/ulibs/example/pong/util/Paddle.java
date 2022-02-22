package main.java.ulibs.example.pong.util;

import main.java.ulibs.common.helpers.MathH;
import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.engine.utils.HitBox;
import main.java.ulibs.engine.utils.ITickable;

public class Paddle implements ITickable {
	public static final float SPEED = 0.1f;
	public static final Vec2f SIZE = new Vec2f(0.5f, 2);
	public final Vec2f pos;
	private final boolean isLeft;
	private boolean isUpDown, isDownDown;
	
	public Paddle(boolean isLeft) { // Sets all the position/size data
		this.isLeft = isLeft;
		this.pos = new Vec2f(isLeft ? 0.2f : 15.8f - SIZE.getX(), 9f / 2f - SIZE.getY() / 2f);
	}
	
	@Override
	public void tick() {
		// Checks if the paddle's input keys are down
		isUpDown = isLeft ? KeyHandler.getLeftInput()[0] : KeyHandler.getRightInput()[0];
		isDownDown = isLeft ? KeyHandler.getLeftInput()[1] : KeyHandler.getRightInput()[1];
		
		if (isUpDown ^ isDownDown) { // Checks if the paddle's inputs are down
			if (isUpDown) {
				pos.setY(MathH.clamp(pos.getY() - SPEED, 0, 9 - SIZE.getY())); // Moves the paddle up
			} else {
				pos.setY(MathH.clamp(pos.getY() + SPEED, 0, 9 - SIZE.getY())); // Moves the paddle down
			}
		}
	}
	
	public HitBox getHitBox() { // Returns a new HitBox using the paddle's current position & size
		float halfWidth = SIZE.getX() / 2;
		return new HitBox(pos.getX() + (isLeft ? halfWidth : 0), pos.getY(), halfWidth, SIZE.getY());
	}
	
	public boolean isUpDown() {
		return isUpDown;
	}
	
	public boolean isDownDown() {
		return isDownDown;
	}
}
