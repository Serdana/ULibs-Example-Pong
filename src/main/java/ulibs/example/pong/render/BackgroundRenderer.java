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
		// Creates a basic VertexArray with a single Quad
		bg.add(new Quad(0, 0, ClientBase.getDefaultWidth(), ClientBase.getDefaultHeight(), ZConstant.Z_BACKGROUND));
		bg.setup();
	}
	
	@Override
	public void renderPre() {
		Shaders.HudTextureless().bind();                    // Binds the shader
		Shaders.HudTextureless().setColor(Color.DARK_GRAY); // Sets the shader's color
		
		bg.drawOnce(); // Draws our VertexArray
		
		GLH.unbindShader(); // Unbinds the shader
	}
	
	@Override
	public void onResize() {
		
	}
}
