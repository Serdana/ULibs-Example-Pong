package main.java.ulibs.example.pong.render;

import java.awt.Color;

import main.java.ulibs.engine.init.Shaders;
import main.java.ulibs.engine.render.IRenderer;
import main.java.ulibs.example.pong.Main;
import main.java.ulibs.example.pong.render.gl.FontVertexArray;
import main.java.ulibs.example.pong.util.Fonts;
import main.java.ulibs.gl.gl.GLH;
import main.java.ulibs.gl.gl.ZConstant;

public class DebugHud implements IRenderer {
	private FontVertexArray fpsVA;
	
	@Override
	public void setupGL() {
		Fonts.bind(Fonts.FONT_16);
		fpsVA = Fonts.getFontVertexArrayFromString("FPS: " + Main.getFPS(), 0, 0, ZConstant.Z_HUD_TEXT);
		fpsVA.setup();
	}
	
	@Override
	public void renderPre() {
		Shaders.Hud().bind();
		Shaders.Hud().setColor(Color.RED);
		
		fpsVA.setNewValues(Fonts.FONT_16, "FPS: " + Main.getFPS(), 0, 0, ZConstant.Z_HUD_TEXT);
		fpsVA.setup();
		
		fpsVA.bind();
		fpsVA.draw();
		fpsVA.unbind();
		
		Shaders.Hud().setColor(Color.WHITE);
		GLH.unbindShader();
	}
}