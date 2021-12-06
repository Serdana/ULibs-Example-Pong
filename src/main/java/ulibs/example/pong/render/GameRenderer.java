package main.java.ulibs.example.pong.render;

import main.java.ulibs.common.helpers.MathH;
import main.java.ulibs.common.math.Vec2f;
import main.java.ulibs.engine.init.Shaders;
import main.java.ulibs.engine.render.IRenderer;
import main.java.ulibs.engine.utils.GetResource;
import main.java.ulibs.example.pong.Main;
import main.java.ulibs.example.pong.render.gl.FontVertexArray;
import main.java.ulibs.example.pong.util.Ball;
import main.java.ulibs.example.pong.util.Fonts;
import main.java.ulibs.example.pong.util.GameHandler;
import main.java.ulibs.example.pong.util.Paddle;
import main.java.ulibs.gl.gl.GLH;
import main.java.ulibs.gl.gl.QuadData;
import main.java.ulibs.gl.gl.Texture;
import main.java.ulibs.gl.gl.VertexArray;
import main.java.ulibs.gl.gl.ZConstant;
import main.java.ulibs.gl.math.Matrix4f;

public class GameRenderer implements IRenderer {
	private VertexArray ballVA = new VertexArray();
	private VertexArray leftPaddleVA = new VertexArray();
	private VertexArray rightPaddleVA = new VertexArray();
	private Texture ballTex;
	private Texture paddleTex;
	
	private static FontVertexArray scoreVA;
	
	@Override
	public void setupGL() {
		ballVA.addVerticesWithDefaults(QuadData.createVertex(new Vec2f(), ZConstant.Z_WORLD_ENTITY, Ball.SIZE));
		ballVA.setup();
		
		leftPaddleVA.addVerticesWithDefaults(QuadData.createVertex(new Vec2f(), ZConstant.Z_WORLD_ENTITY, Paddle.SIZE));
		leftPaddleVA.setup();
		
		rightPaddleVA.addVerticesWithDefaults(QuadData.createVertex(new Vec2f(), ZConstant.Z_WORLD_ENTITY, Paddle.SIZE));
		rightPaddleVA.setup();
		
		ballTex = new Texture(GetResource.getTexture("ball"), true);
		paddleTex = new Texture(GetResource.getTexture("paddle"));
		
		Fonts.bind(Fonts.FONT_16);
		scoreVA = Fonts.getFontVertexArrayFromString("0:0", MathH.floor(Main.getHudWidth() / 2 - Fonts.getWidth(Fonts.FONT_16, "0:0") / 2), 0, ZConstant.Z_HUD_DETAIL_0);
		scoreVA.setup();
	}
	
	@Override
	public void renderPre() {
		Shaders.MoveableObject().bind();
		Shaders.MoveableObject().setTransformMatrix(Matrix4f.newTranslate(GameHandler.BALL.pos));
		ballTex.bind();
		
		ballVA.drawOnce();
		
		paddleTex.bind();
		Shaders.MoveableObject().setTransformMatrix(Matrix4f.newTranslate(GameHandler.LEFT_PADDLE.pos));
		leftPaddleVA.drawOnce();
		Shaders.MoveableObject().setTransformMatrix(Matrix4f.newTranslate(GameHandler.RIGHT_PADDLE.pos));
		rightPaddleVA.drawOnce();
		
		Shaders.MoveableObject().setTransformMatrix(Matrix4f.identity());
		GLH.unbindTexture();
		Shaders.Hud().bind();
		
		scoreVA.drawOnce();
		
		GLH.unbindShader();
	}
	
	public static void redoScore() {
		String score = GameHandler.getLeftScore() + ":" + GameHandler.getRightScore();
		scoreVA.setNewValues(Fonts.FONT_16, score, MathH.floor(Main.getHudWidth() / 2 - Fonts.getWidth(Fonts.FONT_16, score) / 2), 0, ZConstant.Z_HUD_DETAIL_0);
		scoreVA.setup();
	}
	
	@Override
	public void onResize() {
		
	}
}
