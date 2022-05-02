package main.java.ulibs.example.pong.util;

import main.java.ulibs.common.helpers.MathH;
import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.engine.utils.HitBox;
import main.java.ulibs.engine.utils.ITickable;
import main.java.ulibs.engine.utils.Timer.TimerType;
import main.java.ulibs.example.pong.Main;

public class Ball implements ITickable {
	public static final float SPEED = 0.25f;
	public static final Vec2f SIZE = new Vec2f(0.5f, 0.5f);
	public final Vec2f pos, moveDir;
	private boolean isDead = false;
	
	public Ball() { // Sets up the ball's position/move direction
		this.pos = new Vec2f(8, 4.5f);
		this.moveDir = new Vec2f(MathH.randomBoolean() ? -0.2f : 0.2f, MathH.randomBoolean() ? -0.2f : 0.2f);
	}
	
	@Override
	public void tick() {
		if (pos.getY() <= 0 || pos.getY() + SIZE.getY() >= 9) { // Checks if the ball is at the top or bottom of the screen
			moveDir.setY(-moveDir.getY()); // Reversed the ball's Y direction
		}
		
		if ((pos.getX() + SIZE.getX() <= 0 || pos.getX() >= 16) && !isDead) { // Checks if the position is off screen & not dead
			Main.getMain().addTimer(new Runnable() { // If the ball is off screen create a timed event
				@Override
				public void run() {
					if (pos.getX() < 0) { // Check who failed to hit the ball and give the other player score
						GameHandler.increaseRightScore();
					} else {
						GameHandler.increaseLeftScore();
					}
					
					// Reset the ball's position & move direction
					pos.set(8, 4.5f);
					moveDir.set(MathH.randomBoolean() ? -0.2f : 0.2f, MathH.randomBoolean() ? -0.2f : 0.2f);
					isDead = false;
				}
			}, TimerType.second, 2, false);
			
			isDead = true;
			return;
		}
		
		pos.add(moveDir.copy().multiply(SPEED, SPEED)); // Move the ball
		HitBox ballBox = new HitBox(pos.getX(), pos.getY(), SIZE.getX(), SIZE.getY()); // Create the ball's HitBox
		
		boolean isUpDown = false, isDownDown = false, isLeft = false;
		Vec2f paddlePos = null;
		if (ballBox.intersects(GameHandler.LEFT_PADDLE.getHitBox())) { // Check if the ball intersects with the left paddle
			// Stores some data used to switch the ball's direction
			paddlePos = GameHandler.LEFT_PADDLE.pos;
			isUpDown = GameHandler.LEFT_PADDLE.isUpDown() && paddlePos.getY() != 0;
			isDownDown = GameHandler.LEFT_PADDLE.isDownDown() && paddlePos.getY() != 9f - Paddle.SIZE.getY();
			isLeft = true;
		} else if (ballBox.intersects(GameHandler.RIGHT_PADDLE.getHitBox())) { // Check if the ball intersects with the right paddle
			// Stores some data used to switch the ball's direction
			paddlePos = GameHandler.RIGHT_PADDLE.pos;
			isUpDown = GameHandler.RIGHT_PADDLE.isUpDown() && paddlePos.getY() != 0;
			isDownDown = GameHandler.RIGHT_PADDLE.isDownDown() && paddlePos.getY() != 9f - Paddle.SIZE.getY();
		}
		
		if (paddlePos != null) { // If we hit a paddle
			float angle = (float) (Math.PI / 4 * (Math.abs(paddlePos.getY() - pos.getY() - (SIZE.getY() / 2)) / Paddle.SIZE.getY()) - Math.PI / 8);
			float x = (float) Math.cos(angle) * SPEED, y = (float) Math.sin(angle) * SPEED;
			
			if (isUpDown ^ isDownDown) { // If the paddle is traveling up or down
				// Add to our Y speed if we're moving
				y += isUpDown ? 0.2f : -0.2f;
				if (isUpDown && y >= 0 || y <= 0) {
					y = -y;
				}
			}
			
			// Finally set our move direction
			moveDir.set(isLeft ? x : -x, y);
		}
	}
}
