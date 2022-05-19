package main.java.ulibs.example.pong.render.gl;

import java.awt.Font;

import main.java.ulibs.engine.client.gl.Texture;
import main.java.ulibs.engine.client.gl.VertexArray;
import main.java.ulibs.engine.client.gl.ZConstant;
import main.java.ulibs.engine.client.helpers.GLH;
import main.java.ulibs.example.pong.util.Fonts;

public class FontVertexArray extends VertexArray {
	private final Texture texture;
	
	public FontVertexArray(Texture texture) {
		this.texture = texture;
	}
	
	public void setNewValues(Font f, String s, int x, int y, ZConstant z) {
		Fonts.bind(f);
		set(Fonts.getQuadDataFromString(s, x, y, z));
	}
	
	@Override
	public void bind() {
		texture.bind();
		super.bind();
	}
	
	@Override
	public void unbind() {
		GLH.unbindTexture();
		super.unbind();
	}
}
