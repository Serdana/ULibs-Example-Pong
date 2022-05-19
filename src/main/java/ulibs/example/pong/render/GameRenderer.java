package main.java.ulibs.example.pong.render;

import main.java.ulibs.common.helpers.MathH;
import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.engine.client.gl.Texture;
import main.java.ulibs.engine.client.gl.VertexArray;
import main.java.ulibs.engine.client.gl.ZConstant;
import main.java.ulibs.engine.client.gl.geometry.Quad;
import main.java.ulibs.engine.client.helpers.GLH;
import main.java.ulibs.engine.client.init.Shaders;
import main.java.ulibs.engine.client.math.Matrix4f;
import main.java.ulibs.engine.client.render.IRenderer;
import main.java.ulibs.engine.client.utils.GetResource;
import main.java.ulibs.example.pong.Main;
import main.java.ulibs.example.pong.render.gl.FontVertexArray;
import main.java.ulibs.example.pong.util.Ball;
import main.java.ulibs.example.pong.util.Fonts;
import main.java.ulibs.example.pong.util.GameHandler;
import main.java.ulibs.example.pong.util.Paddle;

public class GameRenderer implements IRenderer {
	private VertexArray ballVA = new VertexArray();
	private VertexArray leftPaddleVA = new VertexArray();
	private VertexArray rightPaddleVA = new VertexArray();
	private Texture ballTex;
	private Texture paddleTex;
	
	private static FontVertexArray scoreVA;
	
	@Override
	public void setupGL() {
		// Creates a VertexArray for our ball
		ballVA.add(new Quad(new Vec2f(), Ball.SIZE, ZConstant.Z_WORLD_ENTITY));
		ballVA.setup();
		
		// Creates a VertexArray for the left paddle
		leftPaddleVA.add(new Quad(new Vec2f(), Paddle.SIZE, ZConstant.Z_WORLD_ENTITY));
		leftPaddleVA.setup();
		
		// Creates a VertexArray for the right paddle
		rightPaddleVA.add(new Quad(new Vec2f(), Paddle.SIZE, ZConstant.Z_WORLD_ENTITY));
		rightPaddleVA.setup();
		
		// Loads our textures
		ballTex = new Texture(GetResource.getTexture("ball"), true);
		paddleTex = new Texture(GetResource.getTexture("paddle"));
		
		Fonts.bind(Fonts.FONT_16);
		scoreVA = Fonts.getFontVertexArrayFromString("0:0", MathH.floor(Main.getDefaultWidth() / 2 - Fonts.getWidth(Fonts.FONT_16, "0:0") / 2), 0, ZConstant.Z_HUD_DETAIL_0);
		scoreVA.setup();
	}
	
	@Override
	public void renderPre() {
		// Bind our shader then set it's transform matrix to our ball's position
		Shaders.Object().bind();
		Shaders.Object().setTransformMatrix(Matrix4f.newTranslate(GameHandler.BALL.pos));
		ballTex.bind(); // Bind our ball's texture
		
		ballVA.drawOnce(); // Draw our ball
		
		paddleTex.bind(); // Bind our paddle's texture
		Shaders.Object().setTransformMatrix(Matrix4f.newTranslate(GameHandler.LEFT_PADDLE.pos)); // Set the shader's transform matrix to the left paddle's position
		leftPaddleVA.drawOnce(); // Draw our left paddle
		Shaders.Object().setTransformMatrix(Matrix4f.newTranslate(GameHandler.RIGHT_PADDLE.pos)); // Set the shader's transform matrix to the right paddle's position
		rightPaddleVA.drawOnce(); // Draw our right paddle
		
		Shaders.Object().setTransformMatrix(Matrix4f.identity()); // Reset our shader's transform matrix
		GLH.unbindTexture(); // Unbinds the current texture
		Shaders.Hud().bind(); // Binds a different shader
		
		scoreVA.drawOnce(); // Draws the score
		
		GLH.unbindShader(); // Unbinds the current shader
	}
	
	public static void redoScore() {
		String score = GameHandler.getLeftScore() + ":" + GameHandler.getRightScore();
		scoreVA.setNewValues(Fonts.FONT_16, score, MathH.floor(Main.getDefaultWidth() / 2 - Fonts.getWidth(Fonts.FONT_16, score) / 2), 0, ZConstant.Z_HUD_DETAIL_0);
		scoreVA.setup();
	}
	
	@Override
	public void onResize() {
		
	}
}
