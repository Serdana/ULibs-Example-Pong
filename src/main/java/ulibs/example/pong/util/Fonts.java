package main.java.ulibs.example.pong.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import main.java.ulibs.common.helpers.MathH;
import main.java.ulibs.common.utils.Console;
import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.example.pong.render.gl.FontVertexArray;
import main.java.ulibs.gl.gl.QuadData;
import main.java.ulibs.gl.gl.Texture;
import main.java.ulibs.gl.gl.ZConstant;

public class Fonts {
	private static final Map<Font, GlyphWrapper> FONT_CHAR_MAP = new HashMap<Font, GlyphWrapper>();
	
	public static final Font FONT_16 = new Font("Font", Font.BOLD, 16);
	
	private static Font boundFont;
	
	public static void registerAll() {
		setupFont(FONT_16);
	}
	
	/** Used for {@link #getFontVertexArrayFromString} not rendering! */
	public static void bind(Font font) {
		boundFont = font;
	}
	
	public static QuadData getQuadDataFromString(String s, int x, int y, ZConstant z) {
		if (boundFont == null) {
			Console.print(WarningType.Error, "Font was not bound!");
			return null;
		}
		
		int realFontSize = boundFont.getSize();
		int charSpacing = MathH.floor(realFontSize / 8f);
		int cellSize = realFontSize * 2;
		float totalSize = cellSize * 16;
		
		GlyphWrapper glw = FONT_CHAR_MAP.get(boundFont);
		QuadData data = new QuadData();
		
		float curX = charSpacing;
		for (char c : s.toCharArray()) {
			if (c == ' ') {
				curX += realFontSize / 2f;
				continue;
			}
			
			Glyph gl = glw.map.get(c);
			float ymod = (c == '-' || c == '\'') ? -gl.h * 2 : (c == 'g' || c == 'j' || c == 'p' || c == 'q' || c == 'y') ? gl.h / 3.8f : 0;
			
			float[] vertices = QuadData.createVertex(x + curX, y - (gl.h) + (realFontSize) + ymod, z, gl.w, gl.h);
			
			curX += (gl.w) + charSpacing;
			
			float xx = (gl.x / totalSize), yy = -((gl.y + cellSize) / totalSize), ww = gl.w / totalSize, hh = (gl.h / totalSize);
			data.addAll(vertices, QuadData.DEFAULT_INDICES, new float[] { xx, yy + hh, xx, yy, xx + ww, yy, xx + ww, yy + hh });
		}
		
		return data;
	}
	
	public static FontVertexArray getFontVertexArrayFromString(String s, int x, int y, ZConstant z) {
		if (boundFont == null) {
			Console.print(WarningType.Error, "Font was not bound!");
			return null;
		}
		
		GlyphWrapper glw = FONT_CHAR_MAP.get(boundFont);
		FontVertexArray fva = new FontVertexArray(glw.tex).addAll(getQuadDataFromString(s, x, y, z));
		
		return fva;
	}
	
	private static void setupFont(Font f) {
		Map<Glyph, BufferedImage> imgs = new LinkedHashMap<Glyph, BufferedImage>();
		int fontSize = f.getSize();
		int cellSize = fontSize * 2;
		
		for (int i = 32; i < 256; i++) {
			if (i == 127) {
				continue;
			}
			char c = (char) i;
			
			BufferedImage image = new BufferedImage(fontSize * 2, fontSize * 2, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(f);
			g.setPaint(Color.WHITE);
			g.drawString(String.valueOf(c), 0, g.getFontMetrics().getAscent());
			g.dispose();
			
			BufferedImage img = trimImage(image);
			Glyph gl = new Glyph(c, 0, 0, img.getWidth(), img.getHeight());
			imgs.put(gl, img);
		}
		
		Iterator<Entry<Glyph, BufferedImage>> it = imgs.entrySet().iterator();
		BufferedImage fullImage = new BufferedImage(cellSize * 16, cellSize * 16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = fullImage.createGraphics();
		Map<Character, Glyph> map = new HashMap<Character, Glyph>();
		
		loop1:
		for (int y = 0; y < 16; y++) {
			for (int x = 0; x < 16; x++) {
				if (it.hasNext()) {
					Entry<Glyph, BufferedImage> pair = it.next();
					Glyph gl = pair.getKey();
					
					int xx = (x % cellSize) * cellSize, yy = y * cellSize;
					g.drawImage(pair.getValue(), xx, yy + (cellSize - gl.h), gl.w, gl.h, null);
					map.put(gl.c, gl.setXY(xx, yy));
				} else {
					break loop1;
				}
			}
		}
		
		g.dispose();
		
		FONT_CHAR_MAP.put(f, new GlyphWrapper(new Texture(fullImage), map));
	}
	
	public static float getWidth(Font font, String text) {
		GlyphWrapper g = FONT_CHAR_MAP.get(font);
		float length = 0;
		for (int i = 0; i < text.toCharArray().length; i++) {
			char c = text.toCharArray()[i];
			
			if (c == ' ') {
				length += font.getSize() / 2f;
			} else {
				length += g.map.get(c).w;
			}
			
			if (i < text.toCharArray().length - 1) {
				length += MathH.floor(font.getSize() / 8f);
			}
		}
		return length;
	}
	
	private static BufferedImage trimImage(BufferedImage image) {
		WritableRaster raster = image.getAlphaRaster();
		int width = raster.getWidth();
		int height = raster.getHeight();
		int left = 0;
		int top = 0;
		int right = width - 1;
		int bottom = height - 1;
		int minRight = width - 1;
		int minBottom = height - 1;
		
		top:
		for (; top < bottom; top++) {
			for (int x = 0; x < width; x++) {
				if (raster.getSample(x, top, 0) != 0) {
					minRight = x;
					minBottom = top;
					break top;
				}
			}
		}
		
		left:
		for (; left < minRight; left++) {
			for (int y = height - 1; y > top; y--) {
				if (raster.getSample(left, y, 0) != 0) {
					minBottom = y;
					break left;
				}
			}
		}
		
		bottom:
		for (; bottom > minBottom; bottom--) {
			for (int x = width - 1; x >= left; x--) {
				if (raster.getSample(x, bottom, 0) != 0) {
					minRight = x;
					break bottom;
				}
			}
		}
		
		right:
		for (; right > minRight; right--) {
			for (int y = bottom; y >= top; y--) {
				if (raster.getSample(right, y, 0) != 0) {
					break right;
				}
			}
		}
		
		return image.getSubimage(left, top, right - left + 1, bottom - top + 1);
	}
	
	public static Texture getFontTexture(Font font) {
		return FONT_CHAR_MAP.get(font).tex;
	}
	
	private static class GlyphWrapper {
		private final Texture tex;
		private final Map<Character, Glyph> map;
		
		private GlyphWrapper(Texture tex, Map<Character, Glyph> map) {
			this.tex = tex;
			this.map = map;
		}
	}
	
	private static class Glyph {
		private final int x, y, w, h;
		private final char c;
		
		private Glyph(char c, int x, int y, int w, int h) {
			this.c = c;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
		
		private Glyph setXY(int x, int y) {
			return new Glyph(c, x, y, w, h);
		}
	}
}
