package main.java.ulibs.example.pong.render;

import java.awt.Color;

import main.java.ulibs.engine.ClientBase;
import main.java.ulibs.engine.init.Shaders;
import main.java.ulibs.engine.render.IRenderer;
import main.java.ulibs.gl.gl.GLH;
import main.java.ulibs.gl.gl.QuadData;
import main.java.ulibs.gl.gl.VertexArray;
import main.java.ulibs.gl.gl.ZConstant;

public class BackgroundRenderer implements IRenderer {
	private VertexArray bg = new VertexArray();
	
	@Override
	public void setupGL() {
		bg.addVerticesWithDefaults(QuadData.createVertex(0, 0, ZConstant.Z_BACKGROUND, ClientBase.getHudWidth(), ClientBase.getHudHeight()));
		bg.setup();
	}
	
	@Override
	public void renderPre() {
		Shaders.HudTextureless().bind();
		Shaders.HudTextureless().setColor(Color.DARK_GRAY);
		
		bg.drawOnce();
		
		GLH.unbindTexture();
		GLH.unbindShader();
	}
}
