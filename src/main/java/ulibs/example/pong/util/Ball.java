package main.java.ulibs.example.pong.util;

import main.java.ulibs.common.helpers.MathH;
import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.engine.utils.HitBox;
import main.java.ulibs.engine.utils.IRunnable;
import main.java.ulibs.engine.utils.ITickable;
import main.java.ulibs.engine.utils.Timer.TimerType;
import main.java.ulibs.example.pong.Main;

public class Ball implements ITickable {
	public static final float SPEED = 0.25f;
	public static final Vec2f SIZE = new Vec2f(0.5f, 0.5f);
	public final Vec2f pos, moveDir;
	private boolean isDead = false;
	
	public Ball() {
		this.pos = new Vec2f(8, 4.5f);
		this.moveDir = new Vec2f(MathH.randomBoolean() ? -0.2f : 0.2f, MathH.randomBoolean() ? -0.2f : 0.2f);
	}
	
	@Override
	public void tick() {
		if (pos.getY() <= 0 || pos.getY() + SIZE.getY() >= 9) {
			moveDir.setY(-moveDir.getY());
		}
		
		if ((pos.getX() + SIZE.getX() <= 0 || pos.getX() >= 16) && !isDead) {
			Main.getMain().addTimer(new IRunnable() {
				@Override
				public void run() {
					if (pos.getX() < 0) {
						GameHandler.increaseRightScore();
					} else {
						GameHandler.increaseLeftScore();
					}
					
					pos.set(8, 4.5f);
					moveDir.set(MathH.randomBoolean() ? -0.2f : 0.2f, MathH.randomBoolean() ? -0.2f : 0.2f);
					isDead = false;
				}
			}, TimerType.second, 2);
			
			isDead = true;
			return;
		}
		
		pos.add(moveDir.copy().multiply(SPEED, SPEED));
		HitBox ballBox = new HitBox(pos.getX(), pos.getY(), SIZE.getX(), SIZE.getY());
		
		boolean isUpDown = false, isDownDown = false, isLeft = false;
		Vec2f paddlePos = null;
		if (ballBox.intersects(GameHandler.LEFT_PADDLE.hitBox)) {
			paddlePos = GameHandler.LEFT_PADDLE.pos;
			isUpDown = GameHandler.LEFT_PADDLE.isUpDown() && paddlePos.getY() != 0;
			isDownDown = GameHandler.LEFT_PADDLE.isDownDown() && paddlePos.getY() != 9f - Paddle.SIZE.getY();
			isLeft = true;
		} else if (ballBox.intersects(GameHandler.RIGHT_PADDLE.hitBox)) {
			paddlePos = GameHandler.RIGHT_PADDLE.pos;
			isUpDown = GameHandler.RIGHT_PADDLE.isUpDown() && paddlePos.getY() != 0;
			isDownDown = GameHandler.RIGHT_PADDLE.isDownDown() && paddlePos.getY() != 9f - Paddle.SIZE.getY();
		}
		
		if (paddlePos != null) {
			float angle = (float) (Math.PI / 4 * (Math.abs(paddlePos.getY() - pos.getY() - (SIZE.getY() / 2)) / Paddle.SIZE.getY()) - Math.PI / 8);
			float x = (float) Math.cos(angle) * SPEED, y = (float) Math.sin(angle) * SPEED;
			
			if (isUpDown ^ isDownDown) {
				y += isUpDown ? 0.2f : -0.2f;
				if ((isUpDown && y >= 0) || (y <= 0)) {
					y = -y;
				}
			}
			
			moveDir.set(isLeft ? x : -x, y);
		}
	}
}
