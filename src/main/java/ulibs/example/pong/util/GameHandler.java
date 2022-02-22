package main.java.ulibs.example.pong.util;

import main.java.ulibs.example.pong.render.GameRenderer;

public class GameHandler {
	private static int leftScore, rightScore;
	
	public static final Ball BALL = new Ball(); // Creates our ball
	public static final Paddle LEFT_PADDLE = new Paddle(true); // Creates the left paddle
	public static final Paddle RIGHT_PADDLE = new Paddle(false); // Creates the right paddle
	
	public static void tick() { // Runs all of our object's tick methods
		BALL.tick();
		LEFT_PADDLE.tick();
		RIGHT_PADDLE.tick();
	}
	
	public static void increaseLeftScore() { // Increases the left player's score
		leftScore++;
		GameRenderer.redoScore();
	}
	
	public static void increaseRightScore() { // Increases the right player's score
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
