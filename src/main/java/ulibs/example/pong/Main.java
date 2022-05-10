package main.java.ulibs.example.pong;

import main.java.ulibs.common.utils.Console.WarningType;
import main.java.ulibs.engine.ClientBase;
import main.java.ulibs.engine.client.ResizeHandler;
import main.java.ulibs.engine.client.ResizeHandlerScale;
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
		main = new Main(); // Creates & stores a new instance of ourselves
	}
	
	protected Main() {
		//@formatter:off
		super(  "Pong",                // Display name
				"ulibs/example/pong",  // Internal title
				960, 540,              // Width/Height
				false,                 // Whether or not our program is in debug mode. Doesn't do anything by itself
				3,                     // Amount of Log files to use
				new WarningType[0],    // An array of WarningType's to disable
				null);                 // A Supplier<List> with any shaders we want to setup & use
		//@formatter:on
	}
	
	@Override
	protected void preInit() {
		Fonts.registerAll();
	}
	
	@Override
	protected void init() {
		// Adds the various renderers our game will use
		addRenderer(new BackgroundRenderer());
		addRenderer(new GameRenderer());
		addRenderer(new DebugHud());
	}
	
	@Override
	protected void postInit() {
		
	}
	
	@Override
	protected void tick() {
		GameHandler.tick(); // Runs all of our game's logic
	}
	
	@Override
	protected void onMouseMoved() {
		
	}
	
	@Override
	protected IInputHandler<EnumKeyInput> setKeyHandler() {
		return new KeyHandler(); // Returns a class that'll handle all of our key input
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
	protected ResizeHandler setResizeHandler() {
		return new ResizeHandlerScale(); // Returns a default ResizeHandler
	}
	
	public static Main getMain() {
		return main; // Returns the instance of ourselves we created earlier
	}
}
