package main.java.ulibs.example.pong.util;

import main.java.ulibs.example.pong.render.GameRenderer;

public class GameHandler {
	private static int leftScore, rightScore;
	
	public static final Ball BALL = new Ball();
	public static final Paddle LEFT_PADDLE = new Paddle(true);
	public static final Paddle RIGHT_PADDLE = new Paddle(false);
	
	public static void tick() {
		BALL.tick();
		LEFT_PADDLE.tick();
		RIGHT_PADDLE.tick();
	}
	
	public static void increaseLeftScore() {
		leftScore++;
		GameRenderer.redoScore();
	}
	
	public static void increaseRightScore() {
		rightScore++;
		GameRenderer.redoScore();
	}
	
	public static int getLeftScore() {
		return leftScore;
	}
	
	public static int getRightScore() {
		return rightScore;
	}
}
