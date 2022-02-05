package main.java.ulibs.example.pong.render;

import java.awt.Color;

import main.java.ulibs.engine.ClientBase;
import main.java.ulibs.engine.init.Shaders;
import main.java.ulibs.engine.render.IRenderer;
import main.java.ulibs.gl.gl.GLH;
import main.java.ulibs.gl.gl.VertexArray;
import main.java.ulibs.gl.gl.ZConstant;
import main.java.ulibs.gl.gl.geometry.Quad;

public class BackgroundRenderer implements IRenderer {
	private VertexArray bg = new VertexArray();
	
	@Override
	public void setupGL() {
		bg.add(new Quad(0, 0, ClientBase.getDefaultWidth(), ClientBase.getDefaultHeight(), ZConstant.Z_BACKGROUND));
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
	
	@Override
	public void onResize() {
		
	}
}
