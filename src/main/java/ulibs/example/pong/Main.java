package main.java.ulibs.example.pong;

import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.ClientBase;
import main.java.ulibs.engine.input.EnumKeyInput;
import main.java.ulibs.engine.input.EnumMouseInput;
import main.java.ulibs.engine.input.IInputHandler;
import main.java.ulibs.engine.input.IScrollHandler;
import main.java.ulibs.example.pong.render.BackgroundRenderer;
import main.java.ulibs.example.pong.render.DebugHud;
import main.java.ulibs.example.pong.render.GameRenderer;
import main.java.ulibs.example.pong.util.Fonts;
import main.java.ulibs.example.pong.util.GameHandler;
import main.java.ulibs.example.pong.util.KeyHandler;

public class Main extends ClientBase {
	public static Main main;
	
	public static void main(String[] args) {
		main = new Main();
	}
	
	protected Main() {
		super("Pong", "ulibs/example/pong", 960, 540, false, 3, new WarningType[0], null);
	}
	
	@Override
	protected void preInit() {
		Fonts.registerAll();
	}
	
	@Override
	protected void init() {
		addRenderer(new BackgroundRenderer());
		addRenderer(new GameRenderer());
		addRenderer(new DebugHud());
	}
	
	@Override
	protected void postInit() {
		
	}
	
	@Override
	protected void tick() {
		GameHandler.tick();
	}
	
	@Override
	protected IInputHandler<EnumKeyInput> setKeyHandler() {
		return new KeyHandler();
	}
	
	@Override
	protected IInputHandler<EnumMouseInput> setMouseHandler() {
		return null;
	}
	
	@Override
	protected IScrollHandler setScrollHandler() {
		return null;
	}
	
	@Override
	protected EnumViewportResizeType getViewportResizeType() {
		return EnumViewportResizeType.scale;
	}
	
	public static Main getMain() {
		return main;
	}
}